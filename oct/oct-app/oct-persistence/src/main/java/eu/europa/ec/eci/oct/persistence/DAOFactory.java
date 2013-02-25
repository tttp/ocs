package eu.europa.ec.eci.oct.persistence;

public interface DAOFactory {
	
	public CountryDAO getCountryDAO() throws PersistenceException;
	
	public PropertyDAO getPropertyDAO() throws PersistenceException;
	
	public SignatureDAO getSignatureDAO() throws PersistenceException;
	
	public SystemPreferencesDAO getSystemPreferencesDAO() throws PersistenceException;
	
	public InitiativeDAO getInitiativeDAO() throws PersistenceException;
	
	public AccountDAO getAccountDAO() throws PersistenceException;
	
}
