package eu.europa.ec.eci.oct.vo.export;

import java.io.Serializable;

public class PropertyValueBean implements Serializable {
	private static final long serialVersionUID = 3987444599415644203L;
	private String name;
	private String groupName;

	public PropertyValueBean(String name, String groupName) {
		this.name = name;
		this.groupName = groupName;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

}
