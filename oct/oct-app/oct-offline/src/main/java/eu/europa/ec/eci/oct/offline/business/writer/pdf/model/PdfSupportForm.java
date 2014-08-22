package eu.europa.ec.eci.oct.offline.business.writer.pdf.model;

import net.sf.jasperreports.engine.JasperReport;

import java.util.Date;
import java.util.List;

/**
 * @author: micleva
 * @created: 12/2/11
 * @project OCT
 */
public class PdfSupportForm {

    private String initiativeId;
    private Date initiativeRegistrationDate;
    private String initiativeTitle;
    private String initiativeSubject;
    private String initiativeDescription;
    private String initiativeSiteUrl;
    private String initiativePublicSiteUrl;
    private String nameOfOrganisers;
    private String contactPersons;
    private Boolean displaySeePartC;

    private String countryName;
    private String allSignatoriesKey;
    // entries to be displayed in the tables
    private List<PdfTableRowData> rowsData;

    private JasperReport tableSubreport;

    public String getInitiativeId() {
        return initiativeId;
    }

    public void setInitiativeId(String initiativeId) {
        this.initiativeId = initiativeId;
    }

    public Date getInitiativeRegistrationDate() {
        return initiativeRegistrationDate;
    }

    public void setInitiativeRegistrationDate(Date initiativeRegistrationDate) {
        this.initiativeRegistrationDate = initiativeRegistrationDate;
    }

    public String getInitiativeTitle() {
        return initiativeTitle;
    }

    public void setInitiativeTitle(String initiativeTitle) {
        this.initiativeTitle = initiativeTitle;
    }

    public String getInitiativeSubject() {
        return initiativeSubject;
    }

    public void setInitiativeSubject(String initiativeSubject) {
        this.initiativeSubject = initiativeSubject;
    }

    public String getInitiativeDescription() {
        return initiativeDescription;
    }

    public void setInitiativeDescription(String initiativeDescription) {
        this.initiativeDescription = initiativeDescription;
    }

    public String getInitiativeSiteUrl() {
        return initiativeSiteUrl;
    }

    public void setInitiativeSiteUrl(String initiativeSiteUrl) {
        this.initiativeSiteUrl = initiativeSiteUrl;
    }

    public String getInitiativePublicSiteUrl() {
        return initiativePublicSiteUrl;
    }

    public void setInitiativePublicSiteUrl(String initiativePublicSiteUrl) {
        this.initiativePublicSiteUrl = initiativePublicSiteUrl;
    }

    public JasperReport getTableSubreport() {
        return tableSubreport;
    }

    public void setTableSubreport(JasperReport tableSubreport) {
        this.tableSubreport = tableSubreport;
    }

    public Boolean getDisplaySeePartC() {
        return displaySeePartC;
    }

    public void setDisplaySeePartC(Boolean displaySeePartC) {
        this.displaySeePartC = displaySeePartC;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public String getAllSignatoriesKey() {
        return allSignatoriesKey;
    }

    public void setAllSignatoriesKey(String allSignatoriesKey) {
        this.allSignatoriesKey = allSignatoriesKey;
    }

    public List<PdfTableRowData> getRowsData() {
        return rowsData;
    }

    public void setRowsData(List<PdfTableRowData> rowsData) {
        this.rowsData = rowsData;
    }

    public String getNameOfOrganisers() {
        return nameOfOrganisers;
    }

    public void setNameOfOrganisers(String nameOfOrganisers) {
        this.nameOfOrganisers = nameOfOrganisers;
    }

    public String getContactPersons() {
        return contactPersons;
    }

    public void setContactPersons(String contactPersons) {
        this.contactPersons = contactPersons;
    }
}
