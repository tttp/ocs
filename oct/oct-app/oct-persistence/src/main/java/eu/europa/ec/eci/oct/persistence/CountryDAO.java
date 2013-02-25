package eu.europa.ec.eci.oct.persistence;

import java.util.List;

import eu.europa.ec.eci.oct.entities.member.Country;
import eu.europa.ec.eci.oct.entities.member.Language;

public interface CountryDAO {

	List<Country> getCountries() throws PersistenceException;
	
	Country getCountryByCode(String code) throws PersistenceException;
	
	List<Language> getLanguages() throws PersistenceException;
	
	Language getLanguageByCode(String code) throws PersistenceException;
	
}
