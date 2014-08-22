package eu.europa.ec.eci.oct.admin.controller;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import eu.europa.ec.eci.oct.admin.model.SystemStateBean;
import eu.europa.ec.eci.oct.business.api.OCTException;
import eu.europa.ec.eci.oct.business.api.OCTMissingCertificateException;
import eu.europa.ec.eci.oct.business.api.OCTMissingSetupDateException;
import eu.europa.ec.eci.oct.business.api.SignatureService;
import eu.europa.ec.eci.oct.business.api.SystemManager;
import eu.europa.ec.eci.oct.business.api.system.OCTIllegalStateException;
import eu.europa.ec.eci.oct.business.api.system.SystemStateChecker;
import eu.europa.ec.eci.oct.entities.admin.SystemPreferences;
import eu.europa.ec.eci.oct.entities.admin.SystemState;
import eu.europa.ec.eci.oct.vo.certificate.Certificate;
import eu.europa.ec.eci.oct.webcommons.controller.CommonControllerConstants;
import eu.europa.ec.eci.oct.webcommons.controller.HttpGetAndPostController;
import eu.europa.ec.eci.oct.webcommons.security.ModelSystemStateProvider;
import eu.europa.ec.eci.oct.webcommons.security.requesttoken.RequestToken;
import eu.europa.ec.eci.oct.webcommons.security.requesttoken.TokenConsumeCondition;
import eu.europa.ec.eci.oct.webcommons.utils.ErrorHandlingUtils;
import eu.europa.ec.eci.oct.webcommons.validator.MultipartFileValidator;
import eu.europa.ec.eci.oct.webcommons.validator.MultipartFileValidator.RejectReason;

@Controller
@RequestMapping("/systemstatus.do")
public class SystemStateController extends HttpGetAndPostController<SystemStateBean> {

	private static final String PARAM_PRODUCTION_CONFIRM = "confirmProduction";

	private static final String PARAM_PRODUCTION_RESET = "resetProduction";

	private static final String PARAM_PRODUCTION = "production";

	private static final String PARAM_COLLECTOR_CONFIRM = "confirmCollector";

	private static final String PARAM_COLLECTOR_RESET = "resetCollector";

	private static final String PARAM_COLLECTOR = "collector";

	Logger logger = Logger.getLogger(SystemStateController.class);

	@EJB
	SystemManager sysManager;

	@EJB
	private SignatureService sigService;

	@Resource(name = "certificateExtensionWhitelist")
	private List<String> uploadExtensionWhitelist;

	@Override
	protected String _doPost(Model model, SystemStateBean bean, BindingResult result, SessionStatus status,
			HttpServletRequest request, HttpServletResponse response) throws OCTException {

		if (request.getParameter(PARAM_COLLECTOR) != null) {
			logger.info("collector state modification request");
			try {
				SystemStateChecker.getController(new ModelSystemStateProvider(model)).disallowedStates(
						SystemState.DEPLOYED);
				if (sysManager.getSystemPreferences().isCollecting() != bean.isCollectorState()) {
					model.addAttribute("collectorConfirm", bean.isCollectorState());
				}
			} catch (OCTIllegalStateException e) {
				result.reject("oct.s7.collection.on.illegal",
						"Turning collection on is not allowed when initiative not set up");
				bean.setCollectorState(false);
			}
			return doGet(model, request, response);

		} else if (request.getParameter(PARAM_COLLECTOR_RESET) != null) {
			logger.info("cancelling request for changing collector state to " + bean.isCollectorState());
		} else if (request.getParameter(PARAM_COLLECTOR_CONFIRM) != null) {
			boolean collector = bean.isCollectorState();
			logger.info("collector state modification. enabled: " + collector);
			sysManager.setCollecting(collector);
		} else if (request.getParameter(PARAM_PRODUCTION) != null) {
			logger.info("system state modification request");

			boolean error = false;
			try {
				sysManager.getCertificate();
			} catch (OCTMissingCertificateException e) {
				logger.error("can not go to production mode, certificate not found", e);
				result.reject("oct.s7.production.certificate.required");
				error = true;
			}

			try {
				sysManager.checkOnlineAvailability();
			} catch (OCTMissingSetupDateException e) {
				logger.error("can not go to production mode if setup manually entered", e);
				result.reject("oct.s7.production.xml.required");
				error = true;
			}

			if (!error && bean.isGoIntoProduction()) {
				model.addAttribute("productionConfirm", bean.isGoIntoProduction());
			}
			return doGet(model, request, response);
		} else if (request.getParameter(PARAM_PRODUCTION_RESET) != null) {
			logger.info("cancelling request for going into production");
		} else if (request.getParameter(PARAM_PRODUCTION_CONFIRM) != null) {

			SystemStateChecker.getController(new ModelSystemStateProvider(model)).disallowedStates(
					SystemState.OPERATIONAL);

			try {
				// fetch certificate in order to check if already deployed
				sysManager.getCertificate();
			} catch (OCTMissingCertificateException e) {
				logger.error("can not go to production mode, certificate not found", e);
				result.reject("oct.s7.production.certificate.required");
				return doGet(model, request, response);
			}

			try {
				sysManager.goToProduction();
			} catch (OCTMissingSetupDateException e) {
				logger.error("can not go to production mode if setup manually entered", e);
				result.reject("oct.s7.production.xml.required");
				return doGet(model, request, response);
			} catch (OCTIllegalStateException e) {
				logger.error("can not go to production mode if setup manually entered", e);
				result.reject("oct.s7.production.xml.required");
				return doGet(model, request, response);
			}
			logger.info("transiting to production mode: " + bean.isGoIntoProduction());

			sysManager.setCollecting(true);
			sigService.deleteAllSignatures();
		} else {
			// when enctype of the form set to multipart/form-data
			// no parameter exists in the request
			// no parameter = upload cert file

			SystemStateChecker.getController(new ModelSystemStateProvider(model)).disallowedStates(
					SystemState.OPERATIONAL);

			MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
			MultipartFile file = multipartRequest.getFile("certFile");

			logger.info("uploading certificate file: " + file.getOriginalFilename());

			// validate the MultipartFile (= the file upload)
			final Map<MultipartFileValidator.RejectReason, String> rejectDetailsMap = new HashMap<MultipartFileValidator.RejectReason, String>();
			rejectDetailsMap.put(RejectReason.EMPTY_CONTENT, "oct.s7.error.certificate.missing");
			rejectDetailsMap.put(RejectReason.EMPTY_NAME, "oct.s7.error.certificate.missing");
			rejectDetailsMap.put(RejectReason.BAD_EXTENSION, "oct.s7.error.certificate.badExtension");
			rejectDetailsMap.put(RejectReason.MAX_SIZE_EXCEEDED, "oct.s7.error.certificate.too.big");

			final Validator multipartFileValidator = new MultipartFileValidator(getCurrentMessageBundle(request),
					"oct.s7.error.certificate.upload", rejectDetailsMap, uploadExtensionWhitelist, 10000000);
			multipartFileValidator.validate(file, result);

			if (result.hasErrors()) {
				return doGet(model, request, response);
			}
			SystemPreferences prefs = (SystemPreferences) model.asMap().get(
					CommonControllerConstants.MODEL_ATTRIBUTE_SYSTEM_PREFS);

			String storagePath = prefs.getFileStoragePath();
			if (storagePath == null || storagePath.trim().length() == 0) {
				storagePath = "./OCT_FileStorage";
			}

			File destFolder = new File(storagePath, "/certificate");

			if (!destFolder.exists()) {
				boolean dirCreated = destFolder.mkdirs();
				if (logger.isDebugEnabled()) {
					logger.debug("Storage directory \"" + destFolder.getPath() + "\" created? => " + dirCreated);
				}
			}

			final String extension = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf('.'));
			final String fileName = new StringBuilder().append("certificate").append(System.currentTimeMillis())
					.append(extension).toString();

			File dest = new File(destFolder, fileName);
			try {
				file.transferTo(dest);
				logger.info("Uploaded certificate file successfully transfered to the local file "
						+ dest.getAbsolutePath());

			} catch (IllegalStateException e) {
				logger.error("illegal state error while uploading certificate", e);
				result.reject("oct.s7.error.certificate.upload", "Error uploading certificate");
				return doGet(model, request, response);
			} catch (IOException e) {
				logger.error("input/output error while uploading certificate", e);
				result.reject("oct.s7.error.certificate.upload", e.getMessage());
				return doGet(model, request, response);
			}

			Certificate cert = new Certificate();
			cert.setContentType(file.getContentType());
			cert.setFileName(fileName);

			try {
				sysManager.installCertificate(cert);
				logger.info("Uploaded certificate file successfully installed within system");
				model.addAttribute("oct_cert", cert);
				addSuccessMessage(request, model, "oct.s7.certificate.upload.success", null);
			} catch (OCTException e) {
				logger.error("error while registering certificate", e);
				result.reject("oct.s7.error.certificate.upload");
				try {
					dest.delete();
				} catch (SecurityException se) {
					// ignore
				}
			}
			return doGet(model, request, response);
		}

		return "redirect:systemstatus.do";
	}

	@Override
	protected String _doGet(Model model, HttpServletRequest request, HttpServletResponse response) throws OCTException {

		if (!model.containsAttribute("form")) {
			SystemStateBean bean = new SystemStateBean();
			model.addAttribute("form", bean);

			SystemPreferences prefs = sysManager.getSystemPreferences();
			bean.setCollectorState(prefs.isCollecting());
			bean.setSystemState(prefs.getState().name());
		}

		return "systemstatus";
	}

	@Override
	protected RequestToken getToken(HttpServletRequest request) {
		// produce request tokens that will not be consumed on
		// viewFile actions
		// (these are downloads which don't redisplay the form, so
		// by consuming them, we would invalidate any token for the form)
		return requestTokenHelper.getToken(request, new TokenConsumeCondition() {
			private static final long serialVersionUID = 2504610011311013117L;

			@Override
			public boolean isConsumable(HttpServletRequest request) {
				return request.getParameter("viewFile") == null;
			}

			@Override
			public String toString() {
				return "<consumed if no 'viewFile' param present>";
			}
		});
	}

}
