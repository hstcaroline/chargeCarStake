package com.sjtu.ist.charge.Model;

import java.sql.Timestamp;

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
 * Collection entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "collection", catalog = "charge")
public class Collection implements java.io.Serializable {

	// Fields

	private Integer id;
	private User user;
	private Stake stake;
	private Timestamp time;

	// Constructors

	/** default constructor */
	public Collection() {
	}

	/** full constructor */
	public Collection(User user, Stake stake, Timestamp time) {
		this.user = user;
		this.stake = stake;
		this.time = time;
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
	@JoinColumn(name = "user_id", nullable = false)
	public User getUser() {
		return this.user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "stake_id", nullable = false)
	public Stake getStake() {
		return this.stake;
	}

	public void setStake(Stake stake) {
		this.stake = stake;
	}
	@Column(name = "time")
	public Timestamp getTime() {
		return time;
	}

	public void setTime(Timestamp time) {
		this.time = time;
	}

}