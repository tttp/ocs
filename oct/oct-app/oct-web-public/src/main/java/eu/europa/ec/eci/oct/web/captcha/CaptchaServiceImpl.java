package eu.europa.ec.eci.oct.web.captcha;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.octo.captcha.service.CaptchaServiceException;
import com.octo.captcha.service.image.ImageCaptchaService;

public class CaptchaServiceImpl implements CaptchaService {

	private static final Logger logger = Logger.getLogger(CaptchaServiceImpl.class);

	private ImageCaptchaService captchaService;

	public byte[] generateCaptcha(HttpServletRequest request) {
		ByteArrayOutputStream result = new ByteArrayOutputStream();
		try {
			String captchaId = request.getSession().getId();
			BufferedImage challenge = captchaService.getImageChallengeForID(captchaId, request.getLocale());

			ImageIO.write(challenge, "jpeg", result);
			result.flush();

			return result.toByteArray();
		} catch (IllegalArgumentException e) {
			logger.error("unable to generate captcha", e);
			return new ByteArrayOutputStream().toByteArray();
		} catch (CaptchaServiceException e) {
			logger.error("unable to generate captcha", e);
			return new ByteArrayOutputStream().toByteArray();
		} catch (IOException e) {
			logger.error("unable to generate captcha", e);
			return new ByteArrayOutputStream().toByteArray();
		} catch (Exception e) {
			logger.error("unable to generate captcha", e);
			return new ByteArrayOutputStream().toByteArray();
		} finally {
			try {
				result.close();
			} catch (Exception e) {
			}
		}
	}

	public boolean validateCaptcha(String captchaId, String captchaValue) {
		boolean result;
		try {
			result = captchaService.validateResponseForID(captchaId, captchaValue).booleanValue();
		} catch (CaptchaServiceException e) {
			result = false;
		}
		return result;
	}

	public void setImageCaptchaService(ImageCaptchaService imageCaptchaService) {
		this.captchaService = imageCaptchaService;
	}

}
