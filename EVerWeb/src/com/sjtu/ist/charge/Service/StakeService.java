package com.sjtu.ist.charge.Service;


import java.sql.Timestamp;
import java.util.List;

import com.sjtu.ist.charge.Model.Order;
import com.sjtu.ist.charge.Model.Pager;
import com.sjtu.ist.charge.Model.Stake;


public interface StakeService {
	/**
	 * 
	 * @return  分页后的充电桩
	 */
	public Pager<Stake> getStakeByPage();
	
	public Pager<Stake> getUncheckedStakeByPage();
	
	/**
	 * 修改车桩的当前状态
	 * @param stake
	 * @return
	 */
	public Boolean update(Stake stake);
	
	/**
	 * 根据id获取stake
	 * @param id
	 * @return
	 */
	public Stake load(int id);
	
	/**
	 * 根据状态查询相应车桩
	 * @param status
	 * @return
	 */
	public List<Stake> showStakeByStatus(int status);
	
	/**
	 * 获得所有的stake
	 * @return
	 */
	public List<Stake> showAllStakes();
	
	/**
	 * 根据id删除充电桩
	 * @param id
	 * @return
	 */
	public boolean deleteStake(int id);
	
	/**
	 * 根据id删除待审核的充电桩
	 * @param id
	 * @return
	 */
	public boolean deleteUncheckedStake(int id);
	
	/**
	 * 车桩注册
	 * @param stake
	 * @return
	 */
	public boolean stakeRegister(Stake stake);
	
	/**
	 * 根据车桩主id查询他拥有的车桩
	 * @param userId
	 * @return
	 */
	public List<Stake> getStakesByOwnerId(int userId);
	
	public List<Stake> getByOwnerAndStatus(int ownerId, int status);
	
	/**
	 * 修改车桩状态为预约状态
	 * @param stakeId
	 * @return
	 */
	public boolean addStakeOrder(int stakeId);
	
	/**
	 * 根据开始时间和结束时间查询可用的车桩
	 * @param stime	2015-11-19 14:45:04
	 * @param etime 2015-11-19 14:48:04
	 * @return
	 */
	public List<Stake> getAvailableStake(String stime,String etime);
	
	/**
	 * 根据时间查询指定车桩是否可用
	 * @param stime
	 * @param etime
	 * @param stakeId
	 * @return
	 */
	public boolean isAvailable(String stime,String etime,int stakeId);
	
	/**
	 * 根据时间和使用者查询该车桩是否可以被当前用户使用
	 * @param stime
	 * @param etime
	 * @param stakeId
	 * @param userId
	 * @return
	 */
	public Order getAvaiableOrder(Timestamp stime,Timestamp etime,int stakeId, int userId);
}
