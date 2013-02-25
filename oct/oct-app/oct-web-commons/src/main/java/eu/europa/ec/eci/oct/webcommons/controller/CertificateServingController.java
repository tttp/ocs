package eu.europa.ec.eci.oct.webcommons.controller;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import javax.ejb.EJB;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

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
public class CertificateServingController extends FileServingController{

	@EJB
	SystemManager sysManager;
	
	protected ServedFile getFile(HttpServletRequest req) throws OCTException{
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
		
		try {
			return new ServedFile(new FileInputStream(certFile), cert.getContentType(), cert.getFileName());
		} catch (FileNotFoundException e) {
			throw new OCTException("file not found", e);
		}
		
	}

}
