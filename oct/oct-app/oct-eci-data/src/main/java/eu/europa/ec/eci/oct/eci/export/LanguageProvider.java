package eu.europa.ec.eci.oct.eci.export;

import eu.europa.ec.eci.oct.entities.member.Language;

public interface LanguageProvider {

	public Language getLanguageByCode(String code) throws ECIDataException;
	
}
