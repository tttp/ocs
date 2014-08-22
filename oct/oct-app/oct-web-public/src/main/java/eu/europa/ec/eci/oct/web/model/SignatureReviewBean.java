package eu.europa.ec.eci.oct.web.model;

import java.io.Serializable;

public class SignatureReviewBean implements Serializable {

	private static final long serialVersionUID = 2628622531089527603L;
	private String uuid;
	private String date;

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

}
