package eu.europa.ec.eci.oct.webcommons.locale;


/**
 * Interface to be used by controllers and classes which expose i18n resources.
 * 
 * @author chiridl
 * 
 */
public interface MessageSourceAware {

	/**
	 * Returns an i18n based only on its code.
	 * 
	 * @param code
	 *            - the message code
	 * @return the i18n message
	 */
	public String getMessage(String code);

	/**
	 * Returns an i18n based only on its code. In case of a non-existing message, the given default message will be
	 * returned.
	 * 
	 * @param code
	 *            - the message code
	 * @param defaultMessage
	 *            - the default message
	 * @return the i18n message
	 */
	public String getMessage(String code, String defaultMessage);

	/**
	 * Returns an i18n based only on its code. Also, provides the needed parameters in case of a parameterized i18n
	 * message.
	 * 
	 * @param code
	 *            - the message code
	 * @param args
	 *            - the parameters
	 * @return the i18n message
	 */
	public String getMessage(String code, Object[] args);

	/**
	 * Returns an i18n based only on its code. Also, provides the needed parameters in case of a parameterized i18n
	 * message. In case of a non-existing message, the given default message will be returned.
	 * 
	 * @param code
	 *            - the message code
	 * @param args
	 *            - the parameters
	 * @param defaultMessage
	 *            - the default message
	 * @return the i18n message
	 */
	public String getMessage(String code, Object[] args, String defaultMessage);
}
