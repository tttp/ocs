package eu.europa.ec.eci.oct.persistence;

import java.util.List;

import junit.framework.Assert;

import org.junit.Test;

import eu.europa.ec.eci.oct.entities.CountryProperty;
import eu.europa.ec.eci.oct.entities.PropertyGroup;
import eu.europa.ec.eci.oct.entities.member.Country;

public class PropertyDAOTest extends DBTestSuite {

	@Test
	public void testGetGroups(){
		try {
			List<PropertyGroup> groups = daof.getPropertyDAO().getGroups();
			Assert.assertEquals("Group items amount doesn't match expected 3 values", 3, groups.size());
		} catch (PersistenceException e) {
			e.printStackTrace();
			Assert.fail("persistence exception");
		}		
	}	
	
	@Test
	public void testGetPropertiesForGroup(){
		try {
			Country c = daof.getCountryDAO().getCountryByCode("PL");			
			List<PropertyGroup> groups = daof.getPropertyDAO().getGroups();			
			
			boolean groupFound = false;
			for (PropertyGroup group : groups) {
				if(group.getName().equalsIgnoreCase("oct.group.address")){
					groupFound = true;				
					List<CountryProperty> cp = daof.getPropertyDAO().getProperties(c, group);
					Assert.assertEquals("Group members amount does not match expected 2 items", 2, cp.size());
				}
			}
			Assert.assertTrue("Group PL_address have not found", groupFound);
			
		} catch (PersistenceException e) {
			e.printStackTrace();
			Assert.fail("persistence exception");
		}		
	}
}
