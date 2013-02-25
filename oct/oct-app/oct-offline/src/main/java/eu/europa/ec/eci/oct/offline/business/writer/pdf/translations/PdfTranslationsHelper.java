package eu.europa.ec.eci.oct.offline.business.writer.pdf.translations;

import eu.europa.ec.eci.oct.offline.business.writer.pdf.converter.SignatureProperty;
import eu.europa.ec.eci.oct.offline.support.localization.LocalizationMessageProvider;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import static eu.europa.ec.eci.oct.offline.business.writer.pdf.translations.PdfTranslationCache.*;

/**
 * @author: micleva
 * @created: 12/16/11
 * @project OCT
 */
public class PdfTranslationsHelper {

    public static List<Locale> getLocaleTranslationsByCountryCode(String countryCode) {
        List<Locale> locales = new ArrayList<Locale>();

        List<String> languagesByCountry = PdfTranslationCache.languagesByCountry.get(countryCode.toUpperCase());

        for (LocalizationMessageProvider messageProvider : byLanguageMessageProvider.values()) {
            if (languagesByCountry.contains(messageProvider.getCurrentLocale().getLanguage().toLowerCase())) {
                locales.add(messageProvider.getCurrentLocale());
            }
        }
        return locales;
    }

    public static String getCountryNameForCountryCodeInLanguage(String countryCode, Locale locale) {
        LocalizationMessageProvider messageProvider = byLanguageMessageProvider.get(locale.getLanguage().toLowerCase());

        return messageProvider.getMessage("oct.country." + countryCode);
    }

    public static String getNationalityNameForCountryCodeInLanguage(String countryCode, Locale locale) {
        LocalizationMessageProvider countryMessageProvider = getMessagesForLocale(locale);

        return countryMessageProvider.getMessage("oct.country." + countryCode + ".nationality");
    }

    public static String getPrefixForProperty(SignatureProperty signatureProperty, Locale locale) {

        String prefix = null;

        if (signatureProperty.equals(SignatureProperty.ISSUING_AUTHORITY)) {
            LocalizationMessageProvider countryMessageProvider = getMessagesForLocale(locale);

            prefix = countryMessageProvider.getMessage(signatureProperty.getKey());
        } else {
            //here we are on the document names scenario
            LocalizationMessageProvider documentNames = documentNamesProvider;

            prefix = documentNames.getMessageOrNullString(signatureProperty.getKey() + "." + locale.getLanguage().toLowerCase());
        }

        return prefix;
    }

    private static LocalizationMessageProvider getMessagesForLocale(Locale locale) {
        LocalizationMessageProvider messageProvider = byLanguageMessageProvider.get(locale.getLanguage().toLowerCase());
        return messageProvider != null ? messageProvider : defaultMessagesProvider;
    }


}
