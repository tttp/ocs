package eu.europa.ec.eci.oct.offline.business.writer.xml;

import eu.europa.ec.eci.export.JAXBHelper;
import eu.europa.ec.eci.export.model.SupportForm;
import eu.europa.ec.eci.oct.offline.business.AnnexRevisionType;
import eu.europa.ec.eci.oct.offline.business.DecryptConstants;
import eu.europa.ec.eci.oct.offline.business.FileType;
import eu.europa.ec.eci.oct.offline.business.writer.AbstractFileWriter;

import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.util.List;
import java.util.Locale;

/**
 * @author: micleva
 * @created: 12/2/11
 * @project OCT
 */
public class XmlFormattedFileWriter extends AbstractFileWriter {

    public XmlFormattedFileWriter() {
        super(FileType.XML);
    }

    @Override
    protected void fillUpContent(SupportForm supportForm, FileOutputStream out, Locale locale, AnnexRevisionType annexRevisionType) throws Exception {

        OutputStreamWriter outputStream = new OutputStreamWriter(out, DecryptConstants.CHARACTER_ENCODING);

        JAXBHelper jaxbHelper = JAXBHelper.getInstance();
        jaxbHelper.marshall(supportForm, outputStream);
    }

    @Override
    protected List<Locale> getLinguisticVersions(String countryCode, AnnexRevisionType annexRevisionType) {
        return null;
    }
}
