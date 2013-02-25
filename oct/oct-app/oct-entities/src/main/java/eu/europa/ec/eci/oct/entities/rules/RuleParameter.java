package eu.europa.ec.eci.oct.entities.rules;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "OCT_RULE_PARAM")
public class RuleParameter {

	@Id
	private Long id;

	@Column
	@Enumerated(EnumType.STRING)
	private RuleParameterType parameterType;

	@Column
	private String value;

	@ManyToOne
	private ValidationRule rule;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public ValidationRule getRule() {
		return rule;
	}

	public void setRule(ValidationRule rule) {
		this.rule = rule;
	}

	public void setParameterType(RuleParameterType parameterType) {
		this.parameterType = parameterType;
	}

	public RuleParameterType getParameterType() {
		return parameterType;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}

}
