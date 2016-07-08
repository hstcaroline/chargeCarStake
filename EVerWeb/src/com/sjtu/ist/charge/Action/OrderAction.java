package com.sjtu.ist.charge.Action;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import com.sjtu.ist.charge.Model.Order;
import com.sjtu.ist.charge.Model.Pager;
import com.sjtu.ist.charge.Service.OrderService;

@Controller("orderAction")
@Scope(value="prototype")
public class OrderAction extends ActionSupport implements ModelDriven<Order> {
	private List<Order> orders;
	private OrderService orderService;

	private Pager<Order> pageOrders;
	public List<Order> getOrders() {
		return orders;
	}

	public void setOrders(List<Order> orders) {
		this.orders = orders;
	}

	public OrderService getOrderService() {
		return orderService;
	}
	@Resource(name = "orderService")
	public void setOrderService(OrderService orderService) {
		this.orderService = orderService;
	}

	public Order getModel() {
		return null;
	}
	public String showAllOrders()
	{
		pageOrders=  orderService.showAllOrders();
		if (pageOrders!=null) {
			setOrders(pageOrders.getDatas());
			return "showAllOrdersSuccess";
		}
		return "login";
	}

	public Pager<Order> getPageOrders() {
		return pageOrders;
	}

	public void setPageOrders(Pager<Order> pageOrders) {
		this.pageOrders = pageOrders;
	}
}
