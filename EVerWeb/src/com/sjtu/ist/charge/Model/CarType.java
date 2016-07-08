package com.sjtu.ist.charge.Model;

import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * CarType entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "car_type", catalog = "charge")
public class CarType implements java.io.Serializable {

	// Fields

	private Integer id;
	private String typeName;
	private Set<Stake> stakes = new HashSet<Stake>(0);
	private Set<Car> cars = new HashSet<Car>(0);

	// Constructors

	/** default constructor */
	public CarType() {
	}

	/** full constructor */
	public CarType(String typeName, Set<Stake> stakes, Set<Car> cars) {
		this.typeName = typeName;
		this.stakes = stakes;
		this.cars = cars;
	}

	// Property accessors
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name = "type_name")
	public String getTypeName() {
		return this.typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "carType")
	public Set<Stake> getStakes() {
		return this.stakes;
	}

	public void setStakes(Set<Stake> stakes) {
		this.stakes = stakes;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "carType")
	public Set<Car> getCars() {
		return this.cars;
	}

	public void setCars(Set<Car> cars) {
		this.cars = cars;
	}

}