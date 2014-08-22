package eu.europa.ec.eci.oct.web.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import eu.europa.ec.eci.oct.business.api.OCTException;
import eu.europa.ec.eci.oct.entities.admin.Contact;
import eu.europa.ec.eci.oct.entities.admin.InitiativeDescription;
import eu.europa.ec.eci.oct.entities.admin.SystemState;
import eu.europa.ec.eci.oct.entities.member.Language;
import eu.europa.ec.eci.oct.webcommons.controller.CommonControllerConstants;
import eu.europa.ec.eci.oct.webcommons.controller.HttpGetController;
import eu.europa.ec.eci.oct.webcommons.security.requesttoken.RequestTokenHelper;

/**
 * Controller for handling the Index (default) page.
 * 
 * @author chiridl
 * 
 */
@Controller
@RequestMapping("/index.do")
public class IndexController extends HttpGetController {

	@Autowired
	protected RequestTokenHelper requestTokenHelper;

	@Override
	protected String _doGet(Model model, HttpServletRequest request, HttpServletResponse response) throws OCTException {
		// generate and expose request token
		model.addAttribute(requestTokenHelper.getToken(request));

		request.getSession().setAttribute(ControllerConstants.SESSION_ATTR_FIRST_PAGE_ACCESSED, "true");

		if ((SystemState) model.asMap().get(CommonControllerConstants.MODEL_ATTRIBUTE_SYSTEM_STATE) == SystemState.DEPLOYED) {
			return "index";
		}

		Contact contact = initiativeService.getContact();
		model.addAttribute("contact", contact);

		Set<String> contacts = new LinkedHashSet<String>(Arrays.asList(contact.getName().split(", ")));
		Set<String> organizers = new LinkedHashSet<String>(Arrays.asList(contact.getOrganizers().split(", ")));
		Set<String> diff = new LinkedHashSet<String>(organizers);
		diff.removeAll(contacts);
		String organizersString = diff.toString();
		organizersString = organizersString.substring(1, organizersString.length() - 1);
		model.addAttribute("organizers", organizersString);

		List<Language> languages = new ArrayList<Language>();
		List<InitiativeDescription> descriptions = initiativeService.getDescriptions();
		for (InitiativeDescription initiativeDescription : descriptions) {
			languages.add(initiativeDescription.getLanguage());
		}
		Collections.sort(languages, new Comparator<Language>() {
			@Override
			public int compare(Language l1, Language l2) {
				return l1.getDisplayOrder().intValue() - l2.getDisplayOrder().intValue();
			}
		});
		model.addAttribute("initiativeLanguages", languages);

		return "index";
	}

	@RequestMapping(method = RequestMethod.POST)
	public String doPost(HttpServletRequest request, HttpServletResponse response) throws OCTException {
		// assert that the expected token is found on the request
		if (!requestTokenHelper.checkAndConsume(request)) {
			try {
				response.sendError(HttpServletResponse.SC_FORBIDDEN);
			} catch (IOException e) {
				throw new OCTException("Unable to send the FORBIDDEN code", e);
			}
			return null;
		}
		return "redirect:signup.do";
	}
}
