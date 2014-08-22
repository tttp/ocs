package eu.europa.ec.eci.oct.persistence;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import junit.framework.Assert;

import org.junit.Test;

import eu.europa.ec.eci.oct.entities.CountryProperty;
import eu.europa.ec.eci.oct.entities.Property;
import eu.europa.ec.eci.oct.entities.PropertyGroup;
import eu.europa.ec.eci.oct.entities.PropertyType;
import eu.europa.ec.eci.oct.entities.member.Country;
import eu.europa.ec.eci.oct.entities.signature.PropertyValue;
import eu.europa.ec.eci.oct.entities.signature.Signature;
import eu.europa.ec.eci.oct.vo.export.ExportParametersBean;

public class SignatureDAOTest extends DBTestSuite {

	@Test
	public void testGetAllSignatures() {
		List<Signature> signatures = null;
		try {
			signatures = daof.getSignatureDAO().getAllSignatures();
			Assert.assertNotNull("Signatures list is empty", signatures);
			Assert.assertEquals("Signatures items amount doesn't match expected 3 values", 4, signatures.size());
		} catch (PersistenceException e) {
			e.printStackTrace();
			Assert.fail("persistence exception");
		}

	}

	public void testCountSignatures() {

		ExportParametersBean parameters = new ExportParametersBean();
		// parameters.
		try {
			daof.getSignatureDAO().countSignatures(parameters);
		} catch (PersistenceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Assert.fail("persistence exception");
		}
	}

	@Test
	public void testGetSignaturesByCountry() {
		try {
			Country country = daof.getCountryDAO().getCountryByCode("PL");

			List<Signature> signatures = daof.getSignatureDAO().getSignaturesForCountry(country);
			Assert.assertNotNull("Signatures list is empty", signatures);
			Assert.assertEquals("Signatures items amount doesn't match expected 2 values", 2, signatures.size());
		} catch (PersistenceException e) {
			e.printStackTrace();
			Assert.fail("persistence exception");
		}
	}

	private String getSampleValueForType(PropertyType type) {
		if (PropertyType.ALPHANUMERIC.equals(type)) {
			return "qwerty";
		} else if (PropertyType.DATE.equals(type)) {
			return new Date().toString();
		} else if (PropertyType.NUMERIC.equals(type)) {
			return "666";
		} else {
			return "unknownType";
		}
	}

	@Test
	public void testInsertSignature() {

		try {
			Country country = daof.getCountryDAO().getCountryByCode("DE");

			List<PropertyGroup> groups = daof.getPropertyDAO().getGroups();

			Signature signature = new Signature();
			signature.setDateOfSignature(new Date());
			signature.setCountryToSignFor(country);
			signature.setDescription(daof.getInitiativeDAO().getDescriptionById(1));
			signature.setFingerprint("a0a0a0");
			signature.setUuid("ca000a");

			List<PropertyValue> values = new ArrayList<PropertyValue>();

			for (PropertyGroup group : groups) {
				List<CountryProperty> mappings = daof.getPropertyDAO().getProperties(country, group);
				for (CountryProperty mapping : mappings) {
					Property p = mapping.getProperty();
					PropertyValue v = new PropertyValue();
					v.setProperty(mapping);
					v.setValue(getSampleValueForType(p.getType()));
					v.setSignature(signature);
					values.add(v);
				}
			}

			signature.setPropertyValues(values);

			// lets check amount of signatures for choosen country
			int signaturesCount = daof.getSignatureDAO().getSignaturesForCountry(country).size();

			((TestDAOFactory) daof).getEntityManager().getTransaction().begin();
			daof.getSignatureDAO().insertSignature(signature);
			((TestDAOFactory) daof).getEntityManager().getTransaction().commit();

			int signaturesCountAfter = daof.getSignatureDAO().getSignaturesForCountry(country).size();

			Assert.assertEquals("signatures count didn't increase after insertion", signaturesCount + 1,
					signaturesCountAfter);

		} catch (PersistenceException pe) {
			pe.printStackTrace();
			Assert.fail("persistence exception: " + pe.getMessage());
		}
	}

}
