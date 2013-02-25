package eu.europa.ec.eci.oct.business.api;

import java.util.List;

import eu.europa.ec.eci.oct.entities.admin.SystemPreferences;
import eu.europa.ec.eci.oct.entities.member.Country;
import eu.europa.ec.eci.oct.entities.member.Language;
import eu.europa.ec.eci.oct.vo.certificate.Certificate;

/**
 * Manager class providing access to the core system artifacts (dictionaries, certificate, configuration etc) and core
 * operations like state transition.
 * 
 */
public interface SystemManager {

	/**
	 * System configuration provider
	 * 
	 * @return - system configuration instance
	 * @throws OCTException
	 */
	public SystemPreferences getSystemPreferences() throws OCTException;

	// TODO move it from here!
	public void setRegistrationData(SystemPreferences prefs) throws OCTException;

	/**
	 * Enables production (online) mode
	 * 
	 * @throws OCTException
	 *             - if transition illegal
	 */
	public void goToProduction() throws OCTException;

	/**
	 * Provides the list of state members registered within the system
	 * 
	 * @return - the list of countries
	 * @throws OCTException
	 */
	public List<Country> getAllCountries() throws OCTException;

	/**
	 * Provides country by given code (ISO 3166-1)
	 * 
	 * @param code
	 *            - language code
	 * @return country instance
	 * @throws OCTException
	 */
	public Country getCountryByCode(String code) throws OCTException;

	/**
	 * Provides the list of languages registered within the system
	 * 
	 * @return
	 * @throws OCTException
	 */
	public List<Language> getAllLanguages() throws OCTException;

	/**
	 * Provides language by given code (ISO 639-1)
	 * 
	 * @param code
	 * @return
	 * @throws OCTException
	 */
	public Language getLanguageByCode(String code) throws OCTException;

	/**
	 * 
	 * 
	 * @param user
	 * @param password
	 * @return
	 * @throws OCTException
	 */
	public boolean authenticate(String user, String password) throws OCTException;

	/**
	 * Counts hash for a given input value
	 * 
	 * @param input
	 *            - input value
	 * @return counted hash value
	 * @throws OCTException
	 */
	public String hash(String input) throws OCTException;

	/**
	 * Encrypts given phrase
	 * 
	 * @param phrase
	 * @return
	 * @throws OCTException
	 */
	public String generateChallenge(String phrase) throws OCTException;

	/**
	 * Sets collector status (on/off)
	 * 
	 * @param collecting
	 * @throws OCTException
	 */
	public void setCollecting(boolean collecting) throws OCTException;

	/**
	 * Provides collector state
	 * 
	 * @return
	 * @throws OCTException
	 */
	public boolean getCollectorState() throws OCTException;

	/**
	 * Checks the availability of transiting into online mode. Checks whether initiative setup comes from importing ECI
	 * data and whether certificate has been installe dwithin the system
	 * 
	 * @throws OCTException
	 */
	void checkOnlineAvailability() throws OCTException;

	/**
	 * Install certificate. Registers its file name and content type
	 * 
	 * @param c
	 * @throws OCTException
	 */
	public void installCertificate(Certificate c) throws OCTException;

	/**
	 * Provides the information about certificate
	 * 
	 * @return
	 * @throws OCTException
	 */
	public Certificate getCertificate() throws OCTException;

	/**
	 * Used to decide, based on system state, whether the public part of the application needs to be password protected.
	 * 
	 * @return true if public part needs password protection, false otherwise
	 * @throws OCTException
	 */
	public boolean isWebPasswordProtected() throws OCTException;

}
