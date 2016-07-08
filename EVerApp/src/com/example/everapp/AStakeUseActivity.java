package com.example.everapp;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

import org.json.JSONObject;

import com.example.JsonToMap.JsonUtil;
import com.example.util.HttpUtil;
import com.example.util.MyHttpRequest;

import android.R.string;
import android.app.Activity;
import android.app.ActionBar.LayoutParams;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.NumberPicker;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

public class AStakeUseActivity extends Activity{
	private Intent intent;
	private TextView title;
	private TextView stake_name,stake_address,stake_type;
	private TextView stake_stime,stake_etime,stake_price,stake_phone;
	private TextView stake_during_time;
	private String token,codeString;
	private Button user_stake_btn;
	private ImageButton retButton;
	private PopupWindow popupWindow;
	private SharedPreferences preferences;
	private HashMap<String, Object>stakeMap;
	private TextView cancelButton;
	private TextView confirmButton;
	private NumberPicker numberPicker;
	private final int minValue = 5, maxValue = 60;
	private int duringValue = minValue;
	private boolean isduringPicker=false,islogin;
	private String starttime,endtime;
	private Handler handler;
	private String user_id;
	private static final int POST_FAIL = -1;
	private static final int POST_SUCCESS = 0;
	private static final int POST_Money_FAIL = 1;
	private static final int POST_NO_TIME=2;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.electric_charging);
		codeString=getIntent().getStringExtra("codeString");
		Log.e("codestring", codeString);
		preferences = this.getSharedPreferences("ever", this.MODE_PRIVATE);
		token=preferences.getString("token", "");//得到用户的token值
		if(token==null||"".equals(token.trim())){
			islogin=false;
		}
		else{
			islogin=true;
		}
		 title=(TextView)findViewById(R.id.title);
		 retButton=(ImageButton)findViewById(R.id.return_btn);
		 stake_name=(TextView)findViewById(R.id.stake_name_txt);
		 stake_address=(TextView)findViewById(R.id.stake_address_txt);
		 stake_type=(TextView)findViewById(R.id.stake_type_txt);
		 stake_stime=(TextView)findViewById(R.id.stake_stime_txt);
		 stake_etime=(TextView)findViewById(R.id.stake_etime_txt);
		 stake_price=(TextView)findViewById(R.id.stake_price_txt);
		 stake_phone=(TextView)findViewById(R.id.stake_phone_txt);
		 stake_during_time=(TextView)findViewById(R.id.during_time_txt);
		 user_stake_btn=(Button)findViewById(R.id.start_use_btn);
		 handler = new Handler(){
				public void handleMessage(Message msg) {
					AStakeUseActivity.this.handleMessage(msg);
				};
			};					
			initActivity();	
	}
	private void initActivity() {
		// TODO Auto-generated method stub
		title.setText("充电界面");
		//test_stake(token);
		if(GetstakeInfo(codeString)){
			stake_name.setText(stakeMap.get("description").toString());
			stake_address.setText(stakeMap.get("address").toString());
			if(stakeMap.get("type").toString().equals("1")){
			stake_type.setText("私有桩");
			}
			else{
				stake_type.setText("公有桩");
			}
			stake_stime.setText(stakeMap.get("availableStime").toString());
			stake_etime.setText(stakeMap.get("availableEtime").toString());
			stake_phone.setText(stakeMap.get("ownerTelephone").toString());
			stake_price.setText(stakeMap.get("price").toString());
			//返回界面
			retButton.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					finish();
				}
			});
		//	弹出充电时间段
			stake_during_time.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					initDuringPickerPopupWindow();
				}
			});
		user_stake_btn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				compute_time(duringValue);
				new Thread(new Runnable() {
					public void run() {
						//向服务器请求当前是否能使用该充电桩
						String stakeId=stakeMap.get("id").toString();
						userStake(stakeId, starttime, endtime, token);
					}
				}).start();
			}
		});
		user_stake_btn.setClickable(false);
		
		}
		else{
			Toast.makeText(AStakeUseActivity.this, "失败", Toast.LENGTH_SHORT).show();
			return;
		}
	}
	private void handleMessage(Message msg){
		Bundle b = msg.getData();
		int res = b.getInt("result");
		switch (res) {
		case POST_SUCCESS:
			Toast.makeText(this, "开始充电", Toast.LENGTH_SHORT).show();
			intent=new Intent();
			intent.putExtra("during", duringValue+"");
			intent.putExtra("starttime", starttime);
			intent.putExtra("user_record_id", user_id);
			intent.putExtra("price", stakeMap.get("price").toString());
			intent.setClass(AStakeUseActivity.this, PayForStakeActivity.class);
			startActivity(intent);
			finish();
			break;
		case POST_Money_FAIL:
			Toast.makeText(this, "余额不足请充值" , Toast.LENGTH_SHORT).show();
			break;
		case POST_NO_TIME:
			Toast.makeText(this, "该时间段不可用", Toast.LENGTH_SHORT).show();
			break;
		case POST_FAIL:
			Toast.makeText(this, b.getString("errmsg"),Toast.LENGTH_SHORT).show();
			break;
		default:
			break;
		}
	}
	private void userStake(String stakeId, String startTime, String endTime, String token){
		Bundle bundle = new Bundle();
		try {
			int type=userElectircDrive(stakeId, startTime, endTime, token);
			if(type==1){
				bundle.putInt("result", POST_SUCCESS);
				Message msg = handler.obtainMessage();
				msg.setData(bundle);
				handler.sendMessage(msg);
			}
			else if(type==2){
				bundle.putInt("result", POST_Money_FAIL);
				bundle.putString("errmsg", "something wrong");
				Message msg = handler.obtainMessage();
				msg.setData(bundle);
				handler.sendMessage(msg);
			}
			else if(type==3){
				bundle.putInt("result", POST_NO_TIME);
				bundle.putString("errmsg", "something wrong");
				Message msg = handler.obtainMessage();
				msg.setData(bundle);
				handler.sendMessage(msg);
			}
			else {
				bundle.putInt("result", POST_FAIL);
				bundle.putString("errmsg", "error");
				Message msg = handler.obtainMessage();
				msg.setData(bundle);
				handler.sendMessage(msg);
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			bundle.putInt("result", POST_FAIL);
			bundle.putString("errmsg", e.getMessage());
			Message msg = handler.obtainMessage();
			msg.setData(bundle);
			handler.sendMessage(msg);
		}

	}
  private int userElectircDrive(String stakeId, String startTime,
			String endTime, String token) {
		// TODO Auto-generated method stub
	  Map<String, String> map = new HashMap<String, String>();
		map.put("stakeId", stakeId);
		map.put("startTime", startTime);
		map.put("endTime", endTime);
		try {
			JSONObject jsonObject = new JSONObject(HttpUtil.postRequest(
					"api/user/chargeRecord.servlet",map,token));
			String result=jsonObject.getString("data");
			if(result.equals("lessRemaining")){
				return 2;//余额不足
			}
			else if(result.equals("notAvailable")){
				return 3;//不可用
			}
			else {
				Object temp=JsonUtil.jsonParse(result);
				if(temp instanceof Map){
					user_id=((Map) temp).get("id").toString();
					Log.e("user_id_okkkkkkkkkk", user_id);
					 return 1;//可用
				}
				
			}
			}
		catch (Exception e) {
			//e.printStackTrace();	
			return -1;
		}
		return -1;
	}
protected boolean GetstakeInfo(String codeString){
	  Object stakeObject = JsonUtil.jsonParse(codeString);
	  if (stakeObject instanceof Map) {
			stakeMap=(HashMap<String, Object>)stakeObject;
		for (java.util.Map.Entry<String, Object> entry : stakeMap
			.entrySet()) {
		System.out.println("key= " + entry.getKey()
				+ " and value= " + entry.getValue());
	}
			return true;
		}
		return false;
  }
 
  
	private void initDuringPickerPopupWindow(){
		LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View popView = inflater.inflate(R.layout.duringpicker_popupwindow, null);
		popupWindow = new PopupWindow(popView, LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
		popupWindow.setOutsideTouchable(true);
		popupWindow.setFocusable(true);
		popupWindow.setBackgroundDrawable(new BitmapDrawable()); 
		popupWindow.setAnimationStyle(R.style.mypopwindow_anim_style);
	    popupWindow.showAtLocation(findViewById(R.id.Userlayout),
	        Gravity.BOTTOM, 0, 0);
	    cancelButton = (TextView) popView.findViewById(R.id.cancel_btn);
	    confirmButton = (TextView) popView.findViewById(R.id.confirm_btn);
	    numberPicker = (NumberPicker) popView.findViewById(R.id.numberPicker);
	    numberPicker.setMinValue(minValue);
	    numberPicker.setMaxValue(maxValue);
	    numberPicker.setValue(minValue);
	    cancelButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				popupWindow.dismiss();
			}
		});
	    confirmButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				isduringPicker = true;
				duringValue = numberPicker.getValue();
				showDuring();
				popupWindow.dismiss();
			}
		});
	    
	    
	}
	private void showDuring(){
		stake_during_time.setText(duringValue + "分");
		stake_during_time.setTextColor(getResources().getColor(R.color.orange));
		initStartButton();
	}
	private void initStartButton() {
		// TODO Auto-generated method stub
		
			if(islogin&&isduringPicker){
				user_stake_btn.setBackgroundColor(getResources().getColor(R.color.orange));
				user_stake_btn.setClickable(true);
				
			}
			else{
				user_stake_btn.setBackgroundColor(getResources().getColor(R.color.darkgray));
				user_stake_btn.setClickable(false);
				Toast.makeText(AStakeUseActivity.this, "请登录", Toast.LENGTH_SHORT).show();
			}
			
		}
	private void compute_time(int during){
		SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
		Date curDate = new Date(System.currentTimeMillis());
		starttime=sDateFormat.format(curDate);//当前时间
		long long_endtime=curDate.getTime()+during*60*1000;
		Date endDate=new Date(long_endtime);//结束时间计算
		 endtime=sDateFormat.format(endDate);//结束时间
	}
	
}
