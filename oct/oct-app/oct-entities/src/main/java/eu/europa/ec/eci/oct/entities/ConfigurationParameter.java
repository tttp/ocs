/** ====================================================================
 * Licensed under the European Union Public Licence (EUPL v1.2) 
 * https://joinup.ec.europa.eu/community/eupl/topic/public-consultation-draft-eupl-v12
 * ====================================================================
 *
 * @author Daniel CHIRITA
 * @created: 23/05/2013
 *
 */
package eu.europa.ec.eci.oct.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "OCT_SETTINGS")
public class ConfigurationParameter {

	@Id
	@Column(nullable = false, length = 128)
	private String param;

	@Column(nullable = true)
	private String value;

	public String getParam() {
		return param;
	}

	public void setParam(String param) {
		this.param = param;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	@Override
	public String toString() {
		return "ConfigurationParameter [param=" + param + ", value=" + value + "]";
	}
}
