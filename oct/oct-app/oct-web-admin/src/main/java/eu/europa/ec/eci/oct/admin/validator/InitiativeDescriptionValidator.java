package eu.europa.ec.eci.oct.admin.validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;

import eu.europa.ec.eci.oct.admin.model.InitiativeDescriptionBean;
import eu.europa.ec.eci.oct.webcommons.locale.MessageSourceAware;
import eu.europa.ec.eci.oct.webcommons.validator.BaseValidator;

@Component
public class InitiativeDescriptionValidator extends BaseValidator {

	public InitiativeDescriptionValidator(MessageSourceAware messageSource) {
		super(messageSource);
	}

	@Override
	public boolean supports(Class<?> clazz) {
		return InitiativeDescriptionBean.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		ValidationUtils.rejectIfEmpty(errors, "language", "oct.admin.error.empty.language",
				"The language cannot be empty!");
		ValidationUtils.rejectIfEmpty(errors, "title", "oct.admin.error.empty.title", "The title cannot be empty!");
		ValidationUtils.rejectIfEmpty(errors, "subjectMatter", "oct.admin.error.empty.subjectMatter",
				"The subject / matter cannot be empty!");
		ValidationUtils.rejectIfEmpty(errors, "objectives", "oct.admin.error.empty.objectives",
				"The objectives cannot be empty!");
		ValidationUtils.rejectIfEmpty(errors, "url", "oct.admin.error.empty.url",
				"The URL cannot be empty!");
	}

}
