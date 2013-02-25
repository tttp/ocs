package eu.europa.ec.eci.oct.web.validator;

import java.util.Set;

import org.springframework.validation.Errors;

import eu.europa.ec.eci.oct.entities.PropertyType;
import eu.europa.ec.eci.oct.entities.rules.RuleParameter;
import eu.europa.ec.eci.oct.entities.rules.ValidationRule;
import eu.europa.ec.eci.oct.entities.signature.PropertyValue;
import eu.europa.ec.eci.oct.utils.validator.AbstractValidator;
import eu.europa.ec.eci.oct.utils.validator.ValidatorFactory;
import eu.europa.ec.eci.oct.web.captcha.CaptchaService;
import eu.europa.ec.eci.oct.web.converters.PropertyType2DataType;
import eu.europa.ec.eci.oct.web.model.SupportFormBean;
import eu.europa.ec.eci.oct.webcommons.locale.MessageSourceAware;
import eu.europa.ec.eci.oct.webcommons.validator.BaseValidator;

/**
 * Validator class for validating the support initiative form. It provides all methods needed by the
 * {@link SupportInitiativeController}.
 * 
 * @author chiridl
 * 
 */
public class SupportFormValidator extends BaseValidator {

	public SupportFormValidator(MessageSourceAware messageSource) {
		super(messageSource);
	}

	public boolean supports(Class<?> clazz) {
		return SupportFormBean.class.isAssignableFrom(clazz);
	}

	/**
	 * Used to invoke all validation methods at once. Should be used by the presentation just before finishing the store
	 * initiative wizard.
	 */
	public void validate(Object target, Errors errors) {
		validatePrerequisites(target, errors);
		validateFields(target, errors);
		validateTerms(target, errors);
	}

	/**
	 * Validates the security code (captcha).
	 * 
	 * @param target
	 * @param errors
	 * @param captchaService
	 * @param captchaId
	 */
	public void validateCaptcha(Object target, Errors errors, CaptchaService captchaService, String captchaId) {
		SupportFormBean bean = (SupportFormBean) target;
		if (!captchaService.validateCaptcha(captchaId, bean.getCaptcha())) {
			errors.rejectValue("captcha", "oct.error.wrongcaptcha", "The security code is wrong! Please try again.");
		}		
	}

	/**
	 * Validates the terms and conditions (if the user has accepted them or not).
	 * 
	 * @param target
	 * @param errors
	 */
	public void validateTerms(Object target, Errors errors) {
		SupportFormBean bean = (SupportFormBean) target;
		if (!bean.isAccepted1()) {
			errors.rejectValue("accepted1", "oct.error.acceptterms1", "Please read and accept the Tems and conditions!");
		}
		if (!bean.isAccepted2()) {
			errors.rejectValue("accepted2", "oct.error.acceptterms2", "Please read and accept the Tems and conditions!");
		}
	}

	/**
	 * Validates the prerequisites for the initiative form (first name, last name and country).
	 * 
	 * @param target
	 * @param errors
	 */
	public void validatePrerequisites(Object target, Errors errors) {
		SupportFormBean bean = (SupportFormBean) target;
		if (bean.getCountryCode() == null || "".equals(bean.getCountryCode())) {
			errors.rejectValue("countryCode", "err.empty.country", "The country name cannot be empty!");
		}
	}

	/**
	 * Validates the custom fields for the initiative.
	 * 
	 * @param target
	 * @param errors
	 */
	public void validateFields(Object target, Errors errors) {
		SupportFormBean bean = (SupportFormBean) target;

		if (bean.getProperties() == null || bean.getProperties().size() == 0) {
			// should never happen
			errors.reject("oct.error.emptyprops",
					"The data values you entered are empty. Please contact the administrator!");
			return;
		}

		// traverse properties and validate each of them
		int idx = 0;
		for (PropertyValue propertyValue : bean.getProperties()) {
			String errorPath = new StringBuilder().append("properties[").append(idx).append("].value").toString();

			if (propertyValue.getProperty() == null) {
				errors.rejectValue(errorPath, "oct.error.emptyprops",
						"The data values you entered are empty. Please contact the administrator!");
				return;
			}

			String name = propertyValue.getProperty().getProperty().getName();
			PropertyType type = propertyValue.getProperty().getProperty().getType();
			String value = propertyValue.getValue();

			// instantiate validator based on property type
			AbstractValidator validator = ValidatorFactory.getValidatorFor(PropertyType2DataType.comvert(type));
			// validate required (if needed)
			if (propertyValue.getProperty().isRequired() && !validator.validateNotEmpty(value)) {
				errors.rejectValue(errorPath, "oct.empty.property",
						new Object[] { getMessageSource().getMessage(name) }, "The {0} cannot be empty!");
			} else {
				// validate data type
				if (!validator.validateDataType(value)) {
					errors.rejectValue(errorPath, "oct.error.propertytypemismatch", new Object[] { getMessageSource()
							.getMessage(name) }, "The {0} format is not valid!");
				} else {
					// apply validation rules for given property
					Set<ValidationRule> validationRules = propertyValue.getProperty().getProperty().getRules();
					for (ValidationRule validationRule : validationRules) {
						Set<RuleParameter> parameters = validationRule.getRuleParameters();

						// for each rule, invoke needed validation method
						// also get the right parameters for each rule
						switch (validationRule.getRuleType()) {
						case RANGE:
							String minValue = "";
							String maxValue = "";
							for (RuleParameter parameter : parameters) {
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
								errors.rejectValue(errorPath, "oct.error.invalidrange", new Object[] {
										getMessageSource().getMessage(name), minValue, maxValue },
										"The {0} value should be between {1} and {2}!");
							}
							break;
						case SIZE:
							int minSize = 0;
							int maxSize = Integer.MAX_VALUE;
							for (RuleParameter parameter : parameters) {
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
								errors.rejectValue(errorPath, "oct.error.invalidsize", new Object[] {
										getMessageSource().getMessage(name), minSize, maxSize },
										"The {0} size should be between {1} and {2} characters!");
							}
							break;
						case REGEXP:
							String regexp = "";
							for (RuleParameter parameter : parameters) {
								switch (parameter.getParameterType()) {
								case REGEXP:
									regexp = parameter.getValue();
									break;
								default:
									break;
								}
							}

							if (!validator.validateRegularExpression(value, regexp)) {
								errors.rejectValue(errorPath, "oct.error.propertytypemismatch",
										new Object[] { getMessageSource().getMessage(name) },
										"The {0} format is not valid!");
							}
							break;
						default:
							break;
						}
					}
				}
			}
			idx++;
		}
	}
}
