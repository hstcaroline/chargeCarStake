package com.sjtu.ist.charge.Service;

import java.util.List;

import com.sjtu.ist.charge.Model.Collection;


public interface CollectionService {

	public List<Collection> getColByUId(int uid);
	
	/**
	 * 添加收藏记录
	 * @param collection
	 * @return
	 */
	public boolean addCollection(Collection collection);
	
	/**
	 * 取消收藏
	 * @param collection
	 * @return
	 */
	public boolean deleteCollection(Collection collection);
	
	/**
	 * 根据id查询collection
	 * @param id
	 * @return
	 */
	public Collection load(int id);
	
	/**
	 * 根据userid和stake Id获取收藏记录
	 * @param user_id
	 * @param stake_id
	 * @return
	 */
	public Collection getByUserAndStakeId(int user_id, int stake_id);
}
