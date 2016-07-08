package com.dto.json;

import com.model.CarType;

public class CarTypeDto {
	private int id;
	private String type_name;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getType_name() {
		return type_name;
	}
	public void setType_name(String type_name) {
		this.type_name = type_name;
	}

	public CarTypeDto(CarType carType) {
		this.id = carType.getId();
		this.setType_name(carType.getTypeName());
	}
	public CarTypeDto(){}
}
