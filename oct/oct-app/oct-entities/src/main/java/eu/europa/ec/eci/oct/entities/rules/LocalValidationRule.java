package eu.europa.ec.eci.oct.entities.rules;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import eu.europa.ec.eci.oct.entities.CountryProperty;

@Entity
@Table(name = "OCT_LOCAL_RULE")
public class LocalValidationRule extends ValidationRule<LocalRuleParameter> {

	private static final long serialVersionUID = 1911940526808114178L;

	@ManyToOne
	private CountryProperty countryProperty;


	public void setCountryProperty(CountryProperty countryProperty) {
		this.countryProperty = countryProperty;
	}

	public CountryProperty getCountryProperty() {
		return countryProperty;
	}

}
