package com.example.everapp;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONObject;

import com.example.JsonToMap.JsonUtil;
import com.example.SerialMap.SerializableMap;
import com.example.util.HttpUtil;

import android.app.Activity;
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

public class UserPswActivity extends Activity{
	private ImageButton rebackButton;//���ذ�ť
	private TextView titleText;//����
	private String token,old_psw_str,new_psw_str,confirm_new_str;//token���ڸ������롢old_psw_str��֤ԭ�����Ƿ���ȷ
	private Button save_psw_btn;
	private EditText old_psw_edit,new_psw_edit,confirm_psw_edit;//�õ��༭���е�����
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.user_modify_psw);
		//�����ϸ����洫��������
		token=getIntent().getStringExtra("token");
		//System.out.println(token);
		//��ʼ���ؼ�
		titleText = (TextView) findViewById(R.id.title);
		titleText.setText("�����޸�");
		old_psw_edit=(EditText)findViewById(R.id.old_psw_edit);
		new_psw_edit=(EditText)findViewById(R.id.new_psw_edit);
		confirm_psw_edit=(EditText)findViewById(R.id.new_confirm_psw_edit);
		save_psw_btn=(Button)findViewById(R.id.psw_save_btn);
		rebackButton=(ImageButton)findViewById(R.id.return_btn);
		rebackButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
		save_psw_btn.setOnClickListener(new OnClickListener() {
	  
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				old_psw_str=old_psw_edit.getText().toString();//��ȡ������
				new_psw_str=new_psw_edit.getText().toString();//��ȡ������
				confirm_new_str=confirm_psw_edit.getText().toString();//��ȡ�ٴ������������
			if(new_psw_str== null || "".equals(new_psw_str.trim())){
				 Toast.makeText(UserPswActivity.this, "���벻��Ϊ��",
							Toast.LENGTH_LONG).show();
				 return;
			}
			if(new_psw_str.equals(confirm_new_str)){
			  if(validate_psw(old_psw_str, token, "0"))
			  {    System.out.println(new_psw_str);
				  if(validate_psw(new_psw_str, token, "1")){
					  Toast.makeText(UserPswActivity.this, "�����޸ĳɹ�",
								Toast.LENGTH_LONG).show();
					  return;
				  }else{
					  Toast.makeText(UserPswActivity.this, "�޸�ʧ��",
								Toast.LENGTH_LONG).show();
					  return;
				  }
			  }
			  else{
				  Toast.makeText(UserPswActivity.this, "ԭ���벻��ȷ",
							Toast.LENGTH_LONG).show();
				  return;
			  }
			}
			else{
				Toast.makeText(UserPswActivity.this, "�������������벻һ��",
						Toast.LENGTH_LONG).show();
				return;
			}
			}
		});
	}
	protected boolean validate_psw(String psw,String token,String type){
		Map<String, String> map = new HashMap<String, String>();
		map.put("type", type);
		map.put("password", psw);
		try {
			JSONObject jsonObject = new JSONObject(HttpUtil.postRequest(
					"api/account/updatePsw.servlet",map,token));
			String tokens=jsonObject.getString("token");
			if (tokens == null||"".equals(tokens.trim())) {
				
				return false;
			}
			else{
				System.out.println(tokens);
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	
	}

}
