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
import org.json.JSONObject;
import org.springframework.http.HttpStatus;

import com.sjtu.ist.charge.Dto.json.UseStakeDto;
import com.sjtu.ist.charge.Model.UseStake;
import com.sjtu.ist.charge.Service.UseStakeService;
import com.sjtu.ist.charge.Util.RedisUtil;

import redis.clients.jedis.Jedis;


/**
 * 获取某个用户的充电记录 url:/api/user/chargeRecord.servlet method : get
 * 
 * @author 黄顺婷
 * 
 */
public class UseStakeServlet extends BaseServlet {

	@Override
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		try {
			String token = request.getHeader("Access-Token");
			Jedis jedis = RedisUtil.getJedis();
			String id = jedis.get(token);
			UseStakeService useStakeService = (UseStakeService) getCtx()
					.getBean("useStakeService");
			List<UseStake> useStakes = useStakeService.getByUserId(Integer
					.parseInt(id));
			List<UseStakeDto> useStakeDtos = new ArrayList<UseStakeDto>(
					useStakes.size());
			//如果当前用户有充电记录
			if(!useStakes.isEmpty()){
				for (UseStake useStake : useStakes) {
					UseStakeDto uStakeDto = new UseStakeDto(useStake);
					useStakeDtos.add(uStakeDto);
				}
				JSONArray jsonArr = new JSONArray(useStakeDtos);
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
			// TODO: handle exception
			e.printStackTrace();
			response.setStatus(HttpStatus.NOT_FOUND.value());
		}

	}
}
