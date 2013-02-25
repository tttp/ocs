package eu.europa.ec.eci.oct.entities.rules;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import eu.europa.ec.eci.oct.entities.Property;

@Entity
@Table(name = "OCT_GLOBAL_RULE")
public class GlobalValidationRule extends ValidationRule<GlobalRuleParameter> {

	private static final long serialVersionUID = 1911940526808114178L;

	@ManyToOne
	private Property property;

	public Property getProperty() {
		return property;
	}

	public void setProperty(Property property) {
		this.property = property;
	}
}
