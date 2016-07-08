package com.example.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.baidu.location.LLSInterface;

import android.R.integer;
import entity.Comment;
import entity.ElectricDrive;
import entity.UserMessage;

public class MyHttpRequest {

	public static List<ElectricDrive> getElectricDrives() throws JSONException, Exception {
		JSONObject jsonObject = new JSONObject(HttpUtil.getRequest("api/account/allStake.servlet", null));
		JSONArray jsonArray = jsonObject.getJSONArray("data");
		List<ElectricDrive> result = new ArrayList<ElectricDrive>();
		for (int i = 0; i < jsonArray.length(); i++) {
			JSONObject elecObject = jsonArray.getJSONObject(i);
			ElectricDrive electricDrive = new ElectricDrive();
			electricDrive.setId(elecObject.getInt("id"));
			electricDrive.setPrice(elecObject.getDouble("price"));
			electricDrive.setStatus(elecObject.getInt("status"));
			electricDrive.setAddress(elecObject.getString("address"));
			electricDrive.setDescription(elecObject.getString("description"));
			electricDrive.setAvailableStime(elecObject.getString("availableStime"));
			electricDrive.setAvailableEtime(elecObject.getString("availableEtime"));
			electricDrive.setLongtitude(elecObject.getDouble("longitude"));
			electricDrive.setLatitude(elecObject.getDouble("latitude"));
			electricDrive.setType(elecObject.getInt("type"));
			result.add(electricDrive);
		}
		return result;
	}

	public static boolean orderElectircDrive(String stakeId, String startTime, String endTime, String token)
			throws Exception {
		Map<String, String> map = new HashMap<String, String>();
		map.put("stakeId", stakeId);
		map.put("startTime", startTime);
		map.put("endTime", endTime);
		JSONObject jsonObject = new JSONObject(HttpUtil.postRequest("api/order/addOrder.servlet", map, token));
		if (!jsonObject.getString("token").equals("")) {
			return true;
		}
		return false;
	}

	public static Map<String, Integer> getAllCartype() throws JSONException, Exception {
		JSONObject jsonObject = new JSONObject(HttpUtil.getRequest("api/account/allcarType.servlet", null));
		JSONArray retArr = jsonObject.getJSONArray("data");
		Map<String, Integer> map = new HashMap<String, Integer>();
		for (int i = 0; i < retArr.length(); i++) {
			JSONObject carObj = retArr.getJSONObject(i);
			map.put(carObj.getString("type_name"), carObj.getInt("id"));
		}
		return map;
	}

	public static boolean chargeRegister(Double longitude, Double latitude, int carTypeId, String sTime, String eTime,
			int price, String description, String address, String token) throws JSONException, Exception {
		Map<String, String> map = new HashMap<String, String>();
		map.put("longitude", longitude + "");
		map.put("latitude", latitude + "");
		map.put("carType_id", carTypeId + "");
		map.put("available_stime", sTime);
		map.put("available_etime", eTime);
		map.put("price", price + "");
		map.put("type", "1");
		map.put("description", description);
		map.put("address", address);
		JSONObject retObj = new JSONObject(HttpUtil.postRequest("api/user/stakeRegister.servlet", map, token));
		String result = retObj.getString("register");
		if (result.equals("success"))
			return true;
		else
			return false;
	}

	public static boolean collectStake(int stakeId, String time, String token) throws JSONException, Exception {
		Map<String, String> map = new HashMap<String, String>();
		map.put("stakeId", stakeId + "");
		map.put("time", time);
		JSONObject retObj = new JSONObject(HttpUtil.postRequest("api/user/collection.servlet", map, token));
		if (retObj.getString("token").equals(""))
			return false;
		return true;
	}

	public static List<ElectricDrive> getAllCollection(String token) throws JSONException, Exception {
		List<ElectricDrive> list = new ArrayList<ElectricDrive>();
		JSONObject retObj = new JSONObject(HttpUtil.getRequest("api/user/collection.servlet", token));
		if (retObj.getString("token").equals(""))
			return list;
		JSONArray retArr = retObj.getJSONArray("data");
		for (int i = 0; i < retArr.length(); i++) {
			ElectricDrive e = new ElectricDrive();
			JSONObject electricObj = retArr.getJSONObject(i);
			e.setId(electricObj.getInt("stake_id"));
			e.setAvailableStime(electricObj.getString("stake_availableStime"));
			e.setType(electricObj.getInt("stake_type"));
			e.setAddress(electricObj.getString("stake_address"));
			e.setLongtitude(electricObj.getDouble("stake_longitude"));
			e.setLatitude(electricObj.getDouble("stake_latitude"));
			e.setDescription(electricObj.getString("stake_description"));
			e.setAvailableEtime(electricObj.getString("stake_availableEtime"));
			e.setPrice(electricObj.getDouble("stake_price"));
			e.setStatus(electricObj.getInt("stake_status"));
			list.add(e);
		}
		return list;
	}

	public static boolean cancelCollect(int stakeId, String token)
			throws JSONException, InterruptedException, ExecutionException {
		Map<String, String> map = new HashMap<String, String>();
		map.put("stakeId", stakeId + "");
		JSONObject retObj = new JSONObject(HttpUtil.putRequest("api/user/collection.servlet", map, token));
		if (retObj.getString("data").equals("deleteSuccess"))
			return true;
		return false;
	}

	public static List<Comment> getComment(int stakeId) throws JSONException, InterruptedException, ExecutionException {
		Map<String, String> map = new HashMap<String, String>();
		List<Comment> list = new ArrayList<Comment>();
		map.put("stakeId", stakeId + "");
		JSONObject retObj = new JSONObject(HttpUtil.putRequest("api/account/judge.servlet", map, null));
		JSONArray retArr = retObj.getJSONArray("data");
		for (int i = 0; i < retArr.length(); i++) {
			JSONObject comment = retArr.getJSONObject(i);
			Comment c = new Comment();
			c.setContent(comment.getString("content"));
			c.setGrade(comment.getInt("grade"));
			c.setId(comment.getInt("id"));
			c.setStakeId(comment.getInt("stake_id"));
			c.setTime(comment.getString("time"));
			c.setUserId(comment.getInt("user_id"));
			c.setUserName(comment.getString("user_name"));
			list.add(c);

		}
		return list;
	}

	public static List<UserMessage> getMessages(int type, String token) throws JSONException, InterruptedException, ExecutionException{
		Map<String, String> map  = new HashMap<String, String>();
		List<UserMessage> list = new ArrayList<UserMessage>();
		map.put("type", type+"");
		JSONObject retObj = new JSONObject(HttpUtil.putRequest("api/user/message.servlet", map, token));
		if(retObj.getString("token").equals(""))
			return list;
		JSONArray retArr = retObj.getJSONArray("data");
		for(int i = 0; i < retArr.length(); i++){
			UserMessage um = new UserMessage();
			JSONObject mObject = retArr.getJSONObject(i);
			um.setContent(mObject.getString("content"));
			um.setFromName(mObject.getString("from_name"));
			um.setId(mObject.getInt("id"));
			um.setTime(mObject.getString("time"));
			um.setTitle(mObject.getString("title"));
			um.setToName(mObject.getString("to_name"));
			um.setType(mObject.getInt("type"));
			list.add(um);
		}
		return list;
	}
	
	public static void dealMessage(List<UserMessage> list, String token) throws Exception{
		for(int i = 0; i < list.size(); i++){
			UserMessage um = list.get(i);
			Map<String, String> map = new HashMap<String, String>();
			map.put("messageId", um.getId()+"");
			HttpUtil.postRequest("api/user/message.servlet", map, token);
			
		}
	}
}
