package eu.europa.ec.eci.oct.business;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.Stateless;

import org.apache.log4j.Logger;

import eu.europa.ec.eci.oct.business.api.InitiativeService;
import eu.europa.ec.eci.oct.business.api.OCTException;
import eu.europa.ec.eci.oct.business.api.SignatureService;
import eu.europa.ec.eci.oct.business.api.system.SystemStateChecker;
import eu.europa.ec.eci.oct.business.system.DBSystemStateProvider;
import eu.europa.ec.eci.oct.entities.admin.Contact;
import eu.europa.ec.eci.oct.entities.admin.InitiativeDescription;
import eu.europa.ec.eci.oct.entities.admin.SystemPreferences;
import eu.europa.ec.eci.oct.entities.admin.SystemState;
import eu.europa.ec.eci.oct.entities.member.Language;
import eu.europa.ec.eci.oct.persistence.DAOFactory;
import eu.europa.ec.eci.oct.persistence.InitiativeDAO;
import eu.europa.ec.eci.oct.persistence.PersistenceException;
import eu.europa.ec.eci.oct.persistence.SystemPreferencesDAO;

@Stateless
@Local(InitiativeService.class)
public class InitiativeServiceBean implements InitiativeService {

	private Logger logger = Logger.getLogger(InitiativeServiceBean.class);

	@EJB
	private DAOFactory daof;
	
	@EJB
	private SignatureService signatureSrv;

	@Override
	public InitiativeDescription insertInitiativeDescription(InitiativeDescription id) throws OCTException {
		try {
			InitiativeDAO idao = daof.getInitiativeDAO();
			InitiativeDescription currentId = idao.getDescriptionByLanguage(id.getLanguage());
			if (currentId == null) {
				currentId = new InitiativeDescription(); // TODO ?
				currentId.setLanguage(id.getLanguage());
			}
			currentId.setTitle(id.getTitle());
			currentId.setSubjectMatter(id.getSubjectMatter());
			currentId.setObjectives(id.getObjectives());
			currentId.setUrl(id.getUrl());

			idao.insertInitiativeDescription(currentId);
			return currentId;
		} catch (PersistenceException e) {
			logger.error("persistence problem while adding initiative description", e);
			throw new OCTException("persistence problem while setting default description language", e);
		}
	}

	@Override
	public void setDefaultDescription(InitiativeDescription id) throws OCTException {

		SystemStateChecker.getController(new DBSystemStateProvider(daof)).disallowedStates(SystemState.OPERATIONAL);

		try {
			daof.getSystemPreferencesDAO().setDefaultDescription(id);
		} catch (PersistenceException e) {
			logger.error("persistence problem while setting default description language", e);
			throw new OCTException("persistence problem while setting default description language", e);
		}

	}

	@Override
	public List<InitiativeDescription> getDescriptions() throws OCTException {
		try {
			// let's obtain entire list of descriptions
			List<InitiativeDescription> lid = daof.getInitiativeDAO().getAllDescriptions();
			for (InitiativeDescription description : lid) {
				//force eager loading
				description.getLanguage();
			}
			return lid;

		} catch (PersistenceException e) {
			logger.error("persistence problem while fetching all descriptions", e);
			throw new OCTException("persistence problem while fetching all descriptions", e);
		}
	}

	@Override
	public List<InitiativeDescription> getDescriptionsExcludeDefault() throws OCTException {
		try {
			// let's obtain entire list of descriptions
			List<InitiativeDescription> lid = daof.getInitiativeDAO().getAllDescriptions();

			// get default description
			InitiativeDescription systemDefaultDesc = daof.getSystemPreferencesDAO().getPreferences()
					.getDefaultDescription();

			// identify default in the list
			InitiativeDescription tmpDefDesc = null;
			for (InitiativeDescription id : lid) {
				if (systemDefaultDesc != null && id.getId().equals(systemDefaultDesc.getId())) {
					tmpDefDesc = id;
					break;
				}
			}

			// remove default if found
			if (tmpDefDesc != null) {
				lid.remove(tmpDefDesc);
			} else {
				logger.error("default description not found. data inconsistency.");
				throw new OCTException("default description not found. data inconsistency.");
			}

			return lid;
		} catch (PersistenceException e) {
			logger.error("persistence problem while fetching descriptions", e);
			throw new OCTException("persistence problem while fetching descriptions", e);
		}
	}

	@Override
	public InitiativeDescription getDescriptionById(long id) throws OCTException {
		try {
			InitiativeDescription desription = daof.getInitiativeDAO().getDescriptionById(id);
			return desription;
		} catch (PersistenceException e) {
			logger.error("persistence problem while fetching description", e);
			throw new OCTException("persistence problem while fetching description", e);
		}
	}

	@Override
	public InitiativeDescription getDescriptionByLang(Language lang) throws OCTException {

		try {
			InitiativeDescription id = daof.getInitiativeDAO().getDescriptionByLanguage(lang);
			return id;
		} catch (PersistenceException e) {
			logger.error("persistence problem while fetching description", e);
			throw new OCTException("persistence problem while fetching description", e);
		}
	}

	@Override
	public InitiativeDescription getDefaultDescription() throws OCTException {

		try {
			SystemPreferences prefs = daof.getSystemPreferencesDAO().getPreferences();
			InitiativeDescription id = prefs.getDefaultDescription();
			if (id == null) {
				logger.error("missing default initiative description");
				throw new OCTException("missing default initiative description");
			}
			return id;
		} catch (PersistenceException e) {
			logger.error("persistence problem while fetching description", e);
			throw new OCTException("persistence problem while fetching description", e);
		}
	}

	@Override
	public List<Language> getUnusedDescriptionLanguages() throws OCTException {
		List<Language> result = new ArrayList<Language>();
		try {
			List<Language> allLanguages = daof.getCountryDAO().getLanguages();
			List<InitiativeDescription> descriptions = daof.getInitiativeDAO().getAllDescriptions();

			for (Language language : allLanguages) {
				boolean used = false;
				for (InitiativeDescription description : descriptions) {
					if (language.equals(description.getLanguage())) {
						used = true;
						break;
					}
				}

				if (!used) {
					result.add(language);
				}
			}
		} catch (PersistenceException e) {
			logger.error("persistence problem while retrieving used languages", e);
			throw new OCTException("persistence problem while retrieving used languages", e);
		}

		return result;
	}

	@Override
	public void removeAllDescriptions() throws OCTException {
		try {

			SystemPreferencesDAO sfdao = daof.getSystemPreferencesDAO();
			SystemPreferences sf = sfdao.getPreferences();
			sf.setDefaultDescription(null);
			sfdao.setPreferences(sf);

			for (InitiativeDescription desc : daof.getInitiativeDAO().getAllDescriptions()) {
				deleteInitiativeDescription(desc);
			}
		} catch (PersistenceException e) {
			logger.error("persistence problem while deleting descriptions", e);
			throw new OCTException("persistence problem while deleting descriptions", e);
		}
	}

	@Override
	public Contact getContact() throws OCTException {

		try {
			SystemPreferencesDAO sfdao = daof.getSystemPreferencesDAO();
			Contact c = sfdao.getContact();
			return c;
		} catch (PersistenceException e) {
			logger.error("persistence problem while fetching contact", e);
			throw new OCTException("persistence problem while fetching contact", e);
		}

	}

	@Override
	public Contact setContactInfo(Contact c) throws OCTException {

		// TODO validation
		try {
			SystemPreferencesDAO sfdao = daof.getSystemPreferencesDAO();

			Contact currentContact = sfdao.getContact();
			if (currentContact == null) {
				currentContact = new Contact();
				currentContact.setSystem(sfdao.getPreferences());
			}
			currentContact.setName(c.getName());
			currentContact.setEmail(c.getEmail());
			currentContact.setOrganizers(c.getOrganizers());

			sfdao.setContact(currentContact);
			return currentContact;
		} catch (PersistenceException e) {
			logger.error("persistence problem while adding contact", e);
			throw new OCTException("persistence problem while adding contact", e);
		}
	}

	public void setRegistrationData(SystemPreferences prefs, Date timestamp, boolean timestampOnly) throws OCTException {

		if (!timestampOnly) {
			SystemStateChecker.getController(new DBSystemStateProvider(daof)).disallowedStates(SystemState.OPERATIONAL);
		}

		try {
			SystemPreferencesDAO spdao = daof.getSystemPreferencesDAO();
			SystemPreferences sf = spdao.getPreferences();
			if (!timestampOnly) {
				sf.setCommissionRegisterUrl(prefs.getCommissionRegisterUrl());
				sf.setRegistrationDate(prefs.getRegistrationDate());
				sf.setRegistrationNumber(prefs.getRegistrationNumber());				
			}
			sf.setEciDataTimestamp(timestamp);

			if (SystemState.DEPLOYED.equals(sf.getState())) {
				sf.setState(SystemState.SETUP);
			}

			spdao.setPreferences(sf);
		} catch (PersistenceException e) {
			logger.error("persistence problem while fetching preferences", e);
			throw new OCTException("persistence problem while fetching preferences", e);
		}
	}

	@Override
	public void overwriteSetup(SystemPreferences prefs, Contact c, List<InitiativeDescription> descs,
			Language defaultLang, boolean recreate, Date timestamp) throws OCTException {

		logger.info("Setting up initiative. Data entered manually: " + !recreate);

		
		if (recreate) {
			signatureSrv.deleteAllSignatures();
			removeAllDescriptions();
		}

		if (recreate) {
			setRegistrationData(prefs, timestamp, !recreate);
		}

		setContactInfo(c);

		InitiativeDescription defaultDesc = null;
		InitiativeDescription tmpDesc = null;

		for (InitiativeDescription id : descs) {
			tmpDesc = insertInitiativeDescription(id);
			if (defaultLang.getId().equals(id.getLanguage().getId())) {
				defaultDesc = tmpDesc;
			}
		}
		if (recreate) {
			if (defaultDesc == null) {
				logger.error("missing default desription. language code: " + defaultLang.getCode());
				throw new OCTException("missing default desription. language code: " + defaultLang.getCode());
			} else {
				setDefaultDescription(defaultDesc);
			}
		}
	}

	private void deleteInitiativeDescription(InitiativeDescription description) throws OCTException {
		try {
			daof.getInitiativeDAO().deleteDescription(description);
		} catch (PersistenceException e) {
			logger.error("persistence problem while deleting description", e);
			throw new OCTException("persistence problem while deleting description", e);
		}
	}

	@Override
	public List<Language> getLanguagesForAvailableDescriptions() throws OCTException {
		List<Language> result = new ArrayList<Language>();
		for (InitiativeDescription description : getDescriptions()) {
			result.add(description.getLanguage());
		}
		return result;
	}
}
