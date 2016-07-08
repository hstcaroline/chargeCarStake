package com.example.everapp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
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

public class UserChargeStakeActivity extends Activity{
	private ListView charge_list;
	private ImageButton reback_btn;
	private TextView titleText;
	//private String token;
	private static final String[] str=new String[]{"车桩注册","车桩分享","车桩修改"};
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.my_charge_stake);
		charge_list=(ListView)findViewById(R.id.user_charge_list);
		reback_btn=(ImageButton)findViewById(R.id.return_btn);
		titleText = (TextView) findViewById(R.id.title);
		 titleText.setText("我的车桩");
		//token=getIntent().getStringExtra("token");
		String[] mFrom = new String[] { "item", "img" };
		int[] mTo = new int[] {R.id.charge_text_item,R.id.charge_image_item};
		List<Map<String, Object>> mList = new ArrayList<Map<String, Object>>();
		for(int i=0;i<3;i++){
			Map<String, Object>listItem=new HashMap<String,Object>();
			listItem.put("item",str[i]);
			listItem.put("img", R.drawable.go);
			mList.add(listItem);
		}
		SimpleAdapter simpleAdapter=new SimpleAdapter(this,mList,R.layout.my_charge_item,mFrom,mTo);
		charge_list.setAdapter(simpleAdapter);
	    reback_btn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				UserChargeStakeActivity.this.finish();
			}
		});
	    //set listview item action
	    charge_list.setOnItemClickListener(new OnItemClickListener(){
	    	Intent intent =new Intent();
	    	@Override
	    	public void onItemClick(AdapterView<?> parent, View view,
	    			int position, long id) {
	    		// TODO Auto-generated method stub
	    		switch (position) {
				case 0://车桩注册
					intent.setClass(UserChargeStakeActivity.this, ChargestakeRegistActivity.class);
					startActivity(intent);
					break;
				case 1://车桩分享
					//intent.putExtra("token", token);
					intent.setClass(UserChargeStakeActivity.this, ChargestakeShareActivity.class);
					startActivity(intent);
					break;
				case 2://车桩修改
					intent.setClass(UserChargeStakeActivity.this, AllStakeActivity.class);
					startActivity(intent);
					break;
				default:
					break;
				}
	    	}
	    });
		
	}

}
