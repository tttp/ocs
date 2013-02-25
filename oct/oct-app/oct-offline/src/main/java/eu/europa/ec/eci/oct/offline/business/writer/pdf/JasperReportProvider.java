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

    public static final String STATEMENT_OF_SUPPORT = "reports/StatementOfSupport.jrxml";
    //part A table sub-reports
    public static final String STATEMENT_PART_A_TYPE_1 = "reports/StatementOfSupport-PartA-Type1.jrxml";
    public static final String STATEMENT_PART_A_TYPE_2 = "reports/StatementOfSupport-PartA-Type2.jrxml";
    public static final String STATEMENT_PART_A_TYPE_3 = "reports/StatementOfSupport-PartA-Type3.jrxml";
    public static final String STATEMENT_PART_A_TYPE_4 = "reports/StatementOfSupport-PartA-Type4.jrxml";
    //part B table sub-reports
    public static final String STATEMENT_PART_B_TYPE_1 = "reports/StatementOfSupport-PartB-Type1.jrxml";
    public static final String STATEMENT_PART_B_TYPE_2 = "reports/StatementOfSupport-PartB-Type2.jrxml";
    public static final String STATEMENT_PART_B_TYPE_3 = "reports/StatementOfSupport-PartB-Type3.jrxml";
    public static final String STATEMENT_PART_B_TYPE_4 = "reports/StatementOfSupport-PartB-Type4.jrxml";
    public static final String STATEMENT_PART_B_TYPE_5 = "reports/StatementOfSupport-PartB-Type5.jrxml";
    public static final String STATEMENT_PART_B_TYPE_6 = "reports/StatementOfSupport-PartB-Type6.jrxml";
    public static final String STATEMENT_PART_B_TYPE_7 = "reports/StatementOfSupport-PartB-Type7.jrxml";
    public static final String STATEMENT_PART_B_TYPE_8 = "reports/StatementOfSupport-PartB-Type8.jrxml";
    public static final String STATEMENT_PART_B_TYPE_9 = "reports/StatementOfSupport-PartB-Type9.jrxml";
    public static final String STATEMENT_PART_B_TYPE_10 = "reports/StatementOfSupport-PartB-Type10.jrxml";
    public static final String STATEMENT_PART_B_TYPE_11 = "reports/StatementOfSupport-PartB-Type11.jrxml";

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
    }
}
