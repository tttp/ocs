package eu.europa.ec.eci.oct.webcommons.locale;

import java.util.Locale;

import javax.ejb.EJB;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.springframework.beans.propertyeditors.LocaleEditor;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import org.springframework.web.servlet.support.RequestContextUtils;

import eu.europa.ec.eci.oct.business.api.OCTException;
import eu.europa.ec.eci.oct.business.api.SystemManager;
import eu.europa.ec.eci.oct.webcommons.controller.CommonControllerConstants;

public class LocaleChangeInterceptor extends HandlerInterceptorAdapter {

	private static final Logger logger = Logger.getLogger(LocaleChangeInterceptor.class);

	private static final String PARAM_NAME = "lang";
	private static final String SESSION_ATTRIBUTE = "__language__";

	@EJB
	private SystemManager sysManager;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
		String language = "en";
		try {
			language = request.getParameter(PARAM_NAME);

			// if gui language has changed, also update the initiative description
			boolean changeInitiativeLang = language != null && !"".equals(language);
			if (changeInitiativeLang) {
				request.getSession().setAttribute(CommonControllerConstants.SESSION_ATTR_INITIATIVE_LANGUAGE, language);
			}

			if (language == null || "".equals(language)) {
				language = (String) request.getSession().getAttribute(SESSION_ATTRIBUTE);

				if (language == null || "".equals(language)) {
					// if parameter is not set, check request
					language = request.getLocale() != null ? request.getLocale().getLanguage() : null;
				}
			}

			// check if language exists in DB
			if (language == null || "".equals(language) || sysManager.getLanguageByCode(language) == null) {
				language = sysManager.getSystemPreferences().getDefaultLanguage().getCode();
			}

			language = language.toLowerCase();
			request.getSession().setAttribute(SESSION_ATTRIBUTE, language);
		} catch (OCTException e) {
			logger.error("Could not process locales! About to set the default locale to ENGLISH...", e);
			language = "en";
		} finally {
			if (logger.isEnabledFor(Level.DEBUG)) {
				logger.debug("About to set language to " + language);
			}

			LocaleResolver localeResolver = RequestContextUtils.getLocaleResolver(request);
			if (localeResolver == null) {
				logger.error("Could not get locale resolver! Nothing more we can do!");
				return true;
			}
			LocaleEditor localeEditor = new LocaleEditor();
			localeEditor.setAsText(language);
			localeResolver.setLocale(request, response, (Locale) localeEditor.getValue());
		}

		return true;
	}
}
