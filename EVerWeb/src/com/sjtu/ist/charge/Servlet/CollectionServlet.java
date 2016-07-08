package com.sjtu.ist.charge.Servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Timestamp;
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

import com.sjtu.ist.charge.Dto.json.CollectionDto;
import com.sjtu.ist.charge.Model.Collection;
import com.sjtu.ist.charge.Service.CollectionService;
import com.sjtu.ist.charge.Service.StakeService;
import com.sjtu.ist.charge.Service.UserService;
import com.sjtu.ist.charge.Util.RedisUtil;
import com.sjtu.ist.charge.Util.RequestUtil;


import redis.clients.jedis.Jedis;

/**
 * 用户的收藏信息 Method：Get uri: /api/user/collection.servlet
 * 
 * @author Administrator
 * 
 */
public class CollectionServlet extends BaseServlet {

	@Override
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		try {
			String token = request.getHeader("Access-Token");
			Jedis jedis = RedisUtil.getJedis();
			String id = jedis.get(token);
			CollectionService colService = (CollectionService) getCtx()
					.getBean("collectionService");
			List<Collection> cols = colService
					.getColByUId(Integer.parseInt(id));
			List<CollectionDto> collectionDtos = new ArrayList<CollectionDto>(
					cols.size());
			// 该用户有收藏的车桩
			if (cols.size() != 0) {
				for (Collection col : cols) {
					CollectionDto collectionDto = new CollectionDto(col);
					collectionDtos.add(collectionDto);
				}
				JSONArray jsonArr = new JSONArray(collectionDtos);
				Map<String, Object> dataMap = new HashMap<String, Object>();
				dataMap.put("data", jsonArr);
				dataMap.put("token", token);

				PrintWriter writer = response.getWriter();
				writer.println(new JSONObject(dataMap));
				writer.close();
				response.setStatus(HttpStatus.OK.value());
			}else {
				Map<String, Object> dataMap = new HashMap<String, Object>();
				dataMap.put("data", "empty");
                dataMap.put("token", "");
				PrintWriter writer = response.getWriter();
				writer.println(new JSONObject(dataMap));
				writer.close();
				response.setStatus(HttpStatus.OK.value());
			}

		} catch (Exception e) {
			response.setStatus(HttpStatus.NOT_FOUND.value());
		}
	}

	@Override
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String token = request.getHeader("Access-Token");
		Jedis jedis = RedisUtil.getJedis();
		String id = jedis.get(token);
		CollectionService colService = (CollectionService) getCtx().getBean(
				"collectionService");
		UserService userService = (UserService) getCtx().getBean("userService");
		StakeService stakeService = (StakeService) getCtx().getBean(
				"stakeService");
		String payload = RequestUtil.getRequestBody(request);
		try {
			JSONObject jsonObject = new JSONObject(payload);
			int stake_id = Integer.parseInt(jsonObject.getString("stakeId"));
			Timestamp time = Timestamp.valueOf(jsonObject.getString("time"));
			Collection collection = new Collection(userService.load(Integer
					.parseInt(id)), stakeService.load(stake_id), time);
			// 添加收藏成功
			if (colService.addCollection(collection)) {
				CollectionDto collectionDto = new CollectionDto(collection);
				JSONObject jsDto = new JSONObject(collectionDto);
				Map<String, Object> dataMap = new HashMap<String, Object>();
				dataMap.put("token", token);
				dataMap.put("data", jsDto);
				response.setStatus(HttpStatus.OK.value());
				response.getWriter().println(new JSONObject(dataMap));
			} else {
				Map<String, Object> dataMap = new HashMap<String, Object>();
				dataMap.put("data", "error");
				dataMap.put("token", "");
				PrintWriter writer = response.getWriter();
				writer.println(new JSONObject(dataMap));
				writer.close();
				response.setStatus(HttpStatus.OK.value());
			}

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			response.setStatus(HttpStatus.UNAUTHORIZED.value());
		}
	}
	@Override
	protected void doPut(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String token = request.getHeader("Access-Token");
		Jedis jedis = RedisUtil.getJedis();
		String id = jedis.get(token);
		CollectionService colService = (CollectionService) getCtx().getBean("collectionService");
		UserService userService = (UserService) getCtx().getBean("userService");
		String payload = RequestUtil.getRequestBody(request);
		try {
			//用户存在
			if(userService.load(Integer.parseInt(id))!=null){
				JSONObject jsonObject = new JSONObject(payload);
				int stake_id = Integer.parseInt(jsonObject.getString("stakeId"));
				Collection collection = colService.getByUserAndStakeId(Integer.parseInt(id), stake_id);
				if(colService.deleteCollection(collection)){
					Map<String, Object> dataMap = new HashMap<String, Object>();
					dataMap.put("data", "deleteSuccess");
					PrintWriter writer = response.getWriter();
					writer.println(new JSONObject(dataMap));
					writer.close();
					response.setStatus(HttpStatus.OK.value());
				}
				else {
					Map<String, Object> dataMap = new HashMap<String, Object>();
					dataMap.put("data", "deleteFail");
					PrintWriter writer = response.getWriter();
					writer.println(new JSONObject(dataMap));
					writer.close();
					response.setStatus(HttpStatus.OK.value());
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
