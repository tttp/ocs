/** ====================================================================
 * Licensed under the European Union Public Licence (EUPL v1.2) 
 * https://joinup.ec.europa.eu/community/eupl/topic/public-consultation-draft-eupl-v12
 * ====================================================================
 *
 * @author Daniel CHIRITA
 * @created: 23/05/2013
 *
 */
package eu.europa.ec.eci.oct.admin.model;

import org.springframework.web.multipart.commons.CommonsMultipartFile;

import eu.europa.ec.eci.oct.webcommons.model.SimpleBean;

public class SettingsBean extends SimpleBean {

	private static final long serialVersionUID = -4468155608624594156L;
	private String customLogo;
	private boolean deleteLogo;
	private CommonsMultipartFile logoFile;
	private boolean optionalValidation;
	private boolean displayMap;
	private String callbackUrl;

	public String getCustomLogo() {
		return customLogo;
	}

	public void setCustomLogo(String customLogo) {
		this.customLogo = customLogo;
	}

	public boolean isDeleteLogo() {
		return deleteLogo;
	}

	public void setDeleteLogo(boolean deleteLogo) {
		this.deleteLogo = deleteLogo;
	}

	public CommonsMultipartFile getLogoFile() {
		return logoFile;
	}

	public void setLogoFile(CommonsMultipartFile logoFile) {
		this.logoFile = logoFile;
	}

	public boolean getOptionalValidation() {
		return optionalValidation;
	}

	public void setOptionalValidation(boolean optionalValidation) {
		this.optionalValidation = optionalValidation;
	}

	public boolean getDisplayMap() {
		return displayMap;
	}

	public void setDisplayMap(boolean displayMap) {
		this.displayMap = displayMap;
	}

	public String getCallbackUrl() {
		return callbackUrl;
	}

	public void setCallbackUrl(String callbackUrl) {
		this.callbackUrl = callbackUrl;
	}
}
