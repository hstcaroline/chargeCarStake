package com.sjtu.ist.charge.Servlet;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.http.HttpStatus;

import com.sjtu.ist.charge.Dto.json.UserDto;
import com.sjtu.ist.charge.Model.User;
import com.sjtu.ist.charge.Service.UserService;
import com.sjtu.ist.charge.Util.RedisUtil;
import com.sjtu.ist.charge.Util.RequestUtil;

import redis.clients.jedis.Jedis;


/**
 * 用户密码修改 url:/api/account/updatePsw.servlet method:post
 * response：返回一个map，key分别为token和data token用作登录标识，data为user信息
 * 
 * @author huangshunting
 * 
 */
public class UpdatePswServlet extends BaseServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setStatus(HttpStatus.METHOD_NOT_ALLOWED.value());
	}

	@Override
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		response.setCharacterEncoding("UTF-8");
		response.setContentType("application/json; charset=utf-8");
		String payloadRequest = RequestUtil.getRequestBody(request);

		try {
			String token = request.getHeader("Access-Token");
			Jedis jedis = RedisUtil.getJedis();
			String id = jedis.get(token);
			JSONObject jsonObject = new JSONObject(payloadRequest);
			String type = jsonObject.getString("type");
			String password = jsonObject.getString("password");
			UserService userService = (UserService) getCtx().getBean(
					"userService");
			User user = userService.load(Integer.parseInt(id));
			// 验证旧密码
			if (type.equals("0")) {
				if (user != null && user.getPassword().equals(password)) {
					UserDto dto = new UserDto(user);
					JSONObject jsDto = new JSONObject(dto);
					Map<String, Object> dataMap = new HashMap<String, Object>();
					dataMap.put("token", token);
					dataMap.put("data", jsDto);
					response.setStatus(HttpStatus.OK.value());
					response.getWriter().println(new JSONObject(dataMap));
				} else {
					Map<String, Object> dataMap = new HashMap<String, Object>();
					dataMap.put("token", "");
					dataMap.put("data", "pswError");
					System.err.println("in pswError");
					response.getWriter().println(new JSONObject(dataMap));
					response.setStatus(HttpStatus.OK.value());
				}
			}
			// 修改密码
			else if (type.equals("1") && user != null) {
				user.setPassword(password);
				if (userService.updateUser(user)) {
					UserDto dto = new UserDto(user);
					JSONObject jsDto = new JSONObject(dto);
					Map<String, Object> dataMap = new HashMap<String, Object>();
					dataMap.put("token", token);
					dataMap.put("data", jsDto);
					response.setStatus(HttpStatus.OK.value());
					response.getWriter().println(new JSONObject(dataMap));
				} else {
					response.setStatus(HttpStatus.NOT_FOUND.value());
				}
			}
		} catch (JSONException e) {
			e.printStackTrace();
			response.setStatus(HttpStatus.UNAUTHORIZED.value());
		}
	}
}
