package com.sjtu.ist.charge.Dao;

import java.util.List;

import com.sjtu.ist.charge.Model.Pager;
import com.sjtu.ist.charge.Model.Stake;


public interface StakeDao extends BaseDao<Stake>{
	
	/**
	 * 
	 * @return 分页查看所有审核通过之后的车桩
	 */
	public Pager<Stake> getStakeByPage();
	
	/**
	 * 分页获得未审核车桩
	 * @return
	 */
	public Pager<Stake> getUncheckedStakeByPage();
	
	/**
	 * 根据状态获取车桩
	 * @param status
	 * @return
	 */
	public List<Stake> getStakeByStatus(int status);
	
	/**
	 * 获取所有的stake
	 * @return
	 */
	public List<Stake> getAllStakes();
	
	public List<Stake> getStakeByOwnerId(int userId);
	
	/**
	 * 查询某用户某一状态的车桩
	 * @param ownerId
	 * @param status
	 * @return
	 */
	public List<Stake> getStakeByOwnerAndStatus(int ownerId, int status);
	
	/**
	 * 修改车桩为预约状态
	 * @param id
	 * @return
	 */
	public boolean updateOrder (int id) throws Exception;
	
}
