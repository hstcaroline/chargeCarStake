package com.sjtu.ist.charge.Servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.http.HttpStatus;

import com.sjtu.ist.charge.Dto.json.OrderDto;
import com.sjtu.ist.charge.Model.Order;
import com.sjtu.ist.charge.Model.Stake;
import com.sjtu.ist.charge.Model.User;
import com.sjtu.ist.charge.Service.OrderService;
import com.sjtu.ist.charge.Service.StakeService;
import com.sjtu.ist.charge.Service.UserService;
import com.sjtu.ist.charge.Util.RedisUtil;
import com.sjtu.ist.charge.Util.RequestUtil;

import redis.clients.jedis.Jedis;


/**
 * 用户预约 ： url:/api/user/stakeOrder.servlet method : post
 * 查看预约记录：   url:/api/user/stakeOrder.servlet method:get
 * @author huangshunting
 * 
 */
public class Stake_OrderServlet extends BaseServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String token = req.getHeader("Access-Token");
		Jedis jedis = RedisUtil.getJedis();
		String id = jedis.get(token);
		UserService userService = (UserService) getCtx().getBean("userService");
		OrderService orderService = (OrderService)getCtx().getBean("orderService");
		User user = userService.load(Integer.parseInt(id));
		if (user == null) {
			resp.setStatus(HttpStatus.NOT_FOUND.value());
			return;
		}
		List<Order> orders = orderService.getByUserId(Integer.parseInt(id));
		List<OrderDto> orderDtos = new ArrayList<OrderDto>();
		//如果有预约记录
		if(!orders.isEmpty()){
			for (Order order : orders) {
				OrderDto orderDto = new OrderDto(order);
				orderDtos.add(orderDto);
			}
			JSONArray jsonArray = new JSONArray(orderDtos);
			Map<String, Object> dataMap = new HashMap<String, Object>();
			dataMap.put("data", jsonArray);
			dataMap.put("token", token);
			PrintWriter writer = resp.getWriter();
			writer.println(new JSONObject(dataMap));
			writer.close();
		}else {
			Map<String, Object> dataMap = new HashMap<String, Object>();
			dataMap.put("data", "empty");
			dataMap.put("token", "");
			PrintWriter writer = resp.getWriter();
			writer.println(new JSONObject(dataMap));
			writer.close();
		}
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		resp.setCharacterEncoding("utf-8");
		req.setCharacterEncoding("utf-8");
		resp.setContentType("application/json; charset=utf-8");
		System.out.println("in post");
		try {
			String token = req.getHeader("Access-Token");
			Jedis jedis = RedisUtil.getJedis();
			String id = jedis.get(token);
			String payload = RequestUtil.getRequestBody(req);
			JSONObject jsonObject = new JSONObject(payload);
			System.out.println("id: " + id + " ,body:" + jsonObject.toString());
			
			StakeService stakeService = (StakeService) getCtx().getBean("stakeService");
			OrderService orderService = (OrderService) getCtx().getBean("orderService");
			UserService userService = (UserService) getCtx().getBean("userService");
			int stakeId = Integer.parseInt(jsonObject.getString("stakeId"));
			Stake stake = stakeService.load(stakeId);
			User user = userService.load(Integer.parseInt(id));
			// 如果用户和桩都存在
			if (user != null && stake != null) {
				
				int type = 0;//默认为订单未使用
				Timestamp start_time = Timestamp.valueOf(jsonObject.getString("startTime"));
				Timestamp end_time = Timestamp.valueOf(jsonObject.getString("endTime"));
				Order order = new Order(user, stake, start_time, end_time, type);
				//注册成功
				if(orderService.addOrder(order)){
					OrderDto orderDto = new OrderDto(order);
					JSONObject jsDto = new JSONObject(orderDto);
					Map<String, Object> dataMap = new HashMap<String, Object>();
					dataMap.put("token", token);
					dataMap.put("data", jsDto);
					PrintWriter writer = resp.getWriter();
					writer.println(new JSONObject(dataMap));
					writer.close();
					resp.setStatus(HttpStatus.OK.value());
				}else{
					JSONObject resObject = new JSONObject();
					resObject.put("data", "orderFail");
                    resObject.put("token", "");
					resp.getWriter().println(resObject);
                    resp.setStatus(HttpStatus.OK.value());
				}
			}else{
				JSONObject resObject = new JSONObject();
				resObject.put("data", "noSuchStakeOrUserFail");
				resObject.put("token", "");
				resp.getWriter().println(resObject);
                resp.setStatus(HttpStatus.OK.value());
			}
		} catch (JSONException e) {
			e.printStackTrace();
			System.out.println("error");
			resp.setStatus(HttpStatus.UNAUTHORIZED.value());
		}
	}
}
