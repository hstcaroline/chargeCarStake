package com.sjtu.ist.charge.Dao;

import java.util.List;

import com.sjtu.ist.charge.Model.Collection;


public interface CollectionDao extends BaseDao<Collection>{

	public List<Collection> getColByUId(int uid);
	
	/**
	 * 根据用户id和车桩id查询收藏记录
	 * @param user_id
	 * @param stake_id
	 * @return
	 */
	public Collection getColByUserIdAndStakeId(int user_id,int stake_id);
}
