package eu.europa.ec.eci.oct.entities.admin;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import eu.europa.ec.eci.oct.entities.AuditableEntity;
import eu.europa.ec.eci.oct.entities.member.Language;

@Entity
@Table(name="OCT_INITIATIVE_DESC")
public class InitiativeDescription extends AuditableEntity implements Serializable {
	
	private static final long serialVersionUID = 2462518290220931962L;

	@SequenceGenerator(name = "OCT_INDSSEQ", sequenceName = "OCT_INDSSEQ", allocationSize = 1)
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "OCT_INDSSEQ")
	private Long id;		

	@Column(nullable=false, length=1000)	
	private String title;
	
	@Column(nullable=true, length=2000)	
	private String subjectMatter;
	
	@Column(nullable=true, length=4000)	
	private String objectives;	
	
	@Column(nullable=true, length=100)	
	private String url;	
		
	@OneToOne(optional=false)
	private Language language;

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

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

}
