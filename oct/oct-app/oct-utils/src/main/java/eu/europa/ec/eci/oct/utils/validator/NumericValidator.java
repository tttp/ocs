package eu.europa.ec.eci.oct.utils.validator;

public class NumericValidator extends AbstractValidator {

	@Override
	public boolean validateDataType(String value) {
		if (value == null || "".equals(value)) {
			return true;
		}

		boolean result = true;

		try {
			Integer.parseInt(value);
		} catch (NumberFormatException e) {
			result = false;
		}

		return result;
	}

	@Override
	public boolean validateSize(String value, int minSize, int maxSize) {
		return true;
	}

	@Override
	public boolean validateRange(String value, String minValue, String maxValue) {
		try {
			long v = Long.parseLong(value);
			long min = Long.parseLong(minValue);
			long max = Long.parseLong(maxValue);

			return v >= min && v <= max;
		} catch (NumberFormatException ex) {
			return false;
		}
	}
}
