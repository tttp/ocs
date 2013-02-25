package eu.europa.ec.eci.oct.business.system;

import eu.europa.ec.eci.oct.business.api.OCTException;
import eu.europa.ec.eci.oct.business.api.system.SystemStateProvider;
import eu.europa.ec.eci.oct.entities.admin.SystemState;
import eu.europa.ec.eci.oct.persistence.DAOFactory;
import eu.europa.ec.eci.oct.persistence.PersistenceException;

/**
 * System state provider basing on DAO
 *
 * @author Dzierzak Marcin
 */
public class DBSystemStateProvider implements SystemStateProvider {
	
	private SystemState state;

	public DBSystemStateProvider(DAOFactory daof) throws OCTException{
		try {
			this.state = daof.getSystemPreferencesDAO().getPreferences().getState();
		} catch (PersistenceException e) {
			throw new OCTException("could not fetch system state", e);
		}
	}
	
	@Override
	public SystemState getCurrentState() {
		return state;
	}

}
