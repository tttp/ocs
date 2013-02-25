package eu.europa.ec.eci.oct.vo.export;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class ExportSignatureBean {

	private long id;
	private Date date;
	private String uuid;

	private Map<String, Map<String, String>> groups = new HashMap<String, Map<String, String>>();

	public ExportSignatureBean(long id, Date date, String uuid) {
		this.id = id;
		this.date = date;
		this.uuid = uuid;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getId() {
		return id;
	}

	public Map<String, Map<String, String>> getGroups() {
		return groups;
	}

	public void setGroups(Map<String, Map<String, String>> groups) {
		this.groups = groups;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getUuid() {
		return uuid;
	}

}
