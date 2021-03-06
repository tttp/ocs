package eu.europa.ec.eci.oct.validation.core;

import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 
 * @author Daniel CHIRITA, DIGIT B.1, EC
 *
 */
public class DateValidator extends AbstractValidator {

	public static final String DEFAULT_DATE_FORMAT = "dd/MM/yyyy";

	@Override
	public boolean validateDataType(String value) {
		if (value == null || "".equals(value)) {
			return true;
		}

		try {
			parseDate(value);
		} catch (ParseException e) {
			return false;
		}

		return true;
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
			date = parseDate(value);
		} catch (ParseException e) {
			return false;
		}

		// TODO currently this only supports ranges consisting of years; if needed, please extend
		int minYear = (minValue != null && !"".equals(minValue)) ? Integer.parseInt(minValue.substring(0,
				minValue.indexOf("y"))) : 0;
		int maxYear = (maxValue != null && !"".equals(maxValue)) ? Integer.parseInt(maxValue.substring(0,
				maxValue.indexOf("y"))) : 150;

		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.YEAR, 0 - minYear);
		Date etalonDate1 = calendar.getTime();

		calendar = Calendar.getInstance();
		calendar.add(Calendar.YEAR, 0 - maxYear);
		Date etalonDate2 = calendar.getTime();

		result = date.before(etalonDate1) && etalonDate2.before(date);

		return result;
	}

	/**
	 * Parses a Date from a string. The date format used is {@link DateValidator.DEFAULT_DATE_FORMAT}.
	 * 
	 * @param text
	 *            the text
	 * @return the date
	 * @throws ParseException
	 *             on parsing failures
	 */
	private Date parseDate(String text) throws ParseException {
		final SimpleDateFormat dateParser = new SimpleDateFormat(DateValidator.DEFAULT_DATE_FORMAT);
		dateParser.setLenient(false); // use strict date parsing

		final ParsePosition parsePosition = new ParsePosition(0);
		final Date date = dateParser.parse(text, parsePosition);
		// accept only, if the *entire format string length* was used during parsing
		if (parsePosition.getIndex() < DateValidator.DEFAULT_DATE_FORMAT.length()) {
			throw new ParseException("Parsed an incomplete string with regard to format '"
					+ DateValidator.DEFAULT_DATE_FORMAT + "': " + text.substring(0, parsePosition.getIndex()),
					parsePosition.getIndex());
		}
		return date;
	}

}
