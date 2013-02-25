package eu.europa.ec.eci.oct.admin.controller;

import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import eu.europa.ec.eci.oct.business.api.InitiativeService;
import eu.europa.ec.eci.oct.business.api.OCTException;
import eu.europa.ec.eci.oct.business.api.SignatureService;
import eu.europa.ec.eci.oct.business.api.SystemManager;
import eu.europa.ec.eci.oct.entities.admin.InitiativeDescription;
import eu.europa.ec.eci.oct.entities.admin.SystemState;
import eu.europa.ec.eci.oct.entities.signature.SignatureCountPerCountry;
import eu.europa.ec.eci.oct.vo.export.ExportParametersBean;
import eu.europa.ec.eci.oct.webcommons.controller.HttpGetController;

@Controller
@RequestMapping("/index.do")
public class IndexController extends HttpGetController {

	private Logger logger = Logger.getLogger(IndexController.class);

	@EJB
	private SystemManager sysManager;

	@EJB
	private SignatureService sigService;

	@EJB
	private InitiativeService initiativeService;

	@Override
	protected String _doGet(Model model, HttpServletRequest request, HttpServletResponse response) throws OCTException {

		if (SystemState.OPERATIONAL.equals(sysManager.getSystemPreferences().getState())) {

			// signatures section
			List<SignatureCountPerCountry> counts = sigService.getSignatureCounts(new ExportParametersBean());
			for (SignatureCountPerCountry sig : counts) {
				translateCountry(sig.getCountry(), getCurrentMessageBundle(request));
			}
			model.addAttribute("countsPerCountry", counts);
			model.addAttribute("totalCount", getTotalSignatures(counts));
			model.addAttribute("today", new Date());

			// initiative section
			InitiativeDescription defaultDescription = initiativeService.getDefaultDescription();
			model.addAttribute("defaultLanguageVersion", defaultDescription);
			List<InitiativeDescription> descriptionList = initiativeService.getDescriptions();
			InitiativeDescription toRemoveDescription = null;
			if (defaultDescription != null) {
				for (InitiativeDescription desc : descriptionList) {
					if (defaultDescription.getId().equals(desc.getId())) {
						toRemoveDescription = desc;
						break;
					}
				}
				if (toRemoveDescription != null) {
					descriptionList.remove(toRemoveDescription);
				}
			}
			Collections.sort(descriptionList, new Comparator<InitiativeDescription>() {			
				@Override
				public int compare(InitiativeDescription i1, InitiativeDescription i2) {						
					return i1.getLanguage().getDisplayOrder().intValue() - i2.getLanguage().getDisplayOrder().intValue(); 
				}			
			});	
			model.addAttribute("languageVersions", descriptionList);

			// preview url
			model.addAttribute("oct_preview_url", "systemprefs.do");
			//model.addAttribute("oct_preview_url", "index.do");
			model.addAttribute("oct_preview_lang_id", -1);

			return "dashboard";

		} else {
			return "index";
		}
	}

	private int getTotalSignatures(List<SignatureCountPerCountry> counts) {
		int result = 0;
		for (SignatureCountPerCountry signatureCountPerCountry : counts) {
			result += signatureCountPerCountry.getCount();
		}
		return result;
	}

}
