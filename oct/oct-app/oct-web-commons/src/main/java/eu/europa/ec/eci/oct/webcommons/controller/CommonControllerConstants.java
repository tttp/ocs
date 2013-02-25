package eu.europa.ec.eci.oct.webcommons.controller;

public interface CommonControllerConstants {

	public static final String ATTR_FRAMEWORK_LANG = "oct.framework.language";
	public static final String ATTR_INITIATIVE_LANG = "oct.initiative.language";
	public static final String MODEL_ATTRIBUTE_LANGUAGES = "languages";
	public static final String MODEL_ATTRIBUTE_COUNTRIES = "countries";
	public static final String MODEL_ATTRIBUTE_SYSTEM_PREFS = "oct_system_preferences";
	public static final String MODEL_ATTRIBUTE_SYSTEM_STATE = "oct_system_state";
	public static final String MODEL_ATTRIBUTE_COLLECTOR_STATE = "oct_collector_state";
	public static final String MODEL_ATTRIBUTE_INITIATIVE_DESC = "oct_initiative_description";

	public static final String MODEL_ATTRIBUTE_SUCCESS_MSG = "oct_success_message";

	public static final String SESSION_ATTR_INITIATIVE_LANGUAGE = "__initiative__language__";

	public static final String MODEL_ATTRIBUTE_PREFERENCES = "oct_system_preferences";
	public static final String ATTRIBUTE_SESSION_CACHE = "oct_cache";

	public static final String SESSION_ATTR_HASHED_PHRASE = "__hashed_phrase__";
	
	/**
	 * Session attribute for an expected request token.
	 * The request token mechanism is used for securing form submissions against
	 * cross-site request forgery attacks. 
	 */
	public static final String SESSION_ATTR_EXPECTED_REQUEST_TOKEN = "__expected_request_token__";
	/**
	 * Request parameter for transmitting the current request token in a form.
	 * The request token mechanism is used for securing form submissions against
	 * cross-site request forgery attacks.
	 */
	public static final String REQUEST_PARAM_REQUEST_TOKEN = "oct_request_token";
	
}
