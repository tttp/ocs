package eu.europa.ec.eci.oct.web.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import eu.europa.ec.eci.oct.entities.PropertyGroup;
import eu.europa.ec.eci.oct.entities.member.Country;
import eu.europa.ec.eci.oct.entities.signature.PropertyValue;
import eu.europa.ec.eci.oct.webcommons.model.SimpleBean;

/**
 * Form bean for the support initiative form.
 * 
 * @author chiridl
 * 
 */
public class SupportFormBean extends SimpleBean {

	private boolean accepted1;
	private boolean accepted2;
	private String countryCode;
	private Country countryToSignFor;
	private List<PropertyValue> properties = new ArrayList<PropertyValue>();
	private List<PropertyGroup> groups = new ArrayList<PropertyGroup>();
	private Map<Long, Long> multichoiceSelections = new HashMap<Long, Long>();
	private Map<Long, Map<Long, String>> groupItems = new HashMap<Long, Map<Long, String>>();
	private Map<String, String> translatedProperties = new HashMap<String, String>();

	private String captcha;

	public List<PropertyValue> getProperties() {
		return properties;
	}

	public List<PropertyGroup> getGroups() {
		return groups;
	}

	public Map<Long, Long> getMultichoiceSelections() {
		return multichoiceSelections;
	}

	public Map<Long, Map<Long, String>> getGroupItems() {
		return groupItems;
	}

	public void setCaptcha(String captcha) {
		this.captcha = captcha;
	}

	public String getCaptcha() {
		return captcha;
	}

	public String getUniqueToken() {
		return "" + System.currentTimeMillis() + new Random().nextInt(9999);
	}

	public void reinitialize() {
		setAccepted1(false);
		setAccepted2(false);
		setCaptcha(null);
		setCountryToSignFor(null);

		getGroups().clear();
		getProperties().clear();
		getTranslatedProperties().clear();
		getMultichoiceSelections().clear();
		getGroupItems().clear();
	}

	public void setAccepted1(boolean accepted1) {
		this.accepted1 = accepted1;
	}

	public boolean isAccepted1() {
		return accepted1;
	}

	public void setAccepted2(boolean accepted2) {
		this.accepted2 = accepted2;
	}

	public boolean isAccepted2() {
		return accepted2;
	}

	public Map<String, String> getTranslatedProperties() {
		return translatedProperties;
	}

	public void setCountryToSignFor(Country countryToSignFor) {
		this.countryToSignFor = countryToSignFor;
	}

	public Country getCountryToSignFor() {
		return countryToSignFor;
	}

	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}

	public String getCountryCode() {
		return countryCode;
	}
}
