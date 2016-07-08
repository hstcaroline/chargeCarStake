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
 * Judge entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "judge", catalog = "charge")
public class Judge implements java.io.Serializable {

	// Fields

	private Integer id;
	private Integer stakeId;
	private UseStake useStake;
	private Double grade;
	private String content;
	private Timestamp time;

	// Constructors

	/** default constructor */
	public Judge() {
	}

	/** full constructor */
	public Judge(UseStake useStake, Double grade, String content, Timestamp time, int stakeId) {
		this.useStake = useStake;
		this.grade = grade;
		this.content = content;
		this.time = time;
		this.stakeId = stakeId;
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
	@JoinColumn(name = "use_stake_id", nullable = false)
	public UseStake getUseStake() {
		return this.useStake;
	}

	public void setUseStake(UseStake useStake) {
		this.useStake = useStake;
	}

	@Column(name = "grade", precision = 22, scale = 0)
	public Double getGrade() {
		return this.grade;
	}

	public void setGrade(Double grade) {
		this.grade = grade;
	}

	@Column(name = "content")
	public String getContent() {
		return this.content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	@Column(name = "time", length = 19)
	public Timestamp getTime() {
		return this.time;
	}

	public void setTime(Timestamp time) {
		this.time = time;
	}
	@Column(name = "stake_id")
	public Integer getStakeId() {
		return stakeId;
	}

	public void setStakeId(Integer stakeId) {
		this.stakeId = stakeId;
	}

}