package eu.europa.ec.eci.oct.validation;

/**
 * 
 * @author Daniel CHIRITA, DIGIT B.1, EC
 * 
 */
public class OCSValidationResult {

	private boolean failed;
	private String reason;
	private boolean canBeSkipped = false;

	public boolean isFailed() {
		return failed;
	}

	public void setFailed(boolean failed) {
		this.failed = failed;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public boolean isCanBeSkipped() {
		return canBeSkipped;
	}

	public void setCanBeSkipped(boolean canBeSkipped) {
		this.canBeSkipped = canBeSkipped;
	}

}
