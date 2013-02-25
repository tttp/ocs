package eu.europa.ec.eci.oct.business;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.Stateless;

import org.apache.commons.codec.binary.Hex;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import eu.europa.ec.eci.oct.business.api.DuplicateSignatureException;
import eu.europa.ec.eci.oct.business.api.OCTException;
import eu.europa.ec.eci.oct.business.api.SignatureService;
import eu.europa.ec.eci.oct.business.crypto.CryptographyService;
import eu.europa.ec.eci.oct.business.crypto.OCTCryptoException;
import eu.europa.ec.eci.oct.entities.CountryProperty;
import eu.europa.ec.eci.oct.entities.PropertyGroup;
import eu.europa.ec.eci.oct.entities.admin.SystemPreferences;
import eu.europa.ec.eci.oct.entities.member.Country;
import eu.europa.ec.eci.oct.entities.signature.PropertyValue;
import eu.europa.ec.eci.oct.entities.signature.Signature;
import eu.europa.ec.eci.oct.entities.signature.SignatureCountPerCountry;
import eu.europa.ec.eci.oct.persistence.DAOFactory;
import eu.europa.ec.eci.oct.persistence.NoResultPersistenceException;
import eu.europa.ec.eci.oct.persistence.PersistenceException;
import eu.europa.ec.eci.oct.vo.export.ExportParametersBean;
import eu.europa.ec.eci.oct.vo.export.ExportPropertyBean;
import eu.europa.ec.eci.oct.vo.export.ExportSignatureBean;

@Stateless
@Local({ SignatureService.class })
public class SignatureServiceImpl implements SignatureService {

	public Logger logger = Logger.getLogger(SignatureServiceImpl.class);

	@EJB
	private DAOFactory daof;

	@Override
	public List<PropertyGroup> getPropertyGroups() throws OCTException {
		logger.info("Getting property groups");

		try {
			List<PropertyGroup> result = daof.getPropertyDAO().getGroups();
			logger.debug("Returning list of " + result.size() + " groups");

			return result;
		} catch (PersistenceException e) {
			logger.error("There was a problem while obtaining property groups. Message: " + e.getLocalizedMessage(), e);
			throw new OCTException("Getting property groups failed", e);
		}
	}

	@Override
	public List<CountryProperty> getProperties(Country c, PropertyGroup group) throws OCTException {
		if (logger.isInfoEnabled()) {
			logger.info("Getting properties for group " + group.toString());
		}

		try {
			List<CountryProperty> result = daof.getPropertyDAO().getProperties(c, group);
			if (logger.isDebugEnabled()) {
				logger.debug("Properties for " + group.toString() + " contain "
						+ (result == null ? "0" : result.size()) + " elements.");
			}

			return result;
		} catch (PersistenceException e) {
			logger.error("There was a problem while obtaining property groups. Message: " + e.getLocalizedMessage(), e);
			throw new OCTException("Getting property groups for " + group.toString() + " failed.", e);
		}
	}

	@Override
	public CountryProperty getCountryPropertyById(Long id) throws OCTException {
		try {
			CountryProperty result = daof.getPropertyDAO().getCountryPropertyById(id);
			if (logger.isDebugEnabled()) {
				logger.debug("Country property matching id " + id + ": "
						+ (result == null ? "Nothing found!" : result.toString()));
			}

			return result;
		} catch (PersistenceException e) {
			logger.error(
					"There was a roblem while retrieving the country property for id " + id + ". Message: "
							+ e.getLocalizedMessage(), e);
			throw new OCTException("Getting country property for id " + id + " failed.", e);
		}
	}

	@Override
	public Signature insertSignature(Signature signature) throws OCTException {
		if (logger.isInfoEnabled()) {
			logger.debug("Storing signature " + signature.toString());
		}

		try {
			SystemPreferences preferences = daof.getSystemPreferencesDAO().getPreferences();
			CryptographyService crypto = CryptographyService.getService(preferences.getPublicKey().toCharArray());

			byte[] signatureHash = crypto.fingerprint(serialize(signature));
			signature.setFingerprint(new String(Hex.encodeHex(signatureHash)));

			signature.setUuid(UUID.randomUUID().toString());
			encryptSignatureData(signature, crypto);
			daof.getSignatureDAO().insertSignature(signature);

			return signature;
		} catch (PersistenceException e) {
			try {
				checkForDuplicate(signature);
			} catch (DuplicateSignatureException de) {
				logger.error("Duplicate signature detected", e);
				throw de;
			}

			logger.error("There was a problem persisting signature. The message was: " + e.getLocalizedMessage(), e);
			throw new OCTException("There was a problem persisting signature", e);
		}
	}

	private void encryptSignatureData(Signature signature, CryptographyService crypto) throws OCTCryptoException {
		logger.debug("Encrypting signature data...");

		for (PropertyValue propertyValue : signature.getPropertyValues()) {
			String encryptedValue = new String(Hex.encodeHex(crypto.encrypt(propertyValue.getValue())));
			propertyValue.setValue(encryptedValue);
		}
	}

	private void checkForDuplicate(Signature signature) throws DuplicateSignatureException, OCTCryptoException,
			OCTException {
		logger.debug("Checking whether signature exists...");

		try {
			daof.getSignatureDAO().findByFingerprint(signature);
			// if NoResultPersistenceException caught then duplicate found
			throw new DuplicateSignatureException("Duplicate of signature with fingerprint "
					+ signature.getFingerprint() + " exists");
		} catch (NoResultPersistenceException e) {
			// expected!
			return;
		} catch (PersistenceException e) {
			throw new OCTException("problem while querying duplicate", e);
		}
	}

	private String serialize(Signature s) {
		StringBuilder buf = new StringBuilder();

		for (Iterator<PropertyValue> iterator = s.getPropertyValues().iterator(); iterator.hasNext();) {
			PropertyValue value = iterator.next();
			String propName = value.getProperty().getProperty().getName();
			String propValue = value.getValue().toUpperCase();
			buf.append("[").append(propName).append("][").append(propValue).append("]");
		}

		return buf.toString();
	}

	@Override
	public List<SignatureCountPerCountry> getSignatureCounts(ExportParametersBean parameters) throws OCTException {
		try {
			return daof.getSignatureDAO().countSignatures(parameters);
		} catch (PersistenceException e) {
			logger.error(
					"There was a problem retrieving signature counts for cpuntries. The message was: "
							+ e.getLocalizedMessage(), e);
			throw new OCTException("There was a problem retrieving signature counts for cpuntries.", e);
		}
	}

	@Override
	public void deleteSignature(Signature signature) throws OCTException {
		try {
			daof.getSignatureDAO().deleteSignature(signature);
		} catch (PersistenceException e) {
			logger.error("There was a problem while deleting signature. The message was: " + e.getLocalizedMessage(), e);
			throw new OCTException("There was a problem while deleting signature.", e);
		}
	}

	@Override
	public Signature findByUuid(String uuid) throws OCTException {
		try {
			return daof.getSignatureDAO().findByUuid(uuid);
		} catch (PersistenceException e) {
			logger.error("There was a problem while locating signature. The message was: " + e.getLocalizedMessage(), e);
			throw new OCTException("There was a problem while locating signature.", e);
		}
	}

	@Override
	public List<CountryProperty> getAllCountryProperties() throws OCTException {
		logger.info("Getting country properties...");

		try {
			List<CountryProperty> result = daof.getPropertyDAO().getAllCountryProperties();
			if (logger.isDebugEnabled()) {
				logger.debug("Country properties table contains " + (result == null ? "0" : result.size())
						+ " elements.");
			}

			return result;
		} catch (PersistenceException e) {
			logger.error(
					"There was a problem while obtaining all country properties. Message: " + e.getLocalizedMessage(),
					e);
			throw new OCTException("Getting all properties failed.", e);
		}
	}

	@Override
	public Map<Long, ExportSignatureBean> getSignaturesForExport(ExportParametersBean parameters) throws OCTException {
		if (logger.isEnabledFor(Level.INFO)) {
			logger.info("Getting signatures for export for the following parameters: " + parameters.toString());
		}
		try {
			Map<Long, ExportSignatureBean> result = new HashMap<Long, ExportSignatureBean>();
			List<ExportSignatureBean> signatures = daof.getSignatureDAO().getSignatures(parameters);
			for (ExportSignatureBean signature : signatures) {
				result.put(signature.getId(), signature);
			}
			if (logger.isDebugEnabled()) {
				logger.debug("Retrieved " + (result == null ? "0" : result.size()) + " signatures.");
			}

			return result;
		} catch (PersistenceException e) {
			logger.error("There was a problem while obtaining the signature list. Message: " + e.getLocalizedMessage(),
					e);
			throw new OCTException("Getting signatures failed.", e);
		}
	}

	@Override
	public List<ExportPropertyBean> getPropertiesForSignatures(List<Long> ids) throws OCTException {
		if (logger.isEnabledFor(Level.INFO)) {
			logger.info("Getting properties for export for " + (ids == null ? "0" : ids.size()) + " signature ids...");
		}
		try {
			List<ExportPropertyBean> result = daof.getSignatureDAO().getPropertiesForSignatures(ids);
			if (logger.isDebugEnabled()) {
				logger.debug("Retrieved " + (result == null ? "0" : result.size()) + " properties.");
			}

			return result;
		} catch (PersistenceException e) {
			logger.error(
					"There was a problem while obtaining the property list for the signature ids. Message: "
							+ e.getLocalizedMessage(), e);
			throw new OCTException("Getting signatures failed.", e);
		}
	}

	@Override
	public void deleteAllSignatures() throws OCTException {
		if (logger.isEnabledFor(Level.INFO)) {
			logger.info("Deleting all signatures...");
		}
		try {
			daof.getSignatureDAO().deleteAllSignatures();
		} catch (PersistenceException e) {
			logger.error(
					"There was a problem while deleting all signatures. The message was: " + e.getLocalizedMessage(), e);
			throw new OCTException("There was a problem while deleting all signatures.", e);
		}
	}
}
