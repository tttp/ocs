package eu.europa.ec.eci.oct.eci.export;

public class ECIDataException extends Exception {

	private static final long serialVersionUID = 4167173534588938134L;

	public ECIDataException(String message, Throwable cause) {
		super(message, cause);		
	}

	public ECIDataException(String message) {
		super(message);		
	}

}
