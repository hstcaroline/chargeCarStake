package com.example.everapp;

import java.util.ArrayList;
import java.util.List;

import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.MapView;
import com.example.everapp.MessageService.MyBinder;
import com.example.everapp.UserLoginActivity.LoginListener;
import com.example.qr_codescan.MipcaActivityCapture;
import com.zxing.activity.CaptureActivity;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTabHost;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TabHost;
import android.widget.TabHost.OnTabChangeListener;
import android.widget.TabHost.TabSpec;
import android.widget.TextView;

public class MainActivity extends FragmentActivity{
	   
		private FragmentTabHost mTabHost;
		
		private LayoutInflater layoutInflater;
			
		public Class fragmentArray[] = {MapFragment.class,MipcaActivityCapture.class,UserFragment.class};
		
		private int mImageViewArray[] = {R.drawable.tab_home_btn,R.drawable.tab_message_btn,R.drawable.tab_selfinfo_btn};
		
		private String mTextviewArray[] = {"地图","充电","我的"};
		
		private SharedPreferences preferences;
		private SharedPreferences.Editor editor;
		
		
		public void onCreate(Bundle savedInstanceState) {
			
			
			
	        super.onCreate(savedInstanceState);
	        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
	        setContentView(R.layout.activity_main);
	        preferences = getSharedPreferences("ever", MODE_PRIVATE);
	        editor = preferences.edit();
	        editor.putString("token", "");
	        editor.commit();
	        initView();
	        }
	    
		 
		private void initView(){
			layoutInflater = LayoutInflater.from(this);
					
			mTabHost = (FragmentTabHost)findViewById(android.R.id.tabhost);
			mTabHost.setup(this, getSupportFragmentManager(), R.id.realtabcontent);	
			
			int count = fragmentArray.length;	
					
			for(int i = 0; i < count; i++){	
				TabSpec tabSpec = mTabHost.newTabSpec(mTextviewArray[i]).setIndicator(getTabItemView(i));
				
				mTabHost.addTab(tabSpec, fragmentArray[i], null);
			
//				mTabHost.getTabWidget().getChildAt(i).setBackgroundResource(R.drawable.selector_tab_background);
			}
		}
					
		private View getTabItemView(int index){
			View view = layoutInflater.inflate(R.layout.tab_item_view, null);
		
			ImageView imageView = (ImageView) view.findViewById(R.id.imageview);
			imageView.setImageResource(mImageViewArray[index]);
			
			TextView textView = (TextView) view.findViewById(R.id.textview);		
			textView.setText(mTextviewArray[index]);
		
			return view;
		}
		
	}
