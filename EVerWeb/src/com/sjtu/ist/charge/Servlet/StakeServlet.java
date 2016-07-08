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

import com.sjtu.ist.charge.Dto.json.StakeDto;
import com.sjtu.ist.charge.Model.Stake;
import com.sjtu.ist.charge.Model.User;
import com.sjtu.ist.charge.Service.StakeService;
import com.sjtu.ist.charge.Service.UserService;
import com.sjtu.ist.charge.Util.RedisUtil;

import redis.clients.jedis.Jedis;


/**
 * 获取某个用户的所有车桩 url:/api/user/stake.servlet method : get
 * 
 * @author 黄顺婷
 * 
 */
public class StakeServlet extends BaseServlet {

	@Override
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		try {
			String token = request.getHeader("Access-Token");
			Jedis jedis = RedisUtil.getJedis();
			String id = jedis.get(token);
			UserService userService = (UserService) getCtx().getBean("userService");
			StakeService stakeService = (StakeService) getCtx().getBean("stakeService");
			User user = userService.load(Integer.parseInt(id));
			//如果该用户没有车桩
			if(user.getHaveStake()==0)
			{
				JSONObject resObject = new JSONObject();
				resObject.put("data", "empty");
				resObject.put("token", "");
				response.getWriter().println(resObject);
				response.setStatus(HttpStatus.OK.value());
			}
			else{
				List<Stake> stakes = stakeService.getStakesByOwnerId(Integer.parseInt(id));
				List<StakeDto> stakeDtos = new ArrayList<StakeDto>(stakes.size());
				for(Stake stake :stakes)
				{
					StakeDto stakeDto = new StakeDto(stake);
					stakeDtos.add(stakeDto);
				}
				JSONArray jsonArr = new JSONArray(stakeDtos);
				Map<String, Object> dataMap = new HashMap<String, Object>();
				dataMap.put("data", jsonArr);
				dataMap.put("token", token);
				
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
