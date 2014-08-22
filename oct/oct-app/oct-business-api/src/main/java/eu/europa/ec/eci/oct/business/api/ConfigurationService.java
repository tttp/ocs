/** ====================================================================
 * Licensed under the European Union Public Licence (EUPL v1.2) 
 * https://joinup.ec.europa.eu/community/eupl/topic/public-consultation-draft-eupl-v12
 * ====================================================================
 *
 * @author Daniel CHIRITA
 * @created: 23/05/2013
 *
 */
package eu.europa.ec.eci.oct.business.api;

import eu.europa.ec.eci.oct.entities.ConfigurationParameter;

public interface ConfigurationService {

	/**
	 * Enum containing possible configuration parameters and there defaul
	 * values.
	 * 
	 * @author Daniel CHIRITA
	 * 
	 */
	public enum Parameter {
		LOGO_PATH("logo_path", "./images/eu.png"), OPTIONAL_VALIDATION("optional_validation", "false"), SHOW_DISTRIBUTION_MAP(
				"show_distribution_map", "true"), CALLBACK_URL("callback_url", "");

		private String key;
		private String defaultValue;

		private Parameter(String key, String defaultValue) {
			this.key = key;
			this.defaultValue = defaultValue;
		}

		public String getKey() {
			return key;
		}

		public String getDefaultValue() {
			return defaultValue;
		}
	}

	/**
	 * Retrieves a configuration parameter from the settings table. If the
	 * parameter is not found, a new one will be created and prepopulated with
	 * the default value specified by the associated {@link Parameter}.
	 * 
	 * @param parameter
	 * @return
	 * @throws OCTException
	 */
	public ConfigurationParameter getConfigurationParameter(Parameter parameter) throws OCTException;

	/**
	 * Stores a parameter in the settings table. If the parameter already
	 * exists, it will be updated. If not, it will be inserted.
	 * 
	 * @param param
	 * @throws OCTException
	 */
	public void updateParameter(ConfigurationParameter param) throws OCTException;
}
