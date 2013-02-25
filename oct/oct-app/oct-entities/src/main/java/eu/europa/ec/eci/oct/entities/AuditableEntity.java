package eu.europa.ec.eci.oct.entities;

import java.sql.Timestamp;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

@MappedSuperclass
public abstract class AuditableEntity {

	private Timestamp insertDate;
	 
	private Timestamp updateDate;	
	
	private int version;
	
	@Column(insertable = true, updatable = false)
	public Date getInsertDate() {
		return insertDate;
	}

	public void setInsertDate(Timestamp insertDate) {
		this.insertDate = insertDate;
	}

	@Column(insertable = true, updatable = true)
	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Timestamp updateDate) {
		this.updateDate = updateDate;
	}
	
	@Column	
	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}

	@PrePersist
	public void onCreate() {
		Date now = new Date();		
		this.setInsertDate(new Timestamp(now.getTime()));
		this.setUpdateDate(new Timestamp(now.getTime()));
		this.setVersion(1);
	}
	
	@PreUpdate
	public void onPersist() {
		this.setUpdateDate((new Timestamp(new Date().getTime())));
		this.setVersion(this.getVersion()+1);
	}



	
}
