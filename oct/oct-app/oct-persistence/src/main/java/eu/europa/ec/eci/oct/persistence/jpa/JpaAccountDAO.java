package eu.europa.ec.eci.oct.persistence.jpa;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.apache.log4j.Logger;

import eu.europa.ec.eci.oct.entities.admin.Account;
import eu.europa.ec.eci.oct.persistence.AccountDAO;
import eu.europa.ec.eci.oct.persistence.PersistenceException;

public class JpaAccountDAO extends AbstractJpaDAO implements AccountDAO {

	Logger logger = Logger.getLogger(JpaAccountDAO.class);
	
	JpaAccountDAO(EntityManager entityManager) {
		super(entityManager);
	}

	@Override
	public Account getAccountByName(String userName)
			throws PersistenceException {
		try {
			if(logger.isDebugEnabled()){
				logger.debug("querying account by username " + userName);
			}
			
			CriteriaBuilder cb = em.getCriteriaBuilder();
			CriteriaQuery<Account> query = cb.createQuery(Account.class);
			Root<Account> aRoot = query.from(Account.class);
			query.where(cb.equal(aRoot.get("userName"), userName));
			Account a = null;
			try{
				a = em.createQuery(query).getSingleResult();
			} catch(NoResultException nre){
				logger.warn("no account for username " + userName);
			}		
			
			return a;
		}
		catch (Exception e) {
			throw wrapException("getAccountByName " + userName, e);
		}
	}

}
