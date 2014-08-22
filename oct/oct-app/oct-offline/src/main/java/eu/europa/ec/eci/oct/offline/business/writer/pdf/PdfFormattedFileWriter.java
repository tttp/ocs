package eu.europa.ec.eci.oct.offline.business.writer.pdf;

import com.lowagie.text.pdf.BaseFont;
import eu.europa.ec.eci.export.model.SupportForm;
import eu.europa.ec.eci.oct.offline.business.AnnexRevisionType;
import eu.europa.ec.eci.oct.offline.business.DecryptConstants;
import eu.europa.ec.eci.oct.offline.business.FileType;
import eu.europa.ec.eci.oct.offline.business.writer.AbstractFileWriter;
import eu.europa.ec.eci.oct.offline.business.writer.pdf.converter.CountrySupportFormConverter;
import eu.europa.ec.eci.oct.offline.business.writer.pdf.model.PdfSupportForm;
import eu.europa.ec.eci.oct.offline.business.writer.pdf.translations.PdfTranslationsHelper;
import eu.europa.ec.eci.oct.offline.support.localization.UTF8Control;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.FontKey;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.export.PdfFont;

import java.io.FileOutputStream;
import java.util.*;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author: micleva
 * @created: 12/2/11
 * @project OCT
 */
public class PdfFormattedFileWriter extends AbstractFileWriter {

    private final Lock lock = new ReentrantLock();

    public PdfFormattedFileWriter() {
        super(FileType.PDF);
        JasperReportProvider.initReports();
    }

    @Override
    protected void fillUpContent(SupportForm supportForm, FileOutputStream out, Locale locale, AnnexRevisionType annexRevisionType) throws Exception {

        CountrySupportFormConverter converter = CountrySupportFormConverter.getInstance();
        PdfSupportForm pdfSupportForm = converter.convertSupportForm(supportForm, locale, annexRevisionType);

        ResourceBundle resourceBundle = getResourceBundleByLocale(locale, annexRevisionType);
        writePdfContent(pdfSupportForm, resourceBundle, out, annexRevisionType);
    }

    private void writePdfContent(PdfSupportForm pdfSupportForm, ResourceBundle resourceBundle, FileOutputStream outputStream, AnnexRevisionType annexRevisionType) throws JRException {

        List<Map<String, PdfSupportForm>> dataBeanList = Collections.singletonList(Collections.singletonMap("backingBean", pdfSupportForm));
        JRBeanCollectionDataSource beanColDataSource = new JRBeanCollectionDataSource(dataBeanList);
        Map<String, Object> parameters = new HashMap<String, Object>();

        parameters.put(JRParameter.REPORT_RESOURCE_BUNDLE, resourceBundle);

        
        
        String jasperReportPath = null;
        if(annexRevisionType == AnnexRevisionType.ANNEX_REVISION_0_INITIAL_RELEASE){
        	jasperReportPath = JasperReportProvider.STATEMENT_OF_SUPPORT;
        }else if(annexRevisionType == AnnexRevisionType.ANNEX_REVISION_1_ANNEX_III){
        	jasperReportPath = JasperReportProvider.STATEMENT_OF_SUPPORT_ANNEX_1;
        }
        JasperReport pdfJasperReport = JasperReportProvider.get(jasperReportPath);

        JasperPrint jasperPrint = null;
        try {
            lock.lock();
            jasperPrint = JasperFillManager.fillReport(pdfJasperReport, parameters, beanColDataSource);
        } finally {
            lock.unlock();
        }

        JRPdfExporter exporter = new JRPdfExporter();

        exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
        exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, outputStream);
        exporter.setParameter(JRExporterParameter.CHARACTER_ENCODING, DecryptConstants.CHARACTER_ENCODING);
        exporter.setParameter(JRExporterParameter.FONT_MAP, getFontMap());


        exporter.exportReport();
    }

    private ResourceBundle getResourceBundleByLocale(Locale locale, AnnexRevisionType annexRevisionType) {
    	String resourcesPath = "reports/annexRevision" + annexRevisionType.getRevisionNumber() + "/translations/report-translations";
        try {
        	
        	
            ResourceBundle resourceBundle = ResourceBundle.getBundle(resourcesPath, locale, new UTF8Control());
            if (resourceBundle.getLocale().getLanguage().equals(locale.getLanguage())) {
                // a matching resource bundle has been found
                return resourceBundle;
            }
        } catch (Exception e) {
            //ignore it for now
        }
        //as a default, load the english locale..
        return ResourceBundle.getBundle(resourcesPath, Locale.ENGLISH, new UTF8Control());
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

    @Override
    protected List<Locale> getLinguisticVersions(String countryCode, AnnexRevisionType annexRevisionType) {
        return PdfTranslationsHelper.getLocaleTranslationsByCountryCode(countryCode, annexRevisionType);
    }
}
