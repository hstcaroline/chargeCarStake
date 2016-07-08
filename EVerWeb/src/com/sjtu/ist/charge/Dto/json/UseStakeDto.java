package com.dto.json;

import java.sql.Timestamp;

import com.model.UseStake;

public class UseStakeDto {
	private int id;
	private int status;
	private String description;
	private String car_type_name;
	private Timestamp start_time;
	private Timestamp end_time;
	private Double longitude;
	private Double latitude;
	private double price;
	private int stakeOwnerId;
	
	public UseStakeDto(UseStake useStake) {
		this.id = useStake.getId();
		this.status = useStake.getStatus();
		this.description = useStake.getStake().getDescription();
		this.car_type_name = useStake.getStake().getCarType().getTypeName();
		this.start_time = useStake.getStartTime();
		this.end_time = useStake.getEndTime();
		this.longitude = useStake.getStake().getLongitude();
		this.latitude = useStake.getStake().getLatitude();
		this.price = useStake.getStake().getPrice();
		this.stakeOwnerId = useStake.getUserByStakeOwnerId().getId();
	}
	public UseStakeDto(){
		
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getCar_type_name() {
		return car_type_name;
	}
	public void setCar_type_name(String car_type_name) {
		this.car_type_name = car_type_name;
	}
	public Timestamp getStart_time() {
		return start_time;
	}
	public void setStart_time(Timestamp start_time) {
		this.start_time = start_time;
	}
	public Timestamp getEnd_time() {
		return end_time;
	}
	public void setEnd_time(Timestamp end_time) {
		this.end_time = end_time;
	}
	public Double getLongitude() {
		return longitude;
	}
	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}
	public Double getLatitude() {
		return latitude;
	}
	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(float price) {
		this.price = price;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getStakeOwnerId() {
		return stakeOwnerId;
	}
	public void setStakeOwnerId(int stakeOwnerId) {
		this.stakeOwnerId = stakeOwnerId;
	}
}
