package eu.europa.ec.eci.oct.persistence;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;

import eu.europa.ec.eci.oct.persistence.jpa.JpaDAOFactory;

public class TestDAOFactory extends JpaDAOFactory {

	protected EntityManager em;
	
	public TestDAOFactory(String unitName){
		em = Persistence.createEntityManagerFactory(unitName).createEntityManager();
	}
	
	@Override
	public EntityManager getEntityManager() {
		return em;
	}

}
