package eu.europa.ec.eci.oct.webcommons.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

public class OctLogoutSuccessHandler implements LogoutSuccessHandler {

	private static final Logger logger = Logger.getLogger(OctLogoutSuccessHandler.class);

	@Override
	public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
			throws IOException, ServletException {
		logger.info("Succesfully logged out.");

		response.sendRedirect("./index.do");
	}

}
