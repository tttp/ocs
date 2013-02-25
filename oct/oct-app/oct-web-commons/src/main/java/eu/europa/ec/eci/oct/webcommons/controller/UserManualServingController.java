package eu.europa.ec.eci.oct.webcommons.controller;

import java.io.InputStream;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import eu.europa.ec.eci.oct.business.api.OCTException;
import eu.europa.ec.eci.oct.webcommons.locale.LocaleUtils;

@Controller
@RequestMapping("/manual.do")
public class UserManualServingController extends FileServingController{
	
	private static final String RESOURCE_FOLDER= "/manual";	
	
	private static final String baseName = "eci-ocs-user_manual";
	
	private static final String fileExtension = "pdf";
	
	private static final String CONTENT_TYPE = "application/pdf";

	@Override
	protected ServedFile getFile(HttpServletRequest req) throws OCTException {
		
		String fileName = baseName + "_" + LocaleUtils.getCurrentLocale(req).getLanguage() + "." + fileExtension;
		InputStream is = this.getClass().getResourceAsStream(RESOURCE_FOLDER + "/" + fileName);
		if(is==null){
			fileName = baseName + "." + fileExtension;
			is = this.getClass().getResourceAsStream(RESOURCE_FOLDER + "/" + fileName);			
		}		
		
		return new ServedFile(is, CONTENT_TYPE, fileName);
	}

}
