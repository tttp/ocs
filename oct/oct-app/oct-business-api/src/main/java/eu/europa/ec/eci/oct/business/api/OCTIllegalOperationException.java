package eu.europa.ec.eci.oct.business.api;

import javax.ejb.ApplicationException;

@ApplicationException(rollback = true)
public class OCTIllegalOperationException extends OCTException {
	
	private static final long serialVersionUID = -4623973376504294318L;

	public OCTIllegalOperationException(String message) {
		super(message);		
	}

}
