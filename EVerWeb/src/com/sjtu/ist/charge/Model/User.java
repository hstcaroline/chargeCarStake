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
 * User entity. @author MyEclipse Persistence Tools
 */
@Entity
@org.hibernate.annotations.Entity(dynamicUpdate=true,dynamicInsert=true)
@Table(name = "user", catalog = "charge")
public class User implements java.io.Serializable {

	// Fields

	private Integer id;
	private String username;
	private String password;
	private String name;
	private String telephone;
	private Integer type;
	private Integer faith;
	private Integer haveStake;
	private Double remaining;
	private Set<Stake> stakes = new HashSet<Stake>(0);
	private Set<Order> orders = new HashSet<Order>(0);
	private Set<Car> cars = new HashSet<Car>(0);
	private Set<Message> messagesForFromId = new HashSet<Message>(0);
	private Set<UseStake> useStakesForUserId = new HashSet<UseStake>(0);
	private Set<UseStake> useStakesForStakeOwnerId = new HashSet<UseStake>(0);
	private Set<Message> messagesForToId = new HashSet<Message>(0);
	private Set<Collection> collections = new HashSet<Collection>(0);

	// Constructors

	/** default constructor */
	public User() {
	}

	/** minimal constructor */
	public User(String username, String password, String telephone,
			Integer type, Integer haveStake, Double remaining) {
		this.username = username;
		this.password = password;
		this.telephone = telephone;
		this.type = type;
		this.haveStake = haveStake;
		this.remaining = remaining;
		this.faith = 100;
		this.name = "user";
	}

	/** full constructor */
	public User(String username, String password, String name,
			String telephone, Integer type, Integer faith, Integer haveStake,
			Double remaining, Set<Stake> stakes, Set<Order> orders,
			Set<Car> cars, Set<Message> messagesForFromId,
			Set<UseStake> useStakesForUserId,
			Set<UseStake> useStakesForStakeOwnerId,
			Set<Message> messagesForToId, Set<Collection> collections) {
		this.username = username;
		this.password = password;
		this.name = name;
		this.telephone = telephone;
		this.type = type;
		this.faith = faith;
		this.haveStake = haveStake;
		this.remaining = remaining;
		this.stakes = stakes;
		this.orders = orders;
		this.cars = cars;
		this.messagesForFromId = messagesForFromId;
		this.useStakesForUserId = useStakesForUserId;
		this.useStakesForStakeOwnerId = useStakesForStakeOwnerId;
		this.messagesForToId = messagesForToId;
		this.collections = collections;
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

	@Column(name = "username")
	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	@Column(name = "password", nullable = false)
	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Column(name = "name")
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "telephone", nullable = false)
	public String getTelephone() {
		return this.telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	@Column(name = "type")
	public Integer getType() {
		return this.type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	@Column(name = "faith")
	public Integer getFaith() {
		return this.faith;
	}

	public void setFaith(Integer faith) {
		this.faith = faith;
	}

	@Column(name = "have_stake")
	public Integer getHaveStake() {
		return this.haveStake;
	}

	public void setHaveStake(Integer haveStake) {
		this.haveStake = haveStake;
	}

	@Column(name = "remaining", precision = 22, scale = 0)
	public Double getRemaining() {
		return this.remaining;
	}

	public void setRemaining(Double remaining) {
		this.remaining = remaining;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "user")
	public Set<Stake> getStakes() {
		return this.stakes;
	}

	public void setStakes(Set<Stake> stakes) {
		this.stakes = stakes;
	}

	
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "user")
	public Set<Order> getOrders() {
		return this.orders;
	}

	public void setOrders(Set<Order> orders) {
		this.orders = orders;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "user")
	public Set<Car> getCars() {
		return this.cars;
	}

	public void setCars(Set<Car> cars) {
		this.cars = cars;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "userByFromId")
	public Set<Message> getMessagesForFromId() {
		return this.messagesForFromId;
	}

	public void setMessagesForFromId(Set<Message> messagesForFromId) {
		this.messagesForFromId = messagesForFromId;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "userByUserId")
	public Set<UseStake> getUseStakesForUserId() {
		return this.useStakesForUserId;
	}

	public void setUseStakesForUserId(Set<UseStake> useStakesForUserId) {
		this.useStakesForUserId = useStakesForUserId;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "userByStakeOwnerId")
	public Set<UseStake> getUseStakesForStakeOwnerId() {
		return this.useStakesForStakeOwnerId;
	}

	public void setUseStakesForStakeOwnerId(
			Set<UseStake> useStakesForStakeOwnerId) {
		this.useStakesForStakeOwnerId = useStakesForStakeOwnerId;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "userByToId")
	public Set<Message> getMessagesForToId() {
		return this.messagesForToId;
	}

	public void setMessagesForToId(Set<Message> messagesForToId) {
		this.messagesForToId = messagesForToId;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "user")
	public Set<Collection> getCollections() {
		return this.collections;
	}

	public void setCollections(Set<Collection> collections) {
		this.collections = collections;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", username=" + username + ", password="
				+ password + ", name=" + name + ", telephone=" + telephone
				+ ", type=" + type + ", faith=" + faith + ", haveStake="
				+ haveStake + ", remaining=" + remaining + ", stakes=" + stakes
				+ ", orders=" + orders + ", cars=" + cars
				+ ", messagesForFromId=" + messagesForFromId
				+ ", useStakesForUserId=" + useStakesForUserId
				+ ", useStakesForStakeOwnerId=" + useStakesForStakeOwnerId
				+ ", messagesForToId=" + messagesForToId + ", collections="
				+ collections + "]";
	}

	 
}