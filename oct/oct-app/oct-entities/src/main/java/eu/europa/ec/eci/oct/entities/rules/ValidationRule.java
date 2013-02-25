package eu.europa.ec.eci.oct.entities.rules;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.OneToMany;

@MappedSuperclass
public abstract class ValidationRule<P extends RuleParameter> implements Serializable {

	private static final long serialVersionUID = -9179841346863042017L;

	@Id
	private Long id;

	@Column
	@Enumerated(EnumType.STRING)
	private RuleType ruleType;

	@OneToMany(mappedBy = "rule", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private Set<P> ruleParameters;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setRuleType(RuleType ruleType) {
		this.ruleType = ruleType;
	}

	public RuleType getRuleType() {
		return ruleType;
	}

	public void setRuleParameters(Set<P> ruleParameters) {
		this.ruleParameters = ruleParameters;
	}

	public Set<P> getRuleParameters() {
		return ruleParameters;
	}

}
