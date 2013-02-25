package eu.europa.ec.eci.oct.admin.controller;

import java.io.IOException;
import java.io.StringReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import eu.europa.ec.eci.export.Export;
import eu.europa.ec.eci.oct.admin.model.SystemPreferencesBean;
import eu.europa.ec.eci.oct.admin.validator.SystemPreferencesValidator;
import eu.europa.ec.eci.oct.business.api.InitiativeService;
import eu.europa.ec.eci.oct.business.api.OCTException;
import eu.europa.ec.eci.oct.business.api.SystemManager;
import eu.europa.ec.eci.oct.business.api.system.SystemStateChecker;
import eu.europa.ec.eci.oct.eci.export.DataLoader;
import eu.europa.ec.eci.oct.eci.export.ECI2OCTConverter;
import eu.europa.ec.eci.oct.eci.export.ECIDataException;
import eu.europa.ec.eci.oct.eci.export.LanguageProvider;
import eu.europa.ec.eci.oct.entities.admin.Contact;
import eu.europa.ec.eci.oct.entities.admin.InitiativeDescription;
import eu.europa.ec.eci.oct.entities.admin.SystemPreferences;
import eu.europa.ec.eci.oct.entities.admin.SystemState;
import eu.europa.ec.eci.oct.entities.member.Language;
import eu.europa.ec.eci.oct.utils.StringUtils;
import eu.europa.ec.eci.oct.webcommons.controller.HttpGetAndPostController;
import eu.europa.ec.eci.oct.webcommons.security.ModelSystemStateProvider;
import eu.europa.ec.eci.oct.webcommons.validator.MultipartFileValidator;

/**
 * Controller class processing request related to 'Initiative Setup' view
 * 
 * @author Dzierzak Marcin
 */
@Controller
@RequestMapping("/systemprefs.do")
@SessionAttributes("form")
public class SystemPreferencesController extends HttpGetAndPostController<SystemPreferencesBean> {

	private static final String LANGUAGE_VERSIONS = "languageVersions";

	private Logger logger = Logger.getLogger(SystemPreferencesController.class);

	@EJB
	private SystemManager sysManager;

	@EJB
	private InitiativeService initiativeService;

	@Resource(name = "eciExportExtensionWhitelist")
	private List<String> uploadExtensionWhitelist;

	@Override
	protected String _doGet(Model model, HttpServletRequest request, HttpServletResponse response) throws OCTException {

		// preview url
		model.addAttribute("oct_preview_url", "systemprefs.do");

		// create and init form if missing
		if (!model.containsAttribute("form")) {
			initForm(model, request);
		} else {
			// otherwise just reset language list
			SystemPreferencesBean bean = (SystemPreferencesBean) model.asMap().get("form");
			bean.setLanguages(getLanguageList(model, request));
		}

		// for preview of linguistic versions
		if (ServletRequestUtils.getStringParameter(request, "preview", "false").equalsIgnoreCase("true")) {
			Long langId = new Long(request.getParameter("langId"));
			logger.info("language version preview request language id " + langId.longValue());

			SystemPreferencesBean bean = (SystemPreferencesBean) model.asMap().get("form");

			@SuppressWarnings("unchecked")
			List<InitiativeDescription> descriptions = (List<InitiativeDescription>) request.getSession().getAttribute(
					LANGUAGE_VERSIONS);
			for (InitiativeDescription id : descriptions) {
				if (id.getLanguage() != null && langId.equals(id.getLanguage().getId())) {

					// mark selected linguistic version
					model.addAttribute("oct_preview_lang_id", langId);

					// mark if we are reviewing draft
					if (ServletRequestUtils.getStringParameter(request, "draft", "false").equals("true")) {
						model.addAttribute("draftUploaded", true);
					}
					// set values regarding linguistic version
					bean.setLvTitle(id.getTitle());
					bean.setLvSubjectMatter(id.getSubjectMatter());
					bean.setLvObjectives(id.getObjectives());
					bean.setLvWebsite(id.getUrl());
					bean.setLvLanguage(id.getLanguage());
					break;
				}
			}
		}

		return "systemprefs";
	}

	private void initForm(Model model, HttpServletRequest request) throws OCTException {
		SystemPreferences prefs = sysManager.getSystemPreferences();

		SystemPreferencesBean bean = new SystemPreferencesBean();
		populateRegistrationInfo(prefs, bean);

		InitiativeDescription defDesc = prefs.getDefaultDescription();
		if (defDesc != null) {
			populateLanguageVersion(prefs.getDefaultDescription(), bean);
		}

		Contact c = initiativeService.getContact();
		if (c != null) {
			populateContactInfo(bean, c);
		}

		bean.setLanguages(getLanguageList(model, request));

		model.addAttribute("form", bean);

		List<InitiativeDescription> committedDescs = initiativeService.getDescriptions();

		for (InitiativeDescription desc : committedDescs) {
			if (defDesc != null && desc.getId().equals(defDesc.getId())) {
				defDesc = desc;
			}
		}
		if (defDesc != null) {
			committedDescs.remove(defDesc);
		}
		Collections.sort(committedDescs, new Comparator<InitiativeDescription>() {
			@Override
			public int compare(InitiativeDescription i1, InitiativeDescription i2) {
				return i1.getLanguage().getDisplayOrder().intValue() - i2.getLanguage().getDisplayOrder().intValue();
			}
		});

		request.getSession().setAttribute(LANGUAGE_VERSIONS, committedDescs);
	}

	private void populateLanguageVersion(InitiativeDescription desc, SystemPreferencesBean bean) {
		if (desc == null) {
			logger.warn("Missing default language version");
			return;
		}
		bean.setTitle(desc.getTitle());
		bean.setSubjectMatter(desc.getSubjectMatter());
		bean.setObjectives(desc.getObjectives());
		bean.setLanguage(desc.getLanguage());
		bean.setWebsite(desc.getUrl());

		if (!StringUtils.isEmpty(desc.getUrl())) {
			try {
				new URL(desc.getUrl());
				bean.setWebsite(desc.getUrl());
			} catch (MalformedURLException e) {
				logger.warn("Commision Register URL syntatically incorrect: " + desc.getUrl(), e);
				bean.setWebsite(null);
			}
		}
	}

	private void populateRegistrationInfo(SystemPreferences prefs, SystemPreferencesBean bean) {
		bean.setRegistrationDate(prefs.getRegistrationDate());
		bean.setRegistrationNumber(prefs.getRegistrationNumber());

		if (!StringUtils.isEmpty(prefs.getCommissionRegisterUrl())) {
			try {
				new URL(prefs.getCommissionRegisterUrl());
				bean.setRegisterUrl(prefs.getCommissionRegisterUrl());
			} catch (MalformedURLException e) {
				logger.warn("Commision Register URL syntatically incorrect: " + prefs.getCommissionRegisterUrl(), e);
				bean.setRegisterUrl(null);
			}
		}

	}

	private void populateContactInfo(SystemPreferencesBean bean, Contact c) {
		bean.setOrganizers(c.getOrganizers());
		bean.setContactPerson(c.getName());
		bean.setContactEmail(c.getEmail());
	}

	private Contact convertContactData(SystemPreferencesBean bean) throws OCTException {
		Contact contact = new Contact();
		contact.setOrganizers(bean.getOrganizers());
		contact.setName(bean.getContactPerson());
		contact.setEmail(bean.getContactEmail());

		return contact;
	}

	private SystemPreferences convertRegistrationInfo(SystemPreferencesBean bean) throws OCTException {
		SystemPreferences prefs = new SystemPreferences();
		prefs.setCommissionRegisterUrl(bean.getRegisterUrl());
		prefs.setRegistrationDate(bean.getRegistrationDate());
		prefs.setRegistrationNumber(bean.getRegistrationNumber());
		return prefs;
	}

	private InitiativeDescription convertDescription(SystemPreferencesBean bean) throws OCTException {

		InitiativeDescription desc = new InitiativeDescription();
		desc.setLanguage(bean.getLanguage());
		desc.setTitle(bean.getTitle());
		desc.setSubjectMatter(bean.getSubjectMatter());
		desc.setObjectives(bean.getObjectives());
		desc.setUrl(bean.getWebsite());

		return desc;
	}

	@Override
	protected String _doPost(Model model, SystemPreferencesBean bean, BindingResult result, SessionStatus status,
			HttpServletRequest request, HttpServletResponse response) throws OCTException {

		model.addAttribute("oct_preview_url", "systemprefs.do");

		if (request.getParameter("edit") != null) {
			logger.info("Edit initiative data request");
			model.addAttribute("editing", true);
			cleanupDraft(model, status, request);

		} else if (request.getParameter("confirmEdit") != null) {
			logger.info("Edit initiative data confirmation");
			Validator v = new SystemPreferencesValidator(getCurrentMessageBundle(request), true);
			v.validate(bean, result);
			if (result.hasErrors()) {
				model.addAttribute("editing", true);
				return doGet(model, request, response);
			}

			InitiativeDescription desc = convertDescription(bean);
			List<InitiativeDescription> descs = new ArrayList<InitiativeDescription>();
			descs.add(desc);

			initiativeService.overwriteSetup(convertRegistrationInfo(bean), convertContactData(bean), descs,
					desc.getLanguage(), true, null);

			cleanupDraft(model, status, request);

		} else if (request.getParameter("resetEdit") != null) {
			logger.info("Edit initiative data cancelation");
			cleanupDraft(model, status, request);

		} else if (request.getParameter("upload") != null) {
			logger.info("Upload initiative data request");
			model.addAttribute("uploading", true);
			cleanupDraft(model, status, request);

		} else if (request.getParameter("confirmUpload") != null) {
			logger.info("Upload initiative data cancelation");
			Validator v = new SystemPreferencesValidator(getCurrentMessageBundle(request), false);
			bean.setLanguageVersions((List<InitiativeDescription>) request.getSession().getAttribute(LANGUAGE_VERSIONS));
			v.validate(bean, result);
			if (result.hasErrors()) {
				model.addAttribute("uploading", true);
				return doGet(model, request, response);
			}

			InitiativeDescription desc = convertDescription(bean);
			@SuppressWarnings("unchecked")
			List<InitiativeDescription> descs = (List<InitiativeDescription>) request.getSession().getAttribute(
					LANGUAGE_VERSIONS);
			descs.add(desc);

			initiativeService.overwriteSetup(
					convertRegistrationInfo(bean),
					convertContactData(bean),
					descs,
					bean.getLanguage(),
					!SystemStateChecker.getController(new ModelSystemStateProvider(model)).isInState(
							SystemState.OPERATIONAL), new Date());

			cleanupDraft(model, status, request);

		} else if (request.getParameter("resetUpload") != null) {
			logger.info("Upload initiative data cancelation");
			cleanupDraft(model, status, request);
		} else if (request.getParameter("preview") != null) {
			logger.info("language version preview request");
			Long langId = new Long(request.getParameter("langId"));

			@SuppressWarnings("unchecked")
			List<InitiativeDescription> descriptions = (List<InitiativeDescription>) request.getSession().getAttribute(
					LANGUAGE_VERSIONS);
			for (InitiativeDescription id : descriptions) {
				if (id.getLanguage() != null && langId.equals(id.getLanguage().getId())) {
					bean.setLvTitle(id.getTitle());
					bean.setLvSubjectMatter(id.getSubjectMatter());
					bean.setLvObjectives(id.getObjectives());
					bean.setLvWebsite(id.getUrl());
					bean.setLvLanguage(id.getLanguage());
					break;
				}
			}
			return doGet(model, request, response);

		} else {

			// doUpload
			// uploading xml file containing initiative setup
			logger.info("Upload initiative data confirmation");
			MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
			MultipartFile multipartFile = multipartRequest.getFile("xmlFile");

			// validate the MultipartFile (= the file upload)
			final Validator multipartFileValidator = new MultipartFileValidator(getCurrentMessageBundle(request),
					"oct.s4.xml.upload.error.invalid.format",
					Collections.<MultipartFileValidator.RejectReason, String> emptyMap(), uploadExtensionWhitelist, 0);
			multipartFileValidator.validate(multipartFile, result);

			if (result.hasErrors()) {
				return doGet(model, multipartRequest, response);
			}

			try {
				byte[] bytes = multipartFile.getBytes();

				Export export = DataLoader.loadData(new StringReader(new String(bytes, "UTF-8")));
				ECI2OCTConverter conv = new ECI2OCTConverter(new LanguageProvider() {
					@Override
					public Language getLanguageByCode(String code) throws ECIDataException {
						try {
							Language lang = sysManager.getLanguageByCode(code);
							if (lang == null) {
								throw new ECIDataException("Missing language entry for a code " + code);
							}
							return lang;
						} catch (OCTException e) {
							logger.error("problem occured while looking up the language by code " + code, e);
							throw new ECIDataException("problem occured while looking up the language by code " + code,
									e);
						}
					}
				});
				logger.info("Uploaded file successfully parsed");

				SystemPreferences prefs = conv.convertInitiative(export.getInitiative());
				Contact contact = conv.convertOrganisers(export.getInitiative());

				bean.setUploadDate(export.getDate().toGregorianCalendar().getTime());
				List<InitiativeDescription> descs = new ArrayList<InitiativeDescription>();
				InitiativeDescription defaultDesc = conv.convertLanguageVersions(export.getInitiative(), descs);

				if (SystemStateChecker.getController(new ModelSystemStateProvider(model)).isInState(
						SystemState.OPERATIONAL)) {
					descs = removeExistingDescriptions(descs, initiativeService.getDescriptions());
				}

				populateContactInfo(bean, contact);

				if (!SystemStateChecker.getController(new ModelSystemStateProvider(model)).isInState(
						SystemState.OPERATIONAL)) {
					populateRegistrationInfo(prefs, bean);
					populateLanguageVersion(defaultDesc, bean);
				}

				Collections.sort(descs, new Comparator<InitiativeDescription>() {
					@Override
					public int compare(InitiativeDescription i1, InitiativeDescription i2) {
						return i1.getLanguage().getDisplayOrder().intValue()
								- i2.getLanguage().getDisplayOrder().intValue();
					}
				});
				request.getSession().setAttribute(LANGUAGE_VERSIONS, descs);

				model.addAttribute("draftUploaded", true);
				model.addAttribute("form", bean);

			} catch (ECIDataException e) {
				logger.error("problem occured while loading xml file", e);
				String errorMessage = getCurrentMessageBundle(request).getMessage(
						"oct.s4.xml.upload.error.invalid.format");
				errorMessage += ": " + e.getMessage();
				result.reject("", errorMessage);
				return doGet(model, multipartRequest, response);
			} catch (IOException e) {
				logger.error("problem occured while loading xml file", e);
				result.reject("oct.s4.xml.upload.error.invalid.format");
				return doGet(model, multipartRequest, response);
			}
		}

		return "redirect:systemprefs.do?clean=false";
	}

	private List<InitiativeDescription> removeExistingDescriptions(List<InitiativeDescription> imported,
			List<InitiativeDescription> existing) {

		List<InitiativeDescription> validated = new ArrayList<InitiativeDescription>();

		for (InitiativeDescription importedDesc : imported) {
			boolean found = false;
			for (InitiativeDescription existingDesc : existing) {
				if (existingDesc.getLanguage().getId().equals(importedDesc.getLanguage().getId())) {
					found = true;
					break;
				}
			}
			if (!found) {
				logger.info("Accepting description for language id [" + importedDesc.getLanguage().getId()
						+ "] for commit");
				validated.add(importedDesc);
			} else {
				logger.info("Rejecting description for language id [" + importedDesc.getLanguage().getId()
						+ "] for commit");
			}
		}

		return validated;
	}

	/**
	 * Clean up draft stored in a session
	 * 
	 * @param model
	 * @param status
	 * @param request
	 * @throws OCTException
	 */
	private void cleanupDraft(Model model, SessionStatus status, HttpServletRequest request) throws OCTException {
		// complete session attributes
		status.setComplete();
		// remove draft language versions
		request.getSession().removeAttribute(LANGUAGE_VERSIONS);

		// set committed language versions in place of draft ones
		List<InitiativeDescription> committedDescs = initiativeService.getDescriptions();

		// MKE: the code below never gets executed, but I cannot
		// fix it, since I don't know what it should do

		// find
		// InitiativeDescription defDesc = null;
		// for (InitiativeDescription desc : committedDescs) {
		// if (defDesc != null && desc.getId().equals(defDesc.getId())) {
		// defDesc = desc;
		// }
		// }
		// if (defDesc != null) {
		// committedDescs.remove(defDesc);
		// }

		request.getSession().setAttribute(LANGUAGE_VERSIONS, committedDescs);
	}

}
