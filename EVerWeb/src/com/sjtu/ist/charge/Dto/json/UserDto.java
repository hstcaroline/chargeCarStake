package com.dto.json;

import java.util.HashSet;
import java.util.Set;

import com.model.Car;
import com.model.Stake;
import com.model.User;

public class UserDto {

	private String username;
	private String telephone;
	private double remaining;
	private int faith;
	private Set<StakeDto> stakes;
	private Set<Car> cars;


	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public double getRemaining() {
		return remaining;
	}

	public void setRemaining(double remaining) {
		this.remaining = remaining;
	}

	public int getFaith() {
		return faith;
	}

	public void setFaith(int faith) {
		this.faith = faith;
	}

	public Set<StakeDto> getStakes() {
		return stakes;
	}

	public void setStakes(Set<StakeDto> stakes) {
		this.stakes = stakes;
	}

	public UserDto(String username, String telephone, double remaining,
			int faith) {
		super();
		this.username = username;
		this.telephone = telephone;
		this.remaining = remaining;
		this.faith = faith;
	}

	public UserDto(User user) {
		this.username = user.getUsername();
		this.telephone = user.getTelephone();
		this.remaining = user.getRemaining();
		this.faith = user.getFaith();
		stakes = new HashSet<StakeDto>();
		Set<Stake> stakeSet = user.getStakes();
		for (Stake stake : stakeSet) {
			stakes.add(new StakeDto(stake));
		}
	}
	
	public UserDto(String username) {
		this.username = username;
	}

	public UserDto() {
		super();
	}

}
