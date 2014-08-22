package eu.europa.ec.eci.oct.admin.propertyeditor;

import javax.ejb.EJB;

import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.context.request.WebRequest;

import eu.europa.ec.eci.oct.business.api.SystemManager;
import eu.europa.ec.eci.oct.entities.member.Language;
import eu.europa.ec.eci.oct.webcommons.propertyeditor.BaseBindingInitializer;

/**
 * This is central location to initialize and register the custom property editors used throughout the application.
 * 
 * @author chiridl
 * 
 */
public class BindingInitializer extends BaseBindingInitializer {

	@EJB
	private SystemManager sysManager;

	@Override
	public void initBinder(WebDataBinder binder, WebRequest request) {
		super.initBinder(binder, request);
		binder.registerCustomEditor(Language.class, new LanguageTypePropertyEditor(sysManager));
	}
}
