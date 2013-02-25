package eu.europa.ec.eci.oct.offline.business.writer.pdf.model;

import java.util.Date;

/**
 * @author: micleva
 * @created: 12/2/11
 * @project OCT
 */
public class PdfTableRowData {

    private String firstName;
    private String lastName;
    private String residence;
    private String datePlaceOfBirth;
    private String identificationNumber;
    private String nationality;
    private Date signatureDate;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getResidence() {
        return residence;
    }

    public void setResidence(String residence) {
        this.residence = residence;
    }

    public String getDatePlaceOfBirth() {
        return datePlaceOfBirth;
    }

    public void setDatePlaceOfBirth(String datePlaceOfBirth) {
        this.datePlaceOfBirth = datePlaceOfBirth;
    }

    public String getIdentificationNumber() {
        return identificationNumber;
    }

    public void setIdentificationNumber(String identificationNumber) {
        this.identificationNumber = identificationNumber;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public Date getSignatureDate() {
        return signatureDate;
    }

    public void setSignatureDate(Date signatureDate) {
        this.signatureDate = signatureDate;
    }
}
