package eu.europa.ec.eci.oct.admin.security;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

import eu.europa.ec.eci.oct.webcommons.security.OctUsernamePasswordAuthenticationFilter;

public class AdminUsernamePasswordAuthenticationFilter extends OctUsernamePasswordAuthenticationFilter {

	private static final Logger logger = Logger.getLogger(AdminUsernamePasswordAuthenticationFilter.class);

	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException {
		logger.debug("Using default security mechanism...");
		return super.attemptAuthentication(request, response);
	}

}
