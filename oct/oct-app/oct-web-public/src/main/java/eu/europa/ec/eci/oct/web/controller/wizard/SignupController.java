package eu.europa.ec.eci.oct.web.controller.wizard;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.EJB;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.util.WebUtils;

import eu.europa.ec.eci.oct.business.api.DuplicateSignatureException;
import eu.europa.ec.eci.oct.business.api.OCTException;
import eu.europa.ec.eci.oct.business.api.SignatureService;
import eu.europa.ec.eci.oct.entities.CountryProperty;
import eu.europa.ec.eci.oct.entities.PropertyGroup;
import eu.europa.ec.eci.oct.entities.admin.InitiativeDescription;
import eu.europa.ec.eci.oct.entities.admin.SystemPreferences;
import eu.europa.ec.eci.oct.entities.admin.SystemState;
import eu.europa.ec.eci.oct.entities.member.Language;
import eu.europa.ec.eci.oct.entities.signature.PropertyValue;
import eu.europa.ec.eci.oct.entities.signature.Signature;
import eu.europa.ec.eci.oct.web.captcha.CaptchaService;
import eu.europa.ec.eci.oct.web.controller.ControllerConstants;
import eu.europa.ec.eci.oct.web.model.SignatureReviewBean;
import eu.europa.ec.eci.oct.web.model.SupportFormBean;
import eu.europa.ec.eci.oct.web.validator.SupportFormValidator;
import eu.europa.ec.eci.oct.webcommons.controller.HttpGetController;
import eu.europa.ec.eci.oct.webcommons.locale.LocaleUtils;
import eu.europa.ec.eci.oct.webcommons.locale.MessageSourceAware;
import eu.europa.ec.eci.oct.webcommons.security.requesttoken.RequestTokenHelper;
import eu.europa.ec.eci.oct.webcommons.utils.DateUtils;

@Controller
@RequestMapping("/signup.do")
@SessionAttributes("form")
public class SignupController extends HttpGetController {

	@EJB
	private SignatureService signatureService;

	@Autowired
	private CaptchaService captchaService;

	@Autowired
	protected RequestTokenHelper requestTokenHelper;

	private static final String SESSION_ATTR_CURRENT_PAGE = "__current__page__";
	private static final int DEFAULT_PROPERTY_VALUE_ID = -1;

	private static final int WIZARD_STEP_0 = 0;
	private static final int WIZARD_STEP_1 = 1;

	@RequestMapping(method = RequestMethod.POST)
	public String doPost(Model model, @ModelAttribute(value = "form") SupportFormBean formBean, BindingResult result,
			@RequestParam("_page") String page, SessionStatus status, HttpServletRequest request,
			HttpServletResponse response) throws OCTException {
		// assert that the expected token is found on the request
		if (!requestTokenHelper.checkAndConsume(request)) {
			try {
				response.sendError(HttpServletResponse.SC_FORBIDDEN);
			} catch (IOException e) {
				throw new OCTException("Unable to send the FORBIDDEN code", e);
			}
			return null;
		}

		if ((SystemState) model.asMap().get(ControllerConstants.MODEL_ATTRIBUTE_SYSTEM_STATE) == SystemState.DEPLOYED) {
			return "wizard/signup0";
		}

		if (!model.containsAttribute("form")) {
			return "redirect:signup.do";
		}

		int currentPage = 0;
		try {
			currentPage = Integer.parseInt(page);
			if (currentPage != WIZARD_STEP_0 && currentPage != WIZARD_STEP_1) {
				currentPage = 0;
			}
		} catch (NumberFormatException e) {
			// ignore
		}

		final MessageSourceAware resourceBundle = getCurrentMessageBundle(request);
		final SupportFormValidator validator = new SupportFormValidator(resourceBundle);

		if (request.getParameter("_cancel") != null) {
			status.setComplete();
			return "redirect:index.do";
		} else if ((request.getParameter("_changeCaptchaToImage.x") != null && request
				.getParameter("_changeCaptchaToImage.y") != null)
				|| (request.getParameter("_changeCaptchaToAudio.x") != null && request
						.getParameter("_changeCaptchaToAudio.y") != null)) {
			if (request.getParameter("_changeCaptchaToImage.x") != null) {
				formBean.setCaptchaType(CaptchaService.CAPTCHA_IMAGE_TYPE);
			} else {
				formBean.setCaptchaType(CaptchaService.CAPTCHA_AUDIO_TYPE);
			}
			setCurrentPage(request.getSession(), WIZARD_STEP_1);
			return super.doGet(model, request, response);
		} else if (request.getParameter("_finish") != null) {
			if (formBean.getCountryToSignFor() == null) {
				result.rejectValue("countryCode", "err.empty.country", "The country name cannot be empty!");

				setCurrentPage(request.getSession(), WIZARD_STEP_0);
				return super.doGet(model, request, response);
			}

			// populate multichoice values
			for (PropertyValue property : formBean.getProperties()) {
				if (property != null && property.getProperty() != null
						&& property.getProperty().getProperty().getGroup().isMultichoice()) {
					CountryProperty selectedProperty = null;
					Long selectedPropertyId = formBean.getMultichoiceSelections().get(
							property.getProperty().getProperty().getGroup().getId());
					if (selectedPropertyId != DEFAULT_PROPERTY_VALUE_ID) {
						selectedProperty = signatureService.getCountryPropertyById(selectedPropertyId);
						if (selectedProperty != null && selectedProperty.getProperty().getGroup().isMultichoice()) {
							property.setProperty(selectedProperty);
						}
					}
				}
			}

			// validate all
			validator.validate(formBean, result);

			if (result.hasErrors()) {
				// reload page
				formBean.setCaptcha(null);

				setCurrentPage(request.getSession(), WIZARD_STEP_1);
				return super.doGet(model, request, response);
			} else {
				// validate captcha
				validator.validateCaptcha(formBean, result, captchaService, request.getSession().getId());

				if (result.hasErrors()) {
					// reload page
					formBean.setCaptcha(null);

					setCurrentPage(request.getSession(), WIZARD_STEP_1);
					return super.doGet(model, request, response);
				} else {
					// everything is ok, store the signature
					Signature signature = new Signature();
					signature.setCountryToSignFor(formBean.getCountryToSignFor());
					signature.setDateOfSignature(new Date());

					// set related initiative description
					String langCode = (String) request.getSession().getAttribute(
							ControllerConstants.SESSION_ATTR_INITIATIVE_LANGUAGE);
					if (langCode == null) {
						langCode = LocaleUtils.getCurrentLanguage(request);
					}
					Language lang = systemManager.getLanguageByCode(langCode);
					InitiativeDescription description = initiativeService.getDescriptionByLang(lang);
					signature.setDescription(description);

					// attach all properties to the new signature
					for (PropertyValue property : formBean.getProperties()) {
						property.setSignature(signature);
						if (property.getValue() != null) {
							property.setValue(property.getValue().trim());
						}
					}
					signature.setPropertyValues(formBean.getProperties());

					// call BL
					try {
						Signature insertedSignature = signatureService.insertSignature(signature);
						if (insertedSignature != null) {
							SignatureReviewBean reviewBean = new SignatureReviewBean();
							reviewBean.setUuid(insertedSignature.getUuid());
							reviewBean.setDate(DateUtils.formatDate(insertedSignature.getDateOfSignature()));
							request.getSession().setAttribute(ControllerConstants.SESSION_ATTR_RESULTING_TOKEN,
									reviewBean);
						}
					} catch (DuplicateSignatureException e) {
						// duplicate signature!
						return "redirect:duplicate.do";
					} finally {
						// finish wizard no matter what
						resetPageNumber(request.getSession(), status);
					}

					return "redirect:success.do";
				}
			}
		} else {
			switch (currentPage) {
			case WIZARD_STEP_0:
				validator.validatePrerequisites(formBean, result);
				break;
			case WIZARD_STEP_1:
				break;
			default:
				break;
			}

			if (result.hasErrors()) {
				formBean.setCaptcha(null);
				setCurrentPage(request.getSession(), currentPage);
				return super.doGet(model, request, response);
			}

			int nextPage = WebUtils.getTargetPage(request, "_target", currentPage);
			switch (nextPage) {
			case WIZARD_STEP_0:
				formBean.setCountryCode(null);
				formBean.setCountryToSignFor(null);

				break;
			case WIZARD_STEP_1:
				generateFormModel(formBean, result, resourceBundle);
				if (result.hasErrors()) {
					formBean.setCaptcha(null);
					setCurrentPage(request.getSession(), currentPage);
					return super.doGet(model, request, response);
				}

				break;
			default:
				break;
			}

			setCurrentPage(request.getSession(), nextPage);
			return super.doGet(model, request, response);
		}
	}

	@Override
	protected String _doGet(Model model, HttpServletRequest request, HttpServletResponse response) throws OCTException {
		// generate and expose request token
		model.addAttribute(requestTokenHelper.getToken(request));

		if ((SystemState) model.asMap().get(ControllerConstants.MODEL_ATTRIBUTE_SYSTEM_STATE) == SystemState.DEPLOYED
				|| !((SystemPreferences) model.asMap().get(ControllerConstants.MODEL_ATTRIBUTE_PREFERENCES))
						.isCollecting()) {
			return "wizard/signup0";
		}

		SupportFormBean formBean;
		if (model.containsAttribute("form")) {
			formBean = (SupportFormBean) model.asMap().get("form");
		} else {
			formBean = new SupportFormBean();
		}
		model.addAttribute("form", formBean);

		String view = "wizard/signup" + getCurrentPage(request.getSession());
		return view;
	}

	private void generateFormModel(SupportFormBean formBean, BindingResult result, MessageSourceAware resourceBundle)
			throws OCTException {
		formBean.reinitialize();

		try {
			formBean.setCountryToSignFor(systemManager.getCountryByCode(formBean.getCountryCode()));
		} catch (OCTException e) {
			// if country code is not found
			formBean.setCountryToSignFor(null);
		}

		if (formBean.getCountryToSignFor() == null) {
			result.rejectValue("countryCode", "err.empty.country", "The country name cannot be empty!");
			return;
		}

		for (PropertyGroup group : signatureService.getPropertyGroups()) {
			List<CountryProperty> properties = signatureService.getProperties(formBean.getCountryToSignFor(), group);
			if (properties != null && properties.size() > 0) {
				formBean.getGroups().add(group);
				if (group.isMultichoice()) {
					Map<Long, String> items = new HashMap<Long, String>();
					for (CountryProperty property : properties) {
						items.put(property.getId(), property.getProperty().getName());

						String key = property.getProperty().getName() + "."
								+ formBean.getCountryToSignFor().getCode().toLowerCase();
						String value = resourceBundle.getMessage(key);
						formBean.getTranslatedProperties().put(property.getProperty().getName(), value);
					}
					formBean.getGroupItems().put(group.getId(), items);

					PropertyValue propertyValue = new PropertyValue();
					propertyValue.setProperty(properties.get(0));
					formBean.getProperties().add(propertyValue);

					formBean.getMultichoiceSelections().put(group.getId(), properties.get(0).getId());
				} else {
					for (CountryProperty property : properties) {
						PropertyValue propertyValue = new PropertyValue();
						propertyValue.setProperty(property);
						formBean.getProperties().add(propertyValue);
					}
				}
			}
		}
	}

	private void setCurrentPage(HttpSession session, int currentPage) {
		session.setAttribute(SESSION_ATTR_CURRENT_PAGE, currentPage);
	}

	private int getCurrentPage(HttpSession session) {
		try {
			Integer page = (Integer) session.getAttribute(SESSION_ATTR_CURRENT_PAGE);
			return page == null ? WIZARD_STEP_0 : page;
		} catch (NumberFormatException e) {
			return WIZARD_STEP_0;
		}
	}

	private void resetPageNumber(HttpSession session, SessionStatus status) {
		session.removeAttribute(SESSION_ATTR_CURRENT_PAGE);
		status.setComplete();
	}
}
