package eu.europa.ec.eci.oct.admin.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import eu.europa.ec.eci.oct.entities.member.Country;
import eu.europa.ec.eci.oct.webcommons.model.SimpleBean;

public class ExportBean extends SimpleBean {

	private List<Country> countries = new ArrayList<Country>();

	private Country country;

	private Date startDate;

	private Date endDate;

	private List<SignatureId> signatureIds = new ArrayList<SignatureId>();

	private Date registrationDate;
	
	public ExportBean(){
	}
	

	public Country getCountry() {
		return country;
	}

	public void setCountry(Country country) {
		this.country = country;
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

	public List<Country> getCountries() {
		return countries;
	}

	public void setCountries(List<Country> countries) {
		this.countries = countries;
	}
	
	public void setRegistrationDate(Date registrationDate) {
		this.registrationDate = registrationDate;
	}

	public Date getRegistrationDate() {
		return registrationDate;
	}

	public List<SignatureId> getSignatureIds() {
		return signatureIds;
	}

	public void setSignatureIds(List<SignatureId> signatureIds) {
		this.signatureIds = signatureIds;
	}


}
