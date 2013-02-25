package eu.europa.ec.eci.oct.export;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.ObjectMessage;
import javax.jms.Queue;
import javax.jms.Session;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import eu.europa.ec.eci.oct.business.api.ExporterService;
import eu.europa.ec.eci.oct.business.api.InitiativeService;
import eu.europa.ec.eci.oct.business.api.OCTException;
import eu.europa.ec.eci.oct.business.api.SignatureService;
import eu.europa.ec.eci.oct.business.api.SystemManager;
import eu.europa.ec.eci.oct.entities.CountryProperty;
import eu.europa.ec.eci.oct.entities.admin.Contact;
import eu.europa.ec.eci.oct.entities.admin.InitiativeDescription;
import eu.europa.ec.eci.oct.entities.admin.SystemPreferences;
import eu.europa.ec.eci.oct.vo.export.ExportMessage;
import eu.europa.ec.eci.oct.vo.export.ExportParametersBean;
import eu.europa.ec.eci.oct.vo.export.InitiativeDetails;
import eu.europa.ec.eci.oct.vo.export.PropertyValueBean;

/**
 * <p>
 * Concrete implementation of the {@link ExporterService}. This is implemented as a stateless EJB.<br />
 * How does the actual exporting process work? It uses two queues for concurrency. The first queue is used only as a
 * dispatcher for the second queue which does the actual export (the XML file creation). Some cache objects are being
 * passed from one queue to another in order to boost performance.
 * </p>
 * First queue: OctExportDispatcherQueue - populated with beans for each separate country to export data from; role: to
 * dispatch work to the second queue<br/>
 * Second queue: OctExportQueue - populated with bean representing chunks of information to be exported; role: to obtain
 * DB data and marshall it to XML files
 * 
 * @author chiridl
 * 
 */
@Stateless
@Local({ ExporterService.class })
public class ExporterServiceImpl implements ExporterService {

	private final Logger logger = Logger.getLogger(ExporterServiceImpl.class);

	@Resource(mappedName = "OctExportQueueConnectionFactory")
	private ConnectionFactory connectionFactory;

	@Resource(mappedName = "OctExportDispatcherQueue")
	private Queue queue;

	@EJB
	private SignatureService sigService;

	@EJB
	private InitiativeService initiativeService;

	@EJB
	private SystemManager systemManager;

	@Override
	public void export(List<ExportParametersBean> parametersList) {
		Connection connection = null;
		try {
			// create necessary JEE resources
			connection = connectionFactory.createConnection();
			final Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
			final MessageProducer messageProducer = session.createProducer(queue);

			// this is going to be used to optimize DB calls (instead of always joining with the Property table, we can
			// just query this once)
			final Map<Long, PropertyValueBean> propertyCache = preparePropertyCache();

			// create initiative details; we need these in the header of the exported file
			final InitiativeDetails initiative = new InitiativeDetails();

			// populate contact information into the initiative
			final Contact contact = initiativeService.getContact();
			if (contact != null) {
				initiative.setContactPersonsList(contact.getName() + " (" + contact.getEmail() + ")");
				initiative.setOrganizerList(contact.getOrganizers());
			}
			// also add system preferences to the initiative details
			final SystemPreferences preferences = systemManager.getSystemPreferences();
			if (preferences != null) {
				initiative.setRegistrationDate(preferences.getRegistrationDate());
				initiative.setRegistrationNumber(preferences.getRegistrationNumber());
				initiative.setUrlOnCommissionRegister(preferences.getCommissionRegisterUrl());
			}

			// cache for storing description information about initiatives
			final Map<String, InitiativeDescription> descriptionCache = new HashMap<String, InitiativeDescription>();

			// this is going to be used for folder creation and timestamping
			final Date now = new Date();

			// traverse all the parameter beans
			for (final ExportParametersBean exportParametersBean : parametersList) {
				final String languageCode = exportParametersBean.getDescriptionLanguageCode();

				// query description and store it to cache
				InitiativeDescription description = descriptionCache.get(languageCode);
				if (description == null) {
					description = initiativeService.getDescriptionByLang(systemManager
							.getLanguageByCode(exportParametersBean.getDescriptionLanguageCode()));
					descriptionCache.put(languageCode, description);
				}
				if (description != null) {
					initiative.setMainObjectives(description.getObjectives());
					initiative.setSubjectMatter(description.getSubjectMatter());
					initiative.setTitle(description.getTitle());
					initiative.setUrl(description.getUrl());
				}

				// populate transport object
				final ExportMessage message = new ExportMessage();
				message.setInitiativeDetails(initiative);
				message.setPropertyCache(propertyCache);
				message.setStartDate(now);
				message.setExportParameters(exportParametersBean);

				// this is the actual object to be processed by the associated worker (MDB)
				final ObjectMessage msg = session.createObjectMessage();
				msg.setObject(message);
				messageProducer.send(msg);

				if (logger.isEnabledFor(Level.DEBUG)) {
					logger.debug("Added message to queue for processing. Parameters sent: "
							+ exportParametersBean.toString());
				}
			}
		} catch (JMSException e) {
			logger.error("Error while preparing export queue!", e);
		} catch (OCTException e) {
			logger.error("OCT Exception while preparing export queue!", e);
		} finally {
			try {
				if (connection != null) {
					if (logger.isEnabledFor(Level.DEBUG)) {
						logger.debug("Closing JMS connection.");
					}
					connection.close();
				}
			} catch (Exception e) {
			}
		}
	}

	/**
	 * Calls BL to query the database in order to retrieve all the available country properties. These are stored in a
	 * cache in order to avoid unneeded loading of object graphs.
	 * 
	 * @return
	 * @throws OCTException
	 */
	private Map<Long, PropertyValueBean> preparePropertyCache() throws OCTException {
		Map<Long, PropertyValueBean> propertyCache = new HashMap<Long, PropertyValueBean>();

		List<CountryProperty> properties = sigService.getAllCountryProperties();
		for (CountryProperty countryProperty : properties) {
			propertyCache.put(countryProperty.getId(), new PropertyValueBean(countryProperty.getProperty().getName(),
					countryProperty.getProperty().getGroup().getName()));
		}

		return propertyCache;
	}

}
