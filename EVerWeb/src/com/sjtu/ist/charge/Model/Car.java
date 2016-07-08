package com.sjtu.ist.charge.Model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * Car entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "car", catalog = "charge")
public class Car implements java.io.Serializable {

	// Fields

	private Integer id;
	private CarType carType;
	private User user;

	// Constructors

	/** default constructor */
	public Car() {
	}

	/** full constructor */
	public Car(CarType carType, User user) {
		this.carType = carType;
		this.user = user;
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

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "car_type_id", nullable = false)
	public CarType getCarType() {
		return this.carType;
	}

	public void setCarType(CarType carType) {
		this.carType = carType;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "owner_id", nullable = false)
	public User getUser() {
		return this.user;
	}

	public void setUser(User user) {
		this.user = user;
	}

}