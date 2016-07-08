package com.example.everapp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

import com.example.dialog.MyDialog;
import com.example.util.HttpUtil;

import android.app.ActionBar.LayoutParams;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;



public class ChargestakeModifyActivity extends Activity{
	private static final int DIALOG_EXIT = 0;
	private TextView title;
	private ImageButton reback_btn;
	private TextView stimetxt,etimetxt;
	private EditText price;
	private TextView cancelButton,confirmButton;
	private TimePicker timePicker;
	private PopupWindow popupWindow;
	private String time,token,stake_id,stake_name;
	private SharedPreferences preferences;
	private int hour=0,minute=0,ehour=0,eminute=0;
	private Button savemodify_btn,cancel_btn;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.charge_modify_item);
		preferences = this.getSharedPreferences("ever", this.MODE_PRIVATE);
		token=preferences.getString("token", "");
		Log.e("token", token);//打印接收的token值
		stake_id=getIntent().getStringExtra("stake_id");//接收传来的车桩id
		stake_name=getIntent().getStringExtra("description");//接收传来的车桩名字
		if(!stake_name.equals("")){
		title = (TextView) findViewById(R.id.title);
		title.setText(stake_name);
		}
		reback_btn = (ImageButton) findViewById(R.id.return_btn);
		reback_btn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent=new Intent();
				intent.setClass( ChargestakeModifyActivity.this,AllStakeActivity.class);
				startActivity(intent);
				finish();
			}
		});
		stimetxt=(TextView)findViewById(R.id.modify_Stime_txt);
		etimetxt=(TextView)findViewById(R.id.modify_Etime_txt);
		price=(EditText)findViewById(R.id.modify_price_edit);
		savemodify_btn=(Button)findViewById(R.id.commit_modify_btn);
		cancel_btn=(Button)findViewById(R.id.cancel_stake_btn);
		stimetxt.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				int flag=1;
				initTimePickerPopupWindow(flag);
			}
		});
		etimetxt.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				int flag=2;
				initTimePickerPopupWindow(flag);
			}
		});
		savemodify_btn.setOnClickListener(new OnClickListener() {//保存修改btn
			@Override
			public void onClick(View v) {
				String stime=stimetxt.getText().toString();
				String etime=etimetxt.getText().toString();
				String Mprice=price.getText().toString();
				// TODO Auto-generated method stub
				if(CheckTime()){
					//Toast.makeText(ChargestakeModifyActivity.this, stime+"时间输入正确"+etime, Toast.LENGTH_SHORT).show();
			     if(Mprice==null||"".equals(Mprice.trim())){
			    	 Toast.makeText(ChargestakeModifyActivity.this, Mprice+"价格error", Toast.LENGTH_SHORT).show();
			    	
			     }
			     else{
			    	 //Toast.makeText(ChargestakeModifyActivity.this, Mprice+"价格", Toast.LENGTH_SHORT).show();
			    	 if(Modify_Stake("0", token, stake_id, stime, etime, Mprice)){
				    		Toast.makeText(ChargestakeModifyActivity.this,"Success!", Toast.LENGTH_SHORT).show();
				    	}
				    	else {
				    		 Toast.makeText(ChargestakeModifyActivity.this,"error", Toast.LENGTH_SHORT).show();
						}
			     }
				}
				else {
					Toast.makeText(ChargestakeModifyActivity.this, stime+"时间输入错误"+etime, Toast.LENGTH_SHORT).show();
				}
			}
		});
		cancel_btn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				 showDialog(DIALOG_EXIT);  
			}
		});
	}
	
	private void initTimePickerPopupWindow(final int flag){
		LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View popView = inflater.inflate(R.layout.timepicker_pop, null);
		popupWindow = new PopupWindow(popView, LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
		popupWindow.setOutsideTouchable(true);
		popupWindow.setFocusable(true);
		popupWindow.setBackgroundDrawable(new BitmapDrawable()); 
		popupWindow.setAnimationStyle(R.style.mypopwindow_anim_style);
	    popupWindow.showAtLocation(findViewById(R.id.ModifyLayout),
	        Gravity.BOTTOM, 0, 0);
	    cancelButton = (TextView) popView.findViewById(R.id.time_cancel_btn);
	    confirmButton = (TextView) popView.findViewById(R.id.time_confirm_btn);
	    timePicker=(TimePicker)popView.findViewById(R.id.time_Picker);
	    cancelButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				popupWindow.dismiss();
				return;
			}
		});
	    confirmButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				if(flag==1){
				hour = timePicker.getCurrentHour().intValue();
				minute = timePicker.getCurrentMinute().intValue();			
				 time=hour+":"+minute+":00";
				 //System.out.println(starttime);
				 
				stimetxt.setText(time);
				popupWindow.dismiss();	
				return;
				}
				if(flag==2){
					ehour = timePicker.getCurrentHour().intValue();
					eminute = timePicker.getCurrentMinute().intValue();			
					 time=ehour+":"+eminute+":00";
					 //System.out.println(starttime);
				    etimetxt.setText(time);
					popupWindow.dismiss();	
					return;
				}
			
			}
		});
	    
	}
	protected boolean CheckTime(){
		if(ehour<hour){
			return false;
		}
		else if(ehour==hour&&eminute<=minute){
			//System.out.println(ehour+eminute+"*******"+hour+minute);
			return false;
		}
		else{
			return true;
		}
		
	}
	protected boolean Modify_Stake(String type,String token,String stake_id,String stime,String etime,String price){
		Map<String, String> map = new HashMap<String, String>();
		map.put("stakeId",stake_id);
		map.put("availableStime",stime);
		map.put("availableEtime", etime);
		map.put("status", type);
		map.put("price", price);
	  try {
		  JSONObject jsonObject = new JSONObject(HttpUtil.putRequest(
					"api/user/stakeUpdate.servlet",map,token));
		  String tokens=jsonObject.getString("token");
		  if(token!=null){
			 Log.e("testtttt",tokens);
			 return true;
		  }
		  else {
			return false;
		}
		
	} catch (Exception e) {
		// TODO: handle exception
		e.printStackTrace();
		return false;
	}
	}
	protected boolean Cancl_Stake(String token,String stake_id){
		Map<String, String> map = new HashMap<String, String>();
		map.put("stakeId",stake_id);
		map.put("status", "2");
	  try {
		  JSONObject jsonObject = new JSONObject(HttpUtil.putRequest(
					"api/user/stakeUpdate.servlet",map,token));
		  String tokens=jsonObject.getString("token");
		  if(token!=null){
			 Log.e("testtttt",tokens);
			 return true;
		  }
		  else {
			return false;
		}
		
	} catch (Exception e) {
		// TODO: handle exception
		e.printStackTrace();
		return false;
	}
	}
	protected Dialog onCreateDialog(int id) {  
	    Dialog dialog = null;  
	    switch (id) {  
	    case DIALOG_EXIT:  
	        MyDialog.Builder myBuilder = new MyDialog.Builder(ChargestakeModifyActivity.this);  
	        myBuilder.setTitle("温馨提示");  
	        myBuilder.setMessage("您确定撤销车桩吗?");  
	        myBuilder.setBackButton("返 回", new DialogInterface.OnClickListener() {  
	            @Override  
	            public void onClick(DialogInterface arg0, int arg1) {  
	                // 关闭对话框  
	                dismissDialog(DIALOG_EXIT);  
	            }  
	        });  
	        myBuilder.setConfirmButton("确 定", new DialogInterface.OnClickListener() {  
	            @Override  
	            public void onClick(DialogInterface arg0, int arg1) {  
	                // 撤销车桩操作 
	            	Cancl_Stake(token, stake_id);
	                finish();  
	            }  
	        });  
	        dialog = myBuilder.create();  
	        break;  
	    }  
	    return dialog;  
	}
}
