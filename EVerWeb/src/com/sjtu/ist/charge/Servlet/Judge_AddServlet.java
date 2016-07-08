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

import com.sjtu.ist.charge.Model.Judge;
import com.sjtu.ist.charge.Model.UseStake;
import com.sjtu.ist.charge.Service.JudgeService;
import com.sjtu.ist.charge.Service.UseStakeService;
import com.sjtu.ist.charge.Util.RedisUtil;
import com.sjtu.ist.charge.Util.RequestUtil;


import redis.clients.jedis.Jedis;

/**
 * 添加车桩的评价记录 Method：post uri: /api/user/judgeAdd.servlet
 * @author huangshunting
 * 
 */
public class Judge_AddServlet extends BaseServlet {

	@Override
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String token = request.getHeader("Access-Token");
		Jedis jedis = RedisUtil.getJedis();
		String id = jedis.get(token);
		JudgeService judgeService = (JudgeService)getCtx().getBean("judgeService");
		UseStakeService useStakeService = (UseStakeService)getCtx().getBean("useStakeService");
		
		String payload = RequestUtil.getRequestBody(request);
		try {
			JSONObject jsonObject = new JSONObject(payload);
			int useStake_id = Integer.parseInt(jsonObject.getString("useStakeId"));
			String content = jsonObject.getString("content");
			double grade = Double.parseDouble(jsonObject.getString("grade"));
			Timestamp time = Timestamp.valueOf(jsonObject.getString("time"));
			UseStake useStake = useStakeService.load(useStake_id);
			int stakeId = useStake.getStake().getId();
			Judge judge = new Judge(useStake, grade, content, time, stakeId);
			// 添加评价成功
			if (judgeService.addJudge(judge)) {
				Map<String, Object> dataMap = new HashMap<String, Object>();
				dataMap.put("data", "success");
				response.setStatus(HttpStatus.OK.value());
				response.getWriter().println(new JSONObject(dataMap));
			} else {
				Map<String, Object> dataMap = new HashMap<String, Object>();
				dataMap.put("data", "fail");
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
