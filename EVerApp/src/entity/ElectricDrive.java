package entity;

import java.io.Serializable;
import java.sql.Time;

public class ElectricDrive implements Serializable{

	private double latitude;//Î³¶È
	private double longtitude;//¾­¶È
	private String description;
	private String address;
	private int id;
	private String availableStime;
	private String availableEtime;
	private int status;
	private Double price;
	private int type;
	private long distance;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public double getLatitude() {
		return latitude;
	}
	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}
	
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public double getLongtitude() {
		return longtitude;
	}
	public void setLongtitude(double longtitude) {
		this.longtitude = longtitude;
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
	public Double getPrice() {
		return price;
	}
	public void setPrice(Double price) {
		this.price = price;
	}
	public String getAvailableStime() {
		return availableStime;
	}
	public void setAvailableStime(String availableStime) {
		this.availableStime = availableStime;
	}
	public String getAvailableEtime() {
		return availableEtime;
	}
	public void setAvailableEtime(String availableEtime) {
		this.availableEtime = availableEtime;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public long getDistance() {
		return distance;
	}
	public void setDistance(long distance) {
		this.distance = distance;
	}
	
}
