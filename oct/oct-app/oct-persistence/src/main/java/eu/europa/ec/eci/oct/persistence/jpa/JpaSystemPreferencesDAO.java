package eu.europa.ec.eci.oct.persistence.jpa;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.apache.log4j.Logger;

import eu.europa.ec.eci.oct.entities.admin.Contact;
import eu.europa.ec.eci.oct.entities.admin.InitiativeDescription;
import eu.europa.ec.eci.oct.entities.admin.SystemPreferences;
import eu.europa.ec.eci.oct.entities.member.Language;
import eu.europa.ec.eci.oct.persistence.NoResultPersistenceException;
import eu.europa.ec.eci.oct.persistence.PersistenceException;
import eu.europa.ec.eci.oct.persistence.SystemPreferencesDAO;

public class JpaSystemPreferencesDAO extends AbstractJpaDAO implements SystemPreferencesDAO {
	
	Logger logger = Logger.getLogger(JpaSystemPreferencesDAO.class);

	JpaSystemPreferencesDAO(EntityManager entityManager) {
		super(entityManager);
	}

	@Override
	public SystemPreferences getPreferences() throws PersistenceException {
		try {
			logger.debug("querying system preferences");
			
			CriteriaQuery<SystemPreferences> query = em.getCriteriaBuilder().createQuery(SystemPreferences.class);
			Root<SystemPreferences> sroot = query.from(SystemPreferences.class);		
			query.select(sroot);
			try {
				SystemPreferences prefs = em.createQuery(query).getSingleResult();
	
				if (logger.isDebugEnabled()) {
					logger.debug("returning preferences object " + prefs);
				}
				return prefs;
			} catch (NoResultException nre) {
				logger.error("no system preferences found");
				throw new NoResultPersistenceException(
						"no system preferences found", nre);
			}
		}
		catch (NoResultPersistenceException e) {
			throw e;
		}
		catch (Exception e) {
			throw wrapException("getPreferences", e);
		}
	}

	@Override
	public void setDefaultSystemLanguage(Language lang)
			throws PersistenceException {
		try {
			logger.debug("setting language " + lang.getCode() + " as a default one");		
			
			SystemPreferences prefs = getPreferences();		
			prefs.setDefaultLanguage(lang);		
			em.persist(prefs);
		}
		catch (Exception e) {
			throw wrapException("setDefaultSystemLanguage " + lang, e);
		}
	}
	
	@Override
	public void setCollecting(boolean collecting) throws PersistenceException {
		try {
			SystemPreferences prefs = getPreferences();
			prefs.setCollecting(collecting);
			em.persist(prefs);
		}
		catch (Exception e) {
			throw wrapException("setCollecting " + collecting, e);
		}			
	}
		
	public void setDefaultDescription(InitiativeDescription desc) throws PersistenceException{
		try {
			logger.debug("setting description " + desc.getLanguage().getCode() + " as a default one");			
			
			SystemPreferences prefs = getPreferences();
			prefs.setDefaultDescription(desc);
			em.persist(prefs);	
		}
		catch (Exception e) {
			throw wrapException("setDefaultDescription " + desc, e);
		}
	}

	@Override
	public void setPreferences(SystemPreferences prefs)
			throws PersistenceException {
		try {
			em.persist(prefs);
		}
		catch (Exception e) {
			throw wrapException("setPreferences " + prefs, e);
		}
	}
		
	public void setContact(Contact c) throws PersistenceException {
		try {
			em.persist(c);
		}
		catch (Exception e) {
			throw wrapException("setContact " + c, e);
		}	
	}

	@Override
	public Contact getContact() throws PersistenceException {
		try {
			logger.debug("querying contact data");
			
			CriteriaQuery<Contact> query = em.getCriteriaBuilder().createQuery(Contact.class);
			Root<Contact> croot = query.from(Contact.class);		
			query.select(croot);
			try {
				List<Contact> lc = em.createQuery(query).getResultList();
				if(lc!=null && lc.size()==1){
					return lc.get(0);
				} else {
					return null;
				}			
				
			} catch (NoResultException nre) {
				logger.warn("no contact found");
				return null;
			}
		}
		catch (Exception e) {
			throw wrapException("getContact", e);
		}	
	}
	
}