package eu.europa.ec.eci.oct.offline.business.writer.pdf.converter;

import eu.europa.ec.eci.export.model.InitiativeDataType;
import eu.europa.ec.eci.export.model.SignatureType;
import eu.europa.ec.eci.export.model.SupportForm;
import eu.europa.ec.eci.oct.offline.business.writer.pdf.JasperReportProvider;
import eu.europa.ec.eci.oct.offline.business.writer.pdf.PdfTranslations;
import eu.europa.ec.eci.oct.offline.business.writer.pdf.model.PdfSupportForm;
import eu.europa.ec.eci.oct.offline.business.writer.pdf.model.PdfTableRowData;
import net.sf.jasperreports.engine.JasperReport;

import java.util.ArrayList;
import java.util.List;

import static eu.europa.ec.eci.oct.offline.business.writer.pdf.converter.NullSafeConverter.getDate;
import static eu.europa.ec.eci.oct.offline.business.writer.pdf.converter.NullSafeConverter.getString;

/**
 * @author: micleva
 * @created: 12/2/11
 * @project OCT
 */
public class CountrySupportFormConverter {

    private static CountrySupportFormConverter instance = new CountrySupportFormConverter();

    public static CountrySupportFormConverter getInstance() {
        return instance;
    }

    public PdfSupportForm convertSupportForm(SupportForm supportForm) {
        PdfSupportForm pdfSupportForm = new PdfSupportForm();

        InitiativeDataType initiativeData = supportForm.getInitiativeData();

        pdfSupportForm.setInitiativeId(getString(initiativeData.getRegistrationNumber()));
        pdfSupportForm.setInitiativeRegistrationDate(getDate(initiativeData.getRegistrationDate()));
        pdfSupportForm.setInitiativeTitle(getString(initiativeData.getTitle()));
        pdfSupportForm.setInitiativeSubject(getString(initiativeData.getSubjectMatter()));
        pdfSupportForm.setInitiativeDescription(getString(initiativeData.getMainObjectives()));

        pdfSupportForm.setInitiativeSiteUrl(getString(initiativeData.getUrlOnCommissionRegister()));
        pdfSupportForm.setInitiativePublicSiteUrl(getString(initiativeData.getUrl()));

        pdfSupportForm.setNameOfOrganisers(getString(initiativeData.getOrganizerList()));
        pdfSupportForm.setContactPersons(getString(initiativeData.getContactPersonsList()));

        String countryCode = getString(supportForm.getForCountry());
        pdfSupportForm.setCountryName(PdfTranslations.getCountryNameForCountryCodeInLanguage(countryCode, countryCode).toUpperCase());

        pdfSupportForm.setAllSignatoriesKey(getString(getAllSignatoriesKey(countryCode)));
        pdfSupportForm.setDisplaySeePartC(countryRequiresPartC(countryCode));

        pdfSupportForm.setTableSubreport(getTableSubReport(countryCode));

        pdfSupportForm.setRowsData(buildTableRowData(supportForm, countryCode));

        return pdfSupportForm;
    }

    private Boolean countryRequiresPartC(String countryCode) {
        return "at,bg,cz,cy,lt,hu,fr,ro,el,it,lu,mt,pt,es,pl,si,se,gr".contains(countryCode);
    }

    private List<PdfTableRowData> buildTableRowData(SupportForm supportForm, String countryCode) {
        List<PdfTableRowData> signatureTableData = new ArrayList<PdfTableRowData>();

        for (SignatureType signatureType : supportForm.getSignatures().getSignature()) {

            SignatoryTypeToPdfTableRowDataConverter dataBuilder = new SignatoryTypeToPdfTableRowDataConverter(signatureType, countryCode);

            PdfTableRowData signatureData = dataBuilder.buildPdfTableRowData();
            signatureTableData.add(signatureData);
        }

        return signatureTableData;
    }

    protected String getAllSignatoriesKey(String countryCode) {
        if ("ie,nl,uk,gb".contains(countryCode)) {
            return "report.1.label.residents.1";
        } else if ("ee,fi,sk".contains(countryCode)) {
            return "report.1.label.residents.2";
        } else if ("be,dk,de".contains(countryCode)) {
            return "report.1.label.residents.3";
        } else {
            return "report.1.label.allSignatories";
        }
    }

    protected JasperReport getTableSubReport(String countryCode) {
        String tableSubReportPath = null;
        // Member States using Part A format
        if (countryCode.equals("uk")) {
            tableSubReportPath = JasperReportProvider.STATEMENT_PART_A_TYPE_1;
        } else if ("ie,ee,be,de,dk".contains(countryCode)) {
            tableSubReportPath = JasperReportProvider.STATEMENT_PART_A_TYPE_2;
        } else if ("nl,sk".contains(countryCode)) {
            tableSubReportPath = JasperReportProvider.STATEMENT_PART_A_TYPE_3;
        } else if (countryCode.equals("fi")) {
            tableSubReportPath = JasperReportProvider.STATEMENT_PART_A_TYPE_4;

            // Member States using Part B format
        } else if (countryCode.equals("at")) {
            tableSubReportPath = JasperReportProvider.STATEMENT_PART_B_TYPE_1;
        } else if (countryCode.equals("bg")) {
            tableSubReportPath = JasperReportProvider.STATEMENT_PART_B_TYPE_2;
        } else if ("cz,cy,lt,hu".contains(countryCode)) {
            tableSubReportPath = JasperReportProvider.STATEMENT_PART_B_TYPE_3;
        } else if ("fr,ro".contains(countryCode)) {
            tableSubReportPath = JasperReportProvider.STATEMENT_PART_B_TYPE_4;
        } else if (countryCode.equals("el")) {
            tableSubReportPath = JasperReportProvider.STATEMENT_PART_B_TYPE_5;
        } else if (countryCode.equals("it")) {
            tableSubReportPath = JasperReportProvider.STATEMENT_PART_B_TYPE_6;
        } else if (countryCode.equals("lv")) {
            tableSubReportPath = JasperReportProvider.STATEMENT_PART_B_TYPE_7;
        } else if (countryCode.equals("lu")) {
            tableSubReportPath = JasperReportProvider.STATEMENT_PART_B_TYPE_8;
        } else if ("mt,pt".contains(countryCode)) {
            tableSubReportPath = JasperReportProvider.STATEMENT_PART_B_TYPE_9;
        } else if ("es,pl".contains(countryCode)) {
            tableSubReportPath = JasperReportProvider.STATEMENT_PART_B_TYPE_10;
        } else if ("si,se".contains(countryCode)) {
            tableSubReportPath = JasperReportProvider.STATEMENT_PART_B_TYPE_11;
        }
        return tableSubReportPath != null ? JasperReportProvider.get(tableSubReportPath) : null;
    }
}
