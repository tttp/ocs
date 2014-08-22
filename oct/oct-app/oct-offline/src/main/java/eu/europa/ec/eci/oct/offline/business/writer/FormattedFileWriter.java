package eu.europa.ec.eci.oct.offline.business.writer;

import eu.europa.ec.eci.export.DataException;
import eu.europa.ec.eci.export.model.SupportForm;
import eu.europa.ec.eci.oct.offline.business.AnnexRevisionType;

import java.io.File;

/**
 * @author: micleva
 * @created: 12/2/11
 * @project OCT
 */
public interface FormattedFileWriter {

    void writeToOutputRelativeToInputPath(SupportForm supportForm, File outputFolder, File fileInSelection, File selectedInput, AnnexRevisionType annexRevisionType) throws DataException;
}
