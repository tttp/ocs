package eu.europa.ec.eci.oct.offline.support.localization;

import java.util.*;

/**
 * @author: micleva
 * @created: 11/8/11
 * @project OCT
 */
public class LocalizationProvider {

    //the list of items registered for localization
    private List<LocalizedItem> localizedItems = new ArrayList<LocalizedItem>();

    //the list of objects that will be notified upon localization change
    private List<LocalizationChangedListener> localizationChangedList = new ArrayList<LocalizationChangedListener>();

    // the localization message provider
    private LocalizationMessageProvider messageProvider;

    // the currently supported locales
    private List<Locale> supportedLocales;

    private static LocalizationProvider localizationProvider = new LocalizationProvider();

    private LocalizationProvider() {
        //initialize the user's default localized messages
        Locale userLocale = getDefaultLocale();

        messageProvider = new LocalizationMessageProvider(userLocale, "messages/messages");

        supportedLocales = buildSupportedLocales();
    }

    private List<Locale> buildSupportedLocales() {

        List<Locale> supportedLocales = new ArrayList<Locale>();
        Locale[] locales = Locale.getAvailableLocales();
        for (Locale locale : locales) {
            try {
                ResourceBundle resourceBundle = ResourceBundle.getBundle("messages/messages", locale);
                if(resourceBundle.getLocale().equals(locale)) {
                    supportedLocales.add(locale);
                }
            } catch (Exception e) {
                //catch any exception at this stage as failing to load a resource bundle for a country like japan is not a reason for the application to fail
            }
        }

        return Collections.unmodifiableList(supportedLocales);
    }

    public static LocalizationProvider getInstance() {
        return localizationProvider;
    }

    public void registerLocalizedItem(LocalizedItem localizedItem) {
        localizedItems.add(localizedItem);

        //as this localized item has just registered for localization, update its messages
        // according to our localization message provider
        localizedItem.updateMessages(messageProvider);
    }

    public void removeLocalizedItem(LocalizedItem localizedItem) {
        localizedItems.remove(localizedItem);
    }

    public void addLocalizedChangedListener(LocalizationChangedListener localizationChangedListener) {
        localizationChangedList.add(localizationChangedListener);
    }

    public Locale getDefaultLocale() {
        //include logic for saving the locale on exiting the tool and loading it at start-up
        return Locale.ENGLISH;
    }

    public synchronized void changeLocale(Locale locale) {
        messageProvider.updateMessagesForLocale(locale);

        for (LocalizedItem localizedItem : localizedItems) {
            localizedItem.updateMessages(messageProvider);
        }
        for (LocalizationChangedListener localizationChangedListener : localizationChangedList) {
            localizationChangedListener.onLocalizationChanged(getCurrentLocale());
        }
    }

    public Locale getCurrentLocale() {
        return messageProvider.getCurrentLocale();
    }

    public LocalizationMessageProvider getCurrentMessageProvider() {
        return messageProvider;
    }

    public List<Locale> getSupportedLocales() {
        return supportedLocales;
    }
}
