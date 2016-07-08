package com.sjtu.ist.charge.Servlet;

import java.io.IOException;
import java.io.PrintWriter;
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

import redis.clients.jedis.Jedis;


/**
 * 根据token获得用户信息  url：domain+/api/user/userInfo.servlet method:get
 * @author Ister
 *
 */
public class UserServlet extends BaseServlet {

	/*@Override
	protected void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String userName = request.getParameter("userName");
		String passWord = request.getParameter("passWord");
		System.out.println("android login: userName is " + userName + ",passWord is " + passWord);
		UserService userService = (UserService) getCtx().getBean("userService");
		int userId = userService.validLogin(userName, passWord).getId(); 
		if (userId != -1) {
			//表明用户登录成功，加入到session�?			request.getSession(true).setAttribute("userId", userId);
			
			User user = userService.load(userId);
			request.getSession(true).setAttribute("user", user);
		}
		try {
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("userId", userId);
			response.getWriter().println(jsonObject);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}*/
	@Override
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String token = request.getHeader("Access-Token");
		Jedis jedis = RedisUtil.getJedis();
		String id = jedis.get(token);
		UserService userService = (UserService) getCtx().getBean("userService");
		User user = userService.load(Integer.parseInt(id));
		if(user != null){
			UserDto userDto = new UserDto(user);
			JSONObject jsonObject = new JSONObject(userDto);
			Map<String, Object> dataMap = new HashMap<String, Object>();
			dataMap.put("data", jsonObject);
            dataMap.put("token", token);
			PrintWriter writer = response.getWriter();
			writer.println(new JSONObject(dataMap));
			writer.close();
			response.setStatus(HttpStatus.OK.value());
		}else {
			response.setStatus(HttpStatus.UNAUTHORIZED.value());
		}
	}

}
