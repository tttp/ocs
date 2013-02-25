package eu.europa.ec.eci.oct.admin.security;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.GrantedAuthorityImpl;

import eu.europa.ec.eci.oct.webcommons.security.OctAuthenticationProvider;

public class AdminAuthenticationProvider extends OctAuthenticationProvider {

	private static final String ROLE_ADMIN = "ROLE_ADMIN";

	@Override
	protected Collection<GrantedAuthority> getRolesToBeGranted() {
		Collection<GrantedAuthority> roles = new ArrayList<GrantedAuthority>();
		roles.add(new GrantedAuthorityImpl(ROLE_ADMIN));
		return roles;
	}

}
