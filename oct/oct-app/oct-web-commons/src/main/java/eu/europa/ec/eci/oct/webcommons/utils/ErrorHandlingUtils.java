package eu.europa.ec.eci.oct.webcommons.utils;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Random;

/**
 * Error handling related utilities.
 * 
 * @author chiridl
 * 
 */
public final class ErrorHandlingUtils {

	/**
	 * Returns the entire stacktrace of the given {@link Exception} as a string.
	 * 
	 * @param t
	 *            the exception
	 * @return the stacktrace
	 */
	public static final String getStackTrace(Throwable t) {
		final Writer result = new StringWriter();
		final PrintWriter printWriter = new PrintWriter(result);
		t.printStackTrace(printWriter);

		return result.toString();
	}

	/**
	 * Generates a random hexadecimal string to be used as error token.
	 * 
	 * @param tokenLength
	 *            the length of the string to be generated
	 * @return
	 */
	public static final String generateErrorToken(byte tokenLength) {
		final StringBuilder result = new StringBuilder();

		Random random = new Random();
		for (byte idx = 0; idx < tokenLength; idx++) {
			result.append(Integer.toHexString(random.nextInt(17)));
		}

		return result.toString().toUpperCase();
	}

}
