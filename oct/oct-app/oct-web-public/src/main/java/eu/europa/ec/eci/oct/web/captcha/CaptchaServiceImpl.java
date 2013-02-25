package eu.europa.ec.eci.oct.web.captcha;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.octo.captcha.service.CaptchaServiceException;
import com.octo.captcha.service.image.ImageCaptchaService;
import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGImageEncoder;

@SuppressWarnings("restriction")
public class CaptchaServiceImpl implements CaptchaService {
	
	private static final Logger logger = Logger.getLogger(CaptchaServiceImpl.class);

	private ImageCaptchaService captchaService;

	public byte[] generateCaptcha(HttpServletRequest request) {
		ByteArrayOutputStream result = new ByteArrayOutputStream();
		try {
			String captchaId = request.getSession().getId();
			BufferedImage challenge = captchaService.getImageChallengeForID(captchaId, request.getLocale());
			JPEGImageEncoder jpegEncoder = JPEGCodec.createJPEGEncoder(result);
			jpegEncoder.encode(challenge);
		} catch (IllegalArgumentException e) {
			logger.error("unable to generate captcha", e);
		} catch (CaptchaServiceException e) {
			logger.error("unable to generate captcha", e);
		} catch (IOException e) {
			logger.error("unable to generate captcha", e);
		} catch (Exception e) {
			logger.error("unable to generate captcha", e);
		}

		return result.toByteArray();
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
