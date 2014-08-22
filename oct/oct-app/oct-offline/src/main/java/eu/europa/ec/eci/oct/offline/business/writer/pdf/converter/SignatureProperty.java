package eu.europa.ec.eci.oct.offline.business.writer.pdf.converter;

/**
 * @author: micleva
 * @created: 12/6/11
 * @project OCT
 */
public enum SignatureProperty {

    NAME_AT_BIRTH("oct.property.name.at.birth"),

    FATHERS_NAME("oct.property.fathers.name"),

    ADDRESS("oct.property.address"),

    STREET("oct.property.street"),

    POSTAL_CODE("oct.property.postal.code"),

    CITY("oct.property.city"),

    COUNTRY("oct.property.country"),

    STATE("oct.property.state"),

    DATE_OF_BIRTH("oct.property.date.of.birth"),

    PLACE_OF_BIRTH("oct.property.place.of.birth"),

    ISSUING_AUTHORITY("oct.property.issuing.authority"),

    PASSPORT("oct.property.passport"),

    ID_CARD("oct.property.id.card"),

    RESIDENCE_PERMIT("oct.property.residence.permit"),

    PERSONAL_NUMBER("oct.property.personal.number"),

    PERSONAL_ID("oct.property.personal.id"),

    PERMANENT_RESIDENCE("oct.property.permanent.residence"),

    DRIVING_LICENSE("oct.property.driving.license"),

    NATIONAL_ID_NUMBER("oct.property.national.id.number"),

    SOCIAL_SECURITY_ID("oct.property.social.security.id"),

    REGISTRATION_CERTIFICATE("oct.property.registration.certificate"),

    CITIZENS_CARD("oct.property.citizens.card"),

    PERSONAL_NO_IN_PASSPORT("oct.property.personal.no.in.passport"),

    PERSONAL_NO_IN_ID_CARD("oct.property.personal.no.in.id.card"),

    OTHER_1("oct.property.other.1"),

    OTHER_2("oct.property.other.2"),

    OTHER_3("oct.property.other.3"),

    OTHER_4("oct.property.other.4"),

    OTHER_5("oct.property.other.5"),

    OTHER_6("oct.property.other.6"),

    OTHER_7("oct.property.other.7"),

    OTHER_8("oct.property.other.8"),

    OTHER_9("oct.property.other.9"),

    OTHER_10("oct.property.other.10"),

    DATE_OF_BIRTH_AT("oct.property.date.of.birth.at"),

    FIRST_NAME("oct.property.firstname"),

    LAST_NAME("oct.property.lastname"),

    NATIONALITY("oct.property.nationality"),

    PERSONAL_ID_OTHER("oct.property.id.other");


    private String key;

    SignatureProperty(String propertyKey) {
        this.key = propertyKey;
    }

    public String getKey() {
        return key;
    }

    public static SignatureProperty byKey(String key) {

        SignatureProperty byKeyProperty = null;
        SignatureProperty[] values = SignatureProperty.values();
        for (int i = 0, valuesLength = values.length; i < valuesLength && byKeyProperty == null; i++) {
            SignatureProperty signatureProperty = values[i];
            if (signatureProperty.getKey().equals(key)) {
                byKeyProperty = signatureProperty;
            }
        }
        return byKeyProperty;
    }
}
