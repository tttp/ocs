package eu.europa.ec.eci.oct.offline.business.writer;

import eu.europa.ec.eci.oct.offline.business.FileType;
import eu.europa.ec.eci.oct.offline.business.writer.pdf.PdfFormattedFileWriter;
import eu.europa.ec.eci.oct.offline.business.writer.xml.XmlFormattedFileWriter;

import java.util.HashMap;
import java.util.Map;

/**
 * @author: micleva
 * @created: 12/2/11
 * @project OCT
 */
public class FormattedFileWriterProvider {

    private static FormattedFileWriterProvider instance;
    private Map<FileType, FormattedFileWriter> writers;

    private FormattedFileWriterProvider() {
        writers = new HashMap<FileType, FormattedFileWriter>();
        writers.put(FileType.XML, new XmlFormattedFileWriter());
        writers.put(FileType.PDF, new PdfFormattedFileWriter());
    }

    private static FormattedFileWriterProvider getInstance() {
        if (instance == null) {
            instance = new FormattedFileWriterProvider();
        }
        return instance;
    }

    public static FormattedFileWriter getFormattedFileWriter(FileType fileType) {
        FormattedFileWriterProvider instance = FormattedFileWriterProvider.getInstance();
        if (!instance.writers.containsKey(fileType)) {
            throw new UnsupportedOperationException("No writer configured for the file type: " + fileType);
        }
        return instance.writers.get(fileType);
    }
}
