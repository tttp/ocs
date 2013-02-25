package eu.europa.ec.eci.oct.web.converters;

import eu.europa.ec.eci.oct.entities.PropertyType;
import eu.europa.ec.eci.oct.utils.validator.DataType;

/**
 * Handles data conversion between {@link PropertyType} and {@link DataType} types.
 * 
 * @author chiridl
 * 
 */
public class PropertyType2DataType {

	public static DataType comvert(PropertyType propertyType) {
		DataType result = DataType.ALPHANUMERIC;
		switch (propertyType) {
		case NUMERIC:
			result = DataType.NUMERIC;
			break;
		case DATE:
			result = DataType.DATE;
			break;
		default:
			break;
		}
		return result;
	}
}
