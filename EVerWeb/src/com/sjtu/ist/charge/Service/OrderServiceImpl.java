package com.sjtu.ist.charge.Service;


import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.sjtu.ist.charge.Dao.OrderDao;
import com.sjtu.ist.charge.Model.Order;
import com.sjtu.ist.charge.Model.Pager;


@Service("orderService")
public class OrderServiceImpl implements OrderService {
    private OrderDao orderDao;

    public OrderDao getOrderDao() {
        return orderDao;
    }

    @Resource(name = "orderDao")
    public void setOrderDao(OrderDao orderDao) {
        this.orderDao = orderDao;
    }

    public Pager<Order> showAllOrders() {
        Pager<Order> orders = orderDao.allOrders();
        return orders;
    }

	public Pager<Order> searchOrdersByUserId(int userId) {
		return orderDao.searchByUserId(userId);
	}

	public boolean addOrder(Order order) {
		return orderDao.add(order);
	}

	public List<Order> getByUserId(int userId) {
		return orderDao.getByUserId(userId);
	}

	public boolean update(Order order) {
		return orderDao.update(order);
	}
	

}
