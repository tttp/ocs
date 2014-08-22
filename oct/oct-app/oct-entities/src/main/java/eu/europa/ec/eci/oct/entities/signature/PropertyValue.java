package eu.europa.ec.eci.oct.entities.signature;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import eu.europa.ec.eci.oct.entities.CountryProperty;

@Entity
@Table(name = "OCT_PROPERTY_VALUE")
public class PropertyValue {

	@SequenceGenerator(name = "OCT_PRVLSEQ", sequenceName = "OCT_PRVLSEQ", allocationSize = 1)
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "OCT_PRVLSEQ")
	private Long id;

	@Column(length = 2048)
	private String value;

	@ManyToOne
	private CountryProperty property;

	@ManyToOne
	private Signature signature;

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public CountryProperty getProperty() {
		return property;
	}

	public void setProperty(CountryProperty property) {
		this.property = property;
	}

	public Signature getSignature() {
		return signature;
	}

	public void setSignature(Signature signature) {
		this.signature = signature;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

}
