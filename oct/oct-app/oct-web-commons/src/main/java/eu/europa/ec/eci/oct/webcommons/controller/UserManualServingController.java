package eu.europa.ec.eci.oct.webcommons.controller;

import java.io.InputStream;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import eu.europa.ec.eci.oct.business.api.OCTException;
import eu.europa.ec.eci.oct.webcommons.locale.LocaleUtils;

@Controller
@RequestMapping("/manual.do")
public class UserManualServingController extends FileServingController {

	private static final Logger logger = Logger.getLogger(UserManualServingController.class);

	private static final String RESOURCE_FOLDER = "/manual";

	private static final String baseName = "eci-ocs-usermanual";

	private static final String fileExtension = "pdf";

	private static final String CONTENT_TYPE = "application/pdf";

	@Override
	protected ServedFile getFile(HttpServletRequest req) throws OCTException {
		String language = LocaleUtils.getCurrentLanguage(req);
		if (null == language || "".equals(language)) {
			language = "en";
		}

		String fileName = baseName + "-" + language + "." + fileExtension;
		InputStream is = this.getClass().getResourceAsStream(RESOURCE_FOLDER + "/" + fileName);
		if (is == null) {
			logger.error("Could not serve the user manual. Filename = " + fileName);
			return null;
		}

		return new ServedFile(is, CONTENT_TYPE, fileName);
	}

}
