package eu.europa.ec.eci.oct.entities.signature;

import java.io.Serializable;

import eu.europa.ec.eci.oct.entities.member.Country;

public class SignatureCountPerCountry implements Serializable {

	private static final long serialVersionUID = 5474831828871098213L;
	private Country country;
	private Long count;

	public SignatureCountPerCountry(Country country, Long count) {
		this.country = country;
		this.count = count;
	}

	public Country getCountry() {
		return country;
	}

	public void setCountry(Country country) {
		this.country = country;
	}

	public Long getCount() {
		return count;
	}

	public void setCount(Long count) {
		this.count = count;
	}

}
