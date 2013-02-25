package eu.europa.ec.eci.oct.webcommons.controller;

import java.io.InputStream;

public class ServedFile {

	private InputStream is;

	private String contentType;

	private String fileName;

	public ServedFile(InputStream is, String contentType, String fileName) {
		this.is = is;
		this.contentType = contentType;
		this.fileName = fileName;
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

}
