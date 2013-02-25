package eu.europa.ec.eci.oct.persistence;

import java.util.List;

import eu.europa.ec.eci.oct.entities.CountryProperty;
import eu.europa.ec.eci.oct.entities.PropertyGroup;
import eu.europa.ec.eci.oct.entities.member.Country;
import eu.europa.ec.eci.oct.entities.rules.LocalValidationRule;

public interface PropertyDAO {

	public List<PropertyGroup> getGroups() throws PersistenceException;

	public List<CountryProperty> getProperties(Country c, PropertyGroup g) throws PersistenceException;

	public CountryProperty getCountryPropertyById(Long id) throws PersistenceException;

	public List<CountryProperty> getAllCountryProperties() throws PersistenceException;

	public List<LocalValidationRule> getLocalRulesForCountryProperty(CountryProperty countryProperty)
			throws PersistenceException;

}
