package eu.europa.ec.eci.oct.web.controller;

public interface ControllerConstants extends eu.europa.ec.eci.oct.webcommons.controller.CommonControllerConstants {

	public static final String SESSION_ATTR_RESULTING_TOKEN = "__oct_signature_review_bean__";
	public static final String SESSION_ATTR_FIRST_PAGE_ACCESSED = "__first_page_accessed__";
	
	public static final String MODEL_ATTRIBUTE_INITIATIVE_URL = "oct_initiative_url";
	public static final String MODEL_ATTRIBUTE_START_URL = "startUrl";

	public static final String URL_PARAMETER_STATUS = "ocsstatus";
	public static final String URL_PARAMETER_STATUS_SUCCESS = "success";
	public static final String URL_PARAMETER_LANG = "ocslanguage";
}
