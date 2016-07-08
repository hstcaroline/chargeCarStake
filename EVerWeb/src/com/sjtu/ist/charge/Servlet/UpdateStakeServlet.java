package com.sjtu.ist.charge.Servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.sql.Time;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;
import org.springframework.http.HttpStatus;

import com.sjtu.ist.charge.Dto.json.StakeDto;
import com.sjtu.ist.charge.Model.Stake;
import com.sjtu.ist.charge.Service.StakeService;
import com.sjtu.ist.charge.Util.JsonUtil;
import com.sjtu.ist.charge.Util.RedisUtil;
import com.sjtu.ist.charge.Util.RequestUtil;

import redis.clients.jedis.Jedis;


/**
 * 修改车桩信息 api:/api/user/stakeUpdate.servlet method:post
 * 
 * @author Ister
 * 
 */
public class UpdateStakeServlet extends BaseServlet {

	@Override
	protected void doPut(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String payloadRequest = RequestUtil.getRequestBody(request);
		try {
			JSONObject jsonObject = new JSONObject(payloadRequest);
			String token = request.getHeader("Access-Token");
			Jedis jedis = RedisUtil.getJedis();
			String id = jedis.get(token);

			StakeService stakeService = (StakeService) getCtx().getBean(
					"stakeService");

			int stakeId = Integer.parseInt(jsonObject.getString("stakeId"));
			Stake stake = stakeService.load(stakeId);
			// 如果该车桩是该用户所有
			if (stake.getUser().getId() == Integer.parseInt(id)) {
				
				Class<?> clazz = stake.getClass();
				Field[] fields = clazz.getDeclaredFields();
				for (Field field : fields) {
					String fname = field.getName();
					String tname = field.getType().getName();
					if ("java.util.Set".equals(tname)|| !JsonUtil.isExist(jsonObject, fname)) {
						continue;
					}
					// 首字母大写
					String Ufname = fname.substring(0, 1).toUpperCase()
							+ fname.substring(1);
					Class[] param = new Class[1];
					param[0] = field.getType();
					Method method = clazz.getMethod("set" + Ufname, param);
					String value = (String) (jsonObject.get(fname));
					// 如果是修改时间
					if ("availableStime".equals(fname)|| "availableEtime".equals(fname)) {
						Time time = Time.valueOf(value);
						method.invoke(stake, time);
						continue;
					}
					if("stakeId".equals(fname)){
						continue;
					}
					if ("java.lang.Integer".equals(tname)) {
						int v = Integer.parseInt(value);
						method.invoke(stake, v);
					} else if ("java.lang.Double".equals(tname)) {
						double v = Double.parseDouble(value);
						method.invoke(stake, v);
					} else if ("java.lang.String".equals(tname)) {
						method.invoke(stake, value);
					}else
						System.out.println(field.getName() + ":");
				}

				stakeService.update(stake);
				StakeDto stakeDto = new StakeDto(stake);
				Map<String, Object> dataMap = new HashMap<String, Object>();
				dataMap.put("data", stakeDto);
				dataMap.put("token", token);

				PrintWriter writer = response.getWriter();
				writer.println(new JSONObject(dataMap));
				writer.close();
				response.setStatus(HttpStatus.OK.value());
			}

		} catch (Exception e) {
			e.printStackTrace();
			response.setStatus(HttpStatus.NOT_MODIFIED.value());
		}
	}
}
