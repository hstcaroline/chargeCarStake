package com.sjtu.ist.charge.Model;

import java.sql.Timestamp;
import java.util.List;

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
 * Message entity. @author MyEclipse Persistence Tools
 */
@Entity
@org.hibernate.annotations.Entity(dynamicUpdate=true,dynamicInsert=true)
@Table(name = "message", catalog = "charge")
public class Message implements java.io.Serializable {

	// Fields

	private Integer id;
	private User userByToId;
	private User userByFromId;
	private String title;
	private String content;
	private Integer done;
	private Timestamp time;
	private Integer type;

	// Constructors

	/** default constructor */
	public Message() {
	}

	/** minimal constructor */
	public Message(User userByToId, User userByFromId, Integer done,
			Integer type) {
		this.userByToId = userByToId;
		this.userByFromId = userByFromId;
		this.done = done;
		this.type = type;
	}

	/** full constructor */
	public Message(User userByToId, User userByFromId, String title,
			String content, Integer done, Timestamp time, Integer type) {
		this.userByToId = userByToId;
		this.userByFromId = userByFromId;
		this.title = title;
		this.content = content;
		this.done = done;
		this.time = time;
		this.type = type;
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

	@Column(name = "title")
	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@Column(name = "content")
	public String getContent() {
		return this.content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	@Column(name = "done")
	public Integer getDone() {
		return this.done;
	}

	public void setDone(Integer done) {
		this.done = done;
	}

	@Column(name = "time", length = 19)
	public Timestamp getTime() {
		return this.time;
	}

	public void setTime(Timestamp time) {
		this.time = time;
	}

	@Column(name = "type")
	public Integer getType() {
		return this.type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

}