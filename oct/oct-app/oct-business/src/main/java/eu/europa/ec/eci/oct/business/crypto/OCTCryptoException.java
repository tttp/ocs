package eu.europa.ec.eci.oct.business.crypto;

import javax.ejb.ApplicationException;

import eu.europa.ec.eci.oct.business.api.OCTException;

@ApplicationException(rollback = true)
public class OCTCryptoException extends OCTException {
	
	private static final long serialVersionUID = -7536176224528337420L;
	
	public OCTCryptoException(String message) {
		super(message);		
	}
	
	public OCTCryptoException(String message, Throwable cause) {
		super(message, cause);		
	}
}
