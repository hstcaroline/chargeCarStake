package com.example.everapp;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.json.JSONObject;

import com.example.util.HttpUtil;

import android.R.string;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class RegistActivity extends Activity {
	private ImageButton reback_btn;
	private TextView titleText;
	private Button get_code, Register_btn;
	private EditText Regist_phone, Regist_psw, Validatecode;
	private TimeCount time;//验证码倒计时参数
	private String regist_phone, regist_psw, validateString;
	private String codeString;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.regist);
		time = new TimeCount(60000, 1000);//构造CountDownTimer对象
		reback_btn = (ImageButton) findViewById(R.id.return_btn);
		reback_btn.setOnClickListener(new RegistClickListener());
		Regist_phone = (EditText) findViewById(R.id.regist_user_edit);
		Regist_psw = (EditText) findViewById(R.id.regist_passwd_edit);
		Validatecode = (EditText) findViewById(R.id.regist_validate_edit);
		get_code = (Button) findViewById(R.id.regist_validatecode_btn);//获得验证码
		Register_btn = (Button) findViewById(R.id.register_btns);//注册
		titleText = (TextView) findViewById(R.id.title);
		titleText.setText("注册");
		// set click listener for getting validate code
		get_code.setOnClickListener(new RegistClickListener());
		//set click listener for registering
		Register_btn.setOnClickListener(new RegistClickListener());
	}

	class RegistClickListener implements OnClickListener {
		@Override
		public void onClick(View v) {
			Intent intent = new Intent();
			// TODO Auto-generated method stub
			switch (v.getId()) {
			case R.id.return_btn:
				RegistActivity.this.finish();
				break;
			case R.id.register_btns:
            if(validateInput()){
            	if(ValidateCode()){
            	Toast.makeText(RegistActivity.this.getApplicationContext(), "success!",
    					Toast.LENGTH_LONG).show();
            	//intent.setClass(RegistActivity.this, UserLoginActivity.class);
            	//startActivity(intent);
				finish();
            	break;
            }
            else{
            	Toast.makeText(RegistActivity.this.getApplicationContext(), "fault!",
    					Toast.LENGTH_LONG).show();
            	break;
            }
            }
				
			case R.id.regist_validatecode_btn:
				time.start();//开始计时
				GetValidateCode() ;
				break;
			default:
				break;
			}
		}
	}

	// check the input string
	private boolean validateInput() {
		regist_phone = Regist_phone.getText().toString();//获取手机号码
		regist_psw = Regist_psw.getText().toString();//获取用户密码
		validateString=Validatecode.getText().toString();//获取验证码
		if (regist_phone == null || "".equals(regist_phone.trim())) {
			Toast.makeText(this.getApplicationContext(), "注册手机号码不能为空",
					Toast.LENGTH_LONG).show();
			return false;
		}
		if (regist_psw == null || "".equals(regist_psw.trim())) {
			Toast.makeText(this.getApplicationContext(), "密码不能为空",
					Toast.LENGTH_LONG).show();
			return false;
		}
		if (validateString == null || "".equals(validateString.trim())) {
			Toast.makeText(this.getApplicationContext(), "验证码不能为空",
					Toast.LENGTH_LONG).show();
			return false;
		}
        if(!isMoblieNum(regist_phone)){
        	Toast.makeText(this.getApplicationContext(), "输入电话号码无效",
					Toast.LENGTH_LONG).show();
        	return false;
        }
        if(!ispswNum(regist_psw)){
        	Toast.makeText(this.getApplicationContext(), "请输入6-16位含中英文密码（不包含特殊字符）",
					Toast.LENGTH_LONG).show();
        	return false;
        }
       /* if(!ValidateCode(validateString)){
        	Toast.makeText(this.getApplicationContext(), "验证码输入错误",
					Toast.LENGTH_LONG).show();
        	return false;
        }*/
		System.out.println("user_phone: " + regist_phone + " password: "
				+ regist_psw+" "+validateString);
		return true;
	}
	//get valiatecode from servlet
	public void GetValidateCode() {
		// TODO Auto-generated method stub
		regist_phone=Regist_phone.getText().toString();
		System.out.println(regist_phone);
		Map<String, String> map = new HashMap<String, String>();
		map.put("telephone", regist_phone);//发送手机号码
		map.put("type", "0");
		try {
			JSONObject jsonObject = new JSONObject(HttpUtil.postRequest(
					"api/account/register.servlet", map, null));
			String dataString=jsonObject.getString("data");
			//System.out.println(dataString);
			
				if(dataString.equals("telphoneAlreadyRegister")){
					Toast.makeText(RegistActivity.this.getApplicationContext(), "该手机号码已经被注册!",
	    					Toast.LENGTH_LONG).show();
					return;
				}
				else {
					Toast.makeText(RegistActivity.this.getApplicationContext(), "验证码已发送，请稍后!",
	    					Toast.LENGTH_LONG).show();
					return;
				}
			
			//JSONObject jsonObject = new JSONObject(outString);//发送给服务器手机号码 服务器返回验证码
			//codeString= jsonObject.getString("code");
			/*if (codeString != null) {
				System.out.println(codeString);
				return true;
				
			}*/
		} catch (Exception e) {
			e.printStackTrace();
			
		}
		
	}
	// check the phone number
	private boolean isMoblieNum(String phone) {
		Pattern p = Pattern.compile("^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$");
		Matcher m = p.matcher(phone);
		System.out.println(m.matches() + "---");
		return m.matches();
	}
	// check the password content
	private boolean ispswNum(String psw) {
		Pattern p = Pattern.compile("(?!^[0-9]*$)(?!^[a-zA-Z]*$)^([a-zA-Z0-9]{6,})$");
		Matcher m = p.matcher(psw);
		System.out.println(m.matches() + "---");
		return m.matches();
	}
	
	private boolean ValidateCode() {
		
		Map<String, String> map = new HashMap<String, String>();
		map.put("username", regist_phone);
		map.put("password", regist_psw);
		map.put("telephone", regist_phone);
		map.put("type", "1");
		map.put("code", validateString);
		try {
			JSONObject jsonObject = new JSONObject(HttpUtil.postRequest(
					"api/account/register.servlet", map,null));
			codeString = jsonObject.getString("data");
			if (codeString.equals("codeTrue")) {
				System.out.println(codeString);
				return true;
				
			}
			else{
				return false;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	class TimeCount extends CountDownTimer {
		public TimeCount(long millisInFuture, long countDownInterval) {
		super(millisInFuture, countDownInterval);//参数依次为总时长,和计时的时间间隔
		}
		@Override
		public void onFinish() {//计时完毕时触发
	    get_code.setText("获取验证码");
	    get_code.setClickable(true);
		}
		@Override
		public void onTick(long millisUntilFinished){//计时过程显示
			get_code.setClickable(false);
			get_code.setText("请等待"+millisUntilFinished /1000+"秒");
		}
	}
}
