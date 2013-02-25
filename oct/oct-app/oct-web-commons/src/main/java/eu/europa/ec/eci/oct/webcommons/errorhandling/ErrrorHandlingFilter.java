package eu.europa.ec.eci.oct.webcommons.errorhandling;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.web.filter.OncePerRequestFilter;

import eu.europa.ec.eci.oct.webcommons.utils.ErrorHandlingUtils;

public class ErrrorHandlingFilter extends OncePerRequestFilter {

	private static final Logger logger = Logger.getLogger(ErrrorHandlingFilter.class);

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		try {
			filterChain.doFilter(request, response);
		} catch (Throwable t) {
			final byte TOKEN_LENGTH = 6;
			String token = ErrorHandlingUtils.generateErrorToken(TOKEN_LENGTH);
			logger.error("Uncaught exception occured. Generated the following token: --->>> " + token + " <<<---.", t);

			HttpServletResponse httpResponse = (HttpServletResponse) response;
			httpResponse.sendRedirect("./error.do?token=" + token);
		}
	}

}
