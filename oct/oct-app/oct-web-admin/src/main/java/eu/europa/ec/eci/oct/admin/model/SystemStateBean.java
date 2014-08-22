package eu.europa.ec.eci.oct.admin.model;

import eu.europa.ec.eci.oct.webcommons.model.SimpleBean;

public class SystemStateBean extends SimpleBean {

	private static final long serialVersionUID = -6175508872450991500L;

	private String systemState;

	private boolean collectorState;

	private byte[] certFile;

	private boolean goIntoProduction;

	public String getSystemState() {
		return systemState;
	}

	public void setSystemState(String systemState) {
		this.systemState = systemState;
	}

	public boolean isCollectorState() {
		return collectorState;
	}

	public void setCollectorState(boolean collectorState) {
		this.collectorState = collectorState;
	}

	public byte[] getCertFile() {
		return certFile;
	}

	public void setCertFile(byte[] certFile) {
		this.certFile = certFile;
	}

	public boolean isGoIntoProduction() {
		return goIntoProduction;
	}

	public void setGoIntoProduction(boolean goIntoProduction) {
		this.goIntoProduction = goIntoProduction;
	}

}
