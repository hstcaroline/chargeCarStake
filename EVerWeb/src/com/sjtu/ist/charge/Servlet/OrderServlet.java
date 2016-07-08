package com.sjtu.ist.charge.Servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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


public class OrderServlet extends BaseServlet {

	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String payload = RequestUtil.getRequestBody(req);
		String token = req.getHeader("Access-Token");
		Jedis jedis = RedisUtil.getJedis();
		String id = jedis.get(token);
		UserService userService = (UserService) getCtx().getBean("userService");
		User user = userService.load(Integer.parseInt(id));
		if (user == null) {
			resp.setStatus(HttpStatus.NOT_FOUND.value());
			return;
		}
		try {
			Map<String, Object> dataMap = new HashMap<String, Object>();
			JSONObject jsonObject = new JSONObject(payload);
			String stakeId = jsonObject.getString("stakeId");
			String stime = jsonObject.getString("startTime");
			String etime = jsonObject.getString("endTime");
			Timestamp startTime = Timestamp.valueOf(stime);
			Timestamp endTime = Timestamp.valueOf(etime);
			StakeService stakeService = (StakeService) getCtx().getBean("stakeService");
			Stake stake = stakeService.load(Integer.parseInt(stakeId));
			if (stake == null) {
				dataMap.put("token", "");
				dataMap.put("data", "orderFail");
			} else {
				try {
					boolean succeed = stakeService.isAvailable(stime, etime, Integer.parseInt(stakeId));
					if (succeed) {
						int nstatus = stake.getStatus() + 1;
						nstatus = Math.max(6, nstatus);
						stake.setStatus(nstatus);
						stakeService.update(stake);
						OrderService orderService = (OrderService) getCtx().getBean("orderService");
						Order order = new Order(user, stake, startTime, endTime, 0);
						orderService.addOrder(order);
						OrderDto orderDto = new OrderDto(order);
						JSONObject json = new JSONObject(orderDto);
						dataMap.put("token", token);
						dataMap.put("data", json);
					} else {
						dataMap.put("token", "");
						dataMap.put("data", "orderFail");
					}
				} catch (Exception e) {
					dataMap.put("token", "");
					dataMap.put("data", "orderFail");
				}
			}
			PrintWriter writer = resp.getWriter();
			writer.println(new JSONObject(dataMap));
			writer.close();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			resp.setStatus(HttpStatus.NOT_FOUND.value());
		}
	}
}
