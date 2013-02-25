package eu.europa.ec.eci.oct.offline.business.writer.pdf.converter;

import javax.xml.datatype.XMLGregorianCalendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * @author: micleva
 * @created: 12/12/11
 * @project OCT
 */
class NullSafeConverter {

    public static String getString(String input) {
        return input != null ? input.trim() : "";
    }

    public static Long getLong(String input) {
        Long result = 0L;
        if (input != null) {
            String strToParse = input.trim();
            if (strToParse.length() > 0) {
                result = Long.valueOf(strToParse);
            }
        }
        return result;
    }

    public static Date getDate(XMLGregorianCalendar input) {
        Date result = null;
        if (input != null) {
            GregorianCalendar calendar = input.toGregorianCalendar();
            if (calendar != null) {
                result = calendar.getTime();
            }
        }
        return result;
    }
}
