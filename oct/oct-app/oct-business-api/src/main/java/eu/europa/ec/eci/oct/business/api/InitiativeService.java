package eu.europa.ec.eci.oct.business.api;

import java.util.Date;
import java.util.List;

import eu.europa.ec.eci.oct.entities.admin.Contact;
import eu.europa.ec.eci.oct.entities.admin.InitiativeDescription;
import eu.europa.ec.eci.oct.entities.admin.SystemPreferences;
import eu.europa.ec.eci.oct.entities.member.Language;

public interface InitiativeService {

	/**
	 * Add initiative description, overwrite if exists
	 * 
	 * @param id
	 *            - description
	 * @throws OCTException
	 */
	public InitiativeDescription insertInitiativeDescription(InitiativeDescription id) throws OCTException;

	/**
	 * Provide the list of descriptions registered within the system
	 * 
	 * @return
	 * @throws OCTException
	 */
	public List<InitiativeDescription> getDescriptions() throws OCTException;

	/**
	 * Provide the list of descriptions registered within the system, exclude default one from the list
	 * 
	 * @return
	 * @throws OCTException
	 */
	public List<InitiativeDescription> getDescriptionsExcludeDefault() throws OCTException;

	/**
	 * Provide description for a given language
	 * 
	 * @param lang
	 * @return
	 * @throws OCTException
	 */
	public InitiativeDescription getDescriptionByLang(Language lang) throws OCTException;

	/**
	 * Provide initiative description by a given identifier
	 * 
	 * @param ud
	 * @return
	 * @throws OCTException
	 */
	public InitiativeDescription getDescriptionById(long ud) throws OCTException;

	/**
	 * Provide default description
	 * 
	 * @return
	 * @throws OCTException
	 */
	public InitiativeDescription getDefaultDescription() throws OCTException;

	/**
	 * Returns a list with all the available initiative descriptions languages.
	 * 
	 * @return
	 * @throws OCTException
	 */
	public List<Language> getLanguagesForAvailableDescriptions() throws OCTException;

	/**
	 * Sets description as a default one
	 * 
	 * @param id
	 * @throws OCTException
	 */
	public void setDefaultDescription(InitiativeDescription id) throws OCTException;

	/**
	 * Provide the list of languages with no description for an initiative
	 * 
	 * @return
	 * @throws OCTException
	 */
	public List<Language> getUnusedDescriptionLanguages() throws OCTException;

	/**
	 * Delete all descriptions
	 * 
	 * @throws OCTException
	 */
	public void removeAllDescriptions() throws OCTException;

	/**
	 * Insert contact information
	 * 
	 * @param c
	 * @throws OCTException
	 */
	public Contact setContactInfo(Contact c) throws OCTException;

	/**
	 * Provide contact information
	 * 
	 * @return
	 * @throws OCTException
	 */
	public Contact getContact() throws OCTException;

	public void overwriteSetup(SystemPreferences prefs, Contact c, List<InitiativeDescription> descs,
			Language defaultLang, boolean recreate, Date timestamp) throws OCTException;

}
