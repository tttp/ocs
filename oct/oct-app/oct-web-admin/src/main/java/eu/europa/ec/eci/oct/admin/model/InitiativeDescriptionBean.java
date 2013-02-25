package eu.europa.ec.eci.oct.admin.model;

import java.util.ArrayList;
import java.util.List;

import eu.europa.ec.eci.oct.entities.member.Language;
import eu.europa.ec.eci.oct.webcommons.model.SimpleBean;

public class InitiativeDescriptionBean extends SimpleBean {

	private Long id;
	private Language language;
	private String title;
	private String subjectMatter;
	private String objectives;
	private boolean defaultDescription;
	private String url;

	private List<Language> languages = new ArrayList<Language>();

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

	public Language getLanguage() {
		return language;
	}

	public void setLanguage(Language language) {
		this.language = language;
	}

	public boolean isDefaultDescription() {
		return defaultDescription;
	}

	public void setDefaultDescription(boolean defaultDescription) {
		this.defaultDescription = defaultDescription;
	}

	public List<Language> getLanguages() {
		return languages;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getId() {
		return id;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

}
