package com.dto.json;

import java.sql.Timestamp;

import com.model.Message;

public class MessageDto {
	private int id;
	private String from_name;
	private String to_name;
	private String content;
	private Timestamp time;
	private String title;
	private int type;
	private int done;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getFrom_name() {
		return from_name;
	}
	public void setFrom_name(String from_name) {
		this.from_name = from_name;
	}
	public String getTo_name() {
		return to_name;
	}
	public void setTo_name(String to_name) {
		this.to_name = to_name;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Timestamp getTime() {
		return time;
	}
	public void setTime(Timestamp time) {
		this.time = time;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public int getDone() {
		return done;
	}
	public void setDone(int done) {
		this.done = done;
	}
	public MessageDto(Message message) {
		this.id = message.getId();
		this.from_name = message.getUserByFromId().getName();
		this.to_name = message.getUserByToId().getName();
		this.content = message.getContent();
		this.time = message.getTime();
		this.title = message.getTitle();
		this.type = message.getType();
		this.done = message.getDone();
	}
	
}
