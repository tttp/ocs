/** ====================================================================
 * Licensed under the European Union Public Licence (EUPL v1.2) 
 * https://joinup.ec.europa.eu/community/eupl/topic/public-consultation-draft-eupl-v12
 * ====================================================================
 *
 * @author Daniel CHIRITA
 * @created: 23/05/2013
 *
 */
package eu.europa.ec.eci.oct;

import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.Stateless;

import org.apache.log4j.Logger;

import eu.europa.ec.eci.oct.business.api.ConfigurationService;
import eu.europa.ec.eci.oct.business.api.OCTException;
import eu.europa.ec.eci.oct.entities.ConfigurationParameter;
import eu.europa.ec.eci.oct.persistence.DAOFactory;
import eu.europa.ec.eci.oct.persistence.PersistenceException;
import eu.europa.ec.eci.oct.persistence.SettingsDAO;

@Stateless
@Local(ConfigurationService.class)
public class ConfigurationServiceImpl implements ConfigurationService {

	private static final Logger logger = Logger.getLogger(ConfigurationServiceImpl.class);

	@EJB
	private DAOFactory daoFactory;

	@Override
	public ConfigurationParameter getConfigurationParameter(Parameter parameter) throws OCTException {
		try {
			SettingsDAO dao = daoFactory.getSettingsDAO();

			ConfigurationParameter result = dao.findConfigurationParametereByKey(parameter.getKey());
			if (result == null) {
				// if not found, prepopulate with default value
				result = new ConfigurationParameter();
				result.setParam(parameter.getKey());
				result.setValue(parameter.getDefaultValue());
			}

			return result;
		} catch (PersistenceException e) {
			logger.error("There was a problem retrieving the configuration parameter: " + parameter.getKey(), e);
			throw new OCTException("There was a problem retrieving the configuration parameter.", e);
		}
	}

	@Override
	public void updateParameter(ConfigurationParameter param) throws OCTException {
		try {
			daoFactory.getSettingsDAO().updateParameter(param);
		} catch (PersistenceException e) {
			logger.error("There was a problem while deleting signature. The message was: " + e.getLocalizedMessage(), e);
			throw new OCTException("There was a problem while deleting signature.", e);
		}
	}

}
