package com.dto.json;

import java.sql.Time;
import java.sql.Timestamp;

import com.model.Collection;

public class CollectionDto {
	private int id;
	private int stake_id;
	private String stake_description;
	private String stake_address;
	private Double stake_longitude;
	private Double stake_latitude;
	private int stake_status;
	private int stake_type;
	private Double stake_price;
	private Time stake_availableStime;
	private Time stake_availableEtime;
	private Timestamp time;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getStake_id() {
		return stake_id;
	}
	public void setStake_id(int stake_id) {
		this.stake_id = stake_id;
	}
	public String getStake_description() {
		return stake_description;
	}
	public void setStake_description(String stake_description) {
		this.stake_description = stake_description;
	}
	public String getStake_address() {
		return stake_address;
	}
	public void setStake_address(String stake_address) {
		this.stake_address = stake_address;
	}
	public Double getStake_longitude() {
		return stake_longitude;
	}
	public void setStake_longitude(Double stake_longitude) {
		this.stake_longitude = stake_longitude;
	}
	public Double getStake_latitude() {
		return stake_latitude;
	}
	public void setStake_latitude(Double stake_latitude) {
		this.stake_latitude = stake_latitude;
	}
	public int getStake_status() {
		return stake_status;
	}
	public void setStake_status(int stake_status) {
		this.stake_status = stake_status;
	}
	public Double getStake_price() {
		return stake_price;
	}
	public void setStake_price(Double stake_price) {
		this.stake_price = stake_price;
	}
	public Time getStake_availableStime() {
		return stake_availableStime;
	}
	public void setStake_availableStime(Time stake_availableStime) {
		this.stake_availableStime = stake_availableStime;
	}
	public Time getStake_availableEtime() {
		return stake_availableEtime;
	}
	public void setStake_availableEtime(Time stake_availableEtime) {
		this.stake_availableEtime = stake_availableEtime;
	}
	public Timestamp getTime() {
		return time;
	}
	public void setTime(Timestamp time) {
		this.time = time;
	}
	
	public int getStake_type() {
		return stake_type;
	}
	public void setStake_type(int stake_type) {
		this.stake_type = stake_type;
	}
	public CollectionDto(Collection collection) {
		this.id = collection.getId();
		this.stake_id = collection.getStake().getId();
		this.stake_description = collection.getStake().getDescription();
		this.stake_address = collection.getStake().getAddress();
		this.stake_longitude = collection.getStake().getLongitude();
		this.stake_latitude = collection.getStake().getLatitude();
		this.stake_status = collection.getStake().getStatus();
		this.stake_price = collection.getStake().getPrice();
		this.stake_availableStime = collection.getStake().getAvailableStime();
		this.stake_availableEtime = collection.getStake().getAvailableEtime();
		this.time = collection.getTime();
	}
	
	
	
}
