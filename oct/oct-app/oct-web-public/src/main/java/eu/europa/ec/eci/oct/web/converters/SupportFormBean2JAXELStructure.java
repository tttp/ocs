package eu.europa.ec.eci.oct.web.converters;

import java.util.LinkedHashMap;
import java.util.Map;

import eu.europa.ec.eci.oct.entities.signature.PropertyValue;
import eu.europa.ec.eci.oct.utils.validator.JAXELStructure;
import eu.europa.ec.eci.oct.web.model.SupportFormBean;

public class SupportFormBean2JAXELStructure {

	public static final String COUNTRY_TO_SIGN_FOR_PROPERTY = "c";

	public static JAXELStructure convert(SupportFormBean bean) {
		final Map<String, String> properties = new LinkedHashMap<String, String>();

		for (PropertyValue property : bean.getProperties()) {
			final String key = property.getProperty().getProperty().getName();
			final String value = property.getValue();
			properties.put(key, value);
		}

		properties.put(JAXELStructure.PROPERTY_PREFIX + COUNTRY_TO_SIGN_FOR_PROPERTY, bean.getCountryToSignFor()
				.getCode().toLowerCase());

		return new JAXELStructure(properties);
	}
}
