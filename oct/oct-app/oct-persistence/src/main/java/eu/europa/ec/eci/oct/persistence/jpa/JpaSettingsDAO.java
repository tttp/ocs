/** ====================================================================
 * Licensed under the European Union Public Licence (EUPL v1.2) 
 * https://joinup.ec.europa.eu/community/eupl/topic/public-consultation-draft-eupl-v12
 * ====================================================================
 *
 * @author Daniel CHIRITA
 * @created: 23/05/2013
 *
 */
package eu.europa.ec.eci.oct.persistence.jpa;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.apache.log4j.Logger;

import eu.europa.ec.eci.oct.entities.ConfigurationParameter;
import eu.europa.ec.eci.oct.persistence.PersistenceException;
import eu.europa.ec.eci.oct.persistence.SettingsDAO;

public class JpaSettingsDAO extends AbstractJpaDAO implements SettingsDAO {

	private static final Logger logger = Logger.getLogger(JpaSettingsDAO.class);

	JpaSettingsDAO(EntityManager entityManager) {
		super(entityManager);
	}

	@Override
	public List<ConfigurationParameter> getAllSettings() throws PersistenceException {
		try {
			logger.debug("Retrieving all configuration parameters");

			CriteriaQuery<ConfigurationParameter> query = em.getCriteriaBuilder().createQuery(ConfigurationParameter.class);
			Root<ConfigurationParameter> root = query.from(ConfigurationParameter.class);
			query.select(root);

			return em.createQuery(query).getResultList();
		} catch (Exception e) {
			throw wrapException("getAllSettings()", e);
		}
	}

	@Override
	public ConfigurationParameter findConfigurationParametereByKey(String key) throws PersistenceException {
		ConfigurationParameter result = null;

		try {
			result = em.find(ConfigurationParameter.class, key);
		} catch (NoResultException nre) {
			if (logger.isDebugEnabled()) {
				logger.debug("No configuration parameter found for " + key);
			}
		}

		return result;
	}

	@Override
	public ConfigurationParameter updateParameter(ConfigurationParameter param) throws PersistenceException {
		try {
			if (findConfigurationParametereByKey(param.getParam()) != null) {
				em.merge(param);
			} else {
				em.persist(param);
			}
		} catch (Exception e) {
			throw wrapException("updateParameter(" + param + ")", e);
		}
		return param;
	}
}
