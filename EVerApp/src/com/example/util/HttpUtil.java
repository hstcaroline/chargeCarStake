package com.example.util;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class HttpUtil {

	public static HttpClient httpClient;
	// public static final String BASE_URL =
	// "http://192.168.2.103:8080/AndroidTest/android/";
	public static final String BASE_URL = "http://120.24.17.156:8080/Charge/";
	//public static final String BASE_URL = "http://192.168.1.23:8080/Charge/";
	// public static final String BASE_URL = "http://duduainankai.imwork.net/Charge/";
	// "http://120.25.127.101:8080/Charge/";
	//public static final String BASE_URL = "http://192.168.31.151:8080/Charge/";

	static {
		httpClient = new DefaultHttpClient();
	}

	/**
	 * 
	 * @param url
	 *            发送请求的URL
	 * @return 服务器响应字符串
	 * @throws Exception
	 */

	public static String getRequest(final String url, final String token)
			throws Exception {
		FutureTask<String> task = new FutureTask<String>(
				new Callable<String>() {
					@Override
					public String call() throws Exception {
						HttpGet get = new HttpGet(BASE_URL+url);
						if (token != null) {
							get.setHeader("Access-Token", token);
						}
						System.out.println(BASE_URL+url);
						HttpResponse httpResponse = httpClient.execute(get);
						System.out.println("status line "
								+ httpResponse.getStatusLine());
						if (httpResponse.getStatusLine().getStatusCode() == 200) {
							String result = EntityUtils.toString(httpResponse
									.getEntity());
							return result;
						}
						System.out.println("cann't connect ");
						return null;
					}
				});
		new Thread(task).start();
		return task.get();
	}

	/**
	 * 
	 * @param url
	 *            请求访问的URL
	 * @param rawParams
	 *            请求的参数
	 * @return 服务器响应字符串
	 * @throws Exception
	 */
	public static String postRequest(final String url,
			final Map<String, String> rawParams, final String token)
			throws Exception {
		FutureTask<String> task = new FutureTask<String>(
				new Callable<String>() {
					@Override
					public String call() throws Exception {
						HttpPost post = new HttpPost(BASE_URL + url);

						if (token != null) {
							post.setHeader("Access-Token", token);
							post.setHeader("Content-Type","application/json;charset=utf-8");
						}
						System.out.println(BASE_URL + url);
						JSONObject jsonObject = new JSONObject();
						for (String key : rawParams.keySet()) {
							// 封装请求参数
							jsonObject.put(key, rawParams.get(key));
						}
						//post.setEntity(new StringEntity(jsonObject.toString()));
						post.setEntity(new StringEntity(jsonObject.toString(), "utf-8"));
						HttpResponse httpResponse = httpClient.execute(post);
						System.out.println("status line "
								+ httpResponse.getStatusLine());

						if (httpResponse.getStatusLine().getStatusCode() == 200) {
							String result = EntityUtils.toString(httpResponse
									.getEntity());
							return result;
						}
						System.out.println("cann't connect ");
						return null;
					}

				});
		new Thread(task).start();
		return task.get();
	}

	/**
	 * 
	 * @param url
	 * @param rawParams
	 * @param token
	 * @return
	 * @throws InterruptedException
	 * @throws ExecutionException
	 */
	public static String putRequest(final String url,
			final Map<String, String> rawParams, final String token)
			throws InterruptedException, ExecutionException {
		FutureTask<String> task = new FutureTask<String>(
				new Callable<String>() {
					@Override
					public String call() throws Exception {
						HttpPut put = new HttpPut(BASE_URL + url);

						if (token != null) {
							put.setHeader("Access-Token", token);
							put.setHeader("Content-Type","application/json;charset=utf-8");
						}
						System.out.println(BASE_URL + url);
						JSONObject jsonObject = new JSONObject();
						for (String key : rawParams.keySet()) {
							// 封装请求参数
							jsonObject.put(key, rawParams.get(key));
						}
						//put.setEntity(new StringEntity(jsonObject.toString()));
						put.setEntity(new StringEntity(jsonObject.toString(), "utf-8"));
						HttpResponse httpResponse = httpClient.execute(put);
						System.out.println("status line "
								+ httpResponse.getStatusLine());

						if (httpResponse.getStatusLine().getStatusCode() == 200) {
							String result = EntityUtils.toString(httpResponse
									.getEntity());
							return result;
						}
						System.out.println("cann't connect ");
						return null;
					}

				});
		new Thread(task).start();
		return task.get();
	}
	
	public static Bitmap getBitmap(final String path)
			throws Exception {
		FutureTask<Bitmap> task = new FutureTask<Bitmap>(
				new Callable<Bitmap>() {
					@Override
					public Bitmap call() throws Exception {
						 URL url = new URL(BASE_URL+path);
						 System.out.println(BASE_URL+url);
						 HttpURLConnection conn = (HttpURLConnection)url.openConnection();
						    conn.setConnectTimeout(2000);
						    conn.setRequestMethod("GET");
						    if(conn.getResponseCode() == 200){
						    InputStream inputStream = conn.getInputStream();
						    Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
						    return bitmap;
						    }
						System.out.println("cann't connect ");
						return null;
					}
				});
		new Thread(task).start();
		return task.get();
	}

}
