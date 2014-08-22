package eu.europa.ec.eci.oct.web.captcha;

import javax.servlet.http.HttpServletRequest;

public interface CaptchaService {

	String CAPTCHA_AUDIO_TYPE = "audio";
	String CAPTCHA_IMAGE_TYPE = "image";

	/**
	 * Generates an image captcha.
	 * 
	 * @param request
	 * @return the captcha image for the request
	 */
	public byte[] generateImageCaptcha(HttpServletRequest request);

	/**
	 * Generates an audio captcha.
	 * 
	 * @param request
	 * @return the captcha audio for the request
	 */
	public byte[] generateAudioCaptcha(HttpServletRequest request);

	/**
	 * 
	 * @param request
	 * @param response
	 *            : the response
	 * @param captchaType
	 *            : which type of captcha will be tested: CAPTCHA_AUDIO_TYPE or CAPTCHA_IMAGE_TYPE
	 * @return true if the correct response has been set for the captcha image
	 */
	public boolean validateCaptcha(String captchaId, String captchaValue, String captchaType);
}
