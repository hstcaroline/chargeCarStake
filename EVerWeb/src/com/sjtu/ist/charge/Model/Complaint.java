package com.sjtu.ist.charge.Model;

import static javax.persistence.GenerationType.IDENTITY;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "complaint", catalog = "charge")
public class Complaint implements java.io.Serializable {

	// Fields
	private Integer id;
	private User userByToId;
	private User userByFromId;
	private UseStake useStakeByUseStakeId;
	private String content;
	private Integer status;
	private Timestamp time;

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "to_id", nullable = false)
	public User getUserByToId() {
		return this.userByToId;
	}

	public void setUserByToId(User userByToId) {
		this.userByToId = userByToId;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "from_id", nullable = false)
	public User getUserByFromId() {
		return this.userByFromId;
	}

	public void setUserByFromId(User userByFromId) {
		this.userByFromId = userByFromId;
	}

	@Column(name = "content")
	public String getContent() {
		return this.content;
	}

	public void setContent(String content) {
		this.content = content;
	}
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "use_stake_id", nullable = false)
	public UseStake getUseStakeByUseStakeId() {
		return useStakeByUseStakeId;
	}

	public void setUseStakeByUseStakeId(UseStake useStakeByUseStakeId) {
		this.useStakeByUseStakeId = useStakeByUseStakeId;
	}

	@Column(name = "status", nullable = false)
	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	@Column(name = "time")
	public Timestamp getTime() {
		return this.time;
	}

	public void setTime(Timestamp time) {
		this.time = time;
	}

	/** default constructor */
	public Complaint() {
	}

	/** full constructor */
	public Complaint(Integer id, User userByToId, User userByFromId,
			UseStake useStakeByUseStakeId, String content, Integer status,
			Timestamp time) {
		super();
		this.id = id;
		this.userByToId = userByToId;
		this.userByFromId = userByFromId;
		this.useStakeByUseStakeId = useStakeByUseStakeId;
		this.content = content;
		this.status = status;
		this.time = time;
	}

	public Complaint(User userByToId, User userByFromId,
			UseStake useStakeByUseStakeId, String content, Integer status,
			Timestamp time) {
		super();
		this.userByToId = userByToId;
		this.userByFromId = userByFromId;
		this.useStakeByUseStakeId = useStakeByUseStakeId;
		this.content = content;
		this.status = status;
		this.time = time;
	}

}
