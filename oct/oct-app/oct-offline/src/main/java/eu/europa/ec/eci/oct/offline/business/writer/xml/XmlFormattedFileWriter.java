package eu.europa.ec.eci.oct.offline.business.writer.xml;

import eu.europa.ec.eci.export.DataException;
import eu.europa.ec.eci.export.JAXBHelper;
import eu.europa.ec.eci.export.model.SupportForm;
import eu.europa.ec.eci.oct.offline.business.DecryptConstants;
import eu.europa.ec.eci.oct.offline.business.writer.FormattedFileWriter;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;

/**
 * @author: micleva
 * @created: 12/2/11
 * @project OCT
 */
public class XmlFormattedFileWriter implements FormattedFileWriter {

    @Override
    public void writeToOutput(SupportForm supportForm, File outputFile) throws DataException {
        try {

            OutputStreamWriter out = new OutputStreamWriter(new FileOutputStream(outputFile), DecryptConstants.CHARACTER_ENCODING);

            JAXBHelper jaxbHelper = JAXBHelper.getInstance();
            jaxbHelper.marshall(supportForm, out);
        } catch (Exception e) {
            throw new DataException("Unable to write the output file: " + outputFile.getAbsolutePath(), e);
        }
    }
}
