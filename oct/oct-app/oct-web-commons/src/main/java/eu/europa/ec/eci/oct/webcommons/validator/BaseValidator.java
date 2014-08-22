package eu.europa.ec.eci.oct.webcommons.validator;

import org.springframework.validation.Validator;

import eu.europa.ec.eci.oct.webcommons.locale.MessageSourceAware;

public abstract class BaseValidator implements Validator {

	private MessageSourceAware messageSource;

	public BaseValidator(MessageSourceAware messageSource) {
		setMessageSource(messageSource);
	}

	private void setMessageSource(MessageSourceAware messageSource) {
		this.messageSource = messageSource;
	}

	protected MessageSourceAware getMessageSource() {
		return messageSource;
	}

}
