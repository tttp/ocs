package eu.europa.ec.eci.oct.offline.business.writer.pdf.converter;

/**
 * @author: micleva
 * @created: 12/6/11
 * @project OCT
 */
public enum SignatureGroupName {

    ADDRESS("oct.group.address"),

    GENERAL("oct.group.general"),

    ID("oct.group.id");

    private String fullName;

    SignatureGroupName(String fullName) {
        this.fullName = fullName;
    }

    public String getFullName() {
        return fullName;
    }
}
