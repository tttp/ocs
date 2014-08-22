package eu.europa.ec.eci.oct.vo.export;

import java.io.Serializable;
import java.util.Date;

/**
 * Simple POJO which unifies all the data necessary for exporting the details of one initiative.
 * 
 * @author chiridl
 * 
 */
public class InitiativeDetails implements Serializable {

	private static final long serialVersionUID = 3712259648716865579L;

	private String registrationNumber;
	private Date registrationDate;
	private String urlOnCommissionRegister;
	private String title;
	private String subjectMatter;
	private String mainObjectives;
	private String organizerList;
	private String contactPersonsList;
	private String url;

	public String getRegistrationNumber() {
		return registrationNumber;
	}

	public void setRegistrationNumber(String registrationNumber) {
		this.registrationNumber = registrationNumber;
	}

	public Date getRegistrationDate() {
		return registrationDate;
	}

	public void setRegistrationDate(Date registrationDate) {
		this.registrationDate = registrationDate;
	}

	public String getUrlOnCommissionRegister() {
		return urlOnCommissionRegister;
	}

	public void setUrlOnCommissionRegister(String urlOnCommissionRegister) {
		this.urlOnCommissionRegister = urlOnCommissionRegister;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getSubjectMatter() {
		return subjectMatter;
	}

	public void setSubjectMatter(String subjectMatter) {
		this.subjectMatter = subjectMatter;
	}

	public String getMainObjectives() {
		return mainObjectives;
	}

	public void setMainObjectives(String mainObjectives) {
		this.mainObjectives = mainObjectives;
	}

	public String getOrganizerList() {
		return organizerList;
	}

	public void setOrganizerList(String organizerList) {
		this.organizerList = organizerList;
	}

	public String getContactPersonsList() {
		return contactPersonsList;
	}

	public void setContactPersonsList(String contactPersonsList) {
		this.contactPersonsList = contactPersonsList;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

}
