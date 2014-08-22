package eu.europa.ec.eci.oct.validation.core;

/**
 * 
 * @author Daniel CHIRITA, DIGIT B.1, EC
 *
 */
public class ValidatorFactory {

	private ValidatorFactory() {

	}

	public static AbstractValidator getValidatorFor(DataType type) {
		switch (type) {
		case NUMERIC:
			return new NumericValidator();
		case DATE:
			return new DateValidator();
		default:
			return new DefaultValidator();
		}
	}
}
