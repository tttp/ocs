package eu.europa.ec.eci.oct.persistence.jpa;

import javax.persistence.EntityManager;

import eu.europa.ec.eci.oct.persistence.AccountDAO;
import eu.europa.ec.eci.oct.persistence.CountryDAO;
import eu.europa.ec.eci.oct.persistence.DAOFactory;
import eu.europa.ec.eci.oct.persistence.InitiativeDAO;
import eu.europa.ec.eci.oct.persistence.PersistenceException;
import eu.europa.ec.eci.oct.persistence.PropertyDAO;
import eu.europa.ec.eci.oct.persistence.SignatureDAO;
import eu.europa.ec.eci.oct.persistence.SystemPreferencesDAO;

public abstract class JpaDAOFactory implements DAOFactory {
	
	public JpaDAOFactory(){		
	}
	
	public CountryDAO getCountryDAO() throws PersistenceException{
		return new JpaCountryDAO(getEntityManager());
	}
	
	
	public abstract EntityManager getEntityManager();

	public PropertyDAO getPropertyDAO() throws PersistenceException {
		return new JpaPropertyDAO(getEntityManager());
	}
	
	public SignatureDAO getSignatureDAO() throws PersistenceException {
		return new JpaSignatureDAO(getEntityManager());
	}
	
	public SystemPreferencesDAO getSystemPreferencesDAO() throws PersistenceException {
		return new JpaSystemPreferencesDAO(getEntityManager());
	}
	
	public InitiativeDAO getInitiativeDAO() throws PersistenceException {
		return new JpaInitiativeDAO(getEntityManager());
	}
	
	public AccountDAO getAccountDAO() throws PersistenceException {
		return new JpaAccountDAO(getEntityManager());
	}
}
