package eu.europa.ec.eci.oct.validation;

import eu.europa.ec.eci.oct.entities.PropertyType;
import eu.europa.ec.eci.oct.validation.core.DataType;

/**
 * Handles data conversion between {@link PropertyType} and {@link DataType} types.
 * 
 * @author Daniel CHIRITA, DIGIT B.1, EC
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
