package eu.europa.ec.eci.oct.webcommons.locale;

import java.util.Locale;

import org.springframework.context.MessageSource;

public class MessagesResolver implements MessageSourceAware {
	private MessageSource messageSource;
	private Locale locale;

	public MessagesResolver(Locale locale, MessageSource messageSource) {
		setLocale(locale);
		setMessageSource(messageSource);
	}

	private void setMessageSource(MessageSource messageSource) {
		this.messageSource = messageSource;
	}

	@Override
	public String getMessage(String code) {
		return getMessage(code, null, code);
	}

	@Override
	public String getMessage(String code, String defaultMessage) {
		return getMessage(code, null, defaultMessage);
	}

	@Override
	public String getMessage(String code, Object[] args) {
		return getMessage(code, args, code);
	}

	@Override
	public String getMessage(String code, Object[] args, String defaultMessage) {
		return messageSource.getMessage(code, args, defaultMessage, getLocale());
	}

	private void setLocale(Locale locale) {
		this.locale = locale;
	}

	public Locale getLocale() {
		return locale;
	}
}
