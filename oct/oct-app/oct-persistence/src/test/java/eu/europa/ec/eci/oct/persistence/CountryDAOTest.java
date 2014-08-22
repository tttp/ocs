package eu.europa.ec.eci.oct.persistence;

import java.util.List;

import junit.framework.Assert;

import org.junit.Test;

import eu.europa.ec.eci.oct.entities.member.Country;

public class CountryDAOTest extends DBTestSuite {

	@Test
	public void testGetAllCountries(){
		List<Country> countries = null;
		try {
			countries = daof.getCountryDAO().getCountries();
		} catch (PersistenceException e) {
			e.printStackTrace();
			Assert.fail("persistence exception");
		}
		Assert.assertEquals("Country items amount doesn't match expected 5 values", 5, countries.size());
	}
	
	@Test
	public void testGetCountryByCode(){
		try {
			Country country = daof.getCountryDAO().getCountryByCode("PL");
			Assert.assertNotNull("Country PL not found", country);
			Assert.assertEquals("Returned country does not match expected 'Poland'", "Poland", country.getName());
		} catch (PersistenceException e) {
			e.printStackTrace();
			Assert.fail("persistence exception");
		}		
	}
	
	@Test
	public void testGetMissingCountryByCode(){
		try {
			Country country = daof.getCountryDAO().getCountryByCode("XX");
			Assert.assertNull("Country XX should be null", country);			
		} catch (PersistenceException e) {
			e.printStackTrace();
			Assert.fail("persistence exception");
		}		
	}
	
	@Test
	public void testLanguageForCountry(){
		try {
			Country country = daof.getCountryDAO().getCountryByCode("BE");
			Assert.assertNotNull("Country BE not found", country);
			Assert.assertNotNull("Language list for country BE is empty", country.getLanguages());
			Assert.assertEquals("Language list size for country BE does not match expected 2 items", 2, country.getLanguages().size());
			
		} catch (PersistenceException e) {
			e.printStackTrace();
			Assert.fail("persistence exception");
		}		
	}
	
}
