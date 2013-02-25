package eu.europa.ec.eci.oct.utils.validator;

public class DefaultValidator extends AbstractValidator {

	@Override
	public boolean validateDataType(String value) {
		return true;
	}

	@Override
	public boolean validateSize(String value, int minSize, int maxSize) {
		if (value == null) {
			return false;
		}

		int size = value.length();
		return size >= minSize && size <= maxSize;
	}

	@Override
	public boolean validateRange(String value, String minValue, String maxValue) {
		return true;
	}

}
