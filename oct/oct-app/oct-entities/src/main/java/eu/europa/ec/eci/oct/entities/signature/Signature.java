package eu.europa.ec.eci.oct.entities.signature;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import eu.europa.ec.eci.oct.entities.AuditableEntity;
import eu.europa.ec.eci.oct.entities.admin.InitiativeDescription;
import eu.europa.ec.eci.oct.entities.member.Country;

@Entity
@Table(name = "OCT_SIGNATURE")
public class Signature extends AuditableEntity {

	@SequenceGenerator(name = "OCT_SGNTSEQ", sequenceName = "OCT_SGNTSEQ", allocationSize = 1)
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "OCT_SGNTSEQ")
	private Long id;

	@Column(nullable = false)
	private String uuid;

	@ManyToOne(optional = false)
	private Country countryToSignFor;

	@Column(nullable = false)
	private Date dateOfSignature;

	@Column(length = 256, nullable = false, unique = true)
	private String fingerprint;

	@ManyToOne(optional = false)
	private InitiativeDescription description;

	@OneToMany(mappedBy = "signature", cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
	private List<PropertyValue> propertyValues;

	@Column
	private Integer annexRevision;

	public Date getDateOfSignature() {
		return dateOfSignature;
	}

	public void setDateOfSignature(Date dateOfSignature) {
		this.dateOfSignature = dateOfSignature;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public List<PropertyValue> getPropertyValues() {
		return propertyValues;
	}

	public void setPropertyValues(List<PropertyValue> propertyValues) {
		this.propertyValues = propertyValues;
	}

	public String getFingerprint() {
		return fingerprint;
	}

	public void setFingerprint(String fingerprint) {
		this.fingerprint = fingerprint;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getUuid() {
		return uuid;
	}

	public void setCountryToSignFor(Country countryToSignFor) {
		this.countryToSignFor = countryToSignFor;
	}

	public Country getCountryToSignFor() {
		return countryToSignFor;
	}

	public void setDescription(InitiativeDescription description) {
		this.description = description;
	}

	public InitiativeDescription getDescription() {
		return description;
	}

	public Integer getAnnexRevision() {
		return annexRevision;
	}

	public void setAnnexRevision(Integer annexRevision) {
		this.annexRevision = annexRevision;
	}

}
