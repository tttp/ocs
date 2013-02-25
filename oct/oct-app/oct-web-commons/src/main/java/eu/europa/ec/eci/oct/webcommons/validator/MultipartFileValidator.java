package eu.europa.ec.eci.oct.webcommons.validator;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.validation.Errors;
import org.springframework.web.multipart.MultipartFile;

import eu.europa.ec.eci.oct.utils.StringUtils;
import eu.europa.ec.eci.oct.webcommons.locale.MessageSourceAware;

/**
 * Validator for MultipartFile objects (= uploaded
 * binary contents).
 * 
 * This validator performs some standard checks, such
 * as empty file contents or empty file names. It also supports
 * a whitelist of file name extensions, that are considered
 * as being valid for the uploaded file.
 * 
 * @author keschma
 *
 */
public class MultipartFileValidator extends BaseValidator {

	private final static Logger LOGGER = Logger.getLogger(MultipartFileValidator.class);
	
	public static enum RejectReason {
		EMPTY_CONTENT,
		EMPTY_NAME,
		BAD_EXTENSION,
		MAX_SIZE_EXCEEDED
	}
	
	private final String rejectKey;
	
	private final Map<RejectReason, String> rejectReasonKeys;
	
	private final List<String> extensionWhitelist;
	
	private final long maxFileSize;
	
	/**
	 * Constructor.
	 * @param messageSource
	 * @param rejectKey the key to use in <code>errors.reject()</code> calls
	 * @param rejectReasonKeys map defining detail messages to be looked up and
	 * provided as arguments in <code>errors.reject()</code> calls
	 * (these details might provide a message indicating the exact error to the user)
 	 * @param extensionWhitelist (optional) the whitelist to use, case insensitive
	 * and with or without the leading "." character. If null, no checks of
	 * the file extensions will be performed.
	 */
	public MultipartFileValidator(MessageSourceAware messageSource,
			String rejectKey, Map<RejectReason, String> rejectReasonKeys,
			List<String> extensionWhitelist, long maxSize) {
		super(messageSource);
		this.rejectKey = rejectKey;
		this.rejectReasonKeys = rejectReasonKeys;
		this.maxFileSize = maxSize;

		// clean up the received whitelist before setting it up:
		// - strip leading dots
		// - convert to lowercase
		if (extensionWhitelist != null) {
			this.extensionWhitelist = new ArrayList<String>(extensionWhitelist.size());
			for (String extension : extensionWhitelist) {
				final String cleanedExtension =
						(extension.charAt(0) == '.') ? extension.substring(1) : extension;
				this.extensionWhitelist.add(cleanedExtension.toLowerCase());
			}
		} else {
			this.extensionWhitelist = null;
		}
		
		
	}
	
	@Override
	public boolean supports(Class<?> clazz) {
		return MultipartFile.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object object, Errors errors) {
		MultipartFile multipartFile = (MultipartFile) object;
		
		// empty uploads		
		if (multipartFile.isEmpty()) {
			LOGGER.debug("Rejecting empty file content: " + multipartFile.getOriginalFilename());
			rejectFor(RejectReason.EMPTY_CONTENT, errors);
			return;
		}
		
		// empty file names		
		if (StringUtils.isEmpty(multipartFile.getOriginalFilename())) {
			LOGGER.debug("Rejecting empty file name.");
			rejectFor(RejectReason.EMPTY_NAME, errors);
			return;
		}
		
		// whitelist-based rejection of filenames, if required
		if (extensionWhitelist != null) {			
			final String lowerCaseFileName = multipartFile.getOriginalFilename().toLowerCase();
			boolean isAllowedExtension = false;
			
			for (String allowedExtension : extensionWhitelist) {
				if (lowerCaseFileName.endsWith(allowedExtension)) {
					LOGGER.debug("Authorised extension detected: " + allowedExtension);
					isAllowedExtension = true;
					break;
				}
			}			
			if (!isAllowedExtension) {
				if (LOGGER.isDebugEnabled()) {
					LOGGER.debug("Rejecting file, no match in whitelist of supported extensions for file: " + multipartFile.getOriginalFilename());
					LOGGER.debug("Allowed extensions are: " + extensionWhitelist);
				}
				rejectFor(RejectReason.BAD_EXTENSION, errors);
			}
		}
		
		if(maxFileSize>0){
			if(multipartFile.getSize()>maxFileSize){
				if (LOGGER.isDebugEnabled()) {
					LOGGER.debug("Rejecting file, file size to big: " + multipartFile.getSize());
					LOGGER.debug("Maximum file size is: " + maxFileSize);	
				}
				rejectFor(RejectReason.MAX_SIZE_EXCEEDED, errors);
			}
		}
	}

	/**
	 * Common wrapper for <code>Errors.reject()</code> calls in this validator.
	 * @param reason the rejection reason
	 * @param errors the Errors object
	 */
	private void rejectFor(RejectReason reason, Errors errors) {
		final String rejectDetailsKey = rejectReasonKeys.get(reason);
		
		if (rejectDetailsKey != null) {
			final String rejectMessage = getMessageSource().getMessage(rejectDetailsKey);
			errors.reject(rejectDetailsKey, new Object[] {  }, rejectMessage);
		} else {
			errors.reject(rejectKey);
		}
	}
	
}
