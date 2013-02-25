package eu.europa.ec.eci.oct.offline.startup;

import eu.europa.ec.eci.oct.offline.support.log.OfflineCryptoToolLogger;

import java.io.IOException;
import java.io.InputStream;
import java.util.PropertyResourceBundle;

/**
 * @author: micleva
 * @created: 11/30/11
 * @project OCT
 */
public class CryptoOfflineConfig {
    private final static String CONFIG_FILE_LOCATION = "cryptToolConfiguration.properties";
    private static OfflineCryptoToolLogger log = OfflineCryptoToolLogger.getLogger(CryptoOfflineConfig.class.getName());

    private PropertyResourceBundle propertyResourceBundle;

    private static CryptoOfflineConfig instance;

    private CryptoOfflineConfig() {
        InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream(CONFIG_FILE_LOCATION);
        try {
            propertyResourceBundle = new PropertyResourceBundle(inputStream);
        } catch (IOException e) {
            //do nothing
            log.debug("Unable to read the config file", e);
        }
    }

    public static CryptoOfflineConfig getInstance() {
        if (instance == null) {
            instance = new CryptoOfflineConfig();
        }
        return instance;
    }

    public boolean getBooleanConfigValue(ConfigProperty configProperty, boolean defaultValue) {
        return getBooleanValue(configProperty.getKey(), defaultValue);
    }

    public int getIntegerConfigValue(ConfigProperty configProperty, int defaultValue) {
        return getIntegerValue(configProperty.getKey(), defaultValue);
    }

    private int getIntegerValue(String propertyName, int defaultValue) {
        String configValue = propertyResourceBundle != null ? propertyResourceBundle.getString(propertyName) : null;

        int resultValue = defaultValue;
        if (configValue != null) {
            try {
                resultValue = Integer.valueOf(configValue);
            } catch (NumberFormatException e) {
                //ignore as the default value will be used in this case
                log.debug("Not a number value for propertyName:" + propertyName, e);
            }
        }

        return resultValue;
    }

    private Boolean getBooleanValue(String propertyName, boolean defaultValue) {
        String configValue = propertyResourceBundle != null ? propertyResourceBundle.getString(propertyName) : null;

        boolean resultValue = defaultValue;
        if (configValue != null) {
            resultValue = Boolean.valueOf(configValue);
        }

        return resultValue;
    }

    private String getStringValue(String propertyName, String defaultValue) {
        String resultValue = propertyResourceBundle != null ? propertyResourceBundle.getString(propertyName) : null;

        if (resultValue == null) {
            resultValue = defaultValue;
        }

        return resultValue;
    }
}
