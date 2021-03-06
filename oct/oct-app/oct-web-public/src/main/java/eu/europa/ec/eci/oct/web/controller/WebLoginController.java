package eu.europa.ec.eci.oct.web.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import eu.europa.ec.eci.oct.business.api.OCTException;
import eu.europa.ec.eci.oct.webcommons.controller.LoginController;

@Controller
@RequestMapping("/login.do")
public class WebLoginController extends LoginController {

	@Override
	protected String _doGet(Model model, HttpServletRequest request, HttpServletResponse response) throws OCTException {
		if (!systemManager.isWebPasswordProtected()) {
			return "redirect:j_spring_security_check";
		} else {
			return super._doGet(model, request, response);
		}
	}

}
