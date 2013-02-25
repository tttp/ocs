package eu.europa.ec.eci.oct.business.api;

import javax.ejb.ApplicationException;

@ApplicationException(rollback = true)
public class OCTMissingCertificateException extends OCTException {

	private static final long serialVersionUID = 1408798200354024607L;

	public OCTMissingCertificateException(String message) {
		super(message);
	}

}
