package eu.europa.ec.eci.oct.webcommons.security;

import org.springframework.ui.Model;

import eu.europa.ec.eci.oct.business.api.system.SystemStateProvider;
import eu.europa.ec.eci.oct.entities.admin.SystemState;
import eu.europa.ec.eci.oct.webcommons.controller.CommonControllerConstants;

public class ModelSystemStateProvider implements SystemStateProvider {
	
	private SystemState state;
	
	public ModelSystemStateProvider(Model model){
		this.state = (SystemState) model.asMap().get(CommonControllerConstants.MODEL_ATTRIBUTE_SYSTEM_STATE);
	}

	@Override
	public SystemState getCurrentState() {
		return state;
	}

}
