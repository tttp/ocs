package eu.europa.ec.eci.oct.business.api;

import java.util.List;
import java.util.Map;

import eu.europa.ec.eci.oct.entities.CountryProperty;
import eu.europa.ec.eci.oct.entities.PropertyGroup;
import eu.europa.ec.eci.oct.entities.member.Country;
import eu.europa.ec.eci.oct.entities.signature.Signature;
import eu.europa.ec.eci.oct.entities.signature.SignatureCountPerCountry;
import eu.europa.ec.eci.oct.vo.export.ExportParametersBean;
import eu.europa.ec.eci.oct.vo.export.ExportPropertyBean;
import eu.europa.ec.eci.oct.vo.export.ExportSignatureBean;

public interface SignatureService {

	public Signature insertSignature(Signature signature) throws OCTException;

	public List<PropertyGroup> getPropertyGroups() throws OCTException;

	public List<CountryProperty> getProperties(Country country, PropertyGroup group) throws OCTException;

	public List<CountryProperty> getAllCountryProperties() throws OCTException;

	public CountryProperty getCountryPropertyById(Long id) throws OCTException;

	public List<SignatureCountPerCountry> getSignatureCounts(ExportParametersBean parameters) throws OCTException;

	public Signature findByUuid(String uuid) throws OCTException;

	public void deleteSignature(Signature signature) throws OCTException;
	
	public void deleteAllSignatures() throws OCTException;

	public Map<Long, ExportSignatureBean> getSignaturesForExport(ExportParametersBean parameters) throws OCTException;

	public List<ExportPropertyBean> getPropertiesForSignatures(List<Long> ids) throws OCTException;
}
