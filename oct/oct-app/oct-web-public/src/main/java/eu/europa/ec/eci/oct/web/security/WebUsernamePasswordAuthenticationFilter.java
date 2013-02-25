package eu.europa.ec.eci.oct.web.security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;

import eu.europa.ec.eci.oct.business.api.OCTException;
import eu.europa.ec.eci.oct.webcommons.security.OctUsernamePasswordAuthenticationFilter;

public class WebUsernamePasswordAuthenticationFilter extends OctUsernamePasswordAuthenticationFilter {

	private static final Logger logger = Logger.getLogger(WebUsernamePasswordAuthenticationFilter.class);

	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException {
		try {
			if (!systemManager.isWebPasswordProtected()) {
				// hack filter, just grant role if the public part is not password protected
				logger.debug("Forcing user authentication.");
				Collection<GrantedAuthority> roles = new ArrayList<GrantedAuthority>();
				Collections.addAll(roles, WebAuthenticationProvider.GRANTED_ROLES);
				return new UsernamePasswordAuthenticationToken("anonymous", "nopassword", roles);
			}
		} catch (OCTException e) {
			throw new AuthenticationServiceException("There was a problem reading system state!", e);
		}

		// normal flow
		return super.attemptAuthentication(request, response);
	}
}
