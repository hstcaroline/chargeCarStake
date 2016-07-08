package com.example.everapp;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import org.json.JSONObject;

import com.example.JsonToMap.JsonUtil;
import com.example.util.HttpUtil;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class UserComplaintActivity extends Activity {
	private String token,record_id,stake_own_id,stakename,complaint_reason;
	private ImageButton reback_btn;
	private TextView title;
	private EditText stake_name,com_reason;
	private Button complaint_Button;
	private boolean flag=true;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.stake_complaint);
		Intent intent=getIntent();
		token=intent.getStringExtra("token");
		record_id=intent.getStringExtra("record_id");
		stake_own_id=intent.getStringExtra("stake_owner_id");
		//System.out.println(stake_own_id);
		//��ʼ���ؼ�
		reback_btn = (ImageButton) findViewById(R.id.return_btn);
		TextView title = (TextView) findViewById(R.id.title);
		title.setText("��׮Ͷ��");
		stake_name=(EditText)findViewById(R.id.com_stake_name);//Ͷ�߳�׮����
		com_reason=(EditText)findViewById(R.id.com_stake_reason);//Ͷ�����ɱ༭��
		complaint_Button=(Button)findViewById(R.id.com_commit_btn);//�ύͶ��button
		reback_btn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
		complaint_Button.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(flag){
				stakename=stake_name.getText().toString();
				complaint_reason=com_reason.getText().toString();
			 if(stakename==null|| "".equals(stakename.trim())){
				 Toast.makeText(UserComplaintActivity.this, "��׮���Ʋ���Ϊ��",
							Toast.LENGTH_LONG).show();
				 return;
			 }
			 if(complaint_reason==null|| "".equals(complaint_reason.trim())){
				 Toast.makeText(UserComplaintActivity.this, "Ͷ�����ɲ���Ϊ��",
							Toast.LENGTH_LONG).show();
				 return;
			 }
			 else{
				if(Commit_complaint(token, record_id, stake_own_id, stakename, complaint_reason))
				{    flag=false;
					 Toast.makeText(UserComplaintActivity.this, "Ͷ�߳ɹ�",
								Toast.LENGTH_LONG).show();
					
				}
				else{
					 Toast.makeText(UserComplaintActivity.this, "Ͷ��ʧ��",
								Toast.LENGTH_LONG).show();
				}
			 }
			}
				else{
					Toast.makeText(UserComplaintActivity.this, "�����ظ��ύͶ�ߣ�",
							Toast.LENGTH_LONG).show();
				}
			}
		});
		
	}
	protected boolean  Commit_complaint(String token,String useStakeId,String stakeOwnerId,String stake_name,String stake_reason)
	{
		Map<String, String> map = new HashMap<String, String>();
		map.put("useStakeId", useStakeId);
		map.put("stakeOwnerId",stakeOwnerId);
		String reason=stake_name+" "+stake_reason;
		map.put("content", reason);
		SimpleDateFormat sDateFormat=   new  SimpleDateFormat("yyyy-MM-dd HH:mm:ss",Locale.getDefault());       
		String    date   =  sDateFormat.format(new Date());//��ȡ��ǰʱ��
		//System.out.println(date);
		map.put("time", date);
		try {
			JSONObject jsonObject = new JSONObject(HttpUtil.postRequest(
					"api/user/complaintAdd.servlet", map,token));
			String tokens = jsonObject.getString("token");
			// ����������������ķ���ֵ
			if (tokens != null) {
				// System.out.println(token);
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return false;
	}

}
