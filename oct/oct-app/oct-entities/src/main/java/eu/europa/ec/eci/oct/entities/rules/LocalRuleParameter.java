package eu.europa.ec.eci.oct.entities.rules;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "OCT_LOCAL_RULE_PARAM")
public class LocalRuleParameter extends RuleParameter<LocalValidationRule> {

	private static final long serialVersionUID = 2082668961588474979L;

}
