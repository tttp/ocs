package eu.europa.ec.eci.oct.persistence.jpa;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import eu.europa.ec.eci.oct.entities.member.Country;
import eu.europa.ec.eci.oct.entities.signature.Signature;
import eu.europa.ec.eci.oct.entities.signature.SignatureCountPerCountry;
import eu.europa.ec.eci.oct.persistence.NoResultPersistenceException;
import eu.europa.ec.eci.oct.persistence.PersistenceException;
import eu.europa.ec.eci.oct.persistence.SignatureDAO;
import eu.europa.ec.eci.oct.vo.export.ExportParametersBean;
import eu.europa.ec.eci.oct.vo.export.ExportPropertyBean;
import eu.europa.ec.eci.oct.vo.export.ExportSignatureBean;

public class JpaSignatureDAO extends AbstractJpaDAO implements SignatureDAO {

	Logger logger = Logger.getLogger(JpaSignatureDAO.class);

	JpaSignatureDAO(EntityManager entityManager) {
		super(entityManager);
	}

	@Override
	public List<Signature> getAllSignatures() throws PersistenceException {
		try {
			logger.debug("querying signatures");

			CriteriaQuery<Signature> query = em.getCriteriaBuilder().createQuery(Signature.class);
			Root<Signature> sroot = query.from(Signature.class);
			query.select(sroot);
			List<Signature> sl = em.createQuery(query).getResultList();

			if (logger.isDebugEnabled()) {
				logger.debug("returning list containing " + sl.size() + " items");
			}
			return sl;
		} catch (Exception e) {
			throw wrapException("getAllSignatures", e);
		}
	}

	@Override
	public List<Signature> getSignaturesForCountry(Country c) throws PersistenceException {
		try {
			if (logger.isDebugEnabled()) {
				logger.debug("querying signatures for country " + c.getName());
			}

			CriteriaBuilder cb = em.getCriteriaBuilder();
			CriteriaQuery<Signature> query = cb.createQuery(Signature.class);
			Root<Signature> sRoot = query.from(Signature.class);
			query.where(cb.equal(sRoot.get("countryToSignFor"), c));
			List<Signature> sl = em.createQuery(query).getResultList();

			if (logger.isDebugEnabled()) {
				logger.debug("returning list containing " + sl.size() + " items");
			}

			return sl;
		} catch (Exception e) {
			throw wrapException("getSignaturesForCountry " + c, e);
		}
	}

	@Override
	public void insertSignature(Signature signature) throws PersistenceException {
		try {
			if (logger.isDebugEnabled()) {
				logger.debug("inserting signature");
			}

			em.persist(signature);

			em.flush();

			logger.debug("signature persisted");
		} catch (Exception e) {
			throw wrapException("insertSignature " + signature, e);
		}
	}

	public Signature findByFingerprint(Signature pattern) throws PersistenceException {
		try {
			if (logger.isDebugEnabled()) {
				logger.debug("looking up signature by fingerprint [" + pattern.getFingerprint() + "]");
			}

			CriteriaBuilder cb = em.getCriteriaBuilder();
			CriteriaQuery<Signature> query = cb.createQuery(Signature.class);
			Root<Signature> sRoot = query.from(Signature.class);
			query.where(cb.equal(sRoot.get("fingerprint"), pattern.getFingerprint()));
			Signature s = null;
			try {
				s = em.createQuery(query).getSingleResult();
			} catch (NoResultException e) {
				throw new NoResultPersistenceException("No signature with fingerprint " + pattern.getFingerprint(), e);
			}

			if (logger.isDebugEnabled()) {
				logger.debug("returning signature with fingerprint " + s.getFingerprint());
			}

			return s;
		} catch (NoResultPersistenceException e) {
			throw e;
		} catch (Exception e) {
			throw wrapException("findByFingerprint " + pattern, e);
		}
	}

	@Override
	public List<SignatureCountPerCountry> countSignatures(ExportParametersBean parameters) throws PersistenceException {
		try {
			String whereClause = computeWhereClause(parameters);

			String query;
			query = "SELECT new eu.europa.ec.eci.oct.entities.signature.SignatureCountPerCountry(c, count(s)) FROM Signature s JOIN s.countryToSignFor c "
					+ whereClause + " GROUP BY s.countryToSignFor";

			return countSignatures(query, parameters);
		} catch (Exception e) {
			throw wrapException("countSignatures " + parameters, e);
		}
	}

	private List<SignatureCountPerCountry> countSignatures(String query, ExportParametersBean parameters)
			throws PersistenceException {
		try {
			Query q = em.createQuery(query);
			if (parameters.getDescriptionLanguageId() != -1) {
				q.setParameter("descriptionId", parameters.getDescriptionLanguageId());
			}
			if (parameters.getStartDate() != null) {
				q.setParameter("startDate", parameters.getStartDate());
			}
			Date endDate = parameters.getEndDate();
			if (endDate != null) {
				// make end date inclusive
				endDate = new Date(endDate.getTime() + (23 * 60 * 60 * 1000 + 59 * 60 * 100 + 59 * 1000 + 999));
				q.setParameter("endDate", endDate);
			}
			if (parameters.getCountryId() != -1) {
				q.setParameter("countryId", parameters.getCountryId());
			}

			@SuppressWarnings("unchecked")
			List<SignatureCountPerCountry> result = q.getResultList();
			return result;
		} catch (Exception e) {
			throw wrapException("countSignatures " + query + " " + parameters, e);
		}
	}

	private String computeWhereClause(ExportParametersBean parameters) {
		final StringBuilder whereClause = new StringBuilder();
		if (parameters.getStartDate() != null || parameters.getEndDate() != null || parameters.getCountryId() != -1) {
			if (parameters.getDescriptionLanguageId() != -1) {
				whereClause.append(" s.description.id = :descriptionId ");
			}
			if (parameters.getStartDate() != null) {
				whereClause.append((whereClause.length() > 0 ? " AND " : "")).append(
						" s.dateOfSignature >= :startDate ");
			}
			if (parameters.getEndDate() != null) {
				whereClause.append((whereClause.length() > 0 ? " AND " : "")).append(" s.dateOfSignature <= :endDate ");
			}
			if (parameters.getCountryId() != -1) {
				whereClause.append((whereClause.length() > 0 ? " AND " : "")).append(
						" s.countryToSignFor.id = :countryId ");
			}
			whereClause.insert(0, " WHERE ");
		}

		return whereClause.toString();
	}

	@Override
	public void deleteSignature(Signature signature) throws PersistenceException {
		try {
			em.remove(em.merge(signature));
		} catch (Exception e) {
			throw wrapException("deleteSignature " + signature, e);
		}
	}

	@Override
	public Signature findByUuid(String uuid) throws PersistenceException {
		try {
			if (logger.isDebugEnabled()) {
				logger.debug("looking up signature by uuid [" + uuid + "]");
			}

			CriteriaBuilder cb = em.getCriteriaBuilder();
			CriteriaQuery<Signature> query = cb.createQuery(Signature.class);
			Root<Signature> sRoot = query.from(Signature.class);
			query.where(cb.equal(sRoot.get("uuid"), uuid));
			Signature signature = null;
			try {
				signature = em.createQuery(query).getSingleResult();
			} catch (NoResultException e) {
				throw new NoResultPersistenceException("No signature with uuid " + uuid, e);
			}

			if (logger.isDebugEnabled()) {
				logger.debug("returning signature with fingerprint " + signature.getFingerprint());
			}

			return signature;
		} catch (NoResultPersistenceException e) {
			throw e;
		} catch (Exception e) {
			throw wrapException("findByUuid " + uuid, e);
		}
	}

	@Override
	public List<ExportSignatureBean> getSignatures(ExportParametersBean parameters) throws PersistenceException {
		try {
			String whereClause = computeWhereClause(parameters);

			String query = "SELECT new eu.europa.ec.eci.oct.vo.export.ExportSignatureBean(s.id, s.dateOfSignature, s.uuid) "
					+ "FROM Signature s " + whereClause + " ORDER BY s.dateOfSignature";

			Query q = em.createQuery(query);
			if (parameters.getDescriptionLanguageId() != -1) {
				q.setParameter("descriptionId", parameters.getDescriptionLanguageId());
			}
			if (parameters.getStartDate() != null) {
				q.setParameter("startDate", parameters.getStartDate());
			}
			if (parameters.getEndDate() != null) {
				// make end date inclusive
				parameters.setEndDate(new Date(parameters.getEndDate().getTime()
						+ (23 * 60 * 60 * 1000 + 59 * 60 * 100 + 59 * 1000 + 999)));
				q.setParameter("endDate", parameters.getEndDate());
			}
			if (parameters.getCountryId() != -1) {
				q.setParameter("countryId", parameters.getCountryId());
			}
			q.setFirstResult(parameters.getStart());
			q.setMaxResults(parameters.getOffset());

			@SuppressWarnings("unchecked")
			List<ExportSignatureBean> result = q.getResultList();
			return result;
		} catch (Exception e) {
			throw wrapException("getSignatures " + parameters, e);
		}
	}

	@Override
	public List<ExportPropertyBean> getPropertiesForSignatures(List<Long> ids) throws PersistenceException {
		try {
			List<ExportPropertyBean> result = new ArrayList<ExportPropertyBean>();

			final int MAX_IDS = 500;
			final int chunks = ids.size() / MAX_IDS + (ids.size() % MAX_IDS > 0 ? 1 : 0);
			if (logger.isEnabledFor(Level.DEBUG)) {
				logger.debug("Splitted the ids array into " + chunks + " chunks.");
			}
			for (int idx = 0; idx < chunks; idx++) {
				String query = "SELECT new eu.europa.ec.eci.oct.vo.export.ExportPropertyBean(p.value, p.property.id, p.signature.id) "
						+ "FROM PropertyValue p WHERE p.signature.id IN :ids";
				Query q = em.createQuery(query);
				final int start = idx * MAX_IDS;
				final int stop = start + MAX_IDS > ids.size() ? ids.size() : start + MAX_IDS;
				q.setParameter("ids", ids.subList(start, stop));

				@SuppressWarnings("unchecked")
				List<ExportPropertyBean> chunkResult = q.getResultList();
				result.addAll(chunkResult);
			}

			return result;
		} catch (Exception e) {
			throw wrapException("getPropertiesForSignatures [list of size " + ids.size() + "]", e);
		}
	}

	@Override
	public void deleteAllSignatures() throws PersistenceException {
		try {
			// cascade is enabled, so property values will also be deleted
			String query = "DELETE FROM Signature";
			Query q = em.createQuery(query);
			q.executeUpdate();
		} catch (Exception e) {
			throw wrapException("deleteAllSignatures", e);
		}
	}

}