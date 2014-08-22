package eu.europa.ec.eci.oct.validation;

import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import eu.europa.ec.eci.oct.entities.CountryProperty;
import eu.europa.ec.eci.oct.entities.PropertyType;
import eu.europa.ec.eci.oct.entities.rules.RuleParameter;
import eu.europa.ec.eci.oct.entities.rules.ValidationRule;
import eu.europa.ec.eci.oct.validation.core.AbstractValidator;
import eu.europa.ec.eci.oct.validation.core.JAXELStructure;
import eu.europa.ec.eci.oct.validation.core.ValidatorFactory;

/**
 * 
 * @author Daniel CHIRITA, DIGIT B.1, EC
 * 
 */
public class OCSValidator {

	private JAXELStructure structure;
	private Map<String, String> properties;

	public OCSValidator() {
		properties = new HashMap<String, String>();
		structure = new JAXELStructure(properties);
	}

	public void addProperty(String key, String value) {
		properties.put(key, value);
	}

	public void clearProperties() {
		properties.clear();
	}

	public OCSValidationResult validate(CountryProperty countryProperty, String value) {
		final OCSValidationResult result = new OCSValidationResult();

		final PropertyType type = countryProperty.getProperty().getType();

		// instantiate validator based on property type
		final AbstractValidator validator = ValidatorFactory.getValidatorFor(PropertyType2DataType.comvert(type));
		// validate required (if needed)
		if (countryProperty.isRequired() && !validator.validateNotEmpty(value)) {
			result.setFailed(true);
			result.setReason("oct.empty.property");

			return result;
		}

		// validate data type
		if (!validator.validateDataType(value)) {
			result.setFailed(true);
			result.setReason("oct.error.propertytypemismatch");

			return result;
		}

		@SuppressWarnings("rawtypes")
		Set<ValidationRule> validationRules = new LinkedHashSet<ValidationRule>();

		// get global rules for the given property
		if (countryProperty.getProperty().getRules() != null) {
			validationRules.addAll(countryProperty.getProperty().getRules());
		}

		// get local rules for the given country property
		if (countryProperty.getRules() != null) {
			validationRules.addAll(countryProperty.getRules());
		}

		// apply all validation rules
		for (@SuppressWarnings("rawtypes")
		ValidationRule validationRule : validationRules) {
			result.setCanBeSkipped(validationRule.isCanBeSkipped());

			@SuppressWarnings({ "unchecked", "rawtypes" })
			Set<RuleParameter> parameters = validationRule.getRuleParameters();

			String errorMessage = validationRule.getErrorMessage();

			// for each rule, invoke needed validation method
			// also get the right parameters for each rule
			switch (validationRule.getRuleType()) {
			case RANGE:
				String minValue = "";
				String maxValue = "";
				for (@SuppressWarnings("rawtypes")
				RuleParameter parameter : parameters) {
					switch (parameter.getParameterType()) {
					case MIN:
						minValue = parameter.getValue();
						break;
					case MAX:
						maxValue = parameter.getValue();
						break;
					default:
						break;
					}
				}

				if (!validator.validateRange(value, minValue, maxValue)) {
					if (errorMessage == null || "".equals(errorMessage)) {
						errorMessage = "oct.error.invalidrange";
					}

					result.setFailed(true);
					result.setReason(errorMessage);

					return result;
				}
				break;
			case SIZE:
				int minSize = 0;
				int maxSize = Integer.MAX_VALUE;
				for (@SuppressWarnings("rawtypes")
				RuleParameter parameter : parameters) {
					switch (parameter.getParameterType()) {
					case MIN:
						minSize = Integer.parseInt(parameter.getValue());
						break;
					case MAX:
						maxSize = Integer.parseInt(parameter.getValue());
						break;
					default:
						break;
					}
				}

				if (!validator.validateSize(value, minSize, maxSize)) {
					if (errorMessage == null || "".equals(errorMessage)) {
						errorMessage = "oct.error.invalidsize";
					}

					result.setFailed(true);
					result.setReason(errorMessage);

					return result;
				}
				break;
			case REGEXP:
				String regexp = "";
				for (@SuppressWarnings("rawtypes")
				RuleParameter parameter : parameters) {
					switch (parameter.getParameterType()) {
					case REGEXP:
						regexp = parameter.getValue();
						break;
					default:
						break;
					}
				}

				if (!validator.validateRegularExpression(value, regexp)) {
					if (errorMessage == null || "".equals(errorMessage)) {
						errorMessage = "oct.error.propertytypemismatch";
					}

					result.setFailed(true);
					result.setReason(errorMessage);

					return result;
				}
				break;
			case JAVAEXP:
				String javaExpression = "";
				for (@SuppressWarnings("rawtypes")
				RuleParameter parameter : parameters) {
					switch (parameter.getParameterType()) {
					case JAVAEXP:
						javaExpression = parameter.getValue();
						break;
					default:
						break;
					}
				}

				if (!validator.validateJavaExpression(javaExpression, structure)) {
					if (errorMessage == null || "".equals(errorMessage)) {
						errorMessage = "oct.error.propertytypemismatch";
					}

					result.setFailed(true);
					result.setReason(errorMessage);

					return result;
				}
				break;
			default:
				break;
			}
		}

		return result;
	}

}
