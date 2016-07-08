package com.example.everapp;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;

import com.example.JsonToMap.JsonUtil;
import com.example.SerialMap.SerializableMap;
import com.example.util.HttpUtil;

import android.R.integer;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class UserModifyActivity extends Activity {

	private Button saveButton;// 保存button
	private ImageButton rebackButton;
	private EditText user_modify;
	private TextView titleText;
	private int tag = -1;
	private String token, username, userphone, modifyString;
	private HashMap<String, Object> usermap;
	private SerializableMap serializableMap;
	private Intent intents;
	final SerializableMap myMap = new SerializableMap();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.modify_use_name);
		titleText = (TextView) findViewById(R.id.title);// 页面主标题
		rebackButton = (ImageButton) findViewById(R.id.return_btn);// 返回上个页面
		user_modify = (EditText) findViewById(R.id.icon_name);// 修改信息编辑框
		saveButton = (Button) findViewById(R.id.save_btn);// 保存按钮
		intents = getIntent();
		tag = Integer.parseInt(intents.getStringExtra("tag"));
		Bundle bundle = new Bundle();
		bundle = this.getIntent().getExtras();
		serializableMap = (SerializableMap) bundle.get("usermap");
		usermap = serializableMap.getMap();
		token = usermap.get("token").toString();// 存储登录用户的token便于后面更新操作
		username = usermap.get("username").toString();
		userphone = usermap.get("telephone").toString();
		if (token != null && tag == 0) {
			titleText.setText("昵称");
			user_modify.setText(username);
		}
		if (token != null && tag == 1) {
			titleText.setText("手机号码");
			user_modify.setText(userphone);
		}
		rebackButton.setOnClickListener(new OnClickListener() {
			Intent intent = new Intent();

			@Override
			public void onClick(View v) {

				// TODO Auto-generated method stub
				myMap.setMap(usermap);// 将map数据添加到封装的myMap<span></span>中
				Bundle bundle = new Bundle();
				bundle.putSerializable("usermap", myMap);
				intent.putExtras(bundle);// bundle封装传递map对象*/
				intent.setClass(UserModifyActivity.this,
						UserDetailActivity.class);
				startActivity(intent);
				finish();
			}

		});
		saveButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				modifyString = user_modify.getText().toString();
				Intent intent = new Intent();
				if (modifyString != null) {
					if (!modifyString.equals(username) && tag == 0) {
						Toast.makeText(UserModifyActivity.this, "username",
								Toast.LENGTH_SHORT).show();
						// 更新操作
						if (modifyUser(token, modifyString, tag)) {
							myMap.setMap(usermap);// 将map数据添加到封装的myMap<span></span>中
							Bundle bundle = new Bundle();
							bundle.putSerializable("usermap", myMap);
							intent.putExtras(bundle);// bundle封装传递map对象*/
							intent.setClass(UserModifyActivity.this,
									UserDetailActivity.class);
							startActivity(intent);
							intents.putExtras(bundle);
							setResult(Activity.RESULT_OK, intents);
							finish();
						} else {
							Toast.makeText(UserModifyActivity.this, "更新失败",
									Toast.LENGTH_SHORT).show();
						}
					}
					if (!modifyString.equals(userphone) && tag == 1) {
						Toast.makeText(UserModifyActivity.this, "telephone",
								Toast.LENGTH_SHORT).show();
						
							if (modifyUser(token, modifyString, tag)) {
								myMap.setMap(usermap);// 将map数据添加到封装的myMap<span></span>中
								Bundle bundle = new Bundle();
								bundle.putSerializable("usermap", myMap);
								intent.putExtras(bundle);// bundle封装传递map对象*/
								intent.setClass(UserModifyActivity.this,
										UserDetailActivity.class);
								startActivity(intent);
								intents.putExtras(bundle);
								setResult(Activity.RESULT_OK, intents);
								finish();
							}
							
						
						else {
							Toast.makeText(UserModifyActivity.this, "更新失败",
									Toast.LENGTH_SHORT).show();
						}

					}

				}

				else {
					saveButton.setEnabled(false);
				}
			}
		});
	}


	private boolean modifyUser(String tokens, String modifyString, int tag) {

		Map<String, String> map = new HashMap<String, String>();
		if (tag == 0) {
			map.put("username", modifyString);
		}
		if (tag == 1) {
			map.put("telephone", modifyString);
		}
		try {
			JSONObject jsonObject = new JSONObject(HttpUtil.putRequest(
					"api/user/update.servlet",map, tokens));
			String restoken = jsonObject.getString("token");
			String json = jsonObject.getString("data");
			
			Log.e("test", json);
			if(restoken==null||"".equals((restoken.trim())))
			{
				Toast.makeText(UserModifyActivity.this, "该号码已经存在!",
						Toast.LENGTH_SHORT).show();
				return false;
			}
			else{
			// 保存服务器发过来的返回值
			Object hello = JsonUtil.jsonParse(json);
			if (hello instanceof Map) {
				usermap = (HashMap<String, Object>) hello;// map存放返回的用户数据
				usermap.put("token", restoken);
				for (java.util.Map.Entry<String, Object> entry : usermap
						.entrySet()) {
					System.out.println("key= " + entry.getKey()
							+ " and value= " + entry.getValue());
					return true;
				}
			}
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return false;
	}
}
