package eu.europa.ec.eci.oct.webcommons.security;

import javax.ejb.EJB;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.TextEscapeUtils;

import eu.europa.ec.eci.oct.business.api.SystemManager;
import eu.europa.ec.eci.oct.webcommons.controller.CommonControllerConstants;

public class OctUsernamePasswordAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

	public static final String SPRING_SECURITY_FORM_CHALLENGE_RESPONSE_KEY = "j_challange_response";

	private static final Logger logger = Logger.getLogger(OctUsernamePasswordAuthenticationFilter.class);

	@EJB
	protected SystemManager systemManager;

	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException {
		logger.debug("Trying to authenticate user...");

		String username = obtainUsername(request);
		if (username == null) {
			username = "";
		}
		username = username.trim();
		HttpSession session = request.getSession(false);
		if (session != null || getAllowSessionCreation()) {
			request.getSession().setAttribute(SPRING_SECURITY_LAST_USERNAME_KEY,
					TextEscapeUtils.escapeEntities(username));
		}

		String password = obtainPassword(request);
		if (password == null) {
			password = "";
		}

		String originalPhrase = obtainOriginalPhrase(request);
		if (originalPhrase == null) {
			originalPhrase = "";
		}

		String challengeResponse = obtainChalengeResponse(request);
		if (challengeResponse == null) {
			challengeResponse = "";
		}

		if (logger.isEnabledFor(Level.DEBUG)) {
			logger.debug("User name = " + username);
			logger.debug("User password = *******");
			logger.debug("Original phrase = *******");
			logger.debug("Challenge response = *******");
		}

		if (!validateData(username, password, originalPhrase, challengeResponse)) {
			logger.debug("Trying to authenticate with empty user, password or challenge. Stop here!");
			throw new BadCredentialsException("Username, password or result cannot be null.");
		}

		UsernamePasswordAuthenticationToken authRequest = new OctAuthenticationToken(username, password,
				originalPhrase, challengeResponse);
		setDetails(request, authRequest);

		logger.debug("Invoking authentication manager...");
		return this.getAuthenticationManager().authenticate(authRequest);
	}

	private String obtainOriginalPhrase(HttpServletRequest request) {
		String result = null;
		HttpSession session = request.getSession(false);
		if (session != null || getAllowSessionCreation()) {
			result = (String) request.getSession().getAttribute(CommonControllerConstants.SESSION_ATTR_HASHED_PHRASE);
		}
		return result;
	}

	protected String obtainChalengeResponse(HttpServletRequest request) {
		return request
				.getParameter(OctUsernamePasswordAuthenticationFilter.SPRING_SECURITY_FORM_CHALLENGE_RESPONSE_KEY);
	}

	private boolean validateData(String user, String password, String originalPhrase, String challengeResponse) {
		boolean result = false;
		result = !"".equals(user) && !"".equals(password) && !"".equals(originalPhrase)
				&& !"".equals(challengeResponse);
		return result;
	}

}
