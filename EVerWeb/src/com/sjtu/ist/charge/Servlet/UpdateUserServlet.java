package com.sjtu.ist.charge.Servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;
import org.springframework.http.HttpStatus;

import com.sjtu.ist.charge.Dto.json.UserDto;
import com.sjtu.ist.charge.Model.User;
import com.sjtu.ist.charge.Service.UserService;
import com.sjtu.ist.charge.Util.JsonUtil;
import com.sjtu.ist.charge.Util.RedisUtil;
import com.sjtu.ist.charge.Util.RequestUtil;

import redis.clients.jedis.Jedis;


public class UpdateUserServlet extends BaseServlet {
	
	@Override
	protected void doPut(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String payloadRequest = RequestUtil.getRequestBody(request);
		try {
			JSONObject jsonObject = new JSONObject(payloadRequest);
			String token = request.getHeader("Access-Token");
			Jedis jedis = RedisUtil.getJedis();
			String id = jedis.get(token);
			
			UserService userService = (UserService) getCtx().getBean("userService");
			User user = userService.load(Integer.parseInt(id));
			Class<?> clazz = user.getClass();
			
			Field[] fields = clazz.getDeclaredFields();
			for (Field field : fields) {
				String fname = field.getName();
				String tname = field.getType().getName();
				if ("java.util.Set".equals(tname) || !JsonUtil.isExist(jsonObject, fname)) {
					continue;
				}
				//首字母大写
				String Ufname = fname.substring(0,1).toUpperCase() + fname.substring(1);
				Class[] param = new Class[1];
				param[0] = field.getType();
				Method method = clazz.getMethod("set" + Ufname,param);
				String value = (String) (jsonObject.get(fname));
				if("telephone".equals(fname)){
					String telephone = value;
					//如果修改后的手机已被注册过
					if(userService.getUserByTel(telephone)!=null)
					{
						Map<String, Object> dataMap = new HashMap<String, Object>();
						dataMap.put("data", "telphoneAlreadyRegister");
			            dataMap.put("token", "");
						PrintWriter writer = response.getWriter();
						writer.println(new JSONObject(dataMap));
						writer.close();
						response.setStatus(HttpStatus.OK.value());
						return ;
					}
					method.invoke(user, telephone);
					continue;
				}
				if ("java.lang.Integer".equals(tname)) {
					int v = Integer.parseInt(value);
					method.invoke(user,v);
				} else if ("java.lang.Double".equals(tname)) {
					double v = Double.parseDouble(value);
					method.invoke(user, v);
				} else if ("java.lang.String".equals(tname)) {
					method.invoke(user, value);
				}
				System.out.println(field.getName() + ":");
			}
			
			userService.updateUser(user);
			UserDto udto = new UserDto(user);
			Map<String, Object> dataMap = new HashMap<String, Object>();
			dataMap.put("data", udto);
            dataMap.put("token", token);
			
			PrintWriter writer = response.getWriter();
			writer.println(new JSONObject(dataMap));
			writer.close();
			response.setStatus(HttpStatus.OK.value());
		} catch (Exception e) {
			e.printStackTrace();
			response.setStatus(HttpStatus.NOT_MODIFIED.value());
		}
	}
}
