package eu.europa.ec.eci.oct.entities.admin;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import eu.europa.ec.eci.oct.entities.AuditableEntity;
import eu.europa.ec.eci.oct.entities.member.Language;

@Entity
@Table(name = "OCT_SYSTEM_PREFS")
public class SystemPreferences extends AuditableEntity{

	@Id
	private Long id;

	@OneToOne
	private Language defaultLanguage;

	@Enumerated(EnumType.STRING)
	private SystemState state;
	
	@Column
	private byte collecting;

	@Column(length = 1024)
	private String publicKey;

	@OneToOne
	private InitiativeDescription defaultDescription;

	@Column(unique = true, length = 64)
	private String registrationNumber;

	@Column(unique = true)
	private Date registrationDate;

	@Column(length = 500)
	private String commissionRegisterUrl;
	
	@Column
	private Date eciDataTimestamp;	
	
	@Column(name="CERT_FILE_NAME", unique=true, nullable=true, length=255)
	private String certFileName;
	
	@Column(name="CERT_CONTENT_TYPE", unique=true, nullable=true, length=255)
	private String certContentType;
	
	
	@Column(name="FILE_STORE", length=255)
	private String fileStoragePath;
	

	public Language getDefaultLanguage() {
		return defaultLanguage;
	}

	public void setDefaultLanguage(Language defaultLanguage) {
		this.defaultLanguage = defaultLanguage;
	}

	public SystemState getState() {
		return state;
	}

	public void setState(SystemState state) {
		this.state = state;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getPublicKey() {
		return publicKey;
	}

	public void setPublicKey(String publicKey) {
		this.publicKey = publicKey;
	}

	public InitiativeDescription getDefaultDescription() {
		return defaultDescription;
	}

	public void setDefaultDescription(InitiativeDescription defaultDescription) {
		this.defaultDescription = defaultDescription;
	}

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

	public String getCommissionRegisterUrl() {
		return commissionRegisterUrl;
	}

	public void setCommissionRegisterUrl(String commissionRegisterUrl) {
		this.commissionRegisterUrl = commissionRegisterUrl;
	}

	public boolean isCollecting() {
		return collecting!=0;
	}

	public void setCollecting(boolean collecting) {
		this.collecting = collecting ? (byte) 1 : (byte) 0;
	}

	public Date getEciDataTimestamp() {
		return eciDataTimestamp;
	}

	public void setEciDataTimestamp(Date eciDataTimestamp) {
		this.eciDataTimestamp = eciDataTimestamp;
	}

	public String getCertFileName() {
		return certFileName;
	}

	public void setCertFileName(String certFileName) {
		this.certFileName = certFileName;
	}

	public String getCertContentType() {
		return certContentType;
	}

	public void setCertContentType(String certContentType) {
		this.certContentType = certContentType;
	}

	public String getFileStoragePath() {
		return fileStoragePath;
	}

	public void setFileStoragePath(String fileStoragePath) {
		this.fileStoragePath = fileStoragePath;
	}

}
