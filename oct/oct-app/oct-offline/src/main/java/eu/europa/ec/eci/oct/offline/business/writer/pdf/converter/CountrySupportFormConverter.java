package eu.europa.ec.eci.oct.offline.business.writer.pdf.converter;

import eu.europa.ec.eci.export.model.InitiativeDataType;
import eu.europa.ec.eci.export.model.SignatureType;
import eu.europa.ec.eci.export.model.SupportForm;
import eu.europa.ec.eci.oct.offline.business.AnnexRevisionType;
import eu.europa.ec.eci.oct.offline.business.writer.pdf.JasperReportProvider;
import eu.europa.ec.eci.oct.offline.business.writer.pdf.model.PdfSupportForm;
import eu.europa.ec.eci.oct.offline.business.writer.pdf.model.PdfTableRowData;
import eu.europa.ec.eci.oct.offline.business.writer.pdf.translations.PdfTranslationsHelper;
import net.sf.jasperreports.engine.JasperReport;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

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

    public PdfSupportForm convertSupportForm(SupportForm supportForm, Locale locale, AnnexRevisionType annexRevisionType) {
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
        pdfSupportForm.setCountryName(PdfTranslationsHelper.getCountryNameForCountryCodeInLanguage(countryCode, locale, annexRevisionType));

        pdfSupportForm.setAllSignatoriesKey(getString(getAllSignatoriesKey(countryCode, annexRevisionType)));
        pdfSupportForm.setDisplaySeePartC(countryRequiresPartC(countryCode));

        pdfSupportForm.setTableSubreport(getTableSubReport(countryCode, annexRevisionType));

        pdfSupportForm.setRowsData(buildTableRowData(supportForm, locale, countryCode, annexRevisionType));

        return pdfSupportForm;
    }

    private Boolean countryRequiresPartC(String countryCode) {
        return "at,bg,cz,cy,lt,hr,hu,fr,ro,el,it,lu,mt,pt,es,pl,si,se,gr".contains(countryCode);
    }

    private List<PdfTableRowData> buildTableRowData(SupportForm supportForm, Locale locale, String countryCode, AnnexRevisionType annexRevisionType) {
        List<PdfTableRowData> signatureTableData = new ArrayList<PdfTableRowData>();

        for (SignatureType signatureType : supportForm.getSignatures().getSignature()) {

            SignatoryTypeToPdfTableRowDataConverter dataBuilder = new SignatoryTypeToPdfTableRowDataConverter(signatureType, countryCode);

            PdfTableRowData signatureData = dataBuilder.buildPdfTableRowData(locale, annexRevisionType);
            signatureTableData.add(signatureData);
        }

        return signatureTableData;
    }

//    protected String getAllSignatoriesKey(String countryCode) {
//        if ("ie,nl,uk,gb".contains(countryCode)) {
//            return "report.1.label.residents.1";
//        } else if ("ee,fi,sk".contains(countryCode)) {
//            return "report.1.label.residents.2";
//        } else if ("be,dk,de".contains(countryCode)) {
//            return "report.1.label.residents.3";
//        } else {
//            return "report.1.label.allSignatories";
//        }
//    }

    protected String getAllSignatoriesKey(String countryCode, AnnexRevisionType annexRevisionType) {
    	
    	if(annexRevisionType == AnnexRevisionType.ANNEX_REVISION_0_INITIAL_RELEASE){
          if ("ie,nl,uk,gb".contains(countryCode)) {
	          return "report.1.label.residents.1";
	      } else if ("ee,fi,sk".contains(countryCode)) {
	          return "report.1.label.residents.2";
	      } else if ("be,dk,de".contains(countryCode)) {
	          return "report.1.label.residents.3";
	      } else {
	          return "report.1.label.allSignatories";
	      }
    	} else {
            if ("ie,uk,gb".contains(countryCode)) {
    			// "All signatories on this form are residents in:"
                return "report.1.label.residents.1";
            } else if ("ee,nl,fi,sk".contains(countryCode)) {
    			// "All signatories on this form are residents in or citizens of:"
                return "report.1.label.residents.2";
            } else if ("be,dk,de,lu".contains(countryCode)) {
    			// "All signatories on this form are residents in or citizens of (citizens living abroad only if they have informed their national authorities about their place of residence):"
                return "report.1.label.residents.3";
    		} else if ("bg,hr,lv,lt,pl,si,se".contains(countryCode)) {
    			// "All signatories on this form hold personal identification numbers of:"
    			return "report.1.label.allSignatories.2";
    		} else if ("pt,at,mt,cy,it,fr,gr,cz".contains(countryCode)) {
    			// "All signatories on this form hold personal identification document numbers of:"
    			return "report.1.label.allSignatories.3";
    		} else {
    			// hu,ro,es
    			// "All signatories on this form hold personal identification numbers / personal identification document numbers of:"
    			return "report.1.label.allSignatories";
    		}
    	}
    }


    protected JasperReport getTableSubReport(String countryCode, AnnexRevisionType annexRevisionType) {
        String tableSubReportPath = null;
        if(annexRevisionType == AnnexRevisionType.ANNEX_REVISION_0_INITIAL_RELEASE){
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
            } else if ("es,pl,hr".contains(countryCode)) {
                tableSubReportPath = JasperReportProvider.STATEMENT_PART_B_TYPE_10;
            } else if ("si,se".contains(countryCode)) {
                tableSubReportPath = JasperReportProvider.STATEMENT_PART_B_TYPE_11;
            }
            
/**
 * ANNEX_REVISION_1_ANNEX_III
 * ANNEX_REVISION_1_ANNEX_III             
 * ANNEX_REVISION_1_ANNEX_III             
 */
            
        }else if(annexRevisionType == AnnexRevisionType.ANNEX_REVISION_1_ANNEX_III){
            // Member States using Part A format
            if (("uk,gb,ie").contains(countryCode)) {
                tableSubReportPath = JasperReportProvider.STATEMENT_PART_A_TYPE_1_ANNEX_1;
            } else if ("ee,be,de,dk,lu".contains(countryCode)) {
                tableSubReportPath = JasperReportProvider.STATEMENT_PART_A_TYPE_2_ANNEX_1;
            } else if ("nl,sk".contains(countryCode)) {
                tableSubReportPath = JasperReportProvider.STATEMENT_PART_A_TYPE_3_ANNEX_1;
            } else if (countryCode.equals("fi")) {
                tableSubReportPath = JasperReportProvider.STATEMENT_PART_A_TYPE_4_ANNEX_1;

                // Member States using Part B format
            } else if (countryCode.equals("at")) {
                tableSubReportPath = JasperReportProvider.STATEMENT_PART_B_TYPE_1_ANNEX_1;
            } else if (countryCode.equals("bg")) {
                tableSubReportPath = JasperReportProvider.STATEMENT_PART_B_TYPE_2_ANNEX_1;
            } else if ("cz,cy".contains(countryCode)) {
                tableSubReportPath = JasperReportProvider.STATEMENT_PART_B_TYPE_3_ANNEX_1;
            } else if ("lt".equals(countryCode)) {
                tableSubReportPath = JasperReportProvider.STATEMENT_PART_B_TYPE_3_LT_ANNEX_1;
            } else if ("hu".equals(countryCode)) {
                tableSubReportPath = JasperReportProvider.STATEMENT_PART_B_TYPE_3_HU_ANNEX_1;
            } else if ("fr".equals(countryCode)) {
                tableSubReportPath = JasperReportProvider.STATEMENT_PART_B_TYPE_4_ANNEX_1;
            } else if ("ro".equals(countryCode)) {
                tableSubReportPath = JasperReportProvider.STATEMENT_PART_B_TYPE_4_RO_ANNEX_1;
            } else if (countryCode.equals("el")) {
                tableSubReportPath = JasperReportProvider.STATEMENT_PART_B_TYPE_5_ANNEX_1;
            } else if (countryCode.equals("it")) {
                tableSubReportPath = JasperReportProvider.STATEMENT_PART_B_TYPE_6_ANNEX_1;
            } else if (countryCode.equals("lv")) {
                tableSubReportPath = JasperReportProvider.STATEMENT_PART_B_TYPE_7_ANNEX_1;
            } else if ("mt,pt".contains(countryCode)) {
                tableSubReportPath = JasperReportProvider.STATEMENT_PART_B_TYPE_9_ANNEX_1;
            } else if (countryCode.equals("es")) {
                tableSubReportPath = JasperReportProvider.STATEMENT_PART_B_TYPE_9_ES_ANNEX_1;
            } else if ("pl,hr".contains(countryCode)) {
                tableSubReportPath = JasperReportProvider.STATEMENT_PART_B_TYPE_10_ANNEX_1;
            } else if ("si,se".contains(countryCode)) {
                tableSubReportPath = JasperReportProvider.STATEMENT_PART_B_TYPE_11_ANNEX_1;
            }
        }
        return tableSubReportPath != null ? JasperReportProvider.get(tableSubReportPath) : null;
    }
}
