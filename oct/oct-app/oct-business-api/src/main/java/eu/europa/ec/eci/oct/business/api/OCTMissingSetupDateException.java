package eu.europa.ec.eci.oct.business.api;

import javax.ejb.ApplicationException;

@ApplicationException(rollback = true)
public class OCTMissingSetupDateException extends OCTException {

	private static final long serialVersionUID = 1935092589037813835L;

	public OCTMissingSetupDateException(String message) {
		super(message);		
	}

}
