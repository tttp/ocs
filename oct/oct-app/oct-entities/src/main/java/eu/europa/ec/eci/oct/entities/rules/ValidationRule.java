package eu.europa.ec.eci.oct.entities.rules;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import eu.europa.ec.eci.oct.entities.Property;

@Entity
@Table(name = "OCT_RULE")
public class ValidationRule {

	@Id
	private Long id;

	@Column
	@Enumerated(EnumType.STRING)
	private RuleType ruleType;

	@ManyToOne
	private Property property;

	@OneToMany(mappedBy = "rule", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private Set<RuleParameter> ruleParameters;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Set<RuleParameter> getRuleParameters() {
		return ruleParameters;
	}

	public void setRuleParameters(Set<RuleParameter> ruleParameters) {
		this.ruleParameters = ruleParameters;
	}

	public Property getProperty() {
		return property;
	}

	public void setProperty(Property property) {
		this.property = property;
	}

	public void setRuleType(RuleType ruleType) {
		this.ruleType = ruleType;
	}

	public RuleType getRuleType() {
		return ruleType;
	}

}
