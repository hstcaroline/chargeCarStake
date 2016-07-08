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
import com.sjtu.ist.charge.Util.TokenGenrerate;

import redis.clients.jedis.Jedis;


/**
 * 用户登录 url:/api/account/login.servlet method:post response：返回一个map，key分别为token和data
 * token用作登录标识，data为user信息
 * 
 * @author lizheming
 * 
 */
public class LoginServlet extends BaseServlet {

    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setStatus(HttpStatus.METHOD_NOT_ALLOWED.value());
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=utf-8");
        String payloadRequest = RequestUtil.getRequestBody(request);
        System.out.println(payloadRequest);
        Map<String, Object> dataMap = new HashMap<String, Object>();
        try {
            JSONObject jsonObject = new JSONObject(payloadRequest);
            String telephone = jsonObject.getString("telephone");
            String password = jsonObject.getString("password");
            if (telephone != null && password != null) {
                UserService userService = (UserService) getCtx().getBean("userService");
                User user = userService.getUserByTelAndPsw(telephone, password);
                if (user != null) {
                    Jedis jedis = RedisUtil.getJedis();
                    String token = TokenGenrerate.getToken(telephone);
                    // jedis.expire(token, 60 * 60 * 24 * 1);
                    jedis.set(token, user.getId() + "");
                    UserDto dto = new UserDto(user);
                    JSONObject jsDto = new JSONObject(dto);
                    
                    dataMap.put("token", token);
                    dataMap.put("data", jsDto);
                } else {
                    //response.setStatus(HttpStatus.NOT_FOUND.value());
                	 dataMap.put("token", "");
                     dataMap.put("data", "null");
                }
            } else {
                //response.setStatus(HttpStatus.UNAUTHORIZED.value());
            	dataMap.put("token", "");
                dataMap.put("data", "null");
            }
        } catch (JSONException e) {
            e.printStackTrace();
            //response.setStatus(HttpStatus.UNAUTHORIZED.value());
            dataMap.put("token", "");
            dataMap.put("data", "null");
        }
        response.setStatus(HttpStatus.OK.value());
        response.getWriter().println(new JSONObject(dataMap));
    }

    /*
     * public void doPost(HttpServletRequest request, HttpServletResponse response) throws
     * ServletException, IOException { System.out.println("URI: " + request.getRequestURI());
     * 
     * String payloadRequest = RequestUtil.getRequestBody(request); try { JSONObject jsonObject =
     * new JSONObject(payloadRequest); //response.getWriter().println(jsonObject.toString()); String
     * username = jsonObject.getString("username"); String password =
     * jsonObject.getString("password"); System.out.println(username + " " + password); if (username
     * != null && password != null) { UserService userService = (UserService)
     * getCtx().getBean("userService"); User user = userService.validLogin(username, password);
     * Map<String, Object> dataMap = new HashMap<String, Object>(); if (user != null) { Jedis jedis
     * = RedisUtil.getJedis(); String token = TokenGenrerate.getToken(username);
     * //jedis.expire(token, 60 * 60 * 24 * 1); jedis.set(token, user.getId()+"");
     * 
     * UserDto userDto = new UserDto(user); JSONObject userJson = new JSONObject(userDto);
     * dataMap.put("token", token); dataMap.put("user", userJson);
     * 
     * response.setCharacterEncoding("UTF-8");
     * response.setContentType("application/json; charset=utf-8");
     * response.setStatus(HttpStatus.OK.value()); response.getWriter().println(new
     * JSONObject(dataMap)); response.getWriter().flush(); } else {
     * response.setStatus(HttpStatus.NOT_FOUND.value()); } } else {
     * response.setStatus(HttpStatus.UNAUTHORIZED.value()); }
     * 
     * } catch (JSONException e) { response.setStatus(HttpStatus.UNAUTHORIZED.value());
     * e.printStackTrace(); } }
     */
}
