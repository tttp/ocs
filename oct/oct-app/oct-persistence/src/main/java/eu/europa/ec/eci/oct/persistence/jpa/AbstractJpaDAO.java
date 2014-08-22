package eu.europa.ec.eci.oct.persistence.jpa;

import javax.persistence.EntityManager;

import org.apache.log4j.Logger;

import eu.europa.ec.eci.oct.persistence.PersistenceException;

/**
 * Parent class for all JPA-based DAO implementations.
 * 
 * @author keschma
 *
 */
public abstract class AbstractJpaDAO {
	
	Logger logger = Logger.getLogger(AbstractJpaDAO.class);
	
	protected final EntityManager em;
	
	/**
	 * Constructor.
	 * @param entityManager the entity manager to use
	 */
	AbstractJpaDAO(EntityManager entityManager) {
		this.em = entityManager;
	}

	/**
	 * Logs an error and produces a persistence layer exception
	 * by wrapping the given reason.
	 * @param errorDetails some informative details about this error
	 * @param e the exception causing this error
	 * @return a suitable instance of PersistenceException
	 */
	protected PersistenceException wrapException(String errorDetails, Exception e) {
		final String errorMessage = "Error: " + errorDetails;
		logger.error(errorMessage, e);
		return new PersistenceException(errorMessage, e);
	}
	
}
