package eu.europa.ec.eci.oct.persistence.jpa;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.apache.log4j.Logger;

import eu.europa.ec.eci.oct.entities.member.Country;
import eu.europa.ec.eci.oct.entities.member.Language;
import eu.europa.ec.eci.oct.persistence.CountryDAO;
import eu.europa.ec.eci.oct.persistence.PersistenceException;

public class JpaCountryDAO extends AbstractJpaDAO implements CountryDAO {

	Logger logger = Logger.getLogger(JpaCountryDAO.class);

	JpaCountryDAO(EntityManager entityManager) {
		super(entityManager);
	}

	@Override
	public List<Country> getCountries() throws PersistenceException {
		try {
			logger.debug("querying all countries registered within system");

			CriteriaBuilder cb = em.getCriteriaBuilder();
			CriteriaQuery<Country> query = cb.createQuery(Country.class);
			Root<Country> croot = query.from(Country.class);
			query.select(croot);
			query.orderBy(cb.asc(croot.get("code")));
			List<Country> cl = em.createQuery(query).getResultList();

			if (logger.isDebugEnabled()) {
				logger.debug("returning list containing " + cl.size() + " countries");
			}

			return cl;
		} catch (Exception e) {
			throw wrapException("getCountries", e);
		}
	}

	@Override
	public List<Language> getLanguages() throws PersistenceException {
		try {
			logger.debug("querying all languages registered within system");

			CriteriaBuilder cb = em.getCriteriaBuilder();
			CriteriaQuery<Language> query = cb.createQuery(Language.class);
			Root<Language> lroot = query.from(Language.class);
			query.select(lroot);
			query.orderBy(cb.asc(lroot.get("displayOrder")));
			List<Language> cl = em.createQuery(query).getResultList();

			if (logger.isDebugEnabled()) {
				logger.debug("returning list containing " + cl.size() + " languages");
			}

			return cl;
		} catch (Exception e) {
			throw wrapException("getLanguages", e);
		}
	}

	@Override
	public Country getCountryByCode(String code) throws PersistenceException {
		try {
			if (logger.isDebugEnabled()) {
				logger.debug("querying for country by code " + code);
			}

			CriteriaBuilder cb = em.getCriteriaBuilder();
			CriteriaQuery<Country> query = cb.createQuery(Country.class);
			Root<Country> countryRoot = query.from(Country.class);
			query.where(cb.equal(countryRoot.get("code"), code));
			Country country = null;
			try {
				country = em.createQuery(query).getSingleResult();
			} catch (NoResultException nre) {
				logger.warn("no country with code " + code + " found");
			}

			return country;
		} catch (Exception e) {
			throw wrapException("getCountryByCode " + code, e);
		}
	}

	@Override
	public Language getLanguageByCode(String code) throws PersistenceException {
		try {
			if (logger.isDebugEnabled()) {
				logger.debug("querying country by code " + code);
			}

			CriteriaBuilder cb = em.getCriteriaBuilder();
			CriteriaQuery<Language> query = cb.createQuery(Language.class);
			Root<Language> countryRoot = query.from(Language.class);
			query.where(cb.equal(countryRoot.get("code"), code));
			Language lang = null;
			try {
				lang = em.createQuery(query).getSingleResult();
			} catch (NoResultException nre) {
				logger.warn("no language with code " + code);
			}

			return lang;
		} catch (Exception e) {
			throw wrapException("getLanguageByCode " + code, e);
		}
	}

}
