/** ====================================================================
 * Licensed under the European Union Public Licence (EUPL v1.2) 
 * https://joinup.ec.europa.eu/community/eupl/topic/public-consultation-draft-eupl-v12
 * ====================================================================
 *
 * @author Daniel CHIRITA
 * @created: 23/05/2013
 *
 */
package eu.europa.ec.eci.oct.persistence;

public interface DAOFactory {

	public CountryDAO getCountryDAO() throws PersistenceException;

	public PropertyDAO getPropertyDAO() throws PersistenceException;

	public SignatureDAO getSignatureDAO() throws PersistenceException;

	public SystemPreferencesDAO getSystemPreferencesDAO() throws PersistenceException;

	public InitiativeDAO getInitiativeDAO() throws PersistenceException;

	public AccountDAO getAccountDAO() throws PersistenceException;

	/**
	 * Return a new {@link SettingsDAO} instance.
	 * 
	 * @return
	 * @throws PersistenceException
	 */
	public SettingsDAO getSettingsDAO() throws PersistenceException;
}
