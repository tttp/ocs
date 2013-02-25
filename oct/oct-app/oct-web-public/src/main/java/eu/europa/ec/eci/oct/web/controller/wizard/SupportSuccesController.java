package eu.europa.ec.eci.oct.web.controller.wizard;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

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

	@Override
	protected String _doGet(Model model, HttpServletRequest request, HttpServletResponse response) throws OCTException {
		final Object bean = request.getSession().getAttribute(ControllerConstants.SESSION_ATTR_RESULTING_TOKEN);
		request.getSession().removeAttribute(ControllerConstants.SESSION_ATTR_RESULTING_TOKEN);
		if (bean == null) {
			return "redirect:./index.do";
		}

		model.addAttribute("reviewBean", (SignatureReviewBean) bean);

		// set related initiative description
		String langCode = (String) request.getSession().getAttribute(
				ControllerConstants.SESSION_ATTR_INITIATIVE_LANGUAGE);
		if (langCode == null) {
			langCode = LocaleUtils.getCurrentLanguage(request);
		}
		Language lang = systemManager.getLanguageByCode(langCode);
		InitiativeDescription description = initiativeService.getDescriptionByLang(lang);

		if (description != null && !StringUtils.isEmpty(description.getUrl())) {
			model.addAttribute(ControllerConstants.MODEL_ATTRIBUTE_INITIATIVE_URL, description.getUrl());
		} else {
			description = initiativeService.getDefaultDescription();
			if (!StringUtils.isEmpty(description.getUrl())) {
				model.addAttribute(ControllerConstants.MODEL_ATTRIBUTE_INITIATIVE_URL, description.getUrl());
			}
		}

		return "wizard/success";
	}
}
