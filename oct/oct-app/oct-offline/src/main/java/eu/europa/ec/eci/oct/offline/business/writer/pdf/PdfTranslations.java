package eu.europa.ec.eci.oct.offline.business.writer.pdf;

import eu.europa.ec.eci.oct.offline.business.writer.pdf.converter.SignatureProperty;
import eu.europa.ec.eci.oct.offline.support.localization.LocalizationMessageProvider;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * @author: micleva
 * @created: 12/16/11
 * @project OCT
 */
public class PdfTranslations {

    private static Map<String, LocalizationMessageProvider> byCountryMessageProvider = new HashMap<String, LocalizationMessageProvider>();
    private static LocalizationMessageProvider documentNamesProvider = null;
    private static Map<String, Locale> localeByCountryCode = new HashMap<String, Locale>();

    static{
        for (Locale locale : Locale.getAvailableLocales()) {
            String countryCode = locale.getCountry().toUpperCase();
            localeByCountryCode.put(countryCode, locale);
            if (countryCode.equals("GR")) {
                localeByCountryCode.put("EL", locale);
            }
            if (countryCode.equals("GB")) {
                localeByCountryCode.put("UK", locale);
            }
        }
    }

    private static synchronized LocalizationMessageProvider getMessagesForLanguage(String countryCodeForLanguage) {
        Locale locale = countryCodeForLanguage != null ? localeByCountryCode.get(countryCodeForLanguage.toUpperCase()) : Locale.ENGLISH;
        if(!byCountryMessageProvider.containsKey(countryCodeForLanguage)){
            LocalizationMessageProvider messageProvider = new LocalizationMessageProvider(locale, "reports/signatureTranslations/messages");
            byCountryMessageProvider.put(countryCodeForLanguage, messageProvider);
        }
        return byCountryMessageProvider.get(countryCodeForLanguage);
    }

    private static synchronized LocalizationMessageProvider getDocumentNamesProvider() {
        if(documentNamesProvider == null) {
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
        LocalizationMessageProvider documentNames = getDocumentNamesProvider();

        return documentNames.getMessage(signatureProperty.getKey() + "." + languageCode);
    }
}
