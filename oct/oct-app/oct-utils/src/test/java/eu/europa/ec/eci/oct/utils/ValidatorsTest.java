package eu.europa.ec.eci.oct.utils;

import junit.framework.Assert;

import org.junit.Test;

import eu.europa.ec.eci.oct.utils.validator.AbstractValidator;
import eu.europa.ec.eci.oct.utils.validator.DataType;
import eu.europa.ec.eci.oct.utils.validator.ValidatorFactory;

public class ValidatorsTest {

	@Test
	public void testNumericValidator() {
		AbstractValidator validator = ValidatorFactory.getValidatorFor(DataType.NUMERIC);
		Assert.assertTrue("This should be validated as a number!", validator.validateDataType("123"));
		Assert.assertFalse("This shouldn't be validated as a number!", validator.validateDataType("aa23"));
		Assert.assertTrue("The value should fit the range!", validator.validateRange("21", "19", "25"));
		Assert.assertTrue("This should always return true!", validator.validateSize("whatever", 1, 1001));
	}
}
