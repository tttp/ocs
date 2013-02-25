package eu.europa.ec.eci.oct.webcommons.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

import eu.europa.ec.eci.oct.utils.validator.DateValidator;

public class DateUtils {

	public static String formatDate(Date date) {
		return DateUtils.formatDate(date, DateValidator.DEFAULT_DATE_FORMAT);
	}

	public static String formatDate(Date date, String format) {
		return date == null ? "" : new SimpleDateFormat(format).format(date);
	}

}
