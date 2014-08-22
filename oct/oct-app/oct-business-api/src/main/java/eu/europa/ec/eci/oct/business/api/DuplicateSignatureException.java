package eu.europa.ec.eci.oct.business.api;

import javax.ejb.ApplicationException;

@ApplicationException(rollback = true)
public class DuplicateSignatureException extends OCTException {

	private static final long serialVersionUID = -7170578125455241888L;

	public DuplicateSignatureException(String message, Throwable cause) {
		super(message, cause);
	}

	public DuplicateSignatureException(String message) {
		super(message);
	}

}
