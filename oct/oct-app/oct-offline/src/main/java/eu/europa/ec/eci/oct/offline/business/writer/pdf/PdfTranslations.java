package eu.europa.ec.eci.oct.offline.business.writer.pdf;

import eu.europa.ec.eci.oct.offline.business.writer.pdf.converter.SignatureProperty;
import eu.europa.ec.eci.oct.offline.support.localization.LocalizationMessageProvider;

import java.util.*;

/**
 * @author: micleva
 * @created: 12/16/11
 * @project OCT
 */
public class PdfTranslations {

    private static Map<String, LocalizationMessageProvider> byCountryMessageProvider = new HashMap<String, LocalizationMessageProvider>();
    private static LocalizationMessageProvider documentNamesProvider = null;
    private static LocalizationMessageProvider defaultMessagesProvider = new LocalizationMessageProvider(Locale.ENGLISH, "reports/signatureTranslations/messages");

    private static synchronized LocalizationMessageProvider getMessagesForLanguage(String countryCode) {
        
        if (!byCountryMessageProvider.containsKey(countryCode)) {
            List<Locale> localesForCountry = getAvailableLocalesByCountryCode(countryCode);

            for (Locale locale : localesForCountry) {
                LocalizationMessageProvider messageProvider = new LocalizationMessageProvider(locale, "reports/signatureTranslations/messages");
                if (messageProvider.getCurrentLocale().equals(locale) && !byCountryMessageProvider.containsKey(countryCode)) {
                    byCountryMessageProvider.put(countryCode, messageProvider);
                }
            }
            if(!byCountryMessageProvider.containsKey(countryCode)) {
                //we searched all available locales for this country but we couldn't load any LocalizationMessageProvider that matches that locase 
                //put then the default LocalizationMessageProvider
                byCountryMessageProvider.put(countryCode, defaultMessagesProvider);
            }
        }
        return byCountryMessageProvider.get(countryCode);
    }
    
    static List<Locale> getAvailableLocalesByCountryCode(String countryCode) {
        List<Locale> localesForCountry = new ArrayList<Locale>();

        String searchedCountryCode = countryCode.toUpperCase();
        for (Locale locale : Locale.getAvailableLocales()) {
            String localeCountryCode = locale.getCountry().toUpperCase();
            if(searchedCountryCode.equals("MT") && !locale.getLanguage().equalsIgnoreCase("mt")) {
                continue;
            }
            if(localeCountryCode.equals(searchedCountryCode)||
                    (localeCountryCode.equals("GR") && searchedCountryCode.equals("EL")) ||
                    (localeCountryCode.equals("GB") && searchedCountryCode.equals("UK"))) {
                localesForCountry.add(locale);
            }
        }
        
        return localesForCountry;
    }

    private static synchronized LocalizationMessageProvider getDocumentNamesProvider() {
        if (documentNamesProvider == null) {
            documentNamesProvider = new LocalizationMessageProvider(Locale.ENGLISH, "reports/signatureTranslations/documentnames");
        }
        return documentNamesProvider;
    }

    public static String getCountryNameForCountryCodeInLanguage(String countryCode, String countryCodeForLanguage) {
        LocalizationMessageProvider countryMessageProvider = getMessagesForLanguage(countryCodeForLanguage);

        return countryMessageProvider.getMessage("oct.country." + countryCode);
    }

    public static String getNationalityNameForCountryCodeInLanguage(String countryCode, String countryCodeForLanguage) {
        LocalizationMessageProvider countryMessageProvider = getMessagesForLanguage(countryCodeForLanguage);

        return countryMessageProvider.getMessage("oct.country." + countryCode + ".nationality");
    }

    public static String getPrefixForProperty(SignatureProperty signatureProperty, String languageCode) {

        String prefix = null;
        
        if (signatureProperty.equals(SignatureProperty.ISSUING_AUTHORITY)) {
            LocalizationMessageProvider countryMessageProvider = getMessagesForLanguage(languageCode);

            prefix = countryMessageProvider.getMessage(signatureProperty.getKey());
        } else {
            //here we are on the document names scenario
            LocalizationMessageProvider documentNames = getDocumentNamesProvider();

            prefix = documentNames.getMessageOrNullString(signatureProperty.getKey() + "." + languageCode);
        }

        return prefix;
    }
}
