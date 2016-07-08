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
import com.sjtu.ist.charge.Dto.json.JudgeDto;
import com.sjtu.ist.charge.Model.Collection;
import com.sjtu.ist.charge.Model.Judge;
import com.sjtu.ist.charge.Service.CollectionService;
import com.sjtu.ist.charge.Service.JudgeService;
import com.sjtu.ist.charge.Service.StakeService;
import com.sjtu.ist.charge.Service.UserService;
import com.sjtu.ist.charge.Util.RedisUtil;
import com.sjtu.ist.charge.Util.RequestUtil;


import redis.clients.jedis.Jedis;

/**
 * 车桩的评价记录 Method：put uri: /api/account/judge.servlet
 * @author huangshunting
 * 
 */
public class JudgeServlet extends BaseServlet {

	@Override
	protected void doPut(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		try {
			String payload = RequestUtil.getRequestBody(request);
			JSONObject jsonObject = new JSONObject(payload);
			int stake_id = Integer.parseInt(jsonObject.getString("stakeId"));
			JudgeService judgeService = (JudgeService)getCtx().getBean("judgeService");
			List<Judge>judges = judgeService.getJudgeBystakeId(stake_id);
			List<JudgeDto> judgeDtos = new ArrayList<JudgeDto>(judges.size());
			// 该车桩有评价
			if (judges.size() != 0) {
				for (Judge judge : judges) {
					JudgeDto judgeDto = new JudgeDto(judge);
					judgeDtos.add(judgeDto);
				}
				JSONArray jsonArr = new JSONArray(judgeDtos);
				Map<String, Object> dataMap = new HashMap<String, Object>();
				dataMap.put("data", jsonArr);
				PrintWriter writer = response.getWriter();
				writer.println(new JSONObject(dataMap));
				writer.close();
				response.setStatus(HttpStatus.OK.value());
			}else {
				Map<String, Object> dataMap = new HashMap<String, Object>();
				dataMap.put("data", "empty");
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
}
