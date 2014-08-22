package eu.europa.ec.eci.oct.webcommons.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.support.SessionStatus;

import eu.europa.ec.eci.oct.business.api.OCTException;
import eu.europa.ec.eci.oct.webcommons.locale.MessageSourceAware;
import eu.europa.ec.eci.oct.webcommons.model.SimpleBean;
import eu.europa.ec.eci.oct.webcommons.security.requesttoken.RequestToken;
import eu.europa.ec.eci.oct.webcommons.security.requesttoken.RequestTokenHelper;

/**
 * Base class for the Spring controllers used throughout the application. This
 * implements the {@link MessageSourceAware} interface, meaning that it can
 * provide i18n resources.
 * 
 * @author chiridl
 * 
 */
public abstract class HttpGetAndPostController<T extends SimpleBean> extends HttpGetController {

	@Autowired
	protected RequestTokenHelper requestTokenHelper;	
	
	/**
	 * Produces and registers a request token. The default implementation
	 * produces a token configured always to be consumed on a
	 * successful match.
	 * @param request the HttpServletRequest
	 * @return the token
	 */
	protected RequestToken getToken(HttpServletRequest request) {
		return requestTokenHelper.getToken(request);
	}
		
	@Override
	public String doGet(Model model, HttpServletRequest request, HttpServletResponse response) throws OCTException {
		final String viewName = super.doGet(model, request, response);
		// produce an expected request token and expose it on the model
		model.addAttribute(getToken(request));
		
		return viewName;
	}

	@RequestMapping(method = RequestMethod.POST)
	public String doPost(Model model, @ModelAttribute(value = "form") T bean, BindingResult result, SessionStatus status,
			HttpServletRequest request, HttpServletResponse response) throws OCTException {
		// assert that the expected token is found on the request
		if (!requestTokenHelper.checkAndConsume(request)) {
			try {
				response.sendError(HttpServletResponse.SC_FORBIDDEN);
			} catch (IOException e) {
				throw new OCTException("Unable to send the FORBIDDEN code", e);
			}
			return null;
		}
		
		cleanMessages(model);
		
		//storeParamsToModel(model, request);
		setState(model, request);
		return _doPost(model, bean, result, status, request, response);
	}
	
	protected void cleanMessages(Model model) {
		
		model.addAttribute(CommonControllerConstants.MODEL_ATTRIBUTE_SUCCESS_MSG, null);
	}

	protected abstract String _doPost(Model model, T bean, BindingResult result, SessionStatus status, 
			HttpServletRequest request,	HttpServletResponse response) throws OCTException;

}
