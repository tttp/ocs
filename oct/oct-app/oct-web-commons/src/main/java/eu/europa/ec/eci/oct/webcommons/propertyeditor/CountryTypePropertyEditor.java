package eu.europa.ec.eci.oct.webcommons.propertyeditor;

import java.beans.PropertyEditorSupport;

import eu.europa.ec.eci.oct.business.api.OCTException;
import eu.europa.ec.eci.oct.business.api.SystemManager;
import eu.europa.ec.eci.oct.entities.member.Country;

/**
 * Property editor for the {@link Country} properties.
 * 
 * @author chiridl
 * 
 */
public class CountryTypePropertyEditor extends PropertyEditorSupport {

	private SystemManager sysManager;

	public CountryTypePropertyEditor(SystemManager sysManager) {
		this.sysManager = sysManager;
	}

	@Override
	public void setAsText(String code) throws IllegalArgumentException {
		if (code == null || "".equals(code) || "-1".equals(code)) {
			return;
		}

		try {
			Country value = sysManager.getCountryByCode(code);
			setValue(value);
		} catch (OCTException e) {
			throw new IllegalArgumentException(e.getMessage());
		}
	}

}
