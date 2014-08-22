package eu.europa.ec.eci.oct.web.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.EJB;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import eu.europa.ec.eci.oct.business.api.ConfigurationService;
import eu.europa.ec.eci.oct.business.api.ConfigurationService.Parameter;
import eu.europa.ec.eci.oct.business.api.SignatureService;
import eu.europa.ec.eci.oct.business.api.SystemManager;
import eu.europa.ec.eci.oct.entities.member.Country;
import eu.europa.ec.eci.oct.entities.signature.SignatureCountPerCountry;
import eu.europa.ec.eci.oct.vo.export.ExportParametersBean;

/**
 * Controller for handling the distribution of signatures map. Returns JSON
 * data.
 * 
 * @author chiridl
 * 
 */
@Controller
@RequestMapping("/mapdata.do")
public class MapDataController {

	@EJB
	private SignatureService sigService;

	@EJB
	protected SystemManager systemManager;

	@EJB
	private ConfigurationService configurationService;

	@RequestMapping(method = RequestMethod.GET)
	public String doGet(Model model, HttpServletRequest request, HttpServletResponse response) throws Exception {
		if (!Boolean.valueOf(configurationService.getConfigurationParameter(Parameter.SHOW_DISTRIBUTION_MAP).getValue())) {
			model.addAttribute("mapData", new ArrayList<SignatureCountPerCountry>());
			return "mapdata";
		}

		long total = 0;
		List<SignatureCountPerCountry> counts = sigService.getSignatureCounts(new ExportParametersBean());
		for (SignatureCountPerCountry count : counts) {
			total += count.getCount();
		}
		model.addAttribute("total", total);

		// as the previous method only returns countries that have count > 0, we
		// need to also add the other countries
		Map<String, Country> tempCountries = new HashMap<String, Country>();
		for (SignatureCountPerCountry signatureCountPerCountry : counts) {
			tempCountries.put(signatureCountPerCountry.getCountry().getCode(), signatureCountPerCountry.getCountry());
		}

		List<Country> countries = systemManager.getAllCountries();
		for (Country country : countries) {
			if (tempCountries.get(country.getCode()) == null) {
				counts.add(new SignatureCountPerCountry(country, 0l));
			}
		}

		model.addAttribute("mapData", counts);

		return "mapdata";
	}

}
