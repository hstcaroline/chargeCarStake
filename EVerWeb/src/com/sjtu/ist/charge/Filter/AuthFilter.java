package com.sjtu.ist.charge.Filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;

import com.sjtu.ist.charge.Util.RedisUtil;


import redis.clients.jedis.Jedis;

public class AuthFilter implements Filter {

	private String loginUrl;
	private String registerUrl;
	private String forgetPswUrl;
	
	public void destroy() {
		
	}

	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse resp = (HttpServletResponse) response;
		System.out.println(req.getRequestURI() + " " + req.getMethod());
		
		if (req.getRequestURI().contains(loginUrl) || req.getRequestURI().contains(registerUrl) || req.getRequestURI().contains(forgetPswUrl)) {
			chain.doFilter(request, response);
		} else {
			String token = req.getHeader("Access-Token");
			if (token == null) {
				System.out.println("token is " + token);
				resp.setStatus(HttpStatus.UNAUTHORIZED.value());
			} else {
				Jedis jedis = RedisUtil.getJedis();
				if (jedis.get(token) == null) {
					resp.setStatus(HttpStatus.UNAUTHORIZED.value());
				} else {
					chain.doFilter(request, response);
				}
			} 
		}
	}

	public void init(FilterConfig filterConfig) throws ServletException {
		loginUrl = filterConfig.getInitParameter("loginUrl");
		registerUrl = filterConfig.getInitParameter("registerUrl");
		forgetPswUrl = filterConfig.getInitParameter("forgetPswUrl");
	}

}
