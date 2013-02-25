package eu.europa.ec.eci.oct.utils.validator;



public abstract class AbstractValidator {

	public boolean validateNotEmpty(String value) {
		return value != null && !"".equals(value);
	}

	public abstract boolean validateDataType(String value);

	public abstract boolean validateSize(String value, int minSize, int maxSize);

	public abstract boolean validateRange(String value, String minValue, String maxValue);

	public boolean validateRegularExpression(String value, String regexp) {
		if (value == null) {
			return false;
		}
		return value.replaceAll("\\s+", "").toLowerCase().matches(regexp);
	}

}
