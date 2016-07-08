package com.sjtu.ist.charge.Model;

import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * UseStake entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "use_stake", catalog = "charge")
public class UseStake implements java.io.Serializable {

	// Fields

	private Integer id;
	private User userByUserId;
	private User userByStakeOwnerId;
	private Stake stake;
	private Timestamp startTime;
	private Timestamp endTime;
	private int status;//状态：0：正在进行；1：已完成；2：异常
	private Set<Judge> judges = new HashSet<Judge>(0);
	private Set<Complaint> complaints = new HashSet<Complaint>();

	// Constructors

	/** default constructor */
	public UseStake() {
	}

	/** minimal constructor */
	public UseStake(User userByUserId, User userByStakeOwnerId, Stake stake,
			Timestamp startTime, Timestamp endTime, int status) {
		this.userByUserId = userByUserId;
		this.userByStakeOwnerId = userByStakeOwnerId;
		this.stake = stake;
		this.startTime = startTime;
		this.endTime = endTime;
		this.status = status;
	}

	/** full constructor */
	public UseStake(User userByUserId, User userByStakeOwnerId, Stake stake,
			Timestamp startTime, Timestamp endTime, int status, Set<Judge> judges) {
		this.userByUserId = userByUserId;
		this.userByStakeOwnerId = userByStakeOwnerId;
		this.stake = stake;
		this.startTime = startTime;
		this.endTime = endTime;
		this.status = status;
		this.judges = judges;
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
	public User getUserByUserId() {
		return this.userByUserId;
	}

	public void setUserByUserId(User userByUserId) {
		this.userByUserId = userByUserId;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "stake_owner_id", nullable = false)
	public User getUserByStakeOwnerId() {
		return this.userByStakeOwnerId;
	}

	public void setUserByStakeOwnerId(User userByStakeOwnerId) {
		this.userByStakeOwnerId = userByStakeOwnerId;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "stake_id", nullable = false)
	public Stake getStake() {
		return this.stake;
	}

	public void setStake(Stake stake) {
		this.stake = stake;
	}

	@Column(name = "start_time", nullable = false, length = 19)
	public Timestamp getStartTime() {
		return this.startTime;
	}

	public void setStartTime(Timestamp startTime) {
		this.startTime = startTime;
	}

	@Column(name = "end_time", nullable = false, length = 19)
	public Timestamp getEndTime() {
		return this.endTime;
	}

	public void setEndTime(Timestamp endTime) {
		this.endTime = endTime;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "useStake")
	public Set<Judge> getJudges() {
		return this.judges;
	}

	public void setJudges(Set<Judge> judges) {
		this.judges = judges;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}
	
	@OneToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY, mappedBy = "useStakeByUseStakeId")
	public Set<Complaint> getComplaints() {
		return this.complaints;
	}

	public void setComplaints(Set<Complaint> complaints) {
		this.complaints = complaints;
	}
}