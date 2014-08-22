package eu.europa.ec.eci.oct.offline.business.writer.pdf.translations;

import eu.europa.ec.eci.oct.offline.business.AnnexRevisionType;
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

    static Map<String, List<String>> languagesByCountry = new HashMap<String, List<String>>();
    static Map<Integer, LocalizationMessageProvider> documentNamesProviderMap;
    static Map<Integer, Map<String, LocalizationMessageProvider>> byLanguageMessageProviderMap;
    static LocalizationMessageProvider defaultMessagesProvider = new LocalizationMessageProvider(Locale.ENGLISH, "reports/annexRevision1/signatureTranslations/messages");


    static Map<Integer, LocalizationMessageProvider> defaultMessagesProviderMap;

    static {
        initLanguagesByCountryCode();
        initDocumentNamesProvider();
        initAvailableMessageProviders();
    }

    private static void initDocumentNamesProvider() {
        try {
            documentNamesProviderMap = new HashMap<Integer, LocalizationMessageProvider>();
            documentNamesProviderMap.put(
            		AnnexRevisionType.ANNEX_REVISION_0_INITIAL_RELEASE.getRevisionNumber(), 
            		new LocalizationMessageProvider(Locale.ENGLISH, "reports/annexRevision" + AnnexRevisionType.ANNEX_REVISION_0_INITIAL_RELEASE.getRevisionNumber() + "/signatureTranslations/documentnames"));
            documentNamesProviderMap.put(
            		AnnexRevisionType.ANNEX_REVISION_1_ANNEX_III.getRevisionNumber(), 
            		new LocalizationMessageProvider(Locale.ENGLISH, "reports/annexRevision" + AnnexRevisionType.ANNEX_REVISION_1_ANNEX_III.getRevisionNumber() + "/signatureTranslations/documentnames"));
            
        } catch (Exception e) {
            log.warning("Unable to load the documentNames provider", e);
        }
    }
    
    public static LocalizationMessageProvider getDocumentNameMessageProvider(AnnexRevisionType annexRevisionType){
    	return documentNamesProviderMap.get(annexRevisionType.getRevisionNumber());
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
        	
        	byLanguageMessageProviderMap = new HashMap<Integer, Map<String, LocalizationMessageProvider>>();
        	
        	Map<String, LocalizationMessageProvider> byLanguageMessageProvider0 = new HashMap<String, LocalizationMessageProvider>();
        	List<Locale> localizationForLanguage = LocalizationProvider.getInstance().getSupportedLocales();
            for (Locale locale : localizationForLanguage) {
                LocalizationMessageProvider messageProvider = new LocalizationMessageProvider(locale, "reports/annexRevision"+AnnexRevisionType.ANNEX_REVISION_0_INITIAL_RELEASE.getRevisionNumber()+"/signatureTranslations/messages");
                if (messageProvider.getCurrentLocale().equals(locale)) {
                    byLanguageMessageProvider0.put(locale.getLanguage().toLowerCase(), messageProvider);
                }
            }
            
        	Map<String, LocalizationMessageProvider> byLanguageMessageProvider1 = new HashMap<String, LocalizationMessageProvider>();
        	localizationForLanguage = LocalizationProvider.getInstance().getSupportedLocales();
            for (Locale locale : localizationForLanguage) {
                LocalizationMessageProvider messageProvider = new LocalizationMessageProvider(locale, "reports/annexRevision"+AnnexRevisionType.ANNEX_REVISION_1_ANNEX_III.getRevisionNumber()+"/signatureTranslations/messages");
                if (messageProvider.getCurrentLocale().equals(locale)) {
                    byLanguageMessageProvider1.put(locale.getLanguage().toLowerCase(), messageProvider);
                }
            }
            
            byLanguageMessageProviderMap.put(AnnexRevisionType.ANNEX_REVISION_0_INITIAL_RELEASE.getRevisionNumber(), byLanguageMessageProvider0);
            byLanguageMessageProviderMap.put(AnnexRevisionType.ANNEX_REVISION_1_ANNEX_III.getRevisionNumber(), byLanguageMessageProvider1);
            
        } catch (Exception e) {
            log.warning("Unable to initialize the available message providers", e);
        }
    }
    
    public static Map<String, LocalizationMessageProvider> getLanguageMessageProviderMap(AnnexRevisionType annexRevisionType){
    	return byLanguageMessageProviderMap.get(annexRevisionType.getRevisionNumber());
    }
}
