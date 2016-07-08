package com.example.everapp;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.json.JSONObject;

import com.example.everapp.RegistActivity.TimeCount;
import com.example.util.HttpUtil;

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

public class ForgetpwActivity extends Activity {
	private Button forget_next_btn,forget_psw_code_btn;
	private ImageButton forget_reback_btn;
	private EditText forget_user_phone,forget_psw_code;//手机号码和验证码string
	private TimeCount time;//验证码倒计时参数
	private TextView titleText;
	private String iscodeToken;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.forget_pw);
		time = new TimeCount(60000, 1000);//构造CountDownTimer对象
		forget_next_btn = (Button) findViewById(R.id.forgetpw_next_btn);
		//验证码button
		forget_psw_code_btn=(Button)findViewById(R.id.forgetpw_validatecode_btn);
		forget_reback_btn=(ImageButton)findViewById(R.id.return_btn);
		//手机号码string
		forget_user_phone=(EditText)findViewById(R.id.forgetpw_user_edit);
		//验证码string
		forget_psw_code=(EditText)findViewById(R.id.forgetpw_validate_edit);
		
		forget_next_btn.setOnClickListener(new ForgetClickListener());
		forget_reback_btn.setOnClickListener(new ForgetClickListener());
		forget_psw_code_btn.setOnClickListener(new ForgetClickListener());
		titleText = (TextView) findViewById(R.id.title);
		 titleText.setText("忘记密码");
	}

	class ForgetClickListener implements OnClickListener {
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			Intent intent = new Intent();
			switch (v.getId()) {
			case R.id.forgetpw_next_btn:
				if(!isMoblieNum(forget_user_phone.getText().toString())){
					Toast.makeText(ForgetpwActivity.this.getApplicationContext(), "请输入正确11位手机号码!",
	    					Toast.LENGTH_LONG).show();
					break;
				}
				else {
					 
					if(ValidateCode()){
					intent.putExtra("phone", forget_user_phone.getText().toString());
					intent.setClass(ForgetpwActivity.this,ForgetnextActivity.class);
					startActivity(intent);
					ForgetpwActivity.this.finish();
					break;
					}
					else{
						Toast.makeText(ForgetpwActivity.this.getApplicationContext(), "验证码错误!",
		    					Toast.LENGTH_LONG).show();
						break;
					}
				}
				
			case R.id.return_btn:
				intent.setClass(ForgetpwActivity.this,UserLoginActivity.class);
				startActivity(intent);
				ForgetpwActivity.this.finish();
				break;
			case R.id.forgetpw_validatecode_btn:
				time.start();
				if(isMoblieNum(forget_user_phone.getText().toString())){
				GetValidateCode();
				}
				break;
			default:
				break;
			}
		}
	}
	 private boolean isMoblieNum(String phone) {
	 		if(phone==null|| "".equals(phone.trim())){
	 			Toast.makeText(this.getApplicationContext(), "手机号码不能为空",
	 					Toast.LENGTH_LONG).show();
	 			return false;			
	 		}
	 		Pattern p = Pattern.compile("^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$");
	 		Matcher m = p.matcher(phone);
	 		System.out.println(m.matches() + "---");
	 		return m.matches();
	 	}
	 class TimeCount extends CountDownTimer {
			public TimeCount(long millisInFuture, long countDownInterval) {
			super(millisInFuture, countDownInterval);//参数依次为总时长,和计时的时间间隔
			}
			@Override
			public void onFinish() {//计时完毕时触发
				forget_psw_code_btn.setText("获取验证码");
				forget_psw_code_btn.setClickable(true);
			}
			@Override
			public void onTick(long millisUntilFinished){//计时过程显示
				forget_psw_code_btn.setClickable(false);
				forget_psw_code_btn.setText("请等待"+millisUntilFinished /1000+"秒");
			}
	 }
	 public void GetValidateCode() {
			// TODO Auto-generated method stub
			String phone=forget_user_phone.getText().toString();
			System.out.println(phone);
			Map<String, String> map = new HashMap<String, String>();
			map.put("telephone", phone);//发送手机号码
			map.put("type", "0");
			try {
				String outString = HttpUtil.postRequest(
						"api/account/forgetPsw.servlet",map,null);
				System.out.println(outString);
				
			} catch (Exception e) {
				e.printStackTrace();
				
			}
			
		}
	 public boolean ValidateCode() {
			// TODO 验证码验证对错
			String phone=forget_user_phone.getText().toString();
			String codeString=forget_psw_code.getText().toString();
			System.out.println(phone);
			System.out.println(codeString);
			Map<String, String> map = new HashMap<String, String>();
			map.put("telephone", phone);//发送手机号码
			map.put("type", "1");
			map.put("code", codeString);	
			try {
				String outString = HttpUtil.postRequest(
						"api/account/forgetPsw.servlet",map,null);
				System.out.println(outString);
				JSONObject jsonObject = new JSONObject(outString);//发送给服务器手机号码 服务器返回验证码
				iscodeToken= jsonObject.getString("validation");
				System.out.println(iscodeToken);
				if(iscodeToken.equals("correct")){
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
	
}
