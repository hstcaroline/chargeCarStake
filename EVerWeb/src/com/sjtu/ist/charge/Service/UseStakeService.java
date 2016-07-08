package com.sjtu.ist.charge.Service;

import java.util.List;

import com.sjtu.ist.charge.Model.Pager;
import com.sjtu.ist.charge.Model.UseStake;


public interface UseStakeService {
	/**
	 * 根据userId分页显示使用记录
	 * @param userId
	 * @return
	 */
	public Pager<UseStake> showUserStakeByUserId(int userId);
	
	/**
	 * 显示所有使用记录
	 * @return
	 */
	public Pager<UseStake> showAllUseStake();
	
	public Pager<UseStake> showUseStakeByStakeOwnerId(int stakeOwnerId);
	
	public UseStake load(int id);
	
	public boolean updateUS(UseStake useStake);

	/**
	 * 根据id删除使用记录
	 * @param id
	 * @return
	 */
	public boolean deleteUseStake(int id);
	
	/**
	 * 返回userStake list
	 * @param userId
	 * @return
	 */
	public List<UseStake> getByUserId(int userId);
	
	/**
	 * 插入一条充电记录
	 * @param useStake
	 * @return
	 */
	public boolean addUseStake(UseStake useStake);
}
