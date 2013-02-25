package eu.europa.ec.eci.oct.web.controller;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import eu.europa.ec.eci.oct.web.captcha.CaptchaService;

/**
 * Controller for generating CAPTCHA security images.
 * 
 * @author chiridl
 *
 */
@Controller
@RequestMapping("/captcha.do")
public class CaptchaController {

	@Autowired
	private CaptchaService captchaService;

	@RequestMapping(method = RequestMethod.GET)
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws Exception {
		byte[] image = captchaService.generateCaptcha(request);

		if (image != null) {
			response.setHeader("Cache-control",
					"private,no-cache,no-store,must-revalidate,proxy-revalidate,max-age=0,s-maxage=0");
			response.setHeader("Pragma", "no-cache");
			response.setDateHeader("Expires", 0);
			response.setContentLength(image.length);
			response.setContentType("image/jpeg");
			ServletOutputStream responseOutputStream = response.getOutputStream();
			responseOutputStream.write(image);
			responseOutputStream.flush();
			responseOutputStream.close();
		}
	}

}