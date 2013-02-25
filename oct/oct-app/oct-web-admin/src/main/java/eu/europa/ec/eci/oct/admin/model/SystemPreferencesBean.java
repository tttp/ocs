package eu.europa.ec.eci.oct.admin.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import eu.europa.ec.eci.oct.entities.admin.InitiativeDescription;
import eu.europa.ec.eci.oct.entities.member.Language;
import eu.europa.ec.eci.oct.webcommons.model.SimpleBean;

public class SystemPreferencesBean extends SimpleBean {

	private String title;
	private String subjectMatter;
	private String objectives;
	
	private String registerUrl;
	private Date registrationDate;
	private String registrationNumber;
	private Language language;
	private List<Language> languages = new ArrayList<Language>();
	
	private String organizers;
	private String contactPerson;
	private String contactEmail;
	private String website;
	
	private byte[] xmlFile;
	
	private Date uploadDate;
	
	private String lvTitle;
	private String lvSubjectMatter;
	private String lvObjectives;
	private String lvWebsite;
	private Language lvLanguage;
	
	private List<InitiativeDescription> languageVersions;

	public String getRegisterUrl() {
		return registerUrl;
	}

	public void setRegisterUrl(String registerUrl) {
		this.registerUrl = registerUrl;
	}

	public String getRegistrationNumber() {
		return registrationNumber;
	}

	public void setRegistrationNumber(String registrationNumber) {
		this.registrationNumber = registrationNumber;
	}

	public void setRegistrationDate(Date registrationDate) {
		this.registrationDate = registrationDate;
	}

	public Date getRegistrationDate() {
		return registrationDate;
	}	

	public String getOrganizers() {
		return organizers;
	}

	public void setOrganizers(String organizers) {
		this.organizers = organizers;
	}

	public String getContactPerson() {
		return contactPerson;
	}

	public void setContactPerson(String contactPerson) {
		this.contactPerson = contactPerson;
	}

	public String getContactEmail() {
		return contactEmail;
	}

	public void setContactEmail(String contactEmail) {
		this.contactEmail = contactEmail;
	}

	public String getWebsite() {
		return website;
	}

	public void setWebsite(String website) {
		this.website = website;
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

	public String getObjectives() {
		return objectives;
	}

	public void setObjectives(String objectives) {
		this.objectives = objectives;
	}

	public byte[] getXmlFile() {
		return xmlFile;
	}

	public void setXmlFile(byte[] xmlFile) {
		this.xmlFile = xmlFile;
	}

	public Language getLanguage() {
		return language;
	}

	public void setLanguage(Language language) {
		this.language = language;
	}

	public List<Language> getLanguages() {
		return languages;
	}

	public void setLanguages(List<Language> languages) {
		this.languages = languages;
	}

	public Date getUploadDate() {
		return uploadDate;
	}

	public void setUploadDate(Date uploadDate) {
		this.uploadDate = uploadDate;
	}

	public String getLvTitle() {
		return lvTitle;
	}

	public void setLvTitle(String lvTitle) {
		this.lvTitle = lvTitle;
	}

	public String getLvSubjectMatter() {
		return lvSubjectMatter;
	}

	public void setLvSubjectMatter(String lvSubjectMatter) {
		this.lvSubjectMatter = lvSubjectMatter;
	}

	public String getLvObjectives() {
		return lvObjectives;
	}

	public void setLvObjectives(String lvObjectives) {
		this.lvObjectives = lvObjectives;
	}

	public Language getLvLanguage() {
		return lvLanguage;
	}

	public void setLvLanguage(Language lvLanguage) {
		this.lvLanguage = lvLanguage;
	}

	public String getLvWebsite() {
		return lvWebsite;
	}

	public void setLvWebsite(String lvWebsite) {
		this.lvWebsite = lvWebsite;
	}

	public List<InitiativeDescription> getLanguageVersions() {
		return languageVersions;
	}

	public void setLanguageVersions(List<InitiativeDescription> languageVersions) {
		this.languageVersions = languageVersions;
	}

}