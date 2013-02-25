package eu.europa.ec.eci.oct.webcommons.propertyeditor;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.ejb.EJB;

import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.support.WebBindingInitializer;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.multipart.support.ByteArrayMultipartFileEditor;

import eu.europa.ec.eci.oct.business.api.SystemManager;
import eu.europa.ec.eci.oct.entities.member.Country;
import eu.europa.ec.eci.oct.utils.validator.DateValidator;

public class BaseBindingInitializer implements WebBindingInitializer {

	@EJB
	private SystemManager sysManager;

	@Override
	public void initBinder(WebDataBinder binder, WebRequest request) {
		binder.registerCustomEditor(byte[].class, new ByteArrayMultipartFileEditor());
		binder.registerCustomEditor(Country.class, new CountryTypePropertyEditor(sysManager));
		final SimpleDateFormat dateFormat = new SimpleDateFormat(DateValidator.DEFAULT_DATE_FORMAT);
		dateFormat.setLenient(false);
		binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true, DateValidator.DEFAULT_DATE_FORMAT.length())); 
	}

}
