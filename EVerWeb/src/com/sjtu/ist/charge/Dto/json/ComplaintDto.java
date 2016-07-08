package com.dto.json;

import java.sql.Timestamp;

import com.model.Complaint;
import com.model.UseStake;
import com.model.User;

public class ComplaintDto {
	private Integer id;
	private int to_id;
	private int from_id;
	private int useStake_id;
	private String content;
	private Integer status;
	private Timestamp time;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public int getTo_id() {
		return to_id;
	}
	public void setTo_id(int to_id) {
		this.to_id = to_id;
	}
	public int getFrom_id() {
		return from_id;
	}
	public void setFrom_id(int from_id) {
		this.from_id = from_id;
	}
	public int getUseStake_id() {
		return useStake_id;
	}
	public void setUseStake_id(int useStake_id) {
		this.useStake_id = useStake_id;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public Timestamp getTime() {
		return time;
	}
	public void setTime(Timestamp time) {
		this.time = time;
	}
	public ComplaintDto(Complaint complaint) {
		this.id = complaint.getId();
		this.to_id = complaint.getUserByToId().getId();
		this.from_id = complaint.getUserByFromId().getId();
		this.useStake_id = complaint.getUseStakeByUseStakeId().getId();
		this.content = complaint.getContent();
		this.status = complaint.getStatus();
		this.time = complaint.getTime();
	}
	public ComplaintDto(){}
}
