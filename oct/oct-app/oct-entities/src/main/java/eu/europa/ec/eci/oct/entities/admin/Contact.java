package eu.europa.ec.eci.oct.entities.admin;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import eu.europa.ec.eci.oct.entities.AuditableEntity;

@Entity
@Table(name = "OCT_CONTACT")
public class Contact extends AuditableEntity{

	@SequenceGenerator(name = "OCT_CTSEQ", sequenceName = "OCT_CTSEQ", allocationSize = 1)
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "OCT_CTSEQ")
	private Long id;

	@Column(nullable = true, length = 2000)
	private String organizers;

	@Column(nullable = true, length = 2000)
	private String name;

	@Column(nullable = true, length = 2000)
	private String email;

	@ManyToOne
	private SystemPreferences system;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getOrganizers() {
		return organizers;
	}

	public void setOrganizers(String organizers) {
		this.organizers = organizers;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public SystemPreferences getSystem() {
		return system;
	}

	public void setSystem(SystemPreferences system) {
		this.system = system;
	}

}
