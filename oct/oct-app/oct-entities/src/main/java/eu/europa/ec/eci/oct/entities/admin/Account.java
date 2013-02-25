package eu.europa.ec.eci.oct.entities.admin;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name="OCT_ACCOUNT")
public class Account {
	
	@SequenceGenerator(name = "OCT_ACTSEQ", sequenceName = "OCT_ACTSEQ", allocationSize = 1)
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "OCT_ACTSEQ")
	private Long id;

	@Column(unique=true, nullable=false, length=255)	
	private String userName;
	
	@Column(unique=false, nullable=false, length=255)	
	private String passHash;

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassHash() {
		return passHash;
	}

	public void setPassHash(String passHash) {
		this.passHash = passHash;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
}
