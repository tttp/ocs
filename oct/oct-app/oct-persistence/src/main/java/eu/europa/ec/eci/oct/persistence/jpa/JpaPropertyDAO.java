package eu.europa.ec.eci.oct.persistence.jpa;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.apache.log4j.Logger;

import eu.europa.ec.eci.oct.entities.CountryProperty;
import eu.europa.ec.eci.oct.entities.PropertyGroup;
import eu.europa.ec.eci.oct.entities.member.Country;
import eu.europa.ec.eci.oct.entities.rules.LocalValidationRule;
import eu.europa.ec.eci.oct.persistence.PersistenceException;
import eu.europa.ec.eci.oct.persistence.PropertyDAO;

public class JpaPropertyDAO extends AbstractJpaDAO implements PropertyDAO {

	Logger logger = Logger.getLogger(JpaPropertyDAO.class);

	private final static String QUERY_PROPERTY_SELECT_M = "SELECT cp ";
	private final static String QUERY_PROPERTY = "FROM CountryProperty cp JOIN cp.property p where cp.country = :country and p.group = :group ORDER BY p.priority DESC ";

	JpaPropertyDAO(EntityManager entityManager) {
		super(entityManager);
	}

	public List<PropertyGroup> getGroups() throws PersistenceException {
		try {
			logger.debug("querying all groups registered within system");

			CriteriaQuery<PropertyGroup> query = em.getCriteriaBuilder().createQuery(PropertyGroup.class);
			Root<PropertyGroup> groot = query.from(PropertyGroup.class);
			query.select(groot);
			List<PropertyGroup> gl = em.createQuery(query).getResultList();

			if (logger.isDebugEnabled()) {
				logger.debug("returning list containing " + gl.size() + " groups");
			}

			return gl;
		} catch (Exception e) {
			throw wrapException("getGroups", e);
		}
	}

	@Override
	public List<CountryProperty> getProperties(Country c, PropertyGroup g) throws PersistenceException {
		try {
			if (logger.isDebugEnabled()) {
				logger.debug("querying properties for group " + g.getName());
			}

			Query q = em.createQuery(QUERY_PROPERTY_SELECT_M + QUERY_PROPERTY);
			q.setParameter("country", c);
			q.setParameter("group", g);

			@SuppressWarnings("unchecked")
			List<CountryProperty> props = q.getResultList();
			if (logger.isDebugEnabled()) {
				logger.debug("returning list containing " + props.size() + " items");
			}

			return props;
		} catch (Exception e) {
			throw wrapException("getProperties " + c + " " + g, e);
		}
	}

	@Override
	public CountryProperty getCountryPropertyById(Long id) throws PersistenceException {
		try {
			if (logger.isDebugEnabled()) {
				logger.debug("querying for country property by id " + id);
			}

			CriteriaBuilder cb = em.getCriteriaBuilder();
			CriteriaQuery<CountryProperty> query = cb.createQuery(CountryProperty.class);
			Root<CountryProperty> countryRoot = query.from(CountryProperty.class);
			query.where(cb.equal(countryRoot.get("id"), id));
			CountryProperty countryProperty = null;
			try {
				countryProperty = em.createQuery(query).getSingleResult();
			} catch (NoResultException nre) {
				logger.warn("no country property with id " + id + " found");
			}

			return countryProperty;
		} catch (Exception e) {
			throw wrapException("getCountryPropertyById " + id, e);
		}
	}

	@Override
	public List<CountryProperty> getAllCountryProperties() throws PersistenceException {
		try {
			logger.debug("querying all country properties registered within system");

			CriteriaQuery<CountryProperty> query = em.getCriteriaBuilder().createQuery(CountryProperty.class);
			Root<CountryProperty> groot = query.from(CountryProperty.class);
			query.select(groot);
			List<CountryProperty> gl = em.createQuery(query).getResultList();

			if (logger.isDebugEnabled()) {
				logger.debug("returning list containing " + gl.size() + " country properties");
			}

			return gl;
		} catch (Exception e) {
			throw wrapException("getAllCountryProperties", e);
		}
	}

	@Override
	public List<LocalValidationRule> getLocalRulesForCountryProperty(CountryProperty countryProperty)
			throws PersistenceException {
		try {
			if (logger.isDebugEnabled()) {
				logger.debug("getting local validation rules for " + countryProperty);
			}

			CriteriaBuilder cb = em.getCriteriaBuilder();
			CriteriaQuery<LocalValidationRule> query = cb.createQuery(LocalValidationRule.class);
			Root<LocalValidationRule> localValidationRuleRoot = query.from(LocalValidationRule.class);
			query.where(cb.equal(localValidationRuleRoot.get("countryProperty"), countryProperty));

			List<LocalValidationRule> gl = em.createQuery(query).getResultList();
			if (logger.isDebugEnabled()) {
				logger.debug("Returning list containing " + gl.size() + " local validation rules.");
			}
			return gl;
		} catch (Exception e) {
			throw wrapException("Exception while getting local validation rules for " + countryProperty, e);
		}
	}

}
