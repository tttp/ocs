/** ====================================================================
 * Licensed under the European Union Public Licence (EUPL v1.2) 
 * https://joinup.ec.europa.eu/community/eupl/topic/public-consultation-draft-eupl-v12
 * ====================================================================
 *
 * @author Daniel CHIRITA
 * @created: 23/05/2013
 *
 */
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

import org.apache.commons.validator.routines.UrlValidator;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import eu.europa.ec.eci.oct.admin.model.SettingsBean;
import eu.europa.ec.eci.oct.business.api.ConfigurationService;
import eu.europa.ec.eci.oct.business.api.ConfigurationService.Parameter;
import eu.europa.ec.eci.oct.business.api.OCTException;
import eu.europa.ec.eci.oct.business.api.SystemManager;
import eu.europa.ec.eci.oct.entities.ConfigurationParameter;
import eu.europa.ec.eci.oct.webcommons.controller.HttpGetAndPostController;
import eu.europa.ec.eci.oct.webcommons.validator.MultipartFileValidator;
import eu.europa.ec.eci.oct.webcommons.validator.MultipartFileValidator.RejectReason;

@Controller
@RequestMapping("/settings.do")
public class SettingsController extends HttpGetAndPostController<SettingsBean> {

	private static final Logger logger = Logger.getLogger(SettingsController.class);

	@EJB
	private ConfigurationService configurationService;

	@EJB
	private SystemManager systemManager;

	@Resource(name = "logoFileExtensionWhitelist")
	private List<String> uploadExtensionWhitelist;

	@Override
	protected String _doGet(Model model, HttpServletRequest request, HttpServletResponse response) throws OCTException {
		initForm(model, request);

		return "settings";
	}

	/**
	 * Initialises the model by reading the settings.
	 * 
	 * @param model
	 * @param request
	 * @throws OCTException
	 */
	private void initForm(Model model, HttpServletRequest request) throws OCTException {
		if (!model.containsAttribute("form")) {
			SettingsBean bean = new SettingsBean();

			ConfigurationParameter param = configurationService.getConfigurationParameter(Parameter.LOGO_PATH);
			bean.setCustomLogo(Parameter.LOGO_PATH.getDefaultValue().equals(param.getValue()) ? null : param.getValue());

			param = configurationService.getConfigurationParameter(Parameter.OPTIONAL_VALIDATION);
			bean.setOptionalValidation(Boolean.valueOf(param.getValue()));

			param = configurationService.getConfigurationParameter(Parameter.SHOW_DISTRIBUTION_MAP);
			bean.setDisplayMap(Boolean.valueOf(param.getValue()));

			param = configurationService.getConfigurationParameter(Parameter.CALLBACK_URL);
			bean.setCallbackUrl(param.getValue());

			model.addAttribute("form", bean);
		}
	}

	@Override
	protected String _doPost(Model model, SettingsBean bean, BindingResult result, SessionStatus status, HttpServletRequest request,
			HttpServletResponse response) throws OCTException {
		if (request.getParameter("saveSettings") != null) {
			ConfigurationParameter param;

			// custom logo settings
			if (bean.isDeleteLogo()) {
				param = configurationService.getConfigurationParameter(Parameter.LOGO_PATH);

				// delete file from disk
				final String storagePath = systemManager.getSystemPreferences().getFileStoragePath();
				final File destFolder = new File(storagePath, "/custom");
				final File dest = new File(destFolder, param.getValue());
				dest.delete();

				// update db
				param.setValue(Parameter.LOGO_PATH.getDefaultValue());
				configurationService.updateParameter(param);
			} else {
				final CommonsMultipartFile file = bean.getLogoFile();
				if (file != null && !"".equals(file.getOriginalFilename())) {
					if (logger.isDebugEnabled()) {
						logger.debug("Uploaded new logo file: " + file.getFileItem().getName() + " / " + file.getSize());
					}

					// validate uploaded logo file
					final Map<MultipartFileValidator.RejectReason, String> rejectDetailsMap = new HashMap<MultipartFileValidator.RejectReason, String>();
					rejectDetailsMap.put(RejectReason.EMPTY_CONTENT, "oct.settings.error.logo.missing");
					rejectDetailsMap.put(RejectReason.EMPTY_NAME, "oct.settings.error.logo.missing");
					rejectDetailsMap.put(RejectReason.BAD_EXTENSION, "oct.settings.error.logo.badExtension");
					rejectDetailsMap.put(RejectReason.MAX_SIZE_EXCEEDED, "oct.settings.error.logo.toobig");

					final Validator validator = new MultipartFileValidator(getCurrentMessageBundle(request),
							"oct.settings.error.logo.upload", rejectDetailsMap, uploadExtensionWhitelist, 150000);
					validator.validate(file, result);
					if (result.hasErrors()) {
						return doGet(model, request, response);
					}

					// validation passed, save file to needed location and
					// update the db
					final String storagePath = systemManager.getSystemPreferences().getFileStoragePath();
					final File destFolder = new File(storagePath, "/custom");
					if (!destFolder.exists()) {
						boolean dirCreated = destFolder.mkdirs();
						if (logger.isDebugEnabled()) {
							logger.debug("Storage directory \"" + destFolder.getPath() + "\" created? => " + dirCreated);
						}
					}

					final String extension = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf('.'));
					final String fileName = new StringBuilder().append("customlogo").append(System.currentTimeMillis()).append(extension)
							.toString();

					final File dest = new File(destFolder, fileName);
					try {
						file.transferTo(dest);
						if (logger.isDebugEnabled()) {
							logger.debug("Uploaded logo file successfully transfered to the local file " + dest.getAbsolutePath());
						}
					} catch (IllegalStateException e) {
						logger.error("illegal state error while uploading logo", e);
						result.reject("oct.settings.error.logo.upload", "Error uploading logo");
						return doGet(model, request, response);
					} catch (IOException e) {
						logger.error("input/output error while uploading logo", e);
						result.reject("oct.settings.error.logo.upload", e.getMessage());
						return doGet(model, request, response);
					}

					param = new ConfigurationParameter();
					param.setParam(Parameter.LOGO_PATH.getKey());
					param.setValue(fileName);
					configurationService.updateParameter(param);
				}
			}

			// callback url
			final String callbackUrl = bean.getCallbackUrl();
			if (callbackUrl != null && !"".equals(callbackUrl)) {
				// validate url
				UrlValidator validator = UrlValidator.getInstance();
				if (!validator.isValid(callbackUrl)) {
					result.rejectValue("callbackUrl", "oct.settings.error.callback.invalidurl", "An invalid URL has been specified.");
					return doGet(model, request, response);
				}
			}
			param = new ConfigurationParameter();
			param.setParam(Parameter.CALLBACK_URL.getKey());
			param.setValue(callbackUrl);
			configurationService.updateParameter(param);

			// optional validation
			param = new ConfigurationParameter();
			param.setParam(Parameter.OPTIONAL_VALIDATION.getKey());
			param.setValue(new Boolean(bean.getOptionalValidation()).toString());
			configurationService.updateParameter(param);

			// distribution map
			param = new ConfigurationParameter();
			param.setParam(Parameter.SHOW_DISTRIBUTION_MAP.getKey());
			param.setValue(new Boolean(bean.getDisplayMap()).toString());
			configurationService.updateParameter(param);
		}

		return "redirect:settings.do";
	}
}
