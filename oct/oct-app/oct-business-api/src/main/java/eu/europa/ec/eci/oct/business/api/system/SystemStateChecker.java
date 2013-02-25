package eu.europa.ec.eci.oct.business.api.system;

import org.apache.log4j.Logger;

import eu.europa.ec.eci.oct.entities.admin.SystemState;

/**
 * Abstract class for controlling state. Provides methods to verify whether the current state 
 * is in list of allowed states or disallowed states
 *
 * @author Dzierzak Marcin
 */
public class SystemStateChecker {

	private Logger logger = Logger.getLogger(SystemStateChecker.class);
	
	private SystemState state;	
	
	private SystemStateChecker(SystemState state){
		this.state = state;
	}
	
	
	/**
	 * Provides checker instance
	 * 
	 * @param stateProvider - state provider instance
	 * @return
	 */
	public static SystemStateChecker getController(SystemStateProvider stateProvider){
		return new SystemStateChecker(stateProvider.getCurrentState());
	}
	
	/**
	 * Check whether the current state is on a list 
	 * 
	 * @param states - states  
	 * @return
	 */
	public boolean isInState(SystemState... states){
		boolean found = isOnList(states);
		
		return found;
	}

	/**
	 * Check if the current state is on a list of allowed states
	 * 
	 * @param states - list of allowed states
	 * @throws OCTIllegalStateException - if the current state is not on a list of allowed states
	 */
	public void allowedStates(SystemState... states) throws OCTIllegalStateException {
		
		boolean found = isOnList(states);
		
		if(!found){			
			logger.error("Illegal system state detected [" + this.state + "], list of legal states [" + toStringList(states) + "]");
			throw new OCTIllegalStateException("Illegal system state: " + this.state, this.state);		
		}
	}

	/**
	 * Check if the current state is on a list of disallowed states
	 * 
	 * @param states - list of disallowed states
	 * @throws OCTIllegalStateException - if the current state is on a list of disallowed states
	 */
	public void disallowedStates(SystemState... states) throws OCTIllegalStateException {
		
		boolean found = isOnList(states);
		
		if(found){			
			logger.error("Illegal system state detected [" + this.state + "]");
			throw new OCTIllegalStateException("Illegal system state: " + this.state, this.state);		
		}
	}


	private boolean isOnList(SystemState... states) {
		boolean found = false;		
		for (SystemState state : states) {
			if(state.equals(this.state)){
				found = true;
				break;
			}
		}
		return found;
	}

	private String toStringList(SystemState[] states) {
		StringBuffer buf = new StringBuffer();
		for (SystemState state : states) {
			if(buf.length()!=0) buf.append(", ");
			buf.append(state);
		}
		return buf.toString();
	}

}