package eu.europa.ec.eci.oct.admin.propertyeditor;

import java.beans.PropertyEditorSupport;

import eu.europa.ec.eci.oct.business.api.OCTException;
import eu.europa.ec.eci.oct.business.api.SystemManager;
import eu.europa.ec.eci.oct.entities.member.Language;

/**
 * Property editor for the {@link Language} properties.
 * 
 * @author chiridl
 * 
 */
public class LanguageTypePropertyEditor extends PropertyEditorSupport {

	private SystemManager sysManager;

	public LanguageTypePropertyEditor(SystemManager dataProvider) {
		this.sysManager = dataProvider;
	}

	@Override
	public void setAsText(String code) throws IllegalArgumentException {
		try {
			Language lang = sysManager.getLanguageByCode(code);
			setValue(lang);
		} catch (OCTException e) {
			throw new IllegalArgumentException(e.getMessage());
		}
	}
}
