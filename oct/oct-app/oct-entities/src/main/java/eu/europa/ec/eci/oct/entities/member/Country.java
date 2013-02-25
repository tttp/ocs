package eu.europa.ec.eci.oct.entities.member;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity
@Table(name = "OCT_COUNTRY")
public class Country implements Serializable {

	private static final long serialVersionUID = 6013365546128974053L;

	@Id
	private Long id;

	@Column(unique = true, nullable = false, insertable = false)
	private String name;

	@Column(unique = true, nullable = false, insertable = false)
	private String code;	
	
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "OCT_COUNTRY_LANG", joinColumns = { @JoinColumn(name = "COUNTRY_ID", referencedColumnName = "ID") }, inverseJoinColumns = { @JoinColumn(name = "LANGUAGE_ID", referencedColumnName = "ID") })
	private Set<Language> languages;
	
	private transient String label;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Set<Language> getLanguages() {
		return languages;
	}

	public void setLanguages(Set<Language> languages) {
		this.languages = languages;
	}

	@Override
	public String toString() {
		return new StringBuffer().append(getName()).append(" [").append(getCode()).append("]").toString();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((code == null) ? 0 : code.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object c) {
		if (this == c) {
			return true;
		}
		if (c == null) {
			return false;
		}
		if (!(c instanceof Country)) {
			return false;
		}
		Country country = (Country) c;
		if (code == null) {
			if (country.code != null) {
				return false;
			}
		} else if (!code.equals(country.code)) {
			return false;
		}
		if (id == null) {
			if (country.id != null) {
				return false;
			}
		} else if (!id.equals(country.id)) {
			return false;
		}
		return true;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

}
