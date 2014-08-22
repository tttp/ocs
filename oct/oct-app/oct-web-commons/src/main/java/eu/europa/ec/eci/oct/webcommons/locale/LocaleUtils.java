package eu.europa.ec.eci.oct.webcommons.locale;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.support.RequestContextUtils;

public class LocaleUtils {

	public static String getCurrentLanguage(HttpServletRequest request) {
		synchronized (request) {
			Locale locale = LocaleUtils.getCurrentLocale(request);
			return locale.getLanguage();
		}
	}

	public static Locale getCurrentLocale(HttpServletRequest request) {
		synchronized (request) {
			LocaleResolver localeResolver = RequestContextUtils.getLocaleResolver(request);
			return localeResolver.resolveLocale(request);
		}
	}
}
