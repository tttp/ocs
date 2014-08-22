package eu.europa.ec.eci.oct.persistence;

import eu.europa.ec.eci.oct.entities.admin.Account;

public interface AccountDAO {

	Account getAccountByName(String userName) throws PersistenceException;
	
}
