package eu.europa.ec.eci.oct.offline.business.writer.pdf;

import com.lowagie.text.pdf.BaseFont;
import eu.europa.ec.eci.export.DataException;
import eu.europa.ec.eci.export.model.SupportForm;
import eu.europa.ec.eci.oct.offline.business.DecryptConstants;
import eu.europa.ec.eci.oct.offline.business.writer.FormattedFileWriter;
import eu.europa.ec.eci.oct.offline.business.writer.pdf.converter.CountrySupportFormConverter;
import eu.europa.ec.eci.oct.offline.business.writer.pdf.model.PdfSupportForm;
import eu.europa.ec.eci.oct.offline.support.localization.UTF8Control;
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

            //pass the resource bundle to be used as a parameter. Specifying it in teh jrxml file
            // will cause the resource bundle to be loaded without an UTF8 control and encoding problems appear
            List<Locale> localeList = PdfTranslations.getAvailableLocalesByCountryCode(countryCode);
            ResourceBundle resourceBundle = getResourceBundleByLocales(localeList);
            parameters.put(JRParameter.REPORT_RESOURCE_BUNDLE, resourceBundle);

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

    private ResourceBundle getResourceBundleByLocales(List<Locale> localeList) {
        for (Locale locale : localeList) {
            try {
                ResourceBundle resourceBundle = ResourceBundle.getBundle("reports/translations/report-translations", locale, new UTF8Control());
                if(resourceBundle.getLocale().getLanguage().equals(locale.getLanguage())) {
                    // a matching resource bundle has been found
                    return resourceBundle;
                }
            } catch (Exception e) {
                //ignore it for now
            }
        }
        //as a default, load the english locale..
        return ResourceBundle.getBundle("reports/translations/report-translations", Locale.ENGLISH, new UTF8Control());
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
