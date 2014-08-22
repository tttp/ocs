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

import java.io.InputStream;

public class ServedFile {

	private InputStream is;

	private String contentType;

	private String fileName;

	private boolean attachment;

	public ServedFile(InputStream is, String contentType, String fileName, boolean attachment) {
		this.is = is;
		this.contentType = contentType;
		this.fileName = fileName;
		this.attachment = attachment;
	}

	public String getContentType() {
		return contentType;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public InputStream getIs() {
		return is;
	}

	public void setIs(InputStream is) {
		this.is = is;
	}

	public boolean isAttachment() {
		return attachment;
	}

	public void setAttachment(boolean attachment) {
		this.attachment = attachment;
	}

}
