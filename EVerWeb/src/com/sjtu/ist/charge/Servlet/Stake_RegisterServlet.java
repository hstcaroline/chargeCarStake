package com.sjtu.ist.charge.Servlet;

import java.io.IOException;
import java.sql.Time;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.http.HttpStatus;

import com.sjtu.ist.charge.Model.CarType;
import com.sjtu.ist.charge.Model.Stake;
import com.sjtu.ist.charge.Model.User;
import com.sjtu.ist.charge.Service.CarTypeService;
import com.sjtu.ist.charge.Service.StakeService;
import com.sjtu.ist.charge.Service.UserService;
import com.sjtu.ist.charge.Util.RedisUtil;
import com.sjtu.ist.charge.Util.RequestUtil;

import redis.clients.jedis.Jedis;


/**
 * 用户注册 url:/api/user/stakeRegister.servlet method : post
 * 
 * @author huangshunting
 * 
 */
public class Stake_RegisterServlet extends BaseServlet {

	@Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException,
            IOException {
        resp.setStatus(HttpStatus.METHOD_NOT_ALLOWED.value());
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
    	resp.setCharacterEncoding("utf-8");  
    	req.setCharacterEncoding("utf-8");    
	    resp.setContentType("application/json; charset=utf-8");
        try {
        	String token = req.getHeader("Access-Token");
			Jedis jedis = RedisUtil.getJedis();
			String id = jedis.get(token);
			String payload = RequestUtil.getRequestBody(req);
			System.out.println("payload: " + payload);
            JSONObject jsonObject = new JSONObject(payload);
            System.out.println(jsonObject.toString());
            CarTypeService carTypeService = (CarTypeService) getCtx().getBean("carTypeService");
            StakeService stakeService = (StakeService) getCtx().getBean("stakeService");
            UserService userService = (UserService) getCtx().getBean("userService");
            //获取到的车桩注册信息
            double longitude = Double.parseDouble(jsonObject.getString("longitude"));
            double latitude = Double.parseDouble(jsonObject.getString("latitude"));
            int carType_id = Integer.parseInt(jsonObject.getString("carType_id"));
            CarType carType = carTypeService.load(carType_id);
            Time available_stime = Time.valueOf(jsonObject.getString("available_stime")); 
            Time available_etime = Time.valueOf(jsonObject.getString("available_etime")); 
            String description = jsonObject.getString("description");
            int status = 4;//注册车桩默认为未审核状态
            double price = Double.parseDouble(jsonObject.getString("price"));
            int type = Integer.parseInt(jsonObject.getString("type"));
            String address = jsonObject.getString("address");
            //添加车桩
            Stake newStake = new Stake(carType, longitude, latitude, available_stime, available_etime, status, type, price, description,address);
            User user = userService.load(Integer.parseInt(id));
            //如果用户存在
            if(user != null){
            	 newStake.setUser(user);
            	 //注册成功
            	 if(stakeService.stakeRegister(newStake)){
                 	//将user修改为拥有车桩
            		 user.setHaveStake(1);
                 	 userService.updateUser(user);
                 	 JSONObject resObject = new JSONObject();
                     resObject.put("register", "success");
                     resp.getWriter().println(resObject);
                     resp.setStatus(HttpStatus.OK.value());
                 }
            }else {
            	JSONObject resObject = new JSONObject();
                resObject.put("register", "fail");
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
