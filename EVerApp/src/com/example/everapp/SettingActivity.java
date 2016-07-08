package com.example.everapp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

public class SettingActivity extends Activity{
	private ListView setting_lis;
	private ImageButton set_reback_btn;
	
	private Button out_btn;
	private TextView titleText;
	
	private SharedPreferences preferences;
	private SharedPreferences.Editor editor;
	private static final String[] str=new String[]{"消息设置","关于我们"};
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.user_setting);
	 //setting the listview's value
		
		preferences = getSharedPreferences("ever", MODE_PRIVATE);
		editor = preferences.edit();
		setting_lis=(ListView)findViewById(R.id.setting_list);
		set_reback_btn=(ImageButton)findViewById(R.id.return_btn);
		out_btn=(Button)findViewById(R.id.out_btn);//退出登录按钮
		titleText = (TextView) findViewById(R.id.title);
		 titleText.setText("设置");
		String[] mFrom = new String[] { "item", "img" };
		int[] mTo = new int[] {R.id.setting_text_item,R.id.setting_image_item };
		List<Map<String, Object>> mList = new ArrayList<Map<String, Object>>();
		for(int i=0;i<2;i++){
			Map<String, Object>listItem=new HashMap<String,Object>();
			listItem.put("item",str[i]);
			listItem.put("img", R.drawable.go);
			mList.add(listItem);
		}
		SimpleAdapter simpleAdapter=new SimpleAdapter(this,mList,R.layout.setting_item,mFrom,mTo);
		setting_lis.setAdapter(simpleAdapter);
		set_reback_btn.setOnClickListener(new OnClickListener() {
				@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				SettingActivity.this.finish();
			}
		});
		out_btn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				editor.putString("token", "");
				editor.commit();
				SettingActivity.this.setResult(RESULT_OK);
				finish();
				  
				
			}
		});
	}

}
