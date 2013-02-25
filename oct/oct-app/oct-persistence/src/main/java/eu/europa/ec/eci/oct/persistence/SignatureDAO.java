package eu.europa.ec.eci.oct.persistence;

import java.util.List;

import eu.europa.ec.eci.oct.entities.member.Country;
import eu.europa.ec.eci.oct.entities.signature.Signature;
import eu.europa.ec.eci.oct.entities.signature.SignatureCountPerCountry;
import eu.europa.ec.eci.oct.vo.export.ExportParametersBean;
import eu.europa.ec.eci.oct.vo.export.ExportPropertyBean;
import eu.europa.ec.eci.oct.vo.export.ExportSignatureBean;

public interface SignatureDAO {

	public List<Signature> getAllSignatures() throws PersistenceException;

	public List<Signature> getSignaturesForCountry(Country c) throws PersistenceException;

	public void insertSignature(Signature s) throws PersistenceException;

	public Signature findByFingerprint(Signature pattern) throws PersistenceException;

	public List<SignatureCountPerCountry> countSignatures(ExportParametersBean parameters) throws PersistenceException;

	public Signature findByUuid(String uuid) throws PersistenceException;

	public void deleteSignature(Signature signature) throws PersistenceException;

	public List<ExportSignatureBean> getSignatures(ExportParametersBean parameters) throws PersistenceException;

	public List<ExportPropertyBean> getPropertiesForSignatures(List<Long> ids) throws PersistenceException;

	public void deleteAllSignatures() throws PersistenceException;
}
