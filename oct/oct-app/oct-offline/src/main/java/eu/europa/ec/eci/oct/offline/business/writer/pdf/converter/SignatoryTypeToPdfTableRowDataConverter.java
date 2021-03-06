package eu.europa.ec.eci.oct.offline.business.writer.pdf.converter;

import eu.europa.ec.eci.export.model.GroupType;
import eu.europa.ec.eci.export.model.PropertyType;
import eu.europa.ec.eci.export.model.SignatureType;
import eu.europa.ec.eci.oct.offline.business.AnnexRevisionType;
import eu.europa.ec.eci.oct.offline.business.writer.pdf.model.PdfTableRowData;
import eu.europa.ec.eci.oct.offline.business.writer.pdf.translations.PdfTranslationsHelper;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import static eu.europa.ec.eci.oct.offline.business.writer.pdf.converter.NullSafeConverter.getDate;
import static eu.europa.ec.eci.oct.offline.business.writer.pdf.converter.NullSafeConverter.getString;

/**
 * @author: micleva
 * @created: 12/13/11
 * @project OCT
 */
class SignatoryTypeToPdfTableRowDataConverter {
    private Map<SignatureProperty, PropertyType> groupedProperties;
    private SignatureType signatureType;
    private String countryCode;

    public SignatoryTypeToPdfTableRowDataConverter(SignatureType signatureType, String countryCode) {
        this.signatureType = signatureType;
        this.countryCode = countryCode;

        groupedProperties = new HashMap<SignatureProperty, PropertyType>();
        for (GroupType groupType : signatureType.getSignatoryInfo().getGroups().getGroup()) {
            for (PropertyType propertyType : groupType.getProperties().getProperty()) {
                groupedProperties.put(SignatureProperty.byKey(propertyType.getKey()), propertyType);
            }
        }
    }

    public PdfTableRowData buildPdfTableRowData(Locale locale, AnnexRevisionType annexRevisionType) {
        PdfTableRowData signatureData = new PdfTableRowData();

        //set the signatory date
        signatureData.setSignatureDate(getDate(signatureType.getSubmissionDate()));

        //set the id related data
        signatureData.setIdentificationNumber(getPropertyValue(annexRevisionType, true, locale, SignatureProperty.ID_CARD,
                SignatureProperty.PERSONAL_ID,
                SignatureProperty.NATIONAL_ID_NUMBER,
                SignatureProperty.PERSONAL_ID_OTHER,
                SignatureProperty.SOCIAL_SECURITY_ID,
                SignatureProperty.PERSONAL_NO_IN_PASSPORT,
                SignatureProperty.PERSONAL_NO_IN_ID_CARD,
                SignatureProperty.PASSPORT,
                SignatureProperty.RESIDENCE_PERMIT,
                SignatureProperty.PERSONAL_NUMBER,
                SignatureProperty.DRIVING_LICENSE,
                SignatureProperty.REGISTRATION_CERTIFICATE,
                SignatureProperty.CITIZENS_CARD,
                SignatureProperty.OTHER_1,
                SignatureProperty.OTHER_2,
                SignatureProperty.OTHER_3,
                SignatureProperty.OTHER_4,
                SignatureProperty.OTHER_5,
                SignatureProperty.OTHER_6,
                SignatureProperty.OTHER_7,
                SignatureProperty.OTHER_8,
                SignatureProperty.OTHER_9,
                SignatureProperty.OTHER_10,
                SignatureProperty.PERMANENT_RESIDENCE,
                SignatureProperty.ISSUING_AUTHORITY));

        //set the first name
        signatureData.setFirstName(getPropertyValue(annexRevisionType, false, locale, SignatureProperty.FIRST_NAME));

        //set the last name
        signatureData.setLastName(getPropertyValue(annexRevisionType, false, locale, SignatureProperty.LAST_NAME,
                SignatureProperty.FATHERS_NAME, SignatureProperty.NAME_AT_BIRTH));

        //set the nationality
        signatureData.setNationality(getPropertyValue(annexRevisionType, false, locale, SignatureProperty.NATIONALITY));

        //set the date place of birth
        signatureData.setDatePlaceOfBirth(getPropertyValue(annexRevisionType, false, locale, SignatureProperty.DATE_OF_BIRTH,
                SignatureProperty.DATE_OF_BIRTH_AT, SignatureProperty.PLACE_OF_BIRTH));

        //set the residence
        signatureData.setResidence(getPropertyValue(annexRevisionType, false, locale, SignatureProperty.ADDRESS, SignatureProperty.STREET,
                SignatureProperty.POSTAL_CODE, SignatureProperty.CITY, SignatureProperty.STATE,
                SignatureProperty.COUNTRY));

        return signatureData;
    }

    private String getPropertyValue(AnnexRevisionType annexRevisionType, boolean includePrefixForProperties, Locale locale, SignatureProperty... properties) {
        StringBuilder result = new StringBuilder();

        for (SignatureProperty property : properties) {
            PropertyType propertyType = groupedProperties.get(property);
            if (propertyType != null) {
                String propertyValue = getString(propertyType.getValue());
                if (propertyValue.length() > 0) {
                    if (result.length() > 0) {
                        result.append(", ");
                    }
                    //include prefixes if needed
                    if(includePrefixForProperties) {
                        String prefix = PdfTranslationsHelper.getPrefixForProperty(property, locale, annexRevisionType);
                        if (prefix != null) {
                            result.append(prefix).append(": ");
                        }
                    }
                    //do translations if needed
                    switch(property) {
                        case COUNTRY:
                        	String translatedCountryName = PdfTranslationsHelper.getCountryNameForCountryCodeInLanguage(propertyValue, locale, annexRevisionType);
                        	if (translatedCountryName.startsWith("???")) {
                        		result.append(propertyValue);
                        	} else {
                        		result.append(translatedCountryName);
                        	}
                            //result.append(PdfTranslationsHelper.getCountryNameForCountryCodeInLanguage(propertyValue, countryCode));
                            break;
                        case NATIONALITY:
                            result.append(PdfTranslationsHelper.getNationalityNameForCountryCodeInLanguage(propertyValue, locale, annexRevisionType));
                            break;
                        default:
                            result.append(propertyValue);
                    }
                }
            }
        }
        return result.toString();
    }
}
