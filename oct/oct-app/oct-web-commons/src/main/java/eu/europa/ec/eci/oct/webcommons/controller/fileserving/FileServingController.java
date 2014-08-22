/** ====================================================================
 * Licensed under the European Union Public Licence (EUPL v1.2) 
 * https://joinup.ec.europa.eu/community/eupl/topic/public-consultation-draft-eupl-v12
 * ====================================================================
 *
 * @author Daniel CHIRITA
 * @created: 23/05/2013
 *
 */
package eu.europa.ec.eci.oct.webcommons.controller.fileserving;

import java.io.IOException;
import java.io.InputStream;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import eu.europa.ec.eci.oct.business.api.OCTException;

public abstract class FileServingController {

	private final static Logger logger = Logger.getLogger(FileServingController.class);

	private static final int FILEBUFFERSIZE = 2048;

	protected abstract ServedFile getFile(HttpServletRequest request, HttpServletResponse response)
			throws RequestHandledTransparentlyException, OCTException;

	@RequestMapping(method = RequestMethod.GET)
	protected void doGet(Model model, HttpServletRequest request, HttpServletResponse response) throws OCTException {
		ServedFile file;
		try {
			file = getFile(request, response);
		} catch (RequestHandledTransparentlyException re) {
			// we have already redirected, no need to proceed
			return;
		}

		ServletOutputStream out;
		InputStream in = null;
		try {
			out = response.getOutputStream();
			in = file.getIs();

			String mimeType = file.getContentType();
			response.setContentType(mimeType);
			if (file.isAttachment()) {
				response.setHeader("Content-Disposition", "attachment; filename=\"" + file.getFileName() + "\"");
			}

			final byte[] bytes = new byte[FILEBUFFERSIZE];
			int bytesRead;
			while ((bytesRead = in.read(bytes)) != -1) {
				out.write(bytes, 0, bytesRead);
			}

		} catch (IOException e) {
			logger.error("I/O exception occured", e);
			throw new OCTException("I/O exception occured", e);
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (IOException e) {
					logger.warn("I/O exception occured while closing input stream", e);
					logger.warn("... will continue anyway");
				}
			}
		}
	}

}