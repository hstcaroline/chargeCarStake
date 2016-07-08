package com.sjtu.ist.charge.Servlet;

import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.http.HttpStatus;

import com.sjtu.ist.charge.Dto.json.UserDto;
import com.sjtu.ist.charge.Model.User;
import com.sjtu.ist.charge.Service.UserService;


public class TestServlet extends BaseServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
//		resp.setCharacterEncoding("UTF-8");  
//	    resp.setContentType("application/json; charset=utf-8");
	    
		UserService userService = (UserService) getCtx().getBean("userService");
		User user = userService.load(4);
		UserDto userDto = new UserDto(user);
		JSONObject json = new JSONObject(userDto);
		resp.getWriter().println(json);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		resp.setCharacterEncoding("UTF-8");  
	    resp.setContentType("application/json; charset=utf-8");
	    
		Set<UserDto> set = new HashSet<UserDto>();
		set.add(new UserDto("123"));
		set.add(new UserDto("234"));
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("data", set);
		map.put("token", "123");
		System.out.println("begin");
		JSONObject js = new JSONObject(map);
		System.out.println(js);
		resp.getWriter().println(js);
		
	}
}
