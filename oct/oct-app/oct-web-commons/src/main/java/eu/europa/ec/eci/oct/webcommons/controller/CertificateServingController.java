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

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import javax.ejb.EJB;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import eu.europa.ec.eci.oct.business.api.OCTException;
import eu.europa.ec.eci.oct.business.api.OCTIllegalOperationException;
import eu.europa.ec.eci.oct.business.api.SystemManager;
import eu.europa.ec.eci.oct.vo.certificate.Certificate;
import eu.europa.ec.eci.oct.webcommons.controller.fileserving.FileServingController;
import eu.europa.ec.eci.oct.webcommons.controller.fileserving.ServedFile;

/**
 * @author Marcin Dzierzak
 * 
 */
@Controller
@RequestMapping("/certificate.do")
public class CertificateServingController extends FileServingController {

	private static final Logger logger = Logger.getLogger(CertificateServingController.class);

	@EJB
	private SystemManager sysManager;

	protected ServedFile getFile(HttpServletRequest req, HttpServletResponse response) throws OCTException {
		final Certificate cert = sysManager.getCertificate();

		String storagePath = cert.getPath();
		if (storagePath == null || storagePath.trim().length() == 0) {
			storagePath = "./OCT_FileStorage";
		}

		File certFile = new File(storagePath + "/certificate", cert.getFileName());
		if (!certFile.exists()) {
			logger.error("certificate file does not exist in a file system:" + certFile.getAbsolutePath());
			throw new OCTIllegalOperationException("Certificate file does not exist");
		}

		try {
			return new ServedFile(new FileInputStream(certFile), cert.getContentType(), cert.getFileName(), true);
		} catch (FileNotFoundException e) {
			throw new OCTException("file not found", e);
		}

	}

}
