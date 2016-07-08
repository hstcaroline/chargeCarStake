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
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class ForgetnextActivity extends Activity {
	private ImageButton reback_forget_btn;
	private Button submit_psw_btn;//修改密码确认按钮
	private String user_phone,new_psw,confirm_psw;
	private EditText newpsw_edit,confirm_psw_Edit;
	private TextView titleText;
	private String token;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.forget_pw_next);
		titleText = (TextView) findViewById(R.id.title);
		submit_psw_btn=(Button)findViewById(R.id.pwnext_submit_btn);
		reback_forget_btn=(ImageButton)findViewById(R.id.return_btn);
		newpsw_edit=(EditText)findViewById(R.id.pwnext_newpw_edit);
		confirm_psw_Edit=(EditText)findViewById(R.id.pwnext_submitpw_edit);
		reback_forget_btn.setOnClickListener(new ForgetnextClickListener());//返回前页
		submit_psw_btn.setOnClickListener(new ForgetnextClickListener());//提交新的密码按钮
		titleText.setText("修改密码");
		//取出用户号码
		Intent intents=getIntent();
		user_phone=intents.getStringExtra("phone");
		
	}
     class ForgetnextClickListener implements OnClickListener{
    	 @Override
    	public void onClick(View v) {
    		// TODO Auto-generated method stub
    		Intent intent=new Intent();
    		switch (v.getId()) {
			case R.id.return_btn:
				intent.setClass(ForgetnextActivity.this,ForgetpwActivity.class);
				 startActivity(intent);
				 ForgetnextActivity.this.finish();
				break;
			case R.id.pwnext_submit_btn:
				new_psw=newpsw_edit.getText().toString();
				confirm_psw=confirm_psw_Edit.getText().toString();
				System.out.println(user_phone);
				if(!ispswNum(new_psw)){
					
					
					break;
				}
				else{
					if(ModifyPswtoServer()){//将修改密码提交给服务器
						Toast.makeText(ForgetnextActivity.this.getApplicationContext(), "修改密码成功!",
								Toast.LENGTH_LONG).show();
					}
					intent.setClass(ForgetnextActivity.this,UserLoginActivity.class);
					startActivity(intent);
					ForgetnextActivity.this.finish();
					break;
				}
				
				
			default:
				break;
			}
    	}
     }
  // check the password content
 	private boolean ispswNum(String psw) {
 		if(psw==null|| "".equals(psw.trim())){
 			Toast.makeText(this.getApplicationContext(), "密码不能为空",
 					Toast.LENGTH_LONG).show();
 			return false;			
 		}
 		if(!psw.equals(confirm_psw)){
 			Toast.makeText(this.getApplicationContext(), "两次密码不一致",
 					Toast.LENGTH_LONG).show();
 			return false;	
 		}
 		Pattern p = Pattern.compile("(?!^[0-9]*$)(?!^[a-zA-Z]*$)^([a-zA-Z0-9]{6,})$");
 		Matcher m = p.matcher(psw);
 		System.out.println(m.matches() + "---");
 		if(!m.matches()){
 			Toast.makeText(ForgetnextActivity.this.getApplicationContext(), "请输入6-16位含中英文密码!",
					Toast.LENGTH_LONG).show();
 		}
 		return m.matches();
 	}
	public boolean ModifyPswtoServer() {
		// TODO Auto-generated method stub
		Map<String, String> map = new HashMap<String, String>();
			
		map.put("telephone", user_phone);
		map.put("type", "2");
		map.put("password", new_psw);		
		
		try {
			JSONObject jsonObject = new JSONObject(HttpUtil.postRequest(
					"api/account/forgetPsw.servlet", map,null));
			token = jsonObject.getString("token");
			if (token != null) {
				System.out.println(token);
				return true;
				
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return false;
	}
}
