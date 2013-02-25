package eu.europa.ec.eci.oct.webcommons.security;

import java.util.Collection;

import javax.ejb.EJB;

import org.apache.log4j.Logger;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;

import eu.europa.ec.eci.oct.business.api.OCTException;
import eu.europa.ec.eci.oct.business.api.SystemManager;

public abstract class OctAuthenticationProvider implements AuthenticationProvider {

	private static final Logger logger = Logger.getLogger(OctAuthenticationProvider.class);

	@EJB
	private SystemManager systemManager;

	protected abstract Collection<GrantedAuthority> getRolesToBeGranted();

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		OctAuthenticationToken token = (OctAuthenticationToken) authentication;

		String user = (String) token.getPrincipal();
		String password = (String) token.getCredentials();
		String originalPhrase = (String) token.getOriginalPhrase();
		String challengeResponse = (String) token.getChallengeResponse();

		if (!preValidate(user, password, originalPhrase, challengeResponse)) {
			logger.debug("Trying to authenticate with empty user, password or challenge. Stop here!");
			throw new BadCredentialsException("Username, password or result cannot be null.");
		}

		try {
			if (!validateUser(user, password)) {
				logger.debug("Failed to authenticate the user.");
				throw new BadCredentialsException("Failed to authenticate the user.");
			}

			if (!validateChallenge(originalPhrase, challengeResponse)) {
				logger.debug("Failed to verify the challenge.");
				throw new BadCredentialsException("Failed to authenticate the user.");
			}
		} catch (OCTException e) {
			throw new AuthenticationServiceException("Failed to invoke the authentication service.", e);
		}

		Collection<GrantedAuthority> roles = getRolesToBeGranted();
		
		logger.debug("User succesfully authenticated.");
		return new UsernamePasswordAuthenticationToken(user, password, roles);
	}

	private boolean preValidate(String user, String password, String challenge, String challengeResponse) {
		logger.debug("Prevalidating credentials...");
		boolean result = false;
		result = user != null && !"".equals(user) && password != null && !"".equals(password) && challenge != null
				&& !"".equals(challenge) && challengeResponse != null && !"".equals(challengeResponse);
		return result;
	}

	private boolean validateUser(String user, String password) throws OCTException {
		logger.debug("Invoking BL to check user credentials...");
		return systemManager.authenticate(user, password);
	}

	private boolean validateChallenge(String originalPhrase, String challengeResponse) throws OCTException {
		logger.debug("Invoking BL to verify the challenge response against the challenge...");
		return systemManager.hash(challengeResponse).equals(originalPhrase);
	}

	@Override
	public boolean supports(Class<? extends Object> authentication) {
		return OctAuthenticationToken.class.isAssignableFrom(authentication);
	}

}
