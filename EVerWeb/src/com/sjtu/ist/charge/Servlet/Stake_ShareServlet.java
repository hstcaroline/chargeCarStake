package com.sjtu.ist.charge.Servlet;

import java.io.IOException;
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

import com.sjtu.ist.charge.Dto.json.StakeDto;
import com.sjtu.ist.charge.Model.Stake;
import com.sjtu.ist.charge.Model.User;
import com.sjtu.ist.charge.Service.StakeService;
import com.sjtu.ist.charge.Service.UserService;
import com.sjtu.ist.charge.Util.RedisUtil;
import com.sjtu.ist.charge.Util.RequestUtil;

import redis.clients.jedis.Jedis;


/**
 * 用户注册 url:/api/user/stakeShare.servlet method : post
 * 
 * @author huangshunting
 * 
 */
public class Stake_ShareServlet extends BaseServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
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
			JSONObject jsonObject = new JSONObject(payload);
			System.out.println("id: " + id + " ,body:" + jsonObject.toString());
			StakeService stakeService = (StakeService) getCtx().getBean(
					"stakeService");
			UserService userService = (UserService) getCtx().getBean(
					"userService");
			int type = Integer.parseInt(jsonObject.getString("type"));
			User user = userService.load(Integer.parseInt(id));
			// 如果用户存在且有车桩
			if (user != null && user.getHaveStake() == 1) {
				//请求为查看所有状态为status的车桩
				if (type == 0) {
					int status = Integer.parseInt(jsonObject.getString("status"));// 根据状态显示当前用户的车桩列表
					List<Stake> stakes = stakeService.getByOwnerAndStatus(
							user.getId(), status);
					List<StakeDto> stakeDtos = new ArrayList<StakeDto>(
							stakes.size());
					//该用户无此状态的车桩
					if(!stakes.isEmpty()){
						for (Stake stake : stakes) {
							StakeDto stakeDto = new StakeDto(stake);
							stakeDtos.add(stakeDto);
						}
						JSONArray jsonArr = new JSONArray(stakeDtos);
						Map<String, Object> dataMap = new HashMap<String, Object>();
						dataMap.put("token", token);
						dataMap.put("data", jsonArr);
						resp.setStatus(HttpStatus.OK.value());
						resp.getWriter().println(new JSONObject(dataMap));
					}else {
						JSONObject resObject = new JSONObject();
						resObject.put("data", "empty");
                        resObject.put("token", "");
						resp.getWriter().println(resObject);
						resp.setStatus(HttpStatus.OK.value());
					}
					
				}
				// 如果是查看某一车桩
				else if (type == 1) {
					int stakeId = Integer.parseInt(jsonObject.getString("stake_id"));
					Stake stake = stakeService.load(stakeId);
					StakeDto stakeDto = new StakeDto(stake);
					JSONObject jsDto = new JSONObject(stakeDto);
					Map<String, Object> dataMap = new HashMap<String, Object>();
                    dataMap.put("token", token);
                    dataMap.put("data", jsDto);
                    resp.setStatus(HttpStatus.OK.value());
                    resp.getWriter().println(new JSONObject(dataMap));
				}
				//如果是分享某一车桩
				else if(type == 2){
					int stakeId = Integer.parseInt(jsonObject.getString("stake_id"));
					int status = Integer.parseInt(jsonObject.getString("status"));
					Stake stake = stakeService.load(stakeId);
					stake.setStatus(status);
					if(stakeService.update(stake)){
						StakeDto stakeDto = new StakeDto(stake);
						JSONObject jsDto = new JSONObject(stakeDto);
						Map<String, Object> dataMap = new HashMap<String, Object>();
	                    dataMap.put("token", token);
	                    dataMap.put("data", jsDto);
	                    resp.setStatus(HttpStatus.OK.value());
	                    resp.getWriter().println(new JSONObject(dataMap));
					}else {
						JSONObject resObject = new JSONObject();
						resObject.put("data", "shareStakeFail");
						resObject.put("token", "");
						resp.getWriter().println(resObject);
						resp.setStatus(HttpStatus.UNAUTHORIZED.value());
					}
				}
				else{
					JSONObject resObject = new JSONObject();
					resObject.put("data", "noStakeFail");
                    resObject.put("token", "");
					resp.getWriter().println(resObject);
                    resp.setStatus(HttpStatus.OK.value());
				}
			}

		} catch (JSONException e) {
			e.printStackTrace();
			System.out.println("error");
			resp.setStatus(HttpStatus.UNAUTHORIZED.value());
		}
	}
}
