package eu.europa.ec.eci.oct.persistence.jpa;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import eu.europa.ec.eci.oct.entities.admin.InitiativeDescription;
import eu.europa.ec.eci.oct.entities.member.Language;
import eu.europa.ec.eci.oct.persistence.InitiativeDAO;
import eu.europa.ec.eci.oct.persistence.PersistenceException;

public class JpaInitiativeDAO extends AbstractJpaDAO implements InitiativeDAO {

	private Logger logger = LoggerFactory.getLogger(JpaInitiativeDAO.class);

	JpaInitiativeDAO(EntityManager entityManager) {
		super(entityManager);
	}

	@Override
	public void insertInitiativeDescription(InitiativeDescription id) throws PersistenceException {
		try {
			em.persist(id);
		}
		catch (Exception e) {
			throw wrapException("insertInitiativeDescription " + id, e);
		}
	}

	public List<InitiativeDescription> getAllDescriptions() throws PersistenceException {
		try {
			logger.debug("querying all descriptions for an initiative");
	
			CriteriaBuilder cb = em.getCriteriaBuilder();
			CriteriaQuery<InitiativeDescription> query = cb.createQuery(InitiativeDescription.class);
			Root<InitiativeDescription> croot = query.from(InitiativeDescription.class);
			query.select(croot);
			List<InitiativeDescription> idl = em.createQuery(query).getResultList();
	
			if (logger.isDebugEnabled()) {
				logger.debug("returning list containing " + idl.size() + " descriptions");
			}
	
			return idl;
		}
		catch (Exception e) {
			throw wrapException("getAllDescriptions", e);
		}
	}

	@Override
	public InitiativeDescription getDescriptionByLanguage(Language lang) throws PersistenceException {
		try {
			logger.debug("querying initiative description for language " + lang.getCode());
	
			CriteriaBuilder cb = em.getCriteriaBuilder();
			CriteriaQuery<InitiativeDescription> query = cb.createQuery(InitiativeDescription.class);
			Root<InitiativeDescription> countryRoot = query.from(InitiativeDescription.class);
			query.where(cb.equal(countryRoot.get("language"), lang));
	
			InitiativeDescription initiative = null;
			try {
				List<InitiativeDescription> lid = em.createQuery(query).getResultList();
				
				if(lid!=null && lid.size()==1){			
					initiative = lid.get(0);				
				} 
						 
				return initiative;
			} catch (NoResultException nre) {
				return null;
			}
		}
		catch (Exception e) {
			throw wrapException("getDescriptionByLanguage " + lang, e);
		}
	}

	@Override
	public InitiativeDescription getDescriptionById(long id) throws PersistenceException {
		try {
			logger.debug("querying initiative description for id " + id);
	
			CriteriaBuilder cb = em.getCriteriaBuilder();
			CriteriaQuery<InitiativeDescription> query = cb.createQuery(InitiativeDescription.class);
			Root<InitiativeDescription> countryRoot = query.from(InitiativeDescription.class);
			query.where(cb.equal(countryRoot.get("id"), id));
	
			InitiativeDescription initiative = null;
			try {
				initiative = em.createQuery(query).getSingleResult();
				return initiative;
			} catch (NoResultException nre) {
				return null;
			}
		}
		catch (Exception e) {
			throw wrapException("getDescriptionById " + id, e);
		}
	}

	@Override
	public void updateInitiativeDescription(InitiativeDescription description) throws PersistenceException {
		try {
			em.merge(description);
		}
		catch (Exception e) {
			throw wrapException("updateInitiativeDescription " + description, e);
		}
	}

	@Override
	public void deleteDescription(InitiativeDescription description) throws PersistenceException {
		try {
			em.remove(em.merge(description));
		}
		catch (Exception e) {
			throw wrapException("deleteDescription " + description, e);
		}
	}

}
