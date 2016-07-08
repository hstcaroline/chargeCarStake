package com.example.test;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import com.example.util.HttpUtil;


public class Test {  
    
    public static void testq() {  
    	String url = "api/user/update.servlet";
    	Map<String, String> rawParams = new HashMap<String,String>();
    	rawParams.put("remaining", "1000");
    	String token = "8e1c05fd05ff38a9556fe785d7934386";
    	try {
			String responseString = HttpUtil.putRequest(url, rawParams, token);
			System.out.println(responseString);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    public static void main(String[] args) {
    	Test.testq();
    }
}  
