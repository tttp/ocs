package eu.europa.ec.eci.oct.entities;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import eu.europa.ec.eci.oct.entities.rules.GlobalValidationRule;

@Entity
@Table(name = "OCT_PROPERTY")
public class Property {

	@Id
	private Long id;

	@Column(unique = true, nullable = false, insertable = false, length = 64)
	private String name;

	@ManyToOne
	private PropertyGroup group;

	@Column()
	@Enumerated(EnumType.STRING)
	private PropertyType type;

	@Column(nullable = false)
	private int priority;

	@OneToMany(mappedBy = "property", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private Set<GlobalValidationRule> rules;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public PropertyType getType() {
		return type;
	}

	public void setType(PropertyType type) {
		this.type = type;
	}

	public Set<GlobalValidationRule> getRules() {
		return rules;
	}

	public void setRules(Set<GlobalValidationRule> rules) {
		this.rules = rules;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public PropertyGroup getGroup() {
		return group;
	}

	public void setGroup(PropertyGroup group) {
		this.group = group;
	}

	public void setPriority(int priority) {
		this.priority = priority;
	}

	public int getPriority() {
		return priority;
	}

}
