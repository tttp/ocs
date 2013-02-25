package eu.europa.ec.eci.oct.export;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.EJB;
import javax.ejb.MessageDriven;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import eu.europa.ec.eci.export.DataConverter;
import eu.europa.ec.eci.export.DataException;
import eu.europa.ec.eci.export.JAXBHelper;
import eu.europa.ec.eci.export.model.InitiativeDataType;
import eu.europa.ec.eci.export.model.SupportForm;
import eu.europa.ec.eci.oct.business.api.OCTException;
import eu.europa.ec.eci.oct.business.api.SignatureService;
import eu.europa.ec.eci.oct.vo.export.ExportMessage;
import eu.europa.ec.eci.oct.vo.export.ExportParametersBean;
import eu.europa.ec.eci.oct.vo.export.ExportPropertyBean;
import eu.europa.ec.eci.oct.vo.export.ExportSignatureBean;
import eu.europa.ec.eci.oct.vo.export.PropertyValueBean;

/**
 * Second queue worker. This handles the actual file creation. JAXB is used to marshall database content to XML.
 * 
 * @author chiridl
 * 
 */
@MessageDriven(activationConfig = { @ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Queue") }, mappedName = "OctExportQueue")
public class ExportWorkerMDB implements MessageListener {

	private final Logger logger = Logger.getLogger(ExportDispatcherWorkerMDB.class);

	@EJB
	private SignatureService sigService;

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

			// only for debugging purposes...
			final long time = System.currentTimeMillis();

			final ExportMessage exportMessage = (ExportMessage) payloadObject;
			final Map<Long, PropertyValueBean> propertyCache = exportMessage.getPropertyCache();
			final ExportParametersBean parameters = exportMessage.getExportParameters();

			if (logger.isEnabledFor(Level.DEBUG)) {
				logger.debug("========================= Processing queued object: " + parameters);
			}

			// get all the signatures we need to export
			final Map<Long, ExportSignatureBean> signatures = sigService.getSignaturesForExport(parameters);
			if (logger.isEnabledFor(Level.DEBUG)) {
				logger.debug("--------------->>>>>>>>>>>>>>>> processing " + signatures.size() + " signatures...");
			}

			// also get all the properties for the given signatures
			final List<ExportPropertyBean> properties = sigService.getPropertiesForSignatures(new ArrayList<Long>(
					signatures.keySet()));
			if (logger.isEnabledFor(Level.DEBUG)) {
				logger.debug("--------------->>>>>>>>>>>>>>>> processing " + properties.size() + " properties...");
			}
			for (final ExportPropertyBean property : properties) {
				final long signatureId = property.getSignatureId();
				final ExportSignatureBean signature = signatures.get(signatureId);
				final PropertyValueBean propertyBean = propertyCache.get(property.getPropertyId());

				// we also need to know about groups (properties always belong to groups)
				final String groupName = propertyBean.getGroupName();
				if (signature.getGroups().get(groupName) == null) {
					signature.getGroups().put(groupName, new HashMap<String, String>());
				}
				signature.getGroups().get(groupName).put(propertyBean.getName(), property.getValue());
			}

			// initialize JAXB root bean
			final SupportForm supportForm = new SupportForm();
			supportForm.setForCountry(parameters.getCountryCode());
			supportForm.setForLanguage(parameters.getDescriptionLanguageCode());
			DataConverter.getInstance().attachSignatures(supportForm, signatures.values());

			final InitiativeDataType initiativeData = DataConverter.getInstance().convertInitiativeDetails(
					exportMessage.getInitiativeDetails());
			supportForm.setInitiativeData(initiativeData);

			// marshall!
			try {
				// create needed directories
				final String filePath = exportMessage.getExportPath();
				boolean dirCreated = new File(filePath).mkdirs();
				if (logger.isDebugEnabled()) {
					logger.debug("Export directory \"" + filePath + "\" created? => " + dirCreated);
				}

				// create writer
				final String fileName = exportMessage.getExportFileName();
				final OutputStreamWriter outFile = new OutputStreamWriter(new FileOutputStream(filePath + fileName),
						"UTF-8");
				JAXBHelper.getInstance().marshall(supportForm, outFile);
			} catch (IOException e) {
				logger.error("Error writing file.", e);
			} catch (DataException e) {
				logger.error("Error writing file.", e);
			}

			if (logger.isEnabledFor(Level.INFO)) {
				logger.info("===============================================================");
				logger.info("=========> Worker execution took: " + (System.currentTimeMillis() - time)
						+ " miliseconds.");
				logger.info("===============================================================");
			}
		} catch (JMSException e) {
			logger.error("Error while processing queued object!", e);
		} catch (OCTException e) {
			logger.error("OCT Exception!", e);
		}
	}
}
