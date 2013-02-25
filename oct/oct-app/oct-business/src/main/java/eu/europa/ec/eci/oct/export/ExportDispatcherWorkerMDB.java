package eu.europa.ec.eci.oct.export;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.UUID;

import javax.annotation.Resource;
import javax.ejb.ActivationConfigProperty;
import javax.ejb.EJB;
import javax.ejb.MessageDriven;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.MessageProducer;
import javax.jms.ObjectMessage;
import javax.jms.Queue;
import javax.jms.Session;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import eu.europa.ec.eci.oct.business.api.OCTException;
import eu.europa.ec.eci.oct.business.api.SignatureService;
import eu.europa.ec.eci.oct.business.api.SystemManager;
import eu.europa.ec.eci.oct.entities.signature.SignatureCountPerCountry;
import eu.europa.ec.eci.oct.vo.export.ExportMessage;
import eu.europa.ec.eci.oct.vo.export.ExportParametersBean;

/**
 * First queue worker. This acts as a dispatcher. it takes an {@link ExportMessage}, queries the database to find how
 * many actual signatures need to be exported and chunks this in order to increase performance. Each chunk is then fed
 * to the OctExportQueue which processes it.
 * 
 * @author chiridl
 * 
 */
@MessageDriven(activationConfig = { @ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Queue") }, mappedName = "OctExportDispatcherQueue")
public class ExportDispatcherWorkerMDB implements MessageListener {

	private final Logger logger = Logger.getLogger(ExportDispatcherWorkerMDB.class);

	@Resource(mappedName = "OctExportQueueConnectionFactory")
	private ConnectionFactory connectionFactory;

	@Resource(mappedName = "OctExportQueue")
	private Queue queue;

	@EJB
	private SignatureService sigService;

	@EJB
	private SystemManager systemManager;

	/**
	 * The number of signatures per worker. This should probably be obtained from some persistent configurable source.
	 */
	private static final int SIGNATURES_PER_WORKER = 5000;

	@Override
	public void onMessage(Message message) {
		if (!(message instanceof ObjectMessage)) {
			logger.error("Received an unsuported message type (" + message.getClass().getCanonicalName()
					+ "). Ignoring it...");
			return;
		}

		try {
			Object payloadObject = ((ObjectMessage) message).getObject();
			if (!(payloadObject instanceof ExportMessage)) {
				logger.error("Received an unsuported payload message (" + payloadObject.getClass().getCanonicalName()
						+ "). Ignoring it...");
				return;
			}

			final ExportMessage exportMessage = (ExportMessage) payloadObject;
			final ExportParametersBean parameters = exportMessage.getExportParameters();

			final Connection connection = connectionFactory.createConnection();
			final Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
			final MessageProducer messageProducer = session.createProducer(queue);

			// this is the total number of signatures which need to be handled by this worker
			final int count = getTotalSignatures(sigService.getSignatureCounts(parameters));
			if (logger.isEnabledFor(Level.DEBUG)) {
				logger.debug("========================= Total signatures to process: " + count + " for parameters "
						+ parameters);
			}
			// if the above number is too great, we have to split it into chunks
			final int workerCount = count / SIGNATURES_PER_WORKER + (count % SIGNATURES_PER_WORKER > 0 ? 1 : 0);
			if (logger.isEnabledFor(Level.DEBUG)) {
				logger.debug("We need to spawn " + workerCount + " workers.");
			}

			// the root of the file system; this is the location where the export will take place
			String fileSystemRoot = systemManager.getSystemPreferences().getFileStoragePath();
			if (null == fileSystemRoot || "".equals(fileSystemRoot)) {
				fileSystemRoot = "";
			}

			// lat's calculate a directory name (which we will append to the above root) - this should be unique
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd_H-m-s-S");
			String now = sdf.format(exportMessage.getStartDate());
			final String filePath = new StringBuilder().append(fileSystemRoot).append("export")
					.append(File.separatorChar).append(now).append(File.separatorChar)
					.append(parameters.getCountryCode().toUpperCase()).append(File.separatorChar)
					.append(parameters.getDescriptionLanguageCode()).append(File.separatorChar).toString();

			// for each worker...
			for (int idx = 0; idx < workerCount; idx++) {
				exportMessage.setExportPath(filePath);

				// calculate the file name where this chunk should be exported
				final String index = String.format("%03d", (idx + 1));
				final String fileName = new StringBuilder().append(index).append("_")
						.append(parameters.getCountryCode().toUpperCase()).append("_")
						.append(parameters.getDescriptionLanguageCode()).append("_")
						.append(UUID.randomUUID().toString()).append(".xml").toString();
				exportMessage.setExportFileName(fileName);

				// start and offset (is signatures are inserted in the mean time, this will produce some phantoms)
				parameters.setStart(idx * SIGNATURES_PER_WORKER);
				parameters.setOffset(SIGNATURES_PER_WORKER);
				exportMessage.setExportParameters(parameters);

				// put the object to the associated queue for processing
				final ObjectMessage msg = session.createObjectMessage();
				msg.setObject(exportMessage);
				messageProducer.send(msg);

				if (logger.isEnabledFor(Level.DEBUG)) {
					logger.debug("Added message to queue for processing. Parameters sent: " + exportMessage.toString());
				}
			}
		} catch (JMSException e) {
			logger.error("Error while processing queued object!", e);
		} catch (OCTException e) {
			logger.error("OCT Exception!", e);
		}
	}

	private int getTotalSignatures(List<SignatureCountPerCountry> counts) {
		int result = 0;
		for (SignatureCountPerCountry signatureCountPerCountry : counts) {
			result += signatureCountPerCountry.getCount();
		}
		return result;
	}

}
