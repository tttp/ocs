package eu.europa.ec.eci.oct.entities;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import eu.europa.ec.eci.oct.entities.member.Country;
import eu.europa.ec.eci.oct.entities.rules.LocalValidationRule;

@Entity
@Table(name = "OCT_COUNTRY_PROPERTY")
public class CountryProperty {

	@Id
	private Long id;

	@ManyToOne
	private Property property;

	@ManyToOne
	private Country country;

	@Column
	private byte required = 1;

	@OneToMany(mappedBy = "countryProperty", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private Set<LocalValidationRule> rules;

	public Property getProperty() {
		return property;
	}

	public void setProperty(Property property) {
		this.property = property;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setRequired(boolean required) {
		this.required = required ? (byte) 1 : (byte) 0;
	}

	public boolean isRequired() {
		return required != 0;
	}

	public Country getCountry() {
		return country;
	}

	public void setCountry(Country country) {
		this.country = country;
	}

	public void setRules(Set<LocalValidationRule> rules) {
		this.rules = rules;
	}

	public Set<LocalValidationRule> getRules() {
		return rules;
	}

}
