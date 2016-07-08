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
import com.sjtu.ist.charge.Service.StakeService;


/**
 * 获取所有车桩 url:/api/account/allStake.servlet method : get
 * 
 * @author 黄顺婷
 * 
 */
public class AllStakeServlet extends BaseServlet {

	@Override
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		try {

			StakeService stakeService = (StakeService) getCtx().getBean(
					"stakeService");
			List<Stake> stakes = stakeService.showAllStakes();
			List<StakeDto> stakeDtos = new ArrayList<StakeDto>(stakes.size());
			//有桩
			if (stakes.size() != 0) {
				for (Stake stake : stakes) {
					StakeDto stakeDto = new StakeDto(stake);
					stakeDtos.add(stakeDto);
				}
				JSONArray jsonArr = new JSONArray(stakeDtos);
				Map<String, Object> dataMap = new HashMap<String, Object>();
				dataMap.put("data", jsonArr);

				PrintWriter writer = response.getWriter();
				writer.println(new JSONObject(dataMap));
				writer.close();
				response.setStatus(HttpStatus.OK.value());
			}else{
				Map<String, Object> dataMap = new HashMap<String, Object>();
				dataMap.put("data", "empty");

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
