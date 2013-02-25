package eu.europa.ec.eci.oct.webcommons.controller;

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

	Logger logger = Logger.getLogger(FileServingController.class);

	private int FILEBUFFERSIZE = 2048;

	protected abstract ServedFile getFile(HttpServletRequest request) throws OCTException;	
	


	@RequestMapping(method = RequestMethod.GET)
	protected void doGet(Model model, HttpServletRequest request, HttpServletResponse response)
			throws OCTException {
								
				ServedFile file = getFile(request);
				
				ServletOutputStream out;
				InputStream in = null;
				try {
					out = response.getOutputStream();
					in = file.getIs();
					
					String mimeType = file.getContentType();
					byte[] bytes = new byte[FILEBUFFERSIZE ];
					int bytesRead;
			
					response.setContentType(mimeType);	
					response.setHeader( "Content-Disposition", "attachment; filename=\"" + file.getFileName() + "\"" );
						
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