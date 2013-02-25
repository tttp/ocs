package eu.europa.ec.eci.oct.vo.export;

import java.io.Serializable;
import java.util.Date;

/**
 * POJO style bean for holding export parameters. These parameters include:<br />
 * <ul>
 * <li>country code and country id (we need both for performance issues) for which the export is performed; is these are
 * not set, the export is performed for all countries within the system</li>
 * <li>start and end date - if these are not specified, the export is performed regardless of the signature date</li>
 * <li>start and offset - these will be populated later on by the business layer - they should'nt be populated directly
 * (it has no effect)</li>
 * <li>the language in which the initiative was consulted upon signing it - shouldn't be manually populated as it has no
 * effect</li>
 * </ul>
 * 
 * @author chiridl
 * 
 */
public class ExportParametersBean implements Serializable {
	private static final long serialVersionUID = 5580994609314800138L;

	private int start;
	private int offset;
	private Date startDate;
	private Date endDate;
	private long countryId = -1;
	private String countryCode = "";
	private long descriptionLanguageId = -1;
	private String descriptionLanguageCode = "";

	public int getStart() {
		return start;
	}

	public void setStart(int start) {
		this.start = start;
	}

	public int getOffset() {
		return offset;
	}

	public void setOffset(int offset) {
		this.offset = offset;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public long getCountryId() {
		return countryId;
	}

	public void setCountryId(long countryId) {
		this.countryId = countryId;
	}

	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}

	public String getCountryCode() {
		return countryCode;
	}

	public void setDescriptionLanguageCode(String descriptionLanguageCode) {
		this.descriptionLanguageCode = descriptionLanguageCode;
	}

	public String getDescriptionLanguageCode() {
		return descriptionLanguageCode;
	}

	public void setDescriptionLanguageId(long descriptionLanguageId) {
		this.descriptionLanguageId = descriptionLanguageId;
	}

	public long getDescriptionLanguageId() {
		return descriptionLanguageId;
	}

	@Override
	public String toString() {
		return "ExportParametersBean [start=" + start + ", offset=" + offset + ", startDate=" + startDate
				+ ", endDate=" + endDate + ", countryId=" + countryId + ", countryCode=" + countryCode
				+ ", descriptionLanguageId=" + descriptionLanguageId + ", descriptionLanguageCode="
				+ descriptionLanguageCode + "]";
	}

}
