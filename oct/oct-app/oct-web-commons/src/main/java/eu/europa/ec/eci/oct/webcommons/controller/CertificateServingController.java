package eu.europa.ec.eci.oct.webcommons.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.ejb.EJB;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import eu.europa.ec.eci.oct.business.api.OCTException;
import eu.europa.ec.eci.oct.business.api.OCTIllegalOperationException;
import eu.europa.ec.eci.oct.business.api.SystemManager;
import eu.europa.ec.eci.oct.vo.certificate.Certificate;

/**
 * @author Marcin Dzierzak
 *
 */
@Controller
@RequestMapping("/certificate.do")
public class CertificateServingController{

	Logger logger = Logger.getLogger(CertificateServingController.class);

	
	private int FILEBUFFERSIZE = 2048;
	
	@EJB
	SystemManager sysManager;
	

	@RequestMapping(method = RequestMethod.GET)	
	protected void doGet(Model model, HttpServletRequest request, HttpServletResponse response) throws OCTException {
		
		Certificate cert = sysManager.getCertificate();
		
		String storagePath = cert.getPath();
		if(storagePath==null||storagePath.trim().length()==0){
			storagePath = "./OCT_FileStorage";
		}
		
		File certFile = new File( storagePath + "/certificate", cert.getFileName());		
		if(!certFile.exists()){
			logger.error("certificate file does not exist in a file system:" + certFile.getAbsolutePath());				
			throw new OCTIllegalOperationException("Certificate file does not exist");
		}
		
		ServletOutputStream out;
		InputStream in = null;
		try {
			out = response.getOutputStream();
			in = new FileInputStream(certFile);
			
			String mimeType = cert.getContentType();
			byte[] bytes = new byte[FILEBUFFERSIZE ];
			int bytesRead;

			response.setContentType(mimeType);	
			response.setHeader( "Content-Disposition", "attachment; filename=\"" + cert.getFileName() + "\"" );


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
