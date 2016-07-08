package com.sjtu.ist.charge.Util;


import org.json.JSONObject;



public class JsonUtil {

	public static boolean isExist(JSONObject jsonObject,String key) {
		try {
			jsonObject.get(key);
			return true;
		} catch (Exception e) {
			return false;
		}
	}
}
