package eu.europa.ec.eci.oct.offline.support.localization;

import eu.europa.ec.eci.oct.offline.support.log.OfflineCryptoToolLogger;

import java.text.MessageFormat;
import java.util.*;

/**
 *
 * @author: micleva
 * @created: 11/9/11
 * @project OCT
 */
public class LocalizationMessageProvider {

    private static OfflineCryptoToolLogger log = OfflineCryptoToolLogger.getLogger(LocalizationMessageProvider.class.getName());

    private Map<String, String> defaultMessages = new HashMap<String, String>();
    private Map<String, String> localizedMessages = new HashMap<String, String>();

    private Locale currentLocale;
    private String resourceBundleName;

    public LocalizationMessageProvider(Locale currentLocale, String resourceBundleName) {
        //initialize the user's default localized messages
        this.currentLocale = currentLocale != null ? currentLocale : Locale.ENGLISH;
        this.resourceBundleName = resourceBundleName;
        refreshLocalizedMessages();

        //initialize system/s default locale messages
        initSystemDefaultMessages();
    }

    private void initSystemDefaultMessages() {
        if (currentLocale.equals(Locale.ENGLISH)) {
            defaultMessages.putAll(localizedMessages);
        } else {
            fillUpResourceMessages(Locale.ENGLISH, defaultMessages);
        }
    }

    private void refreshLocalizedMessages() {
        localizedMessages.clear();
        fillUpResourceMessages(currentLocale, localizedMessages);
    }

    private void fillUpResourceMessages(Locale locale, Map<String, String> messageMap) {
        try {
            ResourceBundle resourceBundle = ResourceBundle.getBundle(resourceBundleName, locale, new UTF8Control());

            Enumeration<String> keys = resourceBundle.getKeys();
            while (keys.hasMoreElements()) {
                String key = keys.nextElement();
                messageMap.put(key, resourceBundle.getString(key));
            }
        } catch (Exception e) {
            log.warning("Unable to fill up the resource message for locale: " + locale, e);
        }
    }

    synchronized void updateMessagesForLocale(Locale newLocale) {
        this.currentLocale = newLocale;
        refreshLocalizedMessages();
    }

    public String getMessage(String key) {
        String message = localizedMessages.get(key);

        //if the message was not found localized, read it from system's default locale messages
        if(message == null) {
            message = defaultMessages.get(key);
        }

        return message == null ? "???" + key + "???" : message;
    }

    public String getMessage(String key, String... args) {
        MessageFormat messageFormat = new MessageFormat(getMessage(key));
        return messageFormat.format(args);
    }

    public Locale getCurrentLocale() {
        return currentLocale;
    }
}