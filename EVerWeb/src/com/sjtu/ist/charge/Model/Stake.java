package com.sjtu.ist.charge.Model;

import java.sql.Time;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Version;

import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * Stake entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "stake", catalog = "charge")
public class Stake implements java.io.Serializable {

	// Fields

	private Integer id;
	private Integer version;
	private CarType carType;
	private User user;
	private Double longitude;
	private Double latitude;
	private Time availableStime;
	private Time availableEtime;
	private String description;
	private String qrCode;
	private Integer status;//0:可用，1：正在使用，2：未分享；3：异常,4:未审核；5：审核未通过
	private Integer type;
	private Double price;
	private String address;
	private int reservation;
	private Set<Order> orders = new HashSet<Order>(0);
	private Set<Collection> collections = new HashSet<Collection>(0);
	private Set<UseStake> useStakes = new HashSet<UseStake>(0);

	// Constructors

	/** default constructor */
	public Stake() {
	}

	/** minimal constructor */
	public Stake(CarType carType, Double longitude, Double latitude,
			Time availableStime, Time availableEtime, Integer status,
			Integer type, Double price, String description, String address) {
		this.carType = carType;
		this.longitude = longitude;
		this.latitude = latitude;
		this.description = description;
		this.availableStime = availableStime;
		this.availableEtime = availableEtime;
		this.status = status;
		this.type = type;
		this.price = price;
		this.address = address;
	}

	/** full constructor */
	public Stake(CarType carType, User user, Double longitude, Double latitude,
			Time availableStime, Time availableEtime, String description,
			String qrCode, Integer status, Integer type, Double price, String address, Set<Order> orders,
			Set<Collection> collections, Set<UseStake> useStakes) {
		this.carType = carType;
		this.user = user;
		this.longitude = longitude;
		this.latitude = latitude;
		this.availableStime = availableStime;
		this.availableEtime = availableEtime;
		this.description = description;
		this.qrCode = qrCode;
		this.status = status;
		this.type = type;
		this.price = price;
		this.address = address;
		this.orders = orders;
		this.collections = collections;
		this.useStakes = useStakes;
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

	@Version
	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "available_cartype_id", nullable = false)
	public CarType getCarType() {
		return this.carType;
	}

	public void setCarType(CarType carType) {
		this.carType = carType;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	//@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "owner_id")
	public User getUser() {
		return this.user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@Column(name = "longitude", nullable = false, precision = 22, scale = 0)
	public Double getLongitude() {
		return this.longitude;
	}

	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}

	@Column(name = "latitude", nullable = false, precision = 22, scale = 0)
	public Double getLatitude() {
		return this.latitude;
	}

	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}

	@Column(name = "available_stime", nullable = false, length = 8)
	public Time getAvailableStime() {
		return this.availableStime;
	}

	public void setAvailableStime(Time availableStime) {
		this.availableStime = availableStime;
	}

	@Column(name = "available_etime", nullable = false, length = 8)
	public Time getAvailableEtime() {
		return this.availableEtime;
	}

	public void setAvailableEtime(Time availableEtime) {
		this.availableEtime = availableEtime;
	}

	@Column(name = "description")
	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Column(name = "qr_code")
	public String getQrCode() {
		return this.qrCode;
	}

	public void setQrCode(String qrCode) {
		this.qrCode = qrCode;
	}

	@Column(name = "status", nullable = false)
	public Integer getStatus() {
		return this.status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	@Column(name = "type", nullable = false)
	public Integer getType() {
		return this.type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "stake")
	public Set<Order> getOrders() {
		return this.orders;
	}

	public void setOrders(Set<Order> orders) {
		this.orders = orders;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "stake")
	public Set<Collection> getCollections() {
		return this.collections;
	}

	public void setCollections(Set<Collection> collections) {
		this.collections = collections;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "stake")
	public Set<UseStake> getUseStakes() {
		return this.useStakes;
	}

	public void setUseStakes(Set<UseStake> useStakes) {
		this.useStakes = useStakes;
	}
	
	@Column(name = "price")
	public Double getPrice() {
		return price;
	}
	
	public void setPrice(Double price) {
		this.price = price;
	}

	@Column(name = "address")
	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public int getReservation() {
		return reservation;
	}

	public void setReservation(int reservation) {
		this.reservation = reservation;
	}

	/*
	@Override
	public String toString() {
		return "Stake [id=" + id + ", carType=" + carType + ", user=" + user
				+ ", longitude=" + longitude + ", latitude=" + latitude
				+ ", availableStime=" + availableStime + ", availableEtime="
				+ availableEtime + ", description=" + description + ", qrCode="
				+ qrCode + ", status=" + status + ", type=" + type
				+ ", orders=" + orders + ", collections=" + collections
				+ ", useStakes=" + useStakes + "]";
				
	}
	*/
	
}