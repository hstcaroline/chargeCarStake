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

import com.sjtu.ist.charge.Dto.json.CarTypeDto;
import com.sjtu.ist.charge.Model.CarType;
import com.sjtu.ist.charge.Service.CarTypeService;

/**
 * 用于获取所有的车型 url:domain+/api/account/allcarType.servlet method:get
 * @author huangshunting
 *
 */
public class CarTypeServlet extends BaseServlet {

	@Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException,
            IOException {
		CarTypeService carTypeService = (CarTypeService) getCtx().getBean("carTypeService");
		List<CarType> carTypes = carTypeService.showAllCarType();
		List<CarTypeDto> carTypeDtos = new ArrayList<CarTypeDto>();
		if(!carTypes.isEmpty()){
			for(CarType type : carTypes)
			{
				CarTypeDto carTypeDto = new CarTypeDto(type); 
				carTypeDtos.add(carTypeDto);
			}
			JSONArray jsonArr = new JSONArray(carTypeDtos);
			Map<String, Object> dataMap = new HashMap<String, Object>();
			dataMap.put("data", jsonArr);
			PrintWriter writer = resp.getWriter();
			writer.println(new JSONObject(dataMap));
			writer.close();
			resp.setStatus(HttpStatus.OK.value());
		}else{
			Map<String, Object> dataMap = new HashMap<String, Object>();
			dataMap.put("data", "empty");
			PrintWriter writer = resp.getWriter();
			writer.println(new JSONObject(dataMap));
			writer.close();
			resp.setStatus(HttpStatus.OK.value());
		}
		
    }

}
