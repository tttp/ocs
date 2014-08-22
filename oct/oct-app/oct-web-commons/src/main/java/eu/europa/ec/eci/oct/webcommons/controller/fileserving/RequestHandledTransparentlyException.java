/** ====================================================================
 * Licensed under the European Union Public Licence (EUPL v1.2) 
 * https://joinup.ec.europa.eu/community/eupl/topic/public-consultation-draft-eupl-v12
 * ====================================================================
 *
 * @author Daniel CHIRITA
 * @created: 23/05/2013
 *
 */
package eu.europa.ec.eci.oct.webcommons.controller.fileserving;

import eu.europa.ec.eci.oct.business.api.OCTException;

public class RequestHandledTransparentlyException extends OCTException {

	private static final long serialVersionUID = -2993449264159724594L;

	public RequestHandledTransparentlyException(String message) {
		super(message);
	}

}
