package eu.europa.ec.eci.oct.admin.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.support.SessionStatus;

import eu.europa.ec.eci.oct.admin.model.ExportBean;
import eu.europa.ec.eci.oct.admin.model.SignatureId;
import eu.europa.ec.eci.oct.admin.model.SignatureId.DeletionStatus;
import eu.europa.ec.eci.oct.business.api.ExporterService;
import eu.europa.ec.eci.oct.business.api.InitiativeService;
import eu.europa.ec.eci.oct.business.api.OCTException;
import eu.europa.ec.eci.oct.business.api.SignatureService;
import eu.europa.ec.eci.oct.entities.admin.InitiativeDescription;
import eu.europa.ec.eci.oct.entities.member.Country;
import eu.europa.ec.eci.oct.entities.signature.Signature;
import eu.europa.ec.eci.oct.entities.signature.SignatureCountPerCountry;
import eu.europa.ec.eci.oct.utils.CalendarHelper;
import eu.europa.ec.eci.oct.utils.StringUtils;
import eu.europa.ec.eci.oct.vo.export.ExportParametersBean;
import eu.europa.ec.eci.oct.webcommons.controller.HttpGetAndPostController;
import eu.europa.ec.eci.oct.webcommons.utils.DateUtils;

@Controller
@RequestMapping("/export.do")
public class ExportController extends HttpGetAndPostController<ExportBean> {

	private final Logger logger = Logger.getLogger(ExportController.class);

	@EJB
	private SignatureService sigService;

	@EJB
	private ExporterService exportService;

	@EJB
	private InitiativeService initiativeService;

	@Override
	protected String _doGet(Model model, HttpServletRequest request, HttpServletResponse response) throws OCTException {
		ExportBean bean = new ExportBean();
		if (model.containsAttribute("form")) {
			bean = (ExportBean) model.asMap().get("form");
			model.addAttribute("startDate", DateUtils.formatDate(bean.getStartDate()));
			model.addAttribute("endDate", DateUtils.formatDate(bean.getEndDate()));
		} else {
			resetDeletionFields(bean);
		}

		bean.getCountries().addAll(systemManager.getAllCountries());

		model.addAttribute("form", bean);

		List<SignatureCountPerCountry> counts = sigService.getSignatureCounts(new ExportParametersBean());

		for (SignatureCountPerCountry sig : counts) {
			translateCountry(sig.getCountry(), getCurrentMessageBundle(request));
		}
		model.addAttribute("countsPerCountry", counts);
		model.addAttribute("totalCount", getTotalSignatures(counts));

		return "export";
	}

	@Override
	protected String _doPost(Model model, ExportBean bean, BindingResult result, SessionStatus status,
			HttpServletRequest request, HttpServletResponse response) throws OCTException {

		if (request.getParameter("exportAllAction") != null) {
			export(null);
			this.addSuccessMessage(request, model, "oct.export.success", null);

		} else if (request.getParameter("countAction") != null) {
			processCountAction(model, (ExportBean) bean, result, request, response);
		} else if (request.getParameter("exportAction") != null) {
			List<ExportParametersBean> exportBeans = new ArrayList<ExportParametersBean>();
			List<InitiativeDescription> descriptions = initiativeService.getDescriptions();

			boolean exportAllCountries = bean.getCountry() == null;
			List<Country> countries = new ArrayList<Country>();
			if (exportAllCountries) {
				countries = systemManager.getAllCountries();
			}

			if (exportAllCountries) {
				for (Country country : countries) {
					for (InitiativeDescription description : descriptions) {
						ExportParametersBean params = new ExportParametersBean();
						params.setStartDate(bean.getStartDate());
						params.setEndDate(bean.getEndDate());
						params.setCountryId(country.getId());
						params.setCountryCode(country.getCode());
						params.setDescriptionLanguageId(description.getId());
						params.setDescriptionLanguageCode(description.getLanguage().getCode());

						exportBeans.add(params);
					}
				}
			} else {
				for (InitiativeDescription description : descriptions) {
					ExportParametersBean params = new ExportParametersBean();
					params.setStartDate(bean.getStartDate());
					params.setEndDate(bean.getEndDate());
					params.setCountryId(bean.getCountry() == null ? -1 : bean.getCountry().getId());
					params.setCountryCode(bean.getCountry() == null ? "" : bean.getCountry().getCode());
					params.setDescriptionLanguageId(description.getId());
					params.setDescriptionLanguageCode(description.getLanguage().getCode());

					exportBeans.add(params);
				}
			}
			export(exportBeans);
			this.addSuccessMessage(request, model, "oct.export.success", null);

		} else if (request.getParameter("deleteSignatureAction") != null) {
			for (SignatureId sigId : bean.getSignatureIds()) {
				if (!StringUtils.isEmpty(sigId.getToken()) || sigId.getDate() != null) {
					model.addAttribute("delete", true);
					break;
				}
			}

		} else if (request.getParameter("confirmDeleteAction") != null) {
			List<SignatureId> sigResult = processDeleteSignatureAction(model, bean, result, request, response);
			model.addAttribute("sigDeletionResult", sigResult);
			model.addAttribute("afterDeletion", true);
			resetDeletionFields(bean);
		}

		return super.doGet(model, request, response);
	}

	private void export(List<ExportParametersBean> params) throws OCTException {
		final List<ExportParametersBean> exportBeans = new ArrayList<ExportParametersBean>();
		if (params == null) {
			logger.debug("Starting export for all countries...");
			List<InitiativeDescription> descriptions = initiativeService.getDescriptions();
			for (Country country : systemManager.getAllCountries()) {
				for (InitiativeDescription description : descriptions) {
					ExportParametersBean exportBean = new ExportParametersBean();
					exportBean.setCountryId(country.getId());
					exportBean.setCountryCode(country.getCode());
					exportBean.setDescriptionLanguageId(description.getId());
					exportBean.setDescriptionLanguageCode(description.getLanguage().getCode());

					exportBeans.add(exportBean);
				}
			}
		} else {
			logger.debug("Starting export for selected country only.");
			exportBeans.addAll(params);
		}
		exportService.export(exportBeans);
	}

	private void processCountAction(Model model, ExportBean bean, BindingResult result, HttpServletRequest request,
			HttpServletResponse response) throws OCTException {
		if (bean.getStartDate() != null && bean.getEndDate() == null) {
			bean.setEndDate(new Date());
		}
		model.addAttribute("form", bean);

		ExportParametersBean params = new ExportParametersBean();
		params.setStartDate(bean.getStartDate());
		params.setEndDate(bean.getEndDate());
		params.setCountryId(bean.getCountry() == null ? -1L : bean.getCountry().getId());
		int count = getTotalSignatures(sigService.getSignatureCounts(params));
		model.addAttribute("count", count);
	}

	private List<SignatureId> processDeleteSignatureAction(Model model, ExportBean bean, BindingResult result,
			HttpServletRequest request, HttpServletResponse response) {

		List<SignatureId> sigResult = new ArrayList<SignatureId>();

		for (SignatureId sigId : bean.getSignatureIds()) {
			if (!StringUtils.isEmpty(sigId.getToken())) {
				Signature signature = null;
				try {
					signature = sigService.findByUuid(sigId.getToken());
				} catch (OCTException e) {
					// signature not found for a token
					logger.warn("exception while quering for deletion signature token " + sigId.getToken(), e);
				}
				if (signature != null) {
					// signature found for a given token
					if (sigId.getDate() != null
							&& !CalendarHelper.removeTime(sigId.getDate()).equals(
									CalendarHelper.removeTime(signature.getDateOfSignature()))) {
						// signature date entered but does not match persisted one
						sigId.setDeletionStatus(DeletionStatus.FAILURE);
						sigResult.add(sigId);
					} else {
						try {
							// date not provided or matches persisted one
							sigService.deleteSignature(signature);
							sigId.setDeletionStatus(DeletionStatus.SUCCES);
							sigResult.add(sigId);

						} catch (OCTException e) {
							logger.warn("exception while deleting signature token " + sigId.getToken(), e);
							sigId.setDeletionStatus(DeletionStatus.FAILURE);
							sigResult.add(sigId);
						}
					}
				} else {
					// signature not found for a token
					sigId.setDeletionStatus(DeletionStatus.FAILURE);
					sigResult.add(sigId);
				}
			} else {
				if (sigId.getDate() != null) {
					sigId.setDeletionStatus(DeletionStatus.TOKEN_NOT_PROVIDED);
					sigResult.add(sigId);
				}
			}
		}

		return sigResult;
	}

	private int getTotalSignatures(List<SignatureCountPerCountry> counts) {
		int result = 0;
		for (SignatureCountPerCountry signatureCountPerCountry : counts) {
			result += signatureCountPerCountry.getCount();
		}
		return result;
	}

	private void resetDeletionFields(ExportBean bean) {

		List<SignatureId> signatureIds = new ArrayList<SignatureId>();
		for (int i = 0; i < 10; i++) {
			signatureIds.add(new SignatureId());
		}
		bean.setSignatureIds(signatureIds);
	}
}
