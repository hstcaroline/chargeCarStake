package com.sjtu.ist.charge.Servlet;

import java.io.IOException;
import java.io.PrintWriter;
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

import com.sjtu.ist.charge.Dto.json.MessageDto;
import com.sjtu.ist.charge.Model.Message;
import com.sjtu.ist.charge.Model.User;
import com.sjtu.ist.charge.Service.MessageService;
import com.sjtu.ist.charge.Service.UserService;
import com.sjtu.ist.charge.Util.RedisUtil;
import com.sjtu.ist.charge.Util.RequestUtil;

import redis.clients.jedis.Jedis;


/**
 * 获取某个用户的所有未查看的某类消息 url:/api/user/message.servlet method : put
 * 修改某一条消息为已读  url:/api/user/message.servlet method : post
 * @author 黄顺婷
 * 
 */
public class MessageServlet extends BaseServlet {

	@Override
	protected void doPut(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String payload = RequestUtil.getRequestBody(request);
		try {
			JSONObject jsonObject = new JSONObject(payload);
			String token = request.getHeader("Access-Token");
			Jedis jedis = RedisUtil.getJedis();
			String id = jedis.get(token);
			UserService userService = (UserService) getCtx().getBean("userService");
			MessageService messageService = (MessageService) getCtx().getBean("messageService");
			User user = userService.load(Integer.parseInt(id));
			// 如果该用户存在
			if (user != null) {
				int type = Integer.parseInt(jsonObject.getString("type"));
				List<Message> messages = messageService.showUnsolvedMsg(user.getId(), type);
				List<MessageDto> messageDtos = new ArrayList<MessageDto>();
				//如果存在未处理的该类消息
                if (!messages.isEmpty()) {
					for (Message message : messages) {
						MessageDto messageDto = new MessageDto(message);
						messageDtos.add(messageDto);
					}
					JSONArray jsonArray = new JSONArray(messageDtos);
					Map<String, Object> dataMap = new HashMap<String, Object>();
					dataMap.put("token", token);
					dataMap.put("data", jsonArray);
					PrintWriter writer = response.getWriter();
					writer.println(new JSONObject(dataMap));
					writer.close();
					response.setStatus(HttpStatus.OK.value());
				}else {
					Map<String, Object> dataMap = new HashMap<String, Object>();
					dataMap.put("data", "empty");
					dataMap.put("token", "");
					response.getWriter().println(new JSONObject(dataMap));
	                response.setStatus(HttpStatus.OK.value());
				}
			} else {
                response.setStatus(HttpStatus.UNAUTHORIZED.value());
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("error");
			response.setStatus(HttpStatus.UNAUTHORIZED.value());
		}
	}
	@Override
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String payload = RequestUtil.getRequestBody(request);
		try {
			JSONObject jsonObject = new JSONObject(payload);
			String token = request.getHeader("Access-Token");
			Jedis jedis = RedisUtil.getJedis();
			String id = jedis.get(token);
			UserService userService = (UserService) getCtx().getBean("userService");
			MessageService messageService = (MessageService) getCtx().getBean("messageService");
			User user = userService.load(Integer.parseInt(id));
			// 如果该用户存在
			if (user != null) {
				int message_id = Integer.parseInt(jsonObject.getString("messageId"));
				Message message = messageService.load(message_id);
				//该条消息存在，且是发给对应用户的
				if(message!=null && message.getUserByToId().getId()==user.getId()){
					//将该消息设置为已处理
					message.setDone(1);
					Map<String, Object> dataMap = new HashMap<String, Object>();
					//更新成功
					if(messageService.updateMsg(message)){
						dataMap.put("data", "updateSuccess");
					}else {
						dataMap.put("data", "updateFail");
					}
					response.getWriter().println(new JSONObject(dataMap));
	                response.setStatus(HttpStatus.OK.value());
				}else {
					response.setStatus(HttpStatus.NOT_FOUND.value());
				}
			}else {
				response.setStatus(HttpStatus.UNAUTHORIZED.value());
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
