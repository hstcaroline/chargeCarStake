package com.sjtu.ist.charge.Service;


import java.util.List;

import com.sjtu.ist.charge.Model.Order;
import com.sjtu.ist.charge.Model.Pager;


public interface OrderService {
	//显示所有订单
	public Pager<Order> showAllOrders();
	
	/**
	 * 根据用户id分页查询order list
	 * @param userId
	 * @return 分页的order list
	 */
	public Pager<Order> searchOrdersByUserId(int userId);
	
	/**
	 * 预约
	 * @param order
	 * @return
	 */
	public boolean addOrder(Order order);
	
	/**
	 * 根据用户id查询order list
	 * @param userId
	 * @return
	 */
	public List<Order> getByUserId(int userId);
	
	/**
	 * 修改充电记录
	 * @param order
	 * @return
	 */
	public boolean update(Order order);

}
