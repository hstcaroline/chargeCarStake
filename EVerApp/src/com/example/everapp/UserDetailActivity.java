package com.example.everapp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.example.SerialMap.SerializableMap;

import android.R.bool;
import android.R.integer;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings.System;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

public class UserDetailActivity extends Activity {
	private ListView user_detaiListView;
	
	private ImageButton rebackButton;
	private TextView titleText;
	private String[] str=new String[]{"昵称","手机","余额","诚信度"};
	private String[] str_detail=new String[4];
	private SerializableMap serializableMap;
	private String token;
	private Button psw_modify_Button;
	private static SerializableMap myMap = new SerializableMap();
	private HashMap<String,Object>userMap;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.user_detail);
		user_detaiListView=(ListView)findViewById(R.id.user_detail_list);
		rebackButton=(ImageButton)findViewById(R.id.return_btn);
		psw_modify_Button=(Button)findViewById(R.id.psw_modify_btn);
		//接收userfragement传来的数据usermap//接收修改回传数据
		Bundle bundle=new Bundle();
		bundle = this.getIntent().getExtras();
		serializableMap = (SerializableMap) bundle.get("usermap");
		userMap=serializableMap.getMap();
		token=userMap.get("token").toString();//存储登录用户的token便于后面更新操作
		setUservalue(str_detail);//设置用户初始
		//set listview value
	 List<Map<String, Object>> mList = new ArrayList<Map<String, Object>>();
		String[] mFrom = new String[] { "title", "title_text" };
		int[] mTo = new int[] {R.id.title_name,R.id.title_text};
		for(int i=0;i<4;i++){
			Map<String, Object>listItem=new HashMap<String,Object>();
			listItem.put("title",str[i]);
			listItem.put("title_text", str_detail[i]);
			mList.add(listItem);
		}
		SimpleAdapter simpleAdapter=new SimpleAdapter(this,mList,R.layout.user_detail_item,mFrom,mTo);
		user_detaiListView.setAdapter(simpleAdapter);
		user_detaiListView.setVerticalScrollBarEnabled(true);
		//reback to homepage
		rebackButton.setOnClickListener(new OnClickListener() {
			  Intent intent=new Intent();

			@Override
			public void onClick(View v) {
				
				UserDetailActivity.this.finish();
			}
		});
		titleText = (TextView) findViewById(R.id.title);
		 titleText.setText("个人信息");
		user_detaiListView.setOnItemClickListener(new OnItemClickListener(){
			Intent intent =new Intent();
			Bundle bundle = new Bundle();
			@Override
			//对单点击的每一个item进行修改
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				
				// TODO Auto-generated method stub
				switch(position){
				
				case 0://修改昵称响应
				    intent.putExtra("tag", "0");
					myMap.setMap(userMap);// 将map数据添加到封装的myMap<span></span>中
					bundle.putSerializable("usermap", myMap);
					intent.putExtras(bundle);// bundle封装传递map对象*/
					intent.setClass(UserDetailActivity.this, UserModifyActivity.class);
					intent.setFlags(Intent.FLAG_ACTIVITY_FORWARD_RESULT);//将A传递给B的传递给C
					startActivity(intent);
					finish();
					break;
				case 1://修改手机号码响应
				
					intent.putExtra("tag", "1");
					myMap.setMap(userMap);// 将map数据添加到封装的myMap<span></span>中					
					bundle.putSerializable("usermap", myMap);
					intent.putExtras(bundle);// bundle封装传递map对象*/
					intent.setClass(UserDetailActivity.this, UserModifyActivity.class);
					intent.setFlags(Intent.FLAG_ACTIVITY_FORWARD_RESULT);//将A传递给B的传递给C
					startActivity(intent);
					finish();
					break;
				default:
					break;
				}
			}
		});
		psw_modify_Button.setOnClickListener(new OnClickListener() {
			      Intent intent=new Intent();
				//Bundle bundle=new Bundle();
			@Override
			public void onClick(View v) {//修改密码响应
				// TODO Auto-generated method stub
				
				intent.putExtra("token", token);
				intent.setClass(UserDetailActivity.this, UserPswActivity.class);
				startActivity(intent);
			}
		});
	}

	protected void setUservalue(String str[]){
		String []uservalue=new String[]{"username","telephone","remaining","faith"};
			for(int i=0;i<4;i++){
				str[i]=userMap.get(uservalue[i]).toString();
				java.lang.System.out.println(str[i]);
			
		}
	}
	
	
	
}
