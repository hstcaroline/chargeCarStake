package com.sjtu.ist.charge.Dao;

import java.util.List;



public interface BaseDao<T> { 

	/**
	 * 增加记录
	 * @param t
	 * @return
	 */
	public boolean add(T t);
	
	/**
	 * 根据记录的内容删除一条记
	 * @return
	 */
	public boolean delete(T t);
	
	/**
	 * 根据记录的id删除记录
	 * @param id
	 * @return
	 */
	public boolean delete(int id);
	
	/**
	 * 更新记录
	 * @param t
	 * @return
	 */
	public boolean update(T t);
	
	/**
	 * 查询加载记录
	 * @param id
	 * @return
	 */
	public T load(int id);
	
	public List<T> getAfterFilter(String hql,Object[] object);
	
	/**
	 * 根据hql查询对应页的list
	 * @param pageOffset
	 * @param pageSize
	 * @param hql
	 * @return
	 */
	public List<T> SearchPerPage(int pageOffset,int pageSize,String hql);
	/**
	 * 查询语句查询出的记录数量
	 * @param hql
	 * @return
	 */
	public Long count(String hql) ;
}
