package eu.europa.ec.eci.oct.webcommons.errorhandling;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Spring controller for handling exceptions.
 * 
 * @author chiridl
 * 
 */
@Controller
public class ErrorController {

	@RequestMapping("/error.do")
	public String doError(HttpServletRequest request, Model model) {
		String token = ServletRequestUtils.getStringParameter(request, "token", "N/A");
		model.addAttribute("token", token);

		return "errors/error";
	}

}
