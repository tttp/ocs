package eu.europa.ec.eci.oct.web.captcha;

import javax.servlet.http.HttpServletRequest;

public interface CaptchaService {

	public byte[] generateCaptcha(HttpServletRequest request);

	public boolean validateCaptcha(String captchaId, String captchaValue);
}
