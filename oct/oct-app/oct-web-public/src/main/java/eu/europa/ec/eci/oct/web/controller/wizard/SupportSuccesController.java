/** ====================================================================
 * Licensed under the European Union Public Licence (EUPL v1.2) 
 * https://joinup.ec.europa.eu/community/eupl/topic/public-consultation-draft-eupl-v12
 * ====================================================================
 *
 * @author Daniel CHIRITA
 * @created: 23/05/2013
 *
 */
package eu.europa.ec.eci.oct.web.controller.wizard;

import javax.ejb.EJB;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import eu.europa.ec.eci.oct.business.api.ConfigurationService;
import eu.europa.ec.eci.oct.business.api.ConfigurationService.Parameter;
import eu.europa.ec.eci.oct.business.api.OCTException;
import eu.europa.ec.eci.oct.entities.admin.InitiativeDescription;
import eu.europa.ec.eci.oct.entities.member.Language;
import eu.europa.ec.eci.oct.utils.StringUtils;
import eu.europa.ec.eci.oct.web.controller.ControllerConstants;
import eu.europa.ec.eci.oct.web.model.SignatureReviewBean;
import eu.europa.ec.eci.oct.webcommons.controller.HttpGetController;
import eu.europa.ec.eci.oct.webcommons.locale.LocaleUtils;

/**
 * Controller for handling the successful registration page.
 * 
 * @author chiridl
 * 
 */
@Controller
@RequestMapping("/success.do")
public class SupportSuccesController extends HttpGetController {

	@EJB
	private ConfigurationService configurationService;

	@Override
	protected String _doGet(Model model, HttpServletRequest request, HttpServletResponse response) throws OCTException {
		final Object bean = request.getSession().getAttribute(ControllerConstants.SESSION_ATTR_RESULTING_TOKEN);
		request.getSession().removeAttribute(ControllerConstants.SESSION_ATTR_RESULTING_TOKEN);
		if (bean == null) {
			return "redirect:./index.do";
		}

		model.addAttribute("reviewBean", (SignatureReviewBean) bean);

		// set related initiative description
		String langCode = (String) request.getSession().getAttribute(ControllerConstants.SESSION_ATTR_INITIATIVE_LANGUAGE);
		if (langCode == null) {
			langCode = LocaleUtils.getCurrentLanguage(request);
		}

		String callbackUrl = configurationService.getConfigurationParameter(Parameter.CALLBACK_URL).getValue();
		if (null == callbackUrl || Parameter.CALLBACK_URL.getDefaultValue().equals(callbackUrl)) {
			Language lang = systemManager.getLanguageByCode(langCode);
			InitiativeDescription description = initiativeService.getDescriptionByLang(lang);

			if (description != null && !StringUtils.isEmpty(description.getUrl())) {
				callbackUrl = description.getUrl();
			} else {
				description = initiativeService.getDefaultDescription();
				if (!StringUtils.isEmpty(description.getUrl())) {
					callbackUrl = description.getUrl();
				}
			}
		} else {
			// custom callback url, we need to add parameters to it
			if (callbackUrl.indexOf("?") < 0) {
				callbackUrl = callbackUrl + "?";
			} else {
				if (!callbackUrl.endsWith("&") && !callbackUrl.endsWith("&amp;")) {
					callbackUrl = callbackUrl + "&";
				}
			}
			callbackUrl = callbackUrl + ControllerConstants.URL_PARAMETER_STATUS + "=" + ControllerConstants.URL_PARAMETER_STATUS_SUCCESS
					+ "&" + ControllerConstants.URL_PARAMETER_LANG + "=" + langCode;
		}
		model.addAttribute(ControllerConstants.MODEL_ATTRIBUTE_INITIATIVE_URL, callbackUrl);

		String url = request.getRequestURL().toString();
		url = url.substring(0, url.indexOf("success.do"));
		model.addAttribute(ControllerConstants.MODEL_ATTRIBUTE_START_URL, url);

		return "wizard/success";
	}
}
