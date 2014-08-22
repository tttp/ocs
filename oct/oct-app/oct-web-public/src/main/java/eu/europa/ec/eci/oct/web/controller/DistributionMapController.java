package eu.europa.ec.eci.oct.web.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import eu.europa.ec.eci.oct.business.api.OCTException;
import eu.europa.ec.eci.oct.webcommons.controller.CommonControllerConstants;
import eu.europa.ec.eci.oct.webcommons.controller.HttpGetController;

/**
 * Controller for handling the distribution of signatures map..
 * 
 * @author chiridl
 * 
 */
@Controller
@RequestMapping("/map.do")
public class DistributionMapController extends HttpGetController {

	@Override
	protected String _doGet(Model model, HttpServletRequest request, HttpServletResponse response) throws OCTException {
		if (!(Boolean) model.asMap().get(CommonControllerConstants.MODEL_ATTRIBUTE_SHOW_MAP)) {
			return "redirect:index.do";
		}

		return "map";
	}
}
