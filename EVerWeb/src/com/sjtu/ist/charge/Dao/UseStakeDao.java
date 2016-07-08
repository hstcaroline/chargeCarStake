package com.sjtu.ist.charge.Dao;


import java.util.List;

import com.sjtu.ist.charge.Model.Pager;
import com.sjtu.ist.charge.Model.UseStake;


public interface UseStakeDao extends BaseDao<UseStake>{
	
	/**
	 * 获取当前用户的车桩使用记录
	 * @param userId
	 * @return
	 */
	public Pager<UseStake> getUseStakeByUserId(int userId);
	
	/**
	 * 获取所有的车桩使用记录
	 * @return
	 */
	public Pager<UseStake> getAllUsestakes();
	
	public Pager<UseStake> getUseStakeByStakeOwnerId(int stakeOwnerId);
	
	public List<UseStake> getByUserId(int userId);
}
