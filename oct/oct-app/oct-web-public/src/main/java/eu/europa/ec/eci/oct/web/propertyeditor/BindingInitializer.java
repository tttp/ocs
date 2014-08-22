package eu.europa.ec.eci.oct.web.propertyeditor;

import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.context.request.WebRequest;

import eu.europa.ec.eci.oct.webcommons.propertyeditor.BaseBindingInitializer;

/**
 * This is central location to initialize and register the custom property editors used throughout the application.
 * 
 * @author chiridl
 * 
 */
public class BindingInitializer extends BaseBindingInitializer {

	@Override
	public void initBinder(WebDataBinder binder, WebRequest request) {
		super.initBinder(binder, request);
	}
}
