package eu.europa.ec.eci.oct.offline.business.writer.pdf;

import com.lowagie.text.pdf.BaseFont;
import eu.europa.ec.eci.export.DataException;
import eu.europa.ec.eci.export.model.SupportForm;
import eu.europa.ec.eci.oct.offline.business.DecryptConstants;
import eu.europa.ec.eci.oct.offline.business.writer.FormattedFileWriter;
import eu.europa.ec.eci.oct.offline.business.writer.pdf.converter.CountrySupportFormConverter;
import eu.europa.ec.eci.oct.offline.business.writer.pdf.model.PdfSupportForm;
import eu.europa.ec.eci.oct.offline.support.log.OfflineCryptoToolLogger;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.FontKey;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.export.PdfFont;

import java.io.File;
import java.io.FileOutputStream;
import java.util.*;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author: micleva
 * @created: 12/2/11
 * @project OCT
 */
public class PdfFormattedFileWriter implements FormattedFileWriter {

    private static OfflineCryptoToolLogger log = OfflineCryptoToolLogger.getLogger(PdfFormattedFileWriter.class.getName());

    private final Lock lock = new ReentrantLock();

    public PdfFormattedFileWriter() {

        JasperReportProvider.initReports();
    }

    @Override
    public void writeToOutput(SupportForm supportForm, File outputFile) throws DataException {
        try {

            CountrySupportFormConverter converter = CountrySupportFormConverter.getInstance();
            PdfSupportForm pdfSupportForm = converter.convertSupportForm(supportForm);

            List<Map<String, PdfSupportForm>> dataBeanList = Collections.singletonList(Collections.singletonMap("backingBean", pdfSupportForm));
            JRBeanCollectionDataSource beanColDataSource = new JRBeanCollectionDataSource(dataBeanList);
            Map<String, Object> parameters = new HashMap<String, Object>();

            String countryCode = supportForm.getForCountry();
            parameters.put(JRParameter.REPORT_LOCALE, new Locale(countryCode, countryCode));

            JasperReport pdfJasperReport = JasperReportProvider.get(JasperReportProvider.STATEMENT_OF_SUPPORT);

            JasperPrint jasperPrint = null;
            try {
                lock.lock();
                jasperPrint = JasperFillManager.fillReport(pdfJasperReport, parameters, beanColDataSource);
            } finally {
                lock.unlock();
            }
            FileOutputStream outputStream = new FileOutputStream(outputFile);

            JRPdfExporter exporter = new JRPdfExporter();

            exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
            exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, outputStream);
            exporter.setParameter(JRExporterParameter.CHARACTER_ENCODING, DecryptConstants.CHARACTER_ENCODING);
            exporter.setParameter(JRExporterParameter.FONT_MAP, getFontMap());


            exporter.exportReport();

            outputStream.flush();
            outputStream.close();
        } catch (Exception e) {
            //do nothing
            log.warning("Unable to write to the PDF file: " + outputFile.getAbsolutePath(), e);
            try {
                if (outputFile.length() == 0) {
                    outputFile.delete();
                }
            } catch (Exception e1) {
                //do nothing
                log.debug("Unable to delete the output file which is empty: " + outputFile.getAbsolutePath(), e);
            }

            throw new DataException("Unable to write the output file, ex: " + e.getClass().getName() + " -> " + e.getMessage(), e);
        }
    }

    public Object getFontMap() {

        HashMap<FontKey, PdfFont> fontMap = new HashMap<FontKey, PdfFont>();
        FontKey key = new FontKey("Arial", false, false);
        FontKey keyBold = new FontKey("Arial", true, false);
        FontKey keyItalic = new FontKey("Arial", false, true);
        FontKey keyBoldItalic = new FontKey("Arial", true, true);
        PdfFont font = new PdfFont("reports/ARIAL.TTF", BaseFont.IDENTITY_H, true);
        PdfFont fontBold = new PdfFont("reports/ARIAL.TTF", BaseFont.IDENTITY_H, true, true, false);
        PdfFont fontItalic = new PdfFont("reports/ARIAL.TTF", BaseFont.IDENTITY_H, true, false, true);
        PdfFont fontBoldItalic = new PdfFont("reports/ARIAL.TTF", BaseFont.IDENTITY_H, true, true, true);
        fontMap.put(key, font);
        fontMap.put(keyBold, fontBold);
        fontMap.put(keyItalic, fontItalic);
        fontMap.put(keyBoldItalic, fontBoldItalic);
        return fontMap;
    }
}
