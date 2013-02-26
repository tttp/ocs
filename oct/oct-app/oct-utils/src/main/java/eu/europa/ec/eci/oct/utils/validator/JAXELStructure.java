package eu.europa.ec.eci.oct.utils.validator;

import java.util.Map;

public class JAXELStructure {

	public static final String PROPERTY_PREFIX = "oct.property.";
	public static final String DEFAULT_VALUE = "N/A";

	private final Map<String, String> properties;

	public JAXELStructure(Map<String, String> properties) {
		this.properties = properties;
	}

	public String gp(String property) {
		if (property == null || "".equals(property)) {
			return DEFAULT_VALUE;
		}

		String result = DEFAULT_VALUE;

		// shorthand notation
		if (!property.startsWith(PROPERTY_PREFIX)) {
			property = PROPERTY_PREFIX + property;
		}

		final String value = properties.get(property);
		if (value != null) {
			result = properties.get(property);
		}
		
		return result;
	}
}
