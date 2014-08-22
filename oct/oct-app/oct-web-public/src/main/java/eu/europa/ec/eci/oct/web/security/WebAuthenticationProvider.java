package eu.europa.ec.eci.oct.web.security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.GrantedAuthorityImpl;

import eu.europa.ec.eci.oct.webcommons.security.OctAuthenticationProvider;

public class WebAuthenticationProvider extends OctAuthenticationProvider {

	private static final String ROLE_USER = "ROLE_USER";

	public static final GrantedAuthority[] GRANTED_ROLES = { new GrantedAuthorityImpl(ROLE_USER) };

	@Override
	protected Collection<GrantedAuthority> getRolesToBeGranted() {
		Collection<GrantedAuthority> roles = new ArrayList<GrantedAuthority>();
		Collections.addAll(roles, GRANTED_ROLES);
		return roles;
	}

}
