package com.sjtu.ist.charge.Servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;

import com.sjtu.ist.charge.Util.RedisUtil;


import redis.clients.jedis.Jedis;

public class LogoutServlet extends BaseServlet {

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setStatus(HttpStatus.METHOD_NOT_ALLOWED.value());
	}
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String token = request.getHeader("Access-Token");
		Jedis jedis = RedisUtil.getJedis();
		if (jedis != null) {
			jedis.del(token);
			response.setStatus(HttpStatus.OK.value());
		} else {
			response.setStatus(HttpStatus.UNAUTHORIZED.value());
		}
	}
}
