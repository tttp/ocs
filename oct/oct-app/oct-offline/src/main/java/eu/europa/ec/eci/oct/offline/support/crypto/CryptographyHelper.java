package eu.europa.ec.eci.oct.offline.support.crypto;

import eu.europa.ec.eci.oct.offline.support.log.OfflineCryptoToolLogger;

/**
 * @author: micleva
 * @created: 11/22/11
 * @project OCT
 */
public class CryptographyHelper {
    private static OfflineCryptoToolLogger log = OfflineCryptoToolLogger.getLogger(CryptographyHelper.class.getName());

    private static byte[] key;

    public static boolean initiateCryptoModule(String pwd) {
        boolean success = false;
        try {
            key = KeyProvider.loadKeyFromFile(pwd);

            success = true;
        } catch (Exception e) {
            //do nothing
            log.debug("Unable to load the key from file with the given password", e);
        }

        return success;
    }

    public static boolean isCryptoModuleInitialized() {
        return key != null && key.length > 0;
    }

    public static byte[] getKey() {
        return key;
    }
}
