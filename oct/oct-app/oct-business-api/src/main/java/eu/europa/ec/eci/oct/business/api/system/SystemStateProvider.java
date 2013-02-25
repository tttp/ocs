package eu.europa.ec.eci.oct.business.api.system;

import eu.europa.ec.eci.oct.entities.admin.SystemState;

public interface SystemStateProvider {

	SystemState getCurrentState();
	
}
