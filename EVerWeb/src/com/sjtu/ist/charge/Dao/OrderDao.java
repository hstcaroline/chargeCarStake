package com.sjtu.ist.charge.Dao;

import java.util.List;

import com.sjtu.ist.charge.Model.Order;
import com.sjtu.ist.charge.Model.Pager;


public interface OrderDao extends BaseDao<Order> {
	//查看所有订单
	public Pager<Order> allOrders();
	
	/**
	 * 根据使用者id分页显示order list
	 * @param name
	 * @return
	 */
	public Pager<Order> searchByUserId(int userId);
	
	/**
	 * 根据使用者id显示order list
	 * @param userId
	 * @return
	 */
	public List<Order> getByUserId(int userId);
	
	/**
	 * 查询某用户所有某种状态的订单//type为0：未使用，1：使用，2：未完成(过期)
	 * @param userId
	 * @param status
	 * @return
	 */
	public List<Order> getByUserIdAndStatus(int userId, int type);
}
