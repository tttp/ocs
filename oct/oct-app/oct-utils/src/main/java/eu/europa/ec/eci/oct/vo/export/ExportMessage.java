package eu.europa.ec.eci.oct.vo.export;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Bean to be used as a transporter of cached properties between queues.
 * 
 * @author chiridl
 * 
 */
public class ExportMessage implements Serializable {

	private static final long serialVersionUID = 4097758538934142914L;

	private Map<Long, PropertyValueBean> propertyCache;

	private ExportParametersBean exportParameters;

	private String exportPath;

	private String exportFileName;

	private Date startDate;

	private Map<Long, Long> countsPerCounttry = new HashMap<Long, Long>();

	private InitiativeDetails initiativeDetails = new InitiativeDetails();

	public void setPropertyCache(Map<Long, PropertyValueBean> propertyCache) {
		this.propertyCache = propertyCache;
	}

	public Map<Long, PropertyValueBean> getPropertyCache() {
		return propertyCache;
	}

	public void setExportParameters(ExportParametersBean exportParameters) {
		this.exportParameters = exportParameters;
	}

	public ExportParametersBean getExportParameters() {
		return exportParameters;
	}

	public void setExportFileName(String exportFileName) {
		this.exportFileName = exportFileName;
	}

	public String getExportFileName() {
		return exportFileName;
	}

	public void setCountsPerCounttry(Map<Long, Long> countsPerCounttry) {
		this.countsPerCounttry = countsPerCounttry;
	}

	public Map<Long, Long> getCountsPerCounttry() {
		return countsPerCounttry;
	}

	public void setExportPath(String exportPath) {
		this.exportPath = exportPath;
	}

	public String getExportPath() {
		return exportPath;
	}

	public void setInitiativeDetails(InitiativeDetails initiativeDetails) {
		this.initiativeDetails = initiativeDetails;
	}

	public InitiativeDetails getInitiativeDetails() {
		return initiativeDetails;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getStartDate() {
		return startDate;
	}

	@Override
	public String toString() {
		return "ExportMessage [exportParameters=" + exportParameters + ", exportPath=" + exportPath
				+ ", exportFileName=" + exportFileName + ", startDate=" + startDate + "]";
	}

}
