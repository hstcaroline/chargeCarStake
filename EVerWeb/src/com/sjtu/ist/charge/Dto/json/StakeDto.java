package com.dto.json;

import java.sql.Time;

import com.model.Stake;

public class StakeDto {

	private int id;
	private Double longitude;
	private Double latitude;
	private Time availableStime;
	private Time availableEtime;
	private String description;
	private int status;
	private Double price;
	private String address;
	private String ownerTelephone;
	private int type;

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

	public Time getAvailableStime() {
		return availableStime;
	}

	public void setAvailableStime(Time availableStime) {
		this.availableStime = availableStime;
	}

	public Time getAvailableEtime() {
		return availableEtime;
	}

	public void setAvailableEtime(Time availableEtime) {
		this.availableEtime = availableEtime;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public StakeDto(Stake stake) {
		this.availableStime = stake.getAvailableStime();
		this.availableEtime = stake.getAvailableEtime();
		this.latitude = stake.getLatitude();
		this.longitude = stake.getLongitude();
		this.description = stake.getDescription();
		this.status = stake.getStatus();
		this.price = stake.getPrice();
		this.id = stake.getId();
		this.address = stake.getAddress();
		this.type = stake.getType();
		this.ownerTelephone = stake.getUser().getTelephone();
	}

	public StakeDto() {

	}

	public double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getOwnerTelephone() {
		return ownerTelephone;
	}

	public void setOwnerTelephone(String ownerTelephone) {
		this.ownerTelephone = ownerTelephone;
	}
}
