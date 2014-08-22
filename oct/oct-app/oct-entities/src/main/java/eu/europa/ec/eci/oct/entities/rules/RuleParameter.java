package eu.europa.ec.eci.oct.entities.rules;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class RuleParameter<R extends ValidationRule> implements Serializable {

	private static final long serialVersionUID = 5963818897707358723L;

	@Id
	private Long id;

	@Column
	@Enumerated(EnumType.STRING)
	private RuleParameterType parameterType;

	@Column
	private String value;

	@ManyToOne
	private R rule;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public void setRule(R rule) {
		this.rule = rule;
	}

	public R getRule() {
		return rule;
	}

}
