package eu.europa.ec.eci.oct.web.validator;

import java.util.ArrayList;
import java.util.List;

import org.springframework.validation.Errors;

import eu.europa.ec.eci.oct.entities.signature.PropertyValue;
import eu.europa.ec.eci.oct.validation.OCSValidationResult;
import eu.europa.ec.eci.oct.validation.OCSValidator;
import eu.europa.ec.eci.oct.validation.core.JAXELStructure;
import eu.europa.ec.eci.oct.web.captcha.CaptchaService;
import eu.europa.ec.eci.oct.web.model.SupportFormBean;
import eu.europa.ec.eci.oct.webcommons.locale.MessageSourceAware;
import eu.europa.ec.eci.oct.webcommons.validator.BaseValidator;

/**
 * Validator class for validating the support initiative form. It provides all
 * methods needed by the {@link SupportInitiativeController}.
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
	 * Used to invoke all validation methods at once. Should be used by the
	 * presentation just before finishing the store initiative wizard.
	 */
	public List<OCSValidationResult> validateAll(Object target, Errors errors) {
		List<OCSValidationResult> result = new ArrayList<OCSValidationResult>();

		validatePrerequisites(target, errors);
		result.addAll(validateFields(target, errors));
		validateTerms(target, errors);

		return result;
	}

	public void validate(Object target, Errors errors) {
	}

	/**
	 * Validates the security code (captcha).
	 * 
	 * @param target
	 * @param errors
	 * @param captchaService
	 * @param captchaId
	 */
	public boolean validateCaptcha(Object target, Errors errors, CaptchaService captchaService, String captchaId) {
		SupportFormBean bean = (SupportFormBean) target;
		if (!captchaService.validateCaptcha(captchaId, bean.getCaptcha(), bean.getCaptchaType())) {
			errors.rejectValue("captcha", "oct.error.wrongcaptcha", "The security code is wrong! Please try again.");
			return false;
		}

		return true;
	}

	/**
	 * Validates the terms and conditions (if the user has accepted them or
	 * not).
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
	 * Validates the prerequisites for the initiative form (first name, last
	 * name and country).
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
	public List<OCSValidationResult> validateFields(Object target, Errors errors) {
		List<OCSValidationResult> result = new ArrayList<OCSValidationResult>();
		SupportFormBean bean = (SupportFormBean) target;

		if (bean.getProperties() == null || bean.getProperties().size() == 0) {
			// should never happen
			errors.reject("oct.error.emptyprops", "The data values you entered are empty. Please contact the administrator!");
			return result;
		}

		OCSValidator validator = new OCSValidator();

		// add referenced properties to validator
		final String COUNTRY_TO_SIGN_FOR_PROPERTY = "c";
		for (PropertyValue property : bean.getProperties()) {
			final String key = property.getProperty().getProperty().getName();
			final String value = property.getValue();
			validator.addProperty(key, value);
		}
		validator.addProperty(JAXELStructure.PROPERTY_PREFIX + COUNTRY_TO_SIGN_FOR_PROPERTY, bean.getCountryToSignFor().getCode()
				.toLowerCase());

		// traverse properties and validate each of them
		int idx = 0;
		for (PropertyValue propertyValue : bean.getProperties()) {
			String errorPath = new StringBuilder().append("properties[").append(idx).append("].value").toString();

			if (propertyValue.getProperty() == null) {
				errors.rejectValue(errorPath, "oct.error.emptyprops",
						"The data values you entered are empty. Please contact the administrator!");
				return result;
			}

			String name = propertyValue.getProperty().getProperty().getName();
			String value = propertyValue.getValue();

			OCSValidationResult validationResult = validator.validate(propertyValue.getProperty(), value);
			if (validationResult.isFailed()) {
				errors.rejectValue(errorPath, validationResult.getReason(), new Object[] { getMessageSource().getMessage(name) },
						"Validation failed for field {0}.!");
				if (validationResult.isCanBeSkipped()) {
					result.add(validationResult);
				}
			}
			idx++;
		}

		return result;
	}
}
