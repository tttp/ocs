package eu.europa.ec.eci.oct.admin.model;

import java.util.Date;

public class SignatureId {
	
	public enum DeletionStatus { SUCCES, FAILURE, TOKEN_NOT_PROVIDED; }

	private String token;
	
	private Date date;
	
	private DeletionStatus deletionStatus;

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public DeletionStatus getDeletionStatus() {
		return deletionStatus;
	}

	public void setDeletionStatus(DeletionStatus deletionStatus) {
		this.deletionStatus = deletionStatus;
	}
	
}
