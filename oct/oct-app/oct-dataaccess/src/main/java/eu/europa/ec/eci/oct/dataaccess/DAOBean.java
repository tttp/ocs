package eu.europa.ec.eci.oct.dataaccess;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import eu.europa.ec.eci.oct.persistence.DAOFactory;
import eu.europa.ec.eci.oct.persistence.jpa.JpaDAOFactory;

/**
 * @author dzierma
 *
 */
@Stateless
@Local(DAOFactory.class)
public class DAOBean extends JpaDAOFactory implements DAOFactory {
	
	@PersistenceContext(unitName="oct") protected EntityManager em;

	@Override
	public EntityManager getEntityManager() {
		return em;
	}

}
