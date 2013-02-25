package eu.europa.ec.eci.oct.persistence;

import eu.europa.ec.eci.oct.entities.admin.Contact;
import eu.europa.ec.eci.oct.entities.admin.InitiativeDescription;
import eu.europa.ec.eci.oct.entities.admin.SystemPreferences;
import eu.europa.ec.eci.oct.entities.member.Language;

public interface SystemPreferencesDAO {
	
	SystemPreferences getPreferences() throws PersistenceException;
	
	void setPreferences(SystemPreferences prefs) throws PersistenceException;
	
	void setDefaultSystemLanguage(Language lang) throws PersistenceException;
	
	void setDefaultDescription(InitiativeDescription desc) throws PersistenceException;
	
	void setContact(Contact c) throws PersistenceException;

	Contact getContact() throws PersistenceException;

	void setCollecting(boolean collecting) throws PersistenceException;
		
	
}
