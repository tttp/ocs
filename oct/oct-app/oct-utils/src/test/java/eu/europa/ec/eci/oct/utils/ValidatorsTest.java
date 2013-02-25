package eu.europa.ec.eci.oct.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;

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
	
	@Test
	public void testDateValidator() {
		AbstractValidator validator = ValidatorFactory.getValidatorFor(DataType.DATE);
		Assert.assertTrue("This should be validated as a date!", validator.validateDataType("31/01/2001"));
		Assert.assertFalse("This shouldn't be validated as a date!", validator.validateDataType("aa23"));
		Assert.assertFalse("This shouldn't be validated as a date!", validator.validateDataType("31/01/11"));
		Assert.assertFalse("This shouldn't be validated as a date!", validator.validateDataType("01/31/2001"));
		Assert.assertFalse("This shouldn't be validated as a date!", validator.validateDataType("01-01-2001"));
				
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.YEAR, -1);
		Assert.assertTrue("The value should be outside the range (OK)!",
					validator.validateRange(new SimpleDateFormat("dd/MM/yyyy").format(cal.getTime()), "1y", "whatever"));
		
		cal.add(Calendar.MONTH, 1);
		Assert.assertFalse("The value should be inside the range (BAD)!",
				validator.validateRange(new SimpleDateFormat("dd/MM/yyyy").format(cal.getTime()), "1y", "whatever"));
		
		Assert.assertFalse("This shouldn't be validated as a date!", validator.validateRange("aa23", "1y", "whatever"));
		Assert.assertFalse("This shouldn't be validated as a date!", validator.validateRange("01/31/2001", "1y", "whatever"));
		Assert.assertFalse("This shouldn't be validated as a date!", validator.validateRange("01-01-2001", "1y", "whatever"));
	}
	
}
