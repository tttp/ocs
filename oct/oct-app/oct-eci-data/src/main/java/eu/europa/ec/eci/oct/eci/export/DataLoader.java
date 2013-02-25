package eu.europa.ec.eci.oct.eci.export;

import java.io.Reader;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import eu.europa.ec.eci.export.Export;

public class DataLoader {

	public static Export loadData(Reader r) throws ECIDataException {

		try {
			JAXBContext context = JAXBContext.newInstance(Export.class);
			Unmarshaller um = context.createUnmarshaller();
			Export export = (Export) um.unmarshal(r);
			return export;
		} catch (JAXBException e) {
			throw new ECIDataException("Exception while unmarshalling data", e);
		}
	}
}
