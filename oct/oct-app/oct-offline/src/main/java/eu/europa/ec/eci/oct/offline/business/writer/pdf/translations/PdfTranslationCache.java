package eu.europa.ec.eci.oct.offline.business.writer.pdf.translations;

import eu.europa.ec.eci.oct.offline.support.localization.LocalizationMessageProvider;
import eu.europa.ec.eci.oct.offline.support.localization.LocalizationProvider;
import eu.europa.ec.eci.oct.offline.support.log.OfflineCryptoToolLogger;

import java.util.*;

/**
 * @author: micleva
 * @created: 4/2/12
 * @project ECI
 */
class PdfTranslationCache {

    private static OfflineCryptoToolLogger log = OfflineCryptoToolLogger.getLogger(PdfTranslationCache.class.getName());

    static Map<String, LocalizationMessageProvider> byLanguageMessageProvider = new HashMap<String, LocalizationMessageProvider>();
    static Map<String, List<String>> languagesByCountry = new HashMap<String, List<String>>();
    static LocalizationMessageProvider documentNamesProvider = null;
    static LocalizationMessageProvider defaultMessagesProvider = new LocalizationMessageProvider(Locale.ENGLISH, "reports/signatureTranslations/messages");

    static {
        initLanguagesByCountryCode();
        initDocumentNamesProvider();
        initAvailableMessageProviders();
    }

    private static void initDocumentNamesProvider() {
        try {
            documentNamesProvider = new LocalizationMessageProvider(Locale.ENGLISH, "reports/signatureTranslations/documentnames");
        } catch (Exception e) {
            log.warning("Unable to load the documentNames provider", e);
        }
    }

    private static void initLanguagesByCountryCode() {

        try {
            List<Locale> localizationForLanguage = LocalizationProvider.getInstance().getSupportedLocales();

            //build all the available language codes for which we have translations
            Set<String> availableLanguageCodes = new HashSet<String>();
            for (Locale locale : localizationForLanguage) {
                availableLanguageCodes.add(locale.getLanguage().toLowerCase());
            }

            //extract all the locales with the given language codes
            //and organize them by country code
            for (Locale locale : Locale.getAvailableLocales()) {
                String countryLanguage = locale.getLanguage().toLowerCase();
                String countryCode = locale.getCountry().toUpperCase();
                if(availableLanguageCodes.contains(countryLanguage)) {

                    addLanguageForCountry(countryCode, countryLanguage);

                    //specific rules go here
                    if(countryCode.equals("GR")) {
                        addLanguageForCountry("EL", countryLanguage);
                    }
                    if(countryCode.equals("GB")) {
                        addLanguageForCountry("UK", countryLanguage);
                    }
                }
            }
        } catch (Exception e) {
            log.warning("Unable to initialize the languages by country code", e);
        }
    }

    private static void addLanguageForCountry(String countryCode, String language) {
        if(!languagesByCountry.containsKey(countryCode)) {
            languagesByCountry.put(countryCode, new ArrayList<String>());
        }
        languagesByCountry.get(countryCode).add(language);
    }

    private static void initAvailableMessageProviders() {
        try {
            List<Locale> localizationForLanguage = LocalizationProvider.getInstance().getSupportedLocales();

            for (Locale locale : localizationForLanguage) {

                LocalizationMessageProvider messageProvider = new LocalizationMessageProvider(locale, "reports/signatureTranslations/messages");
                if (messageProvider.getCurrentLocale().equals(locale)) {
                    byLanguageMessageProvider.put(locale.getLanguage().toLowerCase(), messageProvider);
                }
            }
        } catch (Exception e) {
            log.warning("Unable to initialize the available message providers", e);
        }
    }
}
