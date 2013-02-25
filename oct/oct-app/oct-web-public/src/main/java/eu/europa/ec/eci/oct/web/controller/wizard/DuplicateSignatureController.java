package eu.europa.ec.eci.oct.web.controller.wizard;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import eu.europa.ec.eci.oct.business.api.OCTException;
import eu.europa.ec.eci.oct.webcommons.controller.HttpGetController;

/**
 * Controller for handling the duplicate signature.
 * 
 * @author chiridl
 * 
 */
@Controller
@RequestMapping("/duplicate.do")
public class DuplicateSignatureController extends HttpGetController {

	@Override
	protected String _doGet(Model model, HttpServletRequest request, HttpServletResponse response) throws OCTException {
		return "wizard/duplicate";
	}
}
