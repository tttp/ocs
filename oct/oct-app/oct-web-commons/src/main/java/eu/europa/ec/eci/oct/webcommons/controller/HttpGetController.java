package eu.europa.ec.eci.oct.webcommons.controller;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.ejb.EJB;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import eu.europa.ec.eci.oct.business.api.InitiativeService;
import eu.europa.ec.eci.oct.business.api.OCTException;
import eu.europa.ec.eci.oct.business.api.OCTMissingCertificateException;
import eu.europa.ec.eci.oct.business.api.SystemManager;
import eu.europa.ec.eci.oct.entities.admin.InitiativeDescription;
import eu.europa.ec.eci.oct.entities.admin.SystemPreferences;
import eu.europa.ec.eci.oct.entities.member.Country;
import eu.europa.ec.eci.oct.entities.member.Language;
import eu.europa.ec.eci.oct.vo.certificate.Certificate;
import eu.europa.ec.eci.oct.webcommons.locale.LocaleUtils;
import eu.europa.ec.eci.oct.webcommons.locale.MessageSourceAware;
import eu.europa.ec.eci.oct.webcommons.locale.MessagesResolver;

public abstract class HttpGetController {

	@EJB
	protected SystemManager systemManager;

	@EJB
	protected InitiativeService initiativeService;

	@Autowired
	private MessageSource messageSource;

	private static final Logger logger = Logger.getLogger(HttpGetController.class);

	@RequestMapping(method = RequestMethod.GET)
	public String doGet(Model model, HttpServletRequest request, HttpServletResponse response) throws OCTException {
		storeParamsToModel(model, request);
		setState(model, request);
		return _doGet(model, request, response);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	protected Map<String, Object> getSessionCache(HttpServletRequest req) {
		if (req.getSession().getAttribute(CommonControllerConstants.ATTRIBUTE_SESSION_CACHE) == null) {
			req.getSession().setAttribute(CommonControllerConstants.ATTRIBUTE_SESSION_CACHE, new HashMap());
		}
		return (Map<String, Object>) req.getSession().getAttribute(CommonControllerConstants.ATTRIBUTE_SESSION_CACHE);
	}

	protected void addToCache(HttpServletRequest req, String key, Object o) {
		getSessionCache(req).put(key, o);
	}

	protected void removeFromCache(HttpServletRequest req, String key) {
		getSessionCache(req).remove(key);
	}

	protected Object getFromCache(HttpServletRequest req, String key) {
		return getSessionCache(req).get(key);
	}

	protected void storeParamsToModel(Model model, HttpServletRequest request) {
		@SuppressWarnings("unchecked")
		Map<String, String[]> params = request.getParameterMap();
		for (Map.Entry<String, String[]> param : params.entrySet()) {
			model.addAttribute(param.getKey(), param.getValue().length > 0 ? param.getValue()[0] : "");
		}
	}

	protected void setState(Model model, HttpServletRequest request) throws OCTException {

		if (request.getParameter("initiativeLang") != null) {
			request.getSession().setAttribute(CommonControllerConstants.SESSION_ATTR_INITIATIVE_LANGUAGE,
					request.getParameter("initiativeLang"));
		}

		if (!getSessionCache(request).containsKey(CommonControllerConstants.MODEL_ATTRIBUTE_LANGUAGES)) {
			getSessionCache(request).put(CommonControllerConstants.MODEL_ATTRIBUTE_LANGUAGES,
					systemManager.getAllLanguages());
		}

		@SuppressWarnings("unchecked")
		List<Language> ll = (List<Language>) getSessionCache(request).get(
				CommonControllerConstants.MODEL_ATTRIBUTE_LANGUAGES);
		processLanguageList(request, ll);

		if (!getSessionCache(request).containsKey(CommonControllerConstants.MODEL_ATTRIBUTE_COUNTRIES)) {
			getSessionCache(request).put(CommonControllerConstants.MODEL_ATTRIBUTE_COUNTRIES,
					systemManager.getAllCountries());
		}

		SystemPreferences prefs = systemManager.getSystemPreferences();

		InitiativeDescription description = null;
		try {
			String langCode = (String) request.getSession().getAttribute(
					CommonControllerConstants.SESSION_ATTR_INITIATIVE_LANGUAGE);
			if (langCode == null) {
				langCode = LocaleUtils.getCurrentLanguage(request);
			}
			Language lang = systemManager.getLanguageByCode(langCode);

			logger.debug("fetching description for language " + lang);
			description = initiativeService.getDescriptionByLang(lang);
			if (description == null) {
				description = prefs.getDefaultDescription();

				if (description == null) {
					logger.warn("initiative description for language " + lang.getCode() + " not found");
					throw new OCTException("initiative description for language " + lang.getCode() + " not found");
				} else {
					request.getSession().setAttribute(CommonControllerConstants.SESSION_ATTR_INITIATIVE_LANGUAGE,
							description.getLanguage().getCode());
				}
			}
		} catch (OCTException e) {
			logger.warn("problem occured while fetching initiative description. obtaining default description", e);
			try {
				description = initiativeService.getDefaultDescription();
			} catch (OCTException e2) {
				logger.warn("default initiative description not found", e2);

			}
		}

		if (!model.containsAttribute("oct_cert")) {
			try {
				Certificate cert = systemManager.getCertificate();
				model.addAttribute("oct_cert", cert);
			} catch (OCTMissingCertificateException e) {
				// ignore
			}
		}

		// sanitize urls
		try {
			if (prefs != null) {
				new URL(prefs.getCommissionRegisterUrl());
			}
		} catch (MalformedURLException e) {
			prefs.setCommissionRegisterUrl("");
		}

		// TODO remove one of preference references
		model.addAttribute(CommonControllerConstants.MODEL_ATTRIBUTE_LANGUAGES, ll);
		model.addAttribute(CommonControllerConstants.MODEL_ATTRIBUTE_COUNTRIES,
				getSessionCache(request).get(CommonControllerConstants.MODEL_ATTRIBUTE_COUNTRIES));
		model.addAttribute("currentLanguage", LocaleUtils.getCurrentLanguage(request));
		model.addAttribute(CommonControllerConstants.MODEL_ATTRIBUTE_SYSTEM_PREFS, prefs);
		model.addAttribute(CommonControllerConstants.MODEL_ATTRIBUTE_INITIATIVE_DESC, description);
		model.addAttribute(CommonControllerConstants.MODEL_ATTRIBUTE_SYSTEM_STATE, prefs.getState());
		model.addAttribute(CommonControllerConstants.MODEL_ATTRIBUTE_COLLECTOR_STATE, prefs.isCollecting());

	}

	protected void addSuccessMessage(HttpServletRequest request, Model model, String messageCode, String[] args) {

		model.addAttribute(CommonControllerConstants.MODEL_ATTRIBUTE_SUCCESS_MSG, getCurrentMessageBundle(request)
				.getMessage(messageCode, args));
	}

	/**
	 * Hook method for the HTPP GET action.
	 * 
	 * @param model
	 *            - the view model
	 * @param request
	 *            - the HTTP request
	 * @param response
	 *            - the HTPP response
	 * @return the name of the view to be rendered
	 * @throws OCTException
	 */
	protected abstract String _doGet(Model model, HttpServletRequest request, HttpServletResponse response)
			throws OCTException;

	protected MessageSource getMessageSource() {
		return messageSource;
	}

	protected MessageSourceAware getCurrentMessageBundle(HttpServletRequest request) {
		synchronized (request) {
			return new MessagesResolver(LocaleUtils.getCurrentLocale(request), getMessageSource());
		}
	}

	protected MessageSourceAware getMessageBundleByLocale(Locale locale) {
		return new MessagesResolver(locale, getMessageSource());
	}

	protected void translateLanguageNames(List<Language> languages, MessageSourceAware messageBundle) {
		for (Language language : languages) {
			translateLanguage(language, messageBundle);
		}
	}

	protected void translateLanguage(Language lang, MessageSourceAware mb) {
		lang.setLabel(mb.getMessage(lang.getName()));
	}

	protected Collection<Country> translateCountryNames(List<Country> countries, MessageSourceAware messageBundle) {
		for (Country country : countries) {
			translateCountry(country, messageBundle);
		}
		Collections.sort(countries, new Comparator<Country>() {
			@Override
			public int compare(Country c1, Country c2) {
				return c1.getLabel().toLowerCase().compareTo(c2.getLabel().toLowerCase());
			}
		});

		return countries;
	}

	protected void translateCountry(Country country, MessageSourceAware mb) {
		country.setLabel(mb.getMessage(country.getName()));
	}

	protected List<Language> getLanguageList(Model model, HttpServletRequest request) {
		@SuppressWarnings("unchecked")
		List<Language> ll = (List<Language>) model.asMap().get(CommonControllerConstants.MODEL_ATTRIBUTE_LANGUAGES);
		processLanguageList(request, ll);
		return ll;
	}

	private void processLanguageList(HttpServletRequest request, List<Language> ll) {
		translateLanguageNames(ll, getCurrentMessageBundle(request));

		// Collections.sort(ll, new Comparator<Language>() {
		// @Override
		// public int compare(Language l1, Language l2) {
		// return l1.getLabel().toLowerCase().compareTo(l2.getLabel().toLowerCase());
		// }
		// });
	}

}
