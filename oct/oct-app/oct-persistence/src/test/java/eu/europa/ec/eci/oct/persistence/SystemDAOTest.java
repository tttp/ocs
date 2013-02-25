package eu.europa.ec.eci.oct.persistence;

import org.junit.Test;

import junit.framework.Assert;
import eu.europa.ec.eci.oct.entities.admin.InitiativeDescription;
import eu.europa.ec.eci.oct.entities.admin.SystemPreferences;

public class SystemDAOTest extends DBTestSuite {

	@Test
	public void testGetPreferences(){
		
		try {
			SystemPreferences prefs = daof.getSystemPreferencesDAO().getPreferences();
			Assert.assertNotNull("preferences should not be null", prefs);
		} catch (NoResultPersistenceException e) {
			e.printStackTrace();
			Assert.fail("no system preferences found");
		} catch (PersistenceException e) {
			e.printStackTrace();
			Assert.fail("persistence exception while fetching preferences");
		}
				
	}
	
	@Test
	public void testGetDefaultDescription(){
		
		try {
			SystemPreferences prefs = daof.getSystemPreferencesDAO().getPreferences();
			InitiativeDescription desc = prefs.getDefaultDescription();
			
			if(desc==null){
				Assert.fail("missing default description");				
			}
			
			
		} catch (NoResultPersistenceException e) {
			e.printStackTrace();
			Assert.fail("no system preferences found");
		} catch (PersistenceException e) {
			e.printStackTrace();
			Assert.fail("persistence exception while fetching preferences");
		}
				
	}
	
}
