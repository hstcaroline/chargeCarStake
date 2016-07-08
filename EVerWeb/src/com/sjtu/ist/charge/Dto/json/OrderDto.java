package com.dto.json;

import java.sql.Timestamp;

import com.model.Order;
import com.model.User;

public class OrderDto {
		private int id;
	    private String stake_address;
	    private String stake_description;
	    private Timestamp startTime;
	    private Timestamp endTime;
	    private Integer type;
		
	    public int getId() {
			return id;
		}
		public void setId(int id) {
			this.id = id;
		}
		public String getStake_address() {
			return stake_address;
		}
		public void setStake_address(String stake_address) {
			this.stake_address = stake_address;
		}
		public String getStake_description() {
			return stake_description;
		}
		public void setStake_description(String stake_description) {
			this.stake_description = stake_description;
		}
		public Timestamp getStartTime() {
			return startTime;
		}
		public void setStartTime(Timestamp startTime) {
			this.startTime = startTime;
		}
		public Timestamp getEndTime() {
			return endTime;
		}
		public void setEndTime(Timestamp endTime) {
			this.endTime = endTime;
		}
		public Integer getType() {
			return type;
		}
		public void setType(Integer type) {
			this.type = type;
		}
		
		public OrderDto(Order order) {
			this.id = order.getId();
			this.stake_address = order.getStake().getAddress();
			this.stake_description = order.getStake().getDescription();
			this.startTime = order.getStartTime();
			this.endTime = order.getEndTime();
			this.type = order.getType();
		}
		public OrderDto(){}
	    
	    
}
