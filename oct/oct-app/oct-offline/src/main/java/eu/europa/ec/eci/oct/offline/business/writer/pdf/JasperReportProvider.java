package eu.europa.ec.eci.oct.offline.business.writer.pdf;

import eu.europa.ec.eci.oct.offline.support.log.OfflineCryptoToolLogger;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * @author: micleva
 * @created: 12/5/11
 * @project OCT
 */
public class JasperReportProvider {
    private static OfflineCryptoToolLogger log = OfflineCryptoToolLogger.getLogger(JasperReportProvider.class.getName());

    //Templates annexRevision 0
    public static final String STATEMENT_OF_SUPPORT = "reports/annexRevision0/StatementOfSupport.jrxml";
    //part A table sub-reports
	public static final String STATEMENT_PART_A_TYPE_1 = "reports/annexRevision0/StatementOfSupport-PartA-Type1.jrxml";
    public static final String STATEMENT_PART_A_TYPE_2 = "reports/annexRevision0/StatementOfSupport-PartA-Type2.jrxml";
    public static final String STATEMENT_PART_A_TYPE_3 = "reports/annexRevision0/StatementOfSupport-PartA-Type3.jrxml";
    public static final String STATEMENT_PART_A_TYPE_4 = "reports/annexRevision0/StatementOfSupport-PartA-Type4.jrxml";
    //part B table sub-reports
    public static final String STATEMENT_PART_B_TYPE_1 = "reports/annexRevision0/StatementOfSupport-PartB-Type1.jrxml";
    public static final String STATEMENT_PART_B_TYPE_2 = "reports/annexRevision0/StatementOfSupport-PartB-Type2.jrxml";
    public static final String STATEMENT_PART_B_TYPE_3 = "reports/annexRevision0/StatementOfSupport-PartB-Type3.jrxml";
    public static final String STATEMENT_PART_B_TYPE_4 = "reports/annexRevision0/StatementOfSupport-PartB-Type4.jrxml";
    public static final String STATEMENT_PART_B_TYPE_5 = "reports/annexRevision0/StatementOfSupport-PartB-Type5.jrxml";
    public static final String STATEMENT_PART_B_TYPE_6 = "reports/annexRevision0/StatementOfSupport-PartB-Type6.jrxml";
    public static final String STATEMENT_PART_B_TYPE_7 = "reports/annexRevision0/StatementOfSupport-PartB-Type7.jrxml";
    public static final String STATEMENT_PART_B_TYPE_8 = "reports/annexRevision0/StatementOfSupport-PartB-Type8.jrxml";
    public static final String STATEMENT_PART_B_TYPE_9 = "reports/annexRevision0/StatementOfSupport-PartB-Type9.jrxml";
    public static final String STATEMENT_PART_B_TYPE_10 = "reports/annexRevision0/StatementOfSupport-PartB-Type10.jrxml";
    public static final String STATEMENT_PART_B_TYPE_11 = "reports/annexRevision0/StatementOfSupport-PartB-Type11.jrxml";

    //Templates annexRevision 1
    public static final String STATEMENT_OF_SUPPORT_ANNEX_1 = "reports/annexRevision1/StatementOfSupport.jrxml";
    //part A table sub-reports
	public static final String STATEMENT_PART_A_TYPE_1_ANNEX_1 = "reports/annexRevision1/StatementOfSupport-PartA-Type1.jrxml";
    public static final String STATEMENT_PART_A_TYPE_2_ANNEX_1 = "reports/annexRevision1/StatementOfSupport-PartA-Type2.jrxml";
    public static final String STATEMENT_PART_A_TYPE_3_ANNEX_1 = "reports/annexRevision1/StatementOfSupport-PartA-Type3.jrxml";
    public static final String STATEMENT_PART_A_TYPE_4_ANNEX_1 = "reports/annexRevision1/StatementOfSupport-PartA-Type4.jrxml";
    //part B table sub-reports
    public static final String STATEMENT_PART_B_TYPE_1_ANNEX_1  = "reports/annexRevision1/StatementOfSupport-PartB-Type1.jrxml";
    public static final String STATEMENT_PART_B_TYPE_2_ANNEX_1  = "reports/annexRevision1/StatementOfSupport-PartB-Type2.jrxml";
    public static final String STATEMENT_PART_B_TYPE_3_ANNEX_1  = "reports/annexRevision1/StatementOfSupport-PartB-Type3.jrxml";
    public static final String STATEMENT_PART_B_TYPE_3_LT_ANNEX_1  = "reports/annexRevision1/StatementOfSupport-PartB-Type3-LT.jrxml";
    public static final String STATEMENT_PART_B_TYPE_3_HU_ANNEX_1  = "reports/annexRevision1/StatementOfSupport-PartB-Type3-HU.jrxml";

    public static final String STATEMENT_PART_B_TYPE_4_ANNEX_1  = "reports/annexRevision1/StatementOfSupport-PartB-Type4.jrxml";
    public static final String STATEMENT_PART_B_TYPE_4_RO_ANNEX_1  = "reports/annexRevision1/StatementOfSupport-PartB-Type4-RO.jrxml";
    public static final String STATEMENT_PART_B_TYPE_5_ANNEX_1  = "reports/annexRevision1/StatementOfSupport-PartB-Type5.jrxml";
    public static final String STATEMENT_PART_B_TYPE_6_ANNEX_1  = "reports/annexRevision1/StatementOfSupport-PartB-Type6.jrxml";
    public static final String STATEMENT_PART_B_TYPE_7_ANNEX_1  = "reports/annexRevision1/StatementOfSupport-PartB-Type7.jrxml";
    public static final String STATEMENT_PART_B_TYPE_8_ANNEX_1  = "reports/annexRevision1/StatementOfSupport-PartB-Type8.jrxml";
    public static final String STATEMENT_PART_B_TYPE_9_ANNEX_1  = "reports/annexRevision1/StatementOfSupport-PartB-Type9.jrxml";
    public static final String STATEMENT_PART_B_TYPE_9_ES_ANNEX_1  = "reports/annexRevision1/StatementOfSupport-PartB-Type9-ES.jrxml";
    public static final String STATEMENT_PART_B_TYPE_10_ANNEX_1 = "reports/annexRevision1/StatementOfSupport-PartB-Type10.jrxml";
    public static final String STATEMENT_PART_B_TYPE_11_ANNEX_1 = "reports/annexRevision1/StatementOfSupport-PartB-Type11.jrxml";    
    
    private static final Map<String, JasperReport> cachedReports = new HashMap<String, JasperReport>();
    private static final ClassLoader classLoader = JasperReportProvider.class.getClassLoader();

    public static JasperReport get(String jasperReportPath) {
        if (cachedReports.containsKey(jasperReportPath)) {
            return cachedReports.get(jasperReportPath);
        } else {
            return loadAndCache(jasperReportPath);
        }
    }

    private synchronized static JasperReport loadAndCache(String jasperReportPath) {

        /*//load directly the compiled report
        InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream("reports/pdfReport.jasper");
        pdfJasperReport = (JasperReport) JRLoader.loadObject(inputStream);*/

        InputStream inputStream = null;
        JasperReport jasperReport = null;
        try {
            inputStream = classLoader.getResourceAsStream(jasperReportPath);
            JasperDesign jasperDesign = JRXmlLoader.load(inputStream);
            jasperReport = JasperCompileManager.compileReport(jasperDesign);
        } catch (JRException e) {
            throw new IllegalStateException("Unable to initialize the Jasper Report: " + jasperReportPath, e);
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    //do nothing
                    log.debug("Unable to close the JasperReport xml: " + jasperReportPath, e);
                }
            }
            if (jasperReport != null) {
                cachedReports.put(jasperReportPath, jasperReport);
            }
        }
        return jasperReport;
    }

    public static void initReports() {
        //load the statement of report jasper report
        loadAndCache(STATEMENT_OF_SUPPORT);

        //load type A sub-report tables
        loadAndCache(STATEMENT_PART_A_TYPE_1);
        loadAndCache(STATEMENT_PART_A_TYPE_2);
        loadAndCache(STATEMENT_PART_A_TYPE_3);
        loadAndCache(STATEMENT_PART_A_TYPE_4);

        //load type B sub-report tables
        loadAndCache(STATEMENT_PART_B_TYPE_1);
        loadAndCache(STATEMENT_PART_B_TYPE_2);
        loadAndCache(STATEMENT_PART_B_TYPE_3);
        loadAndCache(STATEMENT_PART_B_TYPE_4);
        loadAndCache(STATEMENT_PART_B_TYPE_5);
        loadAndCache(STATEMENT_PART_B_TYPE_6);
        loadAndCache(STATEMENT_PART_B_TYPE_7);
        loadAndCache(STATEMENT_PART_B_TYPE_7);
        loadAndCache(STATEMENT_PART_B_TYPE_8);
        loadAndCache(STATEMENT_PART_B_TYPE_9);
        loadAndCache(STATEMENT_PART_B_TYPE_10);
        loadAndCache(STATEMENT_PART_B_TYPE_11);
        
        //load the annexRevision1 templates
        loadAndCache(STATEMENT_OF_SUPPORT_ANNEX_1);
        
        //part A table sub-reports
        loadAndCache(STATEMENT_PART_A_TYPE_1_ANNEX_1);
        loadAndCache(STATEMENT_PART_A_TYPE_2_ANNEX_1);
        loadAndCache(STATEMENT_PART_A_TYPE_3_ANNEX_1);
        loadAndCache(STATEMENT_PART_A_TYPE_4_ANNEX_1);
        //part B table sub-reports
        loadAndCache(STATEMENT_PART_B_TYPE_1_ANNEX_1);
        loadAndCache(STATEMENT_PART_B_TYPE_2_ANNEX_1);
        loadAndCache(STATEMENT_PART_B_TYPE_3_ANNEX_1);
        loadAndCache(STATEMENT_PART_B_TYPE_3_LT_ANNEX_1);
        loadAndCache(STATEMENT_PART_B_TYPE_3_HU_ANNEX_1);
        loadAndCache(STATEMENT_PART_B_TYPE_4_ANNEX_1);
        loadAndCache(STATEMENT_PART_B_TYPE_4_RO_ANNEX_1);
        loadAndCache(STATEMENT_PART_B_TYPE_5_ANNEX_1);
        loadAndCache(STATEMENT_PART_B_TYPE_6_ANNEX_1);
        loadAndCache(STATEMENT_PART_B_TYPE_7_ANNEX_1);
        loadAndCache(STATEMENT_PART_B_TYPE_8_ANNEX_1);
        loadAndCache(STATEMENT_PART_B_TYPE_9_ANNEX_1);
        loadAndCache(STATEMENT_PART_B_TYPE_9_ES_ANNEX_1);
        loadAndCache(STATEMENT_PART_B_TYPE_10_ANNEX_1);
        loadAndCache(STATEMENT_PART_B_TYPE_11_ANNEX_1);
        
    }
}
