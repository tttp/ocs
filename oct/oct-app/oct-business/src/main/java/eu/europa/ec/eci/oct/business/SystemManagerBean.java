package eu.europa.ec.eci.oct.business;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.Stateless;

import org.apache.commons.codec.binary.Hex;
import org.apache.log4j.Logger;

import eu.europa.ec.eci.oct.business.api.OCTException;
import eu.europa.ec.eci.oct.business.api.OCTMissingCertificateException;
import eu.europa.ec.eci.oct.business.api.OCTMissingSetupDateException;
import eu.europa.ec.eci.oct.business.api.SystemManager;
import eu.europa.ec.eci.oct.business.api.system.SystemStateChecker;
import eu.europa.ec.eci.oct.business.crypto.CryptographyService;
import eu.europa.ec.eci.oct.business.system.DBSystemStateProvider;
import eu.europa.ec.eci.oct.entities.admin.Account;
import eu.europa.ec.eci.oct.entities.admin.SystemPreferences;
import eu.europa.ec.eci.oct.entities.admin.SystemState;
import eu.europa.ec.eci.oct.entities.member.Country;
import eu.europa.ec.eci.oct.entities.member.Language;
import eu.europa.ec.eci.oct.persistence.DAOFactory;
import eu.europa.ec.eci.oct.persistence.PersistenceException;
import eu.europa.ec.eci.oct.persistence.SystemPreferencesDAO;
import eu.europa.ec.eci.oct.vo.certificate.Certificate;

@Stateless
@Local({ SystemManager.class })
public class SystemManagerBean implements SystemManager {

	Logger logger = Logger.getLogger(SystemManagerBean.class);

	@EJB
	private DAOFactory daof;

	@Override
	public SystemPreferences getSystemPreferences() throws OCTException {
		try {
			SystemPreferences sf = daof.getSystemPreferencesDAO().getPreferences();
			return sf;
		} catch (PersistenceException e) {
			logger.error("persistence problem while fetching preferences", e);
			throw new OCTException("persistence problem while fetching preferences", e);
		}
	}

	@Override
	public void setRegistrationData(SystemPreferences prefs) throws OCTException {
		try {
			SystemPreferencesDAO spdao = daof.getSystemPreferencesDAO();
			SystemPreferences sf = spdao.getPreferences();
			sf.setCommissionRegisterUrl(prefs.getCommissionRegisterUrl());
			sf.setRegistrationDate(prefs.getRegistrationDate());
			sf.setRegistrationNumber(prefs.getRegistrationNumber());
			sf.setDefaultLanguage(prefs.getDefaultLanguage());

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
	public void goToProduction() throws OCTException {

		SystemStateChecker.getController(new DBSystemStateProvider(daof)).allowedStates(SystemState.SETUP);

		try {
			SystemPreferences prefs = daof.getSystemPreferencesDAO().getPreferences();

			if (prefs.getEciDataTimestamp() == null) {
				throw new OCTMissingSetupDateException("Missinng setup insertion date");
			}
			prefs.setState(SystemState.OPERATIONAL);

			daof.getSystemPreferencesDAO().setPreferences(prefs);
		} catch (PersistenceException e) {
			logger.error("persistence problem while changing system state", e);
			throw new OCTException("persistence problem while changing system state", e);
		}
	}

	@Override
	public void checkOnlineAvailability() throws OCTException {

		try {
			SystemPreferences prefs = daof.getSystemPreferencesDAO().getPreferences();

			if (prefs.getEciDataTimestamp() == null) {
				throw new OCTMissingSetupDateException("Missinng setup insertion date");
			}
		} catch (PersistenceException e) {
			logger.error("persistence problem while changing system state", e);
			throw new OCTException("persistence problem while changing system state", e);
		}

	}

	@Override
	public void setCollecting(boolean collecting) throws OCTException {
		try {
			daof.getSystemPreferencesDAO().setCollecting(collecting);
		} catch (PersistenceException e) {
			logger.error("persistence problem while setting collector state to " + collecting, e);
			throw new OCTException("persistence problem while setting collector state to " + collecting, e);
		}

	}

	@Override
	public boolean getCollectorState() throws OCTException {
		try {
			return daof.getSystemPreferencesDAO().getPreferences().isCollecting();
		} catch (PersistenceException e) {
			logger.error("persistence problem while getting collector state", e);
			throw new OCTException("persistence problem while getting collector state", e);
		}

	}

	@Override
	public List<Country> getAllCountries() throws OCTException {
		logger.info("obtaining country list");

		try {
			List<Country> countries = daof.getCountryDAO().getCountries();
			if (logger.isDebugEnabled()) {
				logger.debug("Country list contains " + (countries == null ? "0" : countries.size()) + " elements");
			}

			return countries;
		} catch (PersistenceException e) {
			logger.error("problem while obtaining countries. message: " + e.getLocalizedMessage(), e);
			throw new OCTException("obtaining country list failed", e);
		}
	}

	@Override
	public Country getCountryByCode(String code) throws OCTException {
		try {
			Country result = daof.getCountryDAO().getCountryByCode(code);
			if (logger.isDebugEnabled()) {
				logger.debug("Country matching code " + code + ": "
						+ (result == null ? "Nothing found!" : result.toString()));
			}

			return result;
		} catch (PersistenceException e) {
			logger.error(
					"There was a problem while retrieving the country for code " + code + ". Message: "
							+ e.getLocalizedMessage(), e);
			throw new OCTException("Getting property groups for " + code + " failed.", e);
		}
	}

	@Override
	public Language getLanguageByCode(String code) throws OCTException {
		try {
			Language result = daof.getCountryDAO().getLanguageByCode(code);
			if (logger.isDebugEnabled()) {
				logger.debug("Language matching code " + code + ": "
						+ (result == null ? "Nothing found!" : result.toString()));
			}

			return result;
		} catch (PersistenceException e) {
			logger.error(
					"There was a roblem while retrieving language with code " + code + ". Message: "
							+ e.getLocalizedMessage(), e);
			throw new OCTException("Fetching language " + code + " failed.", e);
		}
	}

	@Override
	public List<Language> getAllLanguages() throws OCTException {
		logger.info("obtaining language list");

		try {
			List<Language> langs = daof.getCountryDAO().getLanguages();
			if (logger.isDebugEnabled()) {
				logger.debug("Language list contains " + (langs == null ? "0" : langs.size()) + " elements");
			}

			return langs;
		} catch (PersistenceException e) {
			logger.error("problem while obtaining languages. message: " + e.getLocalizedMessage(), e);
			throw new OCTException("obtaining language list failed", e);
		}
	}

	@Override
	public boolean authenticate(String user, String password) throws OCTException {

		try {
			Account a = daof.getAccountDAO().getAccountByName(user);
			if (a == null)
				return false;

			CryptographyService crypto = CryptographyService.getService(daof.getSystemPreferencesDAO().getPreferences()
					.getPublicKey().toCharArray());

			String fingerPrint = new String(Hex.encodeHex(crypto.fingerprint(password)));

			return fingerPrint.equals(a.getPassHash());

		} catch (PersistenceException e) {
			logger.error("problem while fetching account info. message: " + e.getLocalizedMessage(), e);
			throw new OCTException("problem while fetching account info", e);
		}
	}

	@Override
	public String hash(String input) throws OCTException {
		CryptographyService crypto = CryptographyService
				.getService(getSystemPreferences().getPublicKey().toCharArray());
		return new String(Hex.encodeHex(crypto.fingerprint(input)));
	}

	@Override
	public String generateChallenge(String phrase) throws OCTException {
		CryptographyService crypto = CryptographyService
				.getService(getSystemPreferences().getPublicKey().toCharArray());
		byte[] encryptedChallenge = crypto.encrypt(phrase);
		return new String(Hex.encodeHex(encryptedChallenge));
	}

	@Override
	public void installCertificate(Certificate c) throws OCTException {

		SystemStateChecker.getController(new DBSystemStateProvider(daof)).disallowedStates(SystemState.OPERATIONAL);

		try {
			SystemPreferencesDAO spdao = daof.getSystemPreferencesDAO();
			SystemPreferences sf = spdao.getPreferences();

			sf.setCertFileName(c.getFileName());
			sf.setCertContentType(c.getContentType());

			spdao.setPreferences(sf);
		} catch (PersistenceException e) {
			logger.error("persistence problem while installing certificate", e);
			throw new OCTException("persistence problem while installing certificate", e);
		}
	}

	@Override
	public Certificate getCertificate() throws OCTException {
		try {
			SystemPreferences sf = daof.getSystemPreferencesDAO().getPreferences();

			if (sf.getCertFileName() == null || sf.getCertFileName().trim().length() == 0) {
				throw new OCTMissingCertificateException("Certificate file has not been uploaded yet");
			}
			Certificate cert = new Certificate();
			cert.setPath(sf.getFileStoragePath());
			cert.setFileName(sf.getCertFileName());
			cert.setContentType(sf.getCertContentType());

			return cert;

		} catch (PersistenceException e) {
			logger.error("persistence problem while fetching preferences", e);
			throw new OCTException("persistence problem while fetching preferences", e);
		}
	}

	@Override
	public boolean isWebPasswordProtected() throws OCTException {
		return getSystemPreferences().getState() != SystemState.OPERATIONAL;
	}

}
