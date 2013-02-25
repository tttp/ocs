package eu.europa.ec.eci.oct.utils.validator;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateValidator extends AbstractValidator {

	public static final String DEFAULT_DATE_FORMAT = "dd/MM/yyyy";

	@Override
	public boolean validateDataType(String value) {
		if (value == null || "".equals(value)) {
			return true;
		}

		boolean result = true;

		try {
			SimpleDateFormat dateParser = new SimpleDateFormat(DateValidator.DEFAULT_DATE_FORMAT);
			dateParser.parse(value);
		} catch (ParseException e) {
			result = false;
		}

		return result;
	}

	@Override
	public boolean validateSize(String value, int minSize, int maxSize) {
		return true;
	}

	@Override
	public boolean validateRange(String value, String minValue, String maxValue) {
		boolean result = true;

		Date date;
		try {
			SimpleDateFormat dateParser = new SimpleDateFormat(DateValidator.DEFAULT_DATE_FORMAT);
			date = dateParser.parse(value);
		} catch (ParseException e) {
			return false;
		}

		// TODO currently this only supports ranges consisting of years; if needed, please extend
		int minYear = Integer.parseInt(minValue.substring(0, minValue.indexOf("y")));
		
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.YEAR, 0 - minYear);
		Date etalonDate = calendar.getTime();

		result = date.before(etalonDate);

		return result;
	}
}
