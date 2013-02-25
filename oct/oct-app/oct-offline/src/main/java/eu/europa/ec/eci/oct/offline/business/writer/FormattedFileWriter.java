package eu.europa.ec.eci.oct.offline.business.writer;

import eu.europa.ec.eci.export.DataException;
import eu.europa.ec.eci.export.model.SupportForm;

import java.io.File;

/**
 * @author: micleva
 * @created: 12/2/11
 * @project OCT
 */
public interface FormattedFileWriter {

    public void writeToOutput(SupportForm supportForm, File outputFile) throws DataException;
}
