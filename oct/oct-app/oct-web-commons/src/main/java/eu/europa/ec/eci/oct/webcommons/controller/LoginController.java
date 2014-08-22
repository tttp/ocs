package eu.europa.ec.eci.oct.webcommons.controller;

import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.ui.Model;

import eu.europa.ec.eci.oct.business.api.OCTException;

public abstract class LoginController extends HttpGetController {

	@Override
	protected String _doGet(Model model, HttpServletRequest request, HttpServletResponse response) throws OCTException {
		String originalPhrase = generatePhrase();
		String hashedPhrase = systemManager.hash(originalPhrase);
		// store hashed phrase to session for later retrieval
		request.getSession().setAttribute(CommonControllerConstants.SESSION_ATTR_HASHED_PHRASE, hashedPhrase);

		String challenge = systemManager.generateChallenge(originalPhrase);
		model.addAttribute("challenge", challenge);

		return "login";
	}

	private String generatePhrase() {
		return UUID.randomUUID().toString();
	}

}
