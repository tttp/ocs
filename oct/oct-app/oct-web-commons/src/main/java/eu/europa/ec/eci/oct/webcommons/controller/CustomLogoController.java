/** ====================================================================
 * Licensed under the European Union Public Licence (EUPL v1.2) 
 * https://joinup.ec.europa.eu/community/eupl/topic/public-consultation-draft-eupl-v12
 * ====================================================================
 *
 * @author Daniel CHIRITA
 * @created: 23/05/2013
 *
 */
package eu.europa.ec.eci.oct.webcommons.controller;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.FileNameMap;
import java.net.URLConnection;

import javax.ejb.EJB;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import eu.europa.ec.eci.oct.business.api.ConfigurationService;
import eu.europa.ec.eci.oct.business.api.ConfigurationService.Parameter;
import eu.europa.ec.eci.oct.business.api.OCTException;
import eu.europa.ec.eci.oct.business.api.SystemManager;
import eu.europa.ec.eci.oct.entities.ConfigurationParameter;
import eu.europa.ec.eci.oct.webcommons.controller.fileserving.FileServingController;
import eu.europa.ec.eci.oct.webcommons.controller.fileserving.RequestHandledTransparentlyException;
import eu.europa.ec.eci.oct.webcommons.controller.fileserving.ServedFile;

@Controller
@RequestMapping("/logo.do")
public class CustomLogoController extends FileServingController {

	private final static Logger logger = Logger.getLogger(CustomLogoController.class);

	@EJB
	private ConfigurationService configurationService;

	@EJB
	private SystemManager systemManager;

	private static final FileNameMap fileMap = URLConnection.getFileNameMap();

	@Override
	protected ServedFile getFile(HttpServletRequest request, HttpServletResponse response) throws RequestHandledTransparentlyException,
			OCTException {
		final ConfigurationParameter param = configurationService.getConfigurationParameter(Parameter.LOGO_PATH);

		String fileName = param.getValue();
		if (!Parameter.LOGO_PATH.getDefaultValue().equals(fileName)) {
			// custom logo stored on the filesystem
			fileName = new StringBuilder().append(systemManager.getSystemPreferences().getFileStoragePath()).append("custom/")
					.append(fileName).toString();
			if (logger.isDebugEnabled()) {
				logger.debug("Custom LOGO file name: " + fileName);
			}

			try {
				return new ServedFile(new FileInputStream(fileName), fileMap.getContentTypeFor(param.getValue()), fileName, false);
			} catch (FileNotFoundException e) {
				throw new OCTException("File not found!", e);
			}
		} else {
			try {
				// default logo, just let server do its job
				response.sendRedirect(fileName);
				throw new RequestHandledTransparentlyException("We have already redirected to the needed resource.");
			} catch (IOException e) {
				throw new OCTException("Exception while loading logo", e);
			}
		}
	}
}
