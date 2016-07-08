package com.sjtu.ist.charge.Servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.http.HttpStatus;

import com.sjtu.ist.charge.Dto.json.ComplaintDto;
import com.sjtu.ist.charge.Model.Complaint;
import com.sjtu.ist.charge.Model.UseStake;
import com.sjtu.ist.charge.Model.User;
import com.sjtu.ist.charge.Service.ComplaintService;
import com.sjtu.ist.charge.Service.UseStakeService;
import com.sjtu.ist.charge.Service.UserService;
import com.sjtu.ist.charge.Util.RedisUtil;
import com.sjtu.ist.charge.Util.RequestUtil;

import redis.clients.jedis.Jedis;


/**
 * 获取某个用户的所有车桩 url:/api/user/stake.servlet method : get
 * 
 * @author 黄顺婷
 * 
 */
public class Complaint_AddServlet extends BaseServlet {

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
			ComplaintService complaintService = (ComplaintService) getCtx().getBean("complaintService");
			UseStakeService useStakeService = (UseStakeService) getCtx().getBean("useStakeService");
			User user = userService.load(Integer.parseInt(id));
			// 如果该用户存在
			if (user != null) {
				User userByToId = userService.load(Integer.parseInt(jsonObject
						.getString("stakeOwnerId")));
				UseStake useStakeByUseStakeId = useStakeService.load(Integer
						.parseInt(jsonObject.getString("useStakeId")));
				String content = jsonObject.getString("content");
				Timestamp time = Timestamp
						.valueOf(jsonObject.getString("time"));
				int status = 0;// 默认为未处理
				Complaint complaint = new Complaint(userByToId, user,
						useStakeByUseStakeId, content, status, time);
				// 插入成功
				if (complaintService.addComp(complaint)) {
					ComplaintDto comDto = new ComplaintDto(complaint);
					JSONObject jsto = new JSONObject(comDto);
					Map<String, Object> dataMap = new HashMap<String, Object>();
					dataMap.put("token", token);
					dataMap.put("data", jsto);
					PrintWriter writer = response.getWriter();
					writer.println(new JSONObject(dataMap));
					writer.close();
					response.setStatus(HttpStatus.OK.value());
				} else {
					JSONObject resObject = new JSONObject();
					resObject.put("data", "addFail");
                    resObject.put("token", "");
					response.getWriter().println(resObject);
                    response.setStatus(HttpStatus.OK.value());
				}
			} else {
				JSONObject resObject = new JSONObject();
				resObject.put("data", "NoSuchUserFail");
                resObject.put("token", "");
				response.getWriter().println(resObject);
                response.setStatus(HttpStatus.OK.value());
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("error");
			response.setStatus(HttpStatus.UNAUTHORIZED.value());
		}
	}
}
