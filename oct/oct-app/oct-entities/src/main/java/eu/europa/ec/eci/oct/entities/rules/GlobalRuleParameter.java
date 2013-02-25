package eu.europa.ec.eci.oct.entities.rules;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "OCT_GLOBAL_RULE_PARAM")
public class GlobalRuleParameter extends RuleParameter<GlobalValidationRule> {

	private static final long serialVersionUID = 2082668961588474979L;

}
