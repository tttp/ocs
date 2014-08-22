package eu.europa.ec.eci.oct.validation;

import java.util.LinkedHashSet;
import java.util.Set;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import eu.europa.ec.eci.oct.entities.CountryProperty;
import eu.europa.ec.eci.oct.entities.Property;
import eu.europa.ec.eci.oct.entities.PropertyType;
import eu.europa.ec.eci.oct.entities.member.Country;
import eu.europa.ec.eci.oct.entities.rules.GlobalRuleParameter;
import eu.europa.ec.eci.oct.entities.rules.GlobalValidationRule;
import eu.europa.ec.eci.oct.entities.rules.LocalRuleParameter;
import eu.europa.ec.eci.oct.entities.rules.LocalValidationRule;
import eu.europa.ec.eci.oct.entities.rules.RuleParameterType;
import eu.europa.ec.eci.oct.entities.rules.RuleType;

/**
 * 
 * @author Daniel CHIRITA, DIGIT B.1, EC
 * 
 */
public class OCSValidatorTest {

	private static OCSValidator validator;
	private static Country country;

	@BeforeClass
	public static void setup() {
		country = new Country();
		country.setId(1l);
		country.setCode("AT");

		validator = new OCSValidator();
	}

	@Test
	public void testAddress() {
		Property p = new Property();
		p.setName("oct.property.address");
		p.setType(PropertyType.LARGETEXT);

		CountryProperty cp = new CountryProperty();
		cp.setCountry(country);
		cp.setProperty(p);
		cp.setRequired(true);

		OCSValidationResult result = validator.validate(cp, "");
		// should fail as it is required
		Assert.assertTrue(result.isFailed());

		result = validator.validate(cp, "some street, 56");
		// should not fail
		Assert.assertFalse(result.isFailed());
	}

	@Test
	public void testPassport() {
		Property p = new Property();
		p.setName("oct.property.passport");
		p.setType(PropertyType.LARGETEXT);

		CountryProperty cp = new CountryProperty();
		cp.setCountry(country);
		cp.setProperty(p);
		cp.setRequired(true);

		LocalValidationRule rule = new LocalValidationRule();
		rule.setRuleType(RuleType.REGEXP);

		LocalRuleParameter param = new LocalRuleParameter();
		param.setParameterType(RuleParameterType.REGEXP);
		param.setRule(rule);
		param.setValue("[0-9]{4}");

		Set<LocalRuleParameter> params = new LinkedHashSet<LocalRuleParameter>();
		params.add(param);

		rule.setRuleParameters(params);

		Set<LocalValidationRule> rules = new LinkedHashSet<LocalValidationRule>();
		rules.add(rule);

		cp.setRules(rules);

		OCSValidationResult result = validator.validate(cp, "1234");
		// pass
		Assert.assertFalse(result.isFailed());

		result = validator.validate(cp, "1234a");
		// fail
		Assert.assertTrue(result.isFailed());

		result = validator.validate(cp, "abcd");
		// fail
		Assert.assertTrue(result.isFailed());

		result = validator.validate(cp, "1 2 3 4--");
		// will not fail, values are pruned for invalid characters
		Assert.assertFalse(result.isFailed());
	}

	@Test
	public void testDateOfBirth() {
		Property p = new Property();
		p.setName("oct.property.date.of.birth");
		p.setType(PropertyType.DATE);

		CountryProperty cp = new CountryProperty();
		cp.setCountry(country);
		cp.setProperty(p);
		cp.setRequired(true);

		GlobalValidationRule rule = new GlobalValidationRule();
		rule.setRuleType(RuleType.RANGE);

		Set<GlobalRuleParameter> params = new LinkedHashSet<GlobalRuleParameter>();

		GlobalRuleParameter param = new GlobalRuleParameter();
		param.setParameterType(RuleParameterType.MIN);
		param.setRule(rule);
		param.setValue("18y");

		params.add(param);

		param = new GlobalRuleParameter();
		param.setParameterType(RuleParameterType.MAX);
		param.setRule(rule);
		param.setValue("55y");

		params.add(param);

		rule.setRuleParameters(params);

		Set<GlobalValidationRule> rules = new LinkedHashSet<GlobalValidationRule>();
		rules.add(rule);

		// notice that the global rules are set to the property itself, not country property!
		p.setRules(rules);

		OCSValidationResult result = validator.validate(cp, "21/07/1981");
		// pass, valid format, > 18 years
		Assert.assertFalse(result.isFailed());

		result = validator.validate(cp, "21/july/1981");
		// fail, bad format
		Assert.assertTrue(result.isFailed());

		result = validator.validate(cp, "21/07/2000");
		// fail, < 18 years
		Assert.assertTrue(result.isFailed());

		result = validator.validate(cp, "21/07/1920");
		// fail, > 55 years
		Assert.assertTrue(result.isFailed());
	}

	@Test
	public void testJaxelValidation() {
		Property p = new Property();
		p.setName("oct.property.nationality");
		p.setType(PropertyType.NATIONALITY);

		CountryProperty cp = new CountryProperty();
		cp.setCountry(country);
		cp.setProperty(p);
		cp.setRequired(true);

		LocalValidationRule rule = new LocalValidationRule();
		rule.setRuleType(RuleType.JAVAEXP);

		LocalRuleParameter param = new LocalRuleParameter();
		param.setParameterType(RuleParameterType.JAVAEXP);
		param.setRule(rule);
		param.setValue("bean.gp(\"nationality\").equals(\"at\") || bean.gp(\"country\").equals(\"at\")");
		// the above line is equivalent to
		// param.setValue("bean.gp(\"oct.property.nationality\").equals(\"at\") || bean.gp(\"oct.property.country\").equals(\"at\")");
		//this is because of the shorthand notation in the JAXELStructure.gp() method

		Set<LocalRuleParameter> params = new LinkedHashSet<LocalRuleParameter>();
		params.add(param);

		rule.setRuleParameters(params);

		Set<LocalValidationRule> rules = new LinkedHashSet<LocalValidationRule>();
		rules.add(rule);

		cp.setRules(rules);

		// add values which will be used for the complex validation rule
		validator.addProperty("oct.property.nationality", "at");
		validator.addProperty("oct.property.country", "be");

		// we need to validate either nationality is Austrian or country of residence is Austria
		OCSValidationResult result = validator.validate(cp, "at");
		validator.clearProperties();
		// pass
		Assert.assertFalse(result.isFailed());
	}

}
