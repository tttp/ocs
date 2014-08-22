package eu.europa.ec.eci.oct.web.captcha;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;

import org.apache.log4j.Logger;

import com.octo.captcha.service.CaptchaServiceException;
import com.octo.captcha.service.image.ImageCaptchaService;
import com.octo.captcha.service.sound.SoundCaptchaService;

public class CaptchaServiceImpl implements CaptchaService {

	private static final Logger logger = Logger.getLogger(CaptchaServiceImpl.class);

	private ImageCaptchaService imageCaptchaService;
	private SoundCaptchaService soundCaptchaService;
	private boolean captchaEnabled;

	public byte[] generateImageCaptcha(HttpServletRequest request) {
		ByteArrayOutputStream result = new ByteArrayOutputStream();
		try {
			String captchaId = request.getSession().getId();
			BufferedImage challenge = getImageCaptchaService().getImageChallengeForID(captchaId, request.getLocale());

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

	public byte[] generateAudioCaptcha(HttpServletRequest request) {
		String captchaId = request.getSession().getId();
		AudioInputStream captchaAudio = getSoundCaptchaService().getSoundChallengeForID(captchaId, request.getLocale());
		ByteArrayOutputStream soundOutputStream = new ByteArrayOutputStream();
		try {
			// wave format conversion
			AudioSystem.write(captchaAudio, AudioFileFormat.Type.WAVE, soundOutputStream);
			soundOutputStream.flush();
			soundOutputStream.close();
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}

		return soundOutputStream.toByteArray();
	}

	public boolean validateCaptcha(String captchaId, String captchaValue, String captchaType) {
		boolean result;

		if (!isCaptchaEnabled()) {
			result = true;
		} else {
			// Call the correct Service method
			com.octo.captcha.service.CaptchaService service;
			if (CAPTCHA_AUDIO_TYPE.equals(captchaType)) {
				service = getSoundCaptchaService();
			} else if (CAPTCHA_IMAGE_TYPE.equals(captchaType)) {
				service = getImageCaptchaService();
			} else {
				return false;
			}
			try {
				result = service.validateResponseForID(captchaId, captchaValue).booleanValue();
			} catch (CaptchaServiceException e) {
				// should not happen, may be thrown if the id is not valid
				logger.error(e.getMessage(), e);
				result = false;
			}
		}

		return result;
	}

	public void setCaptchaEnabled(boolean captchaEnabled) {
		this.captchaEnabled = captchaEnabled;
	}

	public boolean isCaptchaEnabled() {
		return captchaEnabled;
	}

	public void setSoundCaptchaService(SoundCaptchaService soundCaptchaService) {
		this.soundCaptchaService = soundCaptchaService;
	}

	public SoundCaptchaService getSoundCaptchaService() {
		return soundCaptchaService;
	}

	public void setImageCaptchaService(ImageCaptchaService imageCaptchaService) {
		this.imageCaptchaService = imageCaptchaService;
	}

	public ImageCaptchaService getImageCaptchaService() {
		return imageCaptchaService;
	}

}
