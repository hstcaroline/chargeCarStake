package com.sjtu.ist.charge.Dto;

public class AdminInfoDto {

	private long userCount;// 当前用户总数
	private long stakeCount;// 当前车桩总数
	private long stakeUsingCount;// 当前正在使用车桩总数
	private long stakeExcepCount;// 当前异常车桩总数
	private long unsolvedCompMsgCount;// 当前未处理的投诉消息总数
	private long unsolvedCheckMsgCount;// 当前未处理的车桩审核消息总数
	private long solvedMsgCount;// 当前已处理消息总数

	public AdminInfoDto(long userCount, long stakeCount, long stakeUsingCount,
			long stakeExcepCount, long unsolvedCompMsgCount,
			long unsolvedCheckMsgCount, long solvedMsgCount) {
		super();
		this.userCount = userCount;
		this.stakeCount = stakeCount;
		this.stakeUsingCount = stakeUsingCount;
		this.stakeExcepCount = stakeExcepCount;
		this.unsolvedCompMsgCount = unsolvedCompMsgCount;
		this.unsolvedCheckMsgCount = unsolvedCheckMsgCount;
		this.solvedMsgCount = solvedMsgCount;
	}

	public long getUserCount() {
		return userCount;
	}

	public void setUserCount(long userCount) {
		this.userCount = userCount;
	}

	public long getStakeCount() {
		return stakeCount;
	}

	public void setStakeCount(long stakeCount) {
		this.stakeCount = stakeCount;
	}

	public long getStakeUsingCount() {
		return stakeUsingCount;
	}

	public void setStakeUsingCount(long stakeUsingCount) {
		this.stakeUsingCount = stakeUsingCount;
	}

	public long getStakeExcepCount() {
		return stakeExcepCount;
	}

	public void setStakeExcepCount(long stakeExcepCount) {
		this.stakeExcepCount = stakeExcepCount;
	}

	public long getUnsolvedCompMsgCount() {
		return unsolvedCompMsgCount;
	}

	public void setUnsolvedCompMsgCount(long unsolvedCompMsgCount) {
		this.unsolvedCompMsgCount = unsolvedCompMsgCount;
	}

	public long getSolvedMsgCount() {
		return solvedMsgCount;
	}

	public void setSolvedMsgCount(long solvedMsgCount) {
		this.solvedMsgCount = solvedMsgCount;
	}

	public long getUnsolvedCheckMsgCount() {
		return unsolvedCheckMsgCount;
	}

	public void setUnsolvedCheckMsgCount(long unsolvedCheckMsgCount) {
		this.unsolvedCheckMsgCount = unsolvedCheckMsgCount;
	}

}
