package eu.europa.ec.eci.oct.persistence;

import java.util.List;

import eu.europa.ec.eci.oct.entities.admin.InitiativeDescription;
import eu.europa.ec.eci.oct.entities.member.Language;

public interface InitiativeDAO {
	
	void insertInitiativeDescription(InitiativeDescription id) throws PersistenceException;

	void updateInitiativeDescription(InitiativeDescription description) throws PersistenceException;

	InitiativeDescription getDescriptionByLanguage(Language lang) throws PersistenceException;

	InitiativeDescription getDescriptionById(long id) throws PersistenceException;

	List<InitiativeDescription> getAllDescriptions() throws PersistenceException;

	void deleteDescription(InitiativeDescription description) throws PersistenceException;
}
