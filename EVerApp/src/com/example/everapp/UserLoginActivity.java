package com.example.everapp;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.json.JSONObject;

import com.example.JsonToMap.JsonUtil;
import com.example.SerialMap.SerializableMap;
import com.example.everapp.UserFragment.MyClickListener;

import android.R.string;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.DropBoxManager.Entry;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.util.HttpUtil;

public class UserLoginActivity extends Activity {
	private SharedPreferences preferences;
	private SharedPreferences.Editor editor;
	private Button login_btn, forget_btn, register_btn;
	private ImageButton reback_btn;
	private EditText userName, passWord;
	private String username, password;
	private String token;
	private HashMap<String, Object> usermap;
	private Handler handler;
	final SerializableMap myMap = new SerializableMap();
	private Intent intents;
	private static final int POST_LOGIN_SUCCESS = 0;
	private static final int POST_LOGIN_FAIL = 1;
	private static final int POST_CON_FAIL = -1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub

		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.login);

		login_btn = (Button) findViewById(R.id.login_btn);
		register_btn = (Button) findViewById(R.id.register_btn);
		reback_btn = (ImageButton) findViewById(R.id.login_reback_btn);
		forget_btn = (Button) findViewById(R.id.forget_passwd);
		userName = (EditText) findViewById(R.id.login_user_edit);
		passWord = (EditText) findViewById(R.id.login_passwd_edit);
		login_btn.setOnClickListener(new LoginListener());
		reback_btn.setOnClickListener(new LoginListener());
		forget_btn.setOnClickListener(new LoginListener());
		register_btn.setOnClickListener(new LoginListener());
		
		preferences = getSharedPreferences("ever", MODE_PRIVATE);
		editor = preferences.edit();
		handler = new Handler(){
			public void handleMessage(Message msg) {
				UserLoginActivity.this.handleMessage(msg);
			};
		};
	}
	private void handleMessage(Message msg){
		Bundle b = msg.getData();
		int res = b.getInt("result");
		switch (res) {
		case POST_LOGIN_SUCCESS:
			intents=new Intent();
			Toast.makeText(this, "登录成功", Toast.LENGTH_SHORT).show();
			myMap.setMap(usermap);// 将map数据添加到封装的myMap<span></span>中
			Bundle bundle = new Bundle();
			bundle.putSerializable("map", myMap);
			intents.putExtras(bundle);// bundle封装传递map对象*/
			UserLoginActivity.this.setResult(RESULT_OK, intents);
			finish();
			break;
		case POST_LOGIN_FAIL:
			Toast.makeText(this, "密码或用户名错误" , Toast.LENGTH_SHORT).show();
			break;
		case POST_CON_FAIL:
			Toast.makeText(this, "网络异常", Toast.LENGTH_SHORT).show();
			break;
		default:
			break;
		}
	}
	class LoginListener implements OnClickListener {
		Intent intent = new Intent();

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			switch (v.getId()) {
			// click to reback the user center
			case R.id.login_btn:

				if (validateInput()) {
					 new Thread(new Runnable() {
							public void run() {
					validateUser();
							}
						}).start();
				}
				break;
			case R.id.login_reback_btn:
				UserLoginActivity.this.finish();
				break;
			// click the register button
			case R.id.register_btn:
				intent.setClass(UserLoginActivity.this, RegistActivity.class);
				startActivity(intent);
				UserLoginActivity.this.finish();
				break;
			case R.id.forget_passwd:
				intent.setClass(UserLoginActivity.this, ForgetpwActivity.class);
				startActivity(intent);
				UserLoginActivity.this.finish();
				break;
			default:
				break;

			}
		}
	}

	// check the username and password

	private boolean validateInput() {
		username = userName.getText().toString();
		password = passWord.getText().toString();
		if (username == null || "".equals(username.trim())) {
			Toast.makeText(this.getApplicationContext(), "用户名不能为空",
					Toast.LENGTH_LONG).show();
			return false;
		}
		if (password == null || "".equals(password.trim())) {
			Toast.makeText(this.getApplicationContext(), "密码不能为空",
					Toast.LENGTH_LONG).show();
			return false;
		}
		System.out.println("username: " + username + " password: " + password);
		return true;
	}

	private void validateUser() {
		
		Bundle bundle = new Bundle();
		Map<String, String> map = new HashMap<String, String>();
		map.put("telephone", username);//登录为电话号码
		map.put("password", password);
		try {
			String response=HttpUtil.postRequest(
					"api/account/login.servlet", map, null);
		if(response!=null){
			JSONObject jsonObject = new JSONObject(response);
			token = jsonObject.getString("token");
			if (token != null&&!token.equals("")) {
				editor.putString("token", token);
				editor.commit();
			String json = jsonObject.getString("data");
			// 保存服务器发过来的返回值
			Object hello = JsonUtil.jsonParse(json);
			if (hello instanceof Map) {
				usermap = (HashMap<String, Object>) hello;// map存放返回的用户数据
				usermap.put("token", token);
				
			}
			bundle.putInt("result", POST_LOGIN_SUCCESS);
			Message msg = handler.obtainMessage();
			msg.setData(bundle);
			handler.sendMessage(msg);
				return;
			}
			else{
				bundle.putInt("result", POST_LOGIN_FAIL);
				Message msg = handler.obtainMessage();
				msg.setData(bundle);
				handler.sendMessage(msg);
				return;
			}
			
		}
		else {
			bundle.putInt("result", POST_LOGIN_FAIL);
			Message msg = handler.obtainMessage();
			msg.setData(bundle);
			handler.sendMessage(msg);
			return;
		}
		} catch (Exception e) {
			//e.printStackTrace();
			bundle.putInt("result", POST_CON_FAIL);
			Message msg = handler.obtainMessage();
			msg.setData(bundle);
			handler.sendMessage(msg);
			return;
		}
		
	}

}
