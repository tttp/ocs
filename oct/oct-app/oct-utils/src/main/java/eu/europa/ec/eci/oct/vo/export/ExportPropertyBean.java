package eu.europa.ec.eci.oct.vo.export;

public class ExportPropertyBean {

	private String value;
	private long propertyId;
	private long signatureId;

	public ExportPropertyBean(String value, long propertyId, long signatureId) {
		this.value = value;
		this.propertyId = propertyId;
		this.signatureId = signatureId;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public long getPropertyId() {
		return propertyId;
	}

	public void setPropertyId(long propertyId) {
		this.propertyId = propertyId;
	}

	public long getSignatureId() {
		return signatureId;
	}

	public void setSignatureId(long signatureId) {
		this.signatureId = signatureId;
	}

}
