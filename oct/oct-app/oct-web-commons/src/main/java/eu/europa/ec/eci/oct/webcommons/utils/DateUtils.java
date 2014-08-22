package eu.europa.ec.eci.oct.webcommons.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtils {
	public static final String DEFAULT_DATE_FORMAT = "dd/MM/yyyy";

	public static String formatDate(Date date) {
		return DateUtils.formatDate(date, DEFAULT_DATE_FORMAT);
	}

	public static String formatDate(Date date, String format) {
		return date == null ? "" : new SimpleDateFormat(format).format(date);
	}

}
