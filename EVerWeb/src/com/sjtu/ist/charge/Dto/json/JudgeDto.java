package com.dto.json;

import java.sql.Timestamp;

import com.model.Judge;

public class JudgeDto {
	private int id;
	private int user_id;
	private String user_name;
	private String user_telephone;
	private int stake_id;
	private String stake_address;

    private String stake_description;
	private Timestamp time;
	private double grade;
	private String content;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getUser_id() {
		return user_id;
	}
	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}
	public String getUser_name() {
		return user_name;
	}
	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}
	public String getUser_telephone() {
		return user_telephone;
	}
	public void setUser_telephone(String user_telephone) {
		this.user_telephone = user_telephone;
	}
	public int getStake_id() {
		return stake_id;
	}
	public void setStake_id(int stake_id) {
		this.stake_id = stake_id;
	}
	public Timestamp getTime() {
		return time;
	}
	public void setTime(Timestamp time) {
		this.time = time;
	}
	public double getGrade() {
		return grade;
	}
	public void setGrade(double grade) {
		this.grade = grade;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getStake_address() {
		return stake_address;
	}
	public void setStake_address(String stake_address) {
		this.stake_address = stake_address;
	}
	public JudgeDto(Judge judge) {
		this.id = judge.getId();
		this.user_id = judge.getUseStake().getUserByUserId().getId();
		this.user_name = judge.getUseStake().getUserByUserId().getName();
		this.user_telephone = judge.getUseStake().getUserByUserId().getTelephone();
		this.stake_id = judge.getUseStake().getStake().getId();
		this.stake_address = judge.getUseStake().getStake().getAddress();
        this.setStake_description(judge.getUseStake().getStake().getDescription());
		this.time = judge.getTime();
		this.grade = judge.getGrade();
		this.content = judge.getContent();
	}
    public String getStake_description() {
        return stake_description;
    }
    public void setStake_description(String stake_description) {
        this.stake_description = stake_description;
    }

	
}
