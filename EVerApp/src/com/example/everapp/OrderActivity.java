package com.example.everapp;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import com.baidu.a.a.a.b;
import com.baidu.mapapi.map.Text;
import com.example.util.MyHttpRequest;

import android.accounts.OnAccountsUpdateListener;
import android.app.Activity;
import android.app.ActionBar.LayoutParams;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.BitmapDrawable;
import android.media.Image;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.NumberPicker;
import android.widget.NumberPicker.OnValueChangeListener;
import android.widget.PopupWindow;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.TimePicker.OnTimeChangedListener;
import android.widget.Toast;

public class OrderActivity extends Activity {

	private ImageButton retButton;
	private TextView title;
	private TextView timePickText;
	private TextView duringPickText;
	private PopupWindow popupWindow;
	private int day = 0;//0表示今天，1表示明天
	private int hour = 0, minute = 0;
	private boolean isTimePicked = false, isDuringPicked;
	private RadioGroup dayRadioGroup;
	private TimePicker timePicker;
	private TextView cancelButton;
	private TextView confirmButton;
	private NumberPicker numberPicker;
	private final int minValue = 5, maxValue = 60;
	private int duringValue = minValue;
	private Button orderButton;
	private SharedPreferences preferneces;
	private Intent i;
	String startTime,endTime;
	private Handler handler;
	private static final int POST_ORDER_SUCCESS = 0;
	private static final int POST_ORDER_FAIL = 1;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.electirc_order);
		i = getIntent();
		preferneces = getSharedPreferences("ever", MODE_PRIVATE);
		handler = new Handler(){
			public void handleMessage(Message msg) {
				OrderActivity.this.handleMessage(msg);
			};
		};
		retButton = (ImageButton) findViewById(R.id.return_btn);
		title = (TextView) findViewById(R.id.title);
		timePickText = (TextView) findViewById(R.id.timepick_btn);
		duringPickText = (TextView) findViewById(R.id.during_btn);
		orderButton = (Button) findViewById(R.id.order_btn);
		
		initActivity();
	}

	
	private void handleMessage(Message msg){
		Bundle b = msg.getData();
		int res = b.getInt("result");
		switch (res) {
		case POST_ORDER_SUCCESS:
			Toast.makeText(this, "预约成功", Toast.LENGTH_SHORT).show();
			finish();
			break;
		case POST_ORDER_FAIL:
			Toast.makeText(this, "预约失败：" + b.getString("errmsg"), Toast.LENGTH_SHORT).show();
			break;
		default:
			break;
		}
	}
	private void initActivity() {
		title.setText("预约");
		retButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				finish();
			}
		});
		timePickText.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				initTimePickerPopupWindow();
			}
		});
		duringPickText.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				initDuringPickerPopupWindow();
			}
		});
		orderButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				
				Calendar calendar = Calendar.getInstance();
				if(day == 1)
					calendar.add(Calendar.DATE, 1);
				calendar.set(Calendar.HOUR_OF_DAY, hour);
				calendar.set(Calendar.MINUTE, minute);
				SimpleDateFormat sFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				startTime = sFormat.format(calendar.getTime());
				calendar.add(Calendar.MINUTE, duringValue);
				endTime = sFormat.format(calendar.getTime());
				new Thread(new Runnable() {
					public void run() {
						orderStake(""+i.getIntExtra("stakeId",-1),startTime,endTime,preferneces.getString("token", ""));
					}
				}).start();
			}
		});
		orderButton.setClickable(false);
		
	}
	
	private void orderStake(String stakeId, String startTime, String endTime, String token){
		Bundle bundle = new Bundle();
		try {
			if(MyHttpRequest.orderElectircDrive(stakeId, startTime, endTime, token)){
				bundle.putInt("result", POST_ORDER_SUCCESS);
				Message msg = handler.obtainMessage();
				msg.setData(bundle);
				handler.sendMessage(msg);
			}
			else{
				bundle.putInt("result", POST_ORDER_FAIL);
				bundle.putString("errmsg", "something wrong");
				Message msg = handler.obtainMessage();
				msg.setData(bundle);
				handler.sendMessage(msg);
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			bundle.putInt("result", POST_ORDER_FAIL);
			bundle.putString("errmsg", e.getMessage());
			Message msg = handler.obtainMessage();
			msg.setData(bundle);
			handler.sendMessage(msg);
		}

	}
	private void initTimePickerPopupWindow(){
		LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View popView = inflater.inflate(R.layout.datepicker_popupwindow, null);
		popupWindow = new PopupWindow(popView, LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
		popupWindow.setOutsideTouchable(true);
		popupWindow.setFocusable(true);
		popupWindow.setBackgroundDrawable(new BitmapDrawable()); 
		popupWindow.setAnimationStyle(R.style.mypopwindow_anim_style);
	    popupWindow.showAtLocation(findViewById(R.id.orderLayout),
	        Gravity.BOTTOM, 0, 0);
	    dayRadioGroup = (RadioGroup) popView.findViewById(R.id.dayPicker);
	    timePicker = (TimePicker) popView.findViewById(R.id.timePicker);
	    timePicker.setDescendantFocusability(TimePicker.FOCUS_BLOCK_DESCENDANTS);  

	    cancelButton = (TextView) popView.findViewById(R.id.cancel_btn);
	    confirmButton = (TextView) popView.findViewById(R.id.confirm_btn);
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
				hour = timePicker.getCurrentHour().intValue();
				minute = timePicker.getCurrentMinute().intValue();
				day = dayRadioGroup.getCheckedRadioButtonId() == R.id.today ? 0 : 1;
				showTime();
				popupWindow.dismiss();
			}
		});
/*	    timePicker.setOnTimeChangedListener(new OnTimeChangedListener() {
			
			@Override
			public void onTimeChanged(TimePicker view, int hourofday, int min) {
				// TODO Auto-generated method stub
				hour = hourofday;
				minute = min;
			}
		});
	    dayRadioGroup.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(RadioGroup arg0, int checkedId) {
				// TODO Auto-generated method stub
				day = checkedId == R.id.today ? 0 : 1;
			}
		});
		*/
	}
	
	private void initDuringPickerPopupWindow(){
		LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View popView = inflater.inflate(R.layout.duringpicker_popupwindow, null);
		popupWindow = new PopupWindow(popView, LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
		popupWindow.setOutsideTouchable(true);
		popupWindow.setFocusable(true);
		popupWindow.setBackgroundDrawable(new BitmapDrawable()); 
		popupWindow.setAnimationStyle(R.style.mypopwindow_anim_style);
	    popupWindow.showAtLocation(findViewById(R.id.orderLayout),
	        Gravity.BOTTOM, 0, 0);
	    cancelButton = (TextView) popView.findViewById(R.id.cancel_btn);
	    confirmButton = (TextView) popView.findViewById(R.id.confirm_btn);
	    numberPicker = (NumberPicker) popView.findViewById(R.id.numberPicker);
	    numberPicker.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);  

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
				isDuringPicked = true;
				duringValue = numberPicker.getValue();
				showDuring();
				popupWindow.dismiss();
			}
		});
	    
	    
	}
	
	private void showTime(){
		if(!checkTime()){
			Toast.makeText(this, "时间选择错误", Toast.LENGTH_SHORT).show();
			return;
		}
		isTimePicked = true;
		String dayStr = (day == 0)?"今天":"明天";
		timePickText.setText(dayStr + hour + "时" + minute + "分");
		timePickText.setTextColor(getResources().getColor(R.color.orange));
		initOrderButton();
	}
	
	private void showDuring(){
		duringPickText.setText(duringValue + "分");
		duringPickText.setTextColor(getResources().getColor(R.color.orange));
		initOrderButton();
	}
	
	private void initOrderButton(){
		if(isDuringPicked && isTimePicked){
			orderButton.setBackgroundColor(getResources().getColor(R.color.orange));
			orderButton.setClickable(true);
			
		}
		else{
			orderButton.setBackgroundColor(getResources().getColor(R.color.darkgray));
			orderButton.setClickable(false);
		}
		
	}
	
	private boolean checkTime(){
		Calendar c = Calendar.getInstance();
		int nowhour = c.get(Calendar.HOUR_OF_DAY);
		int nowminute = c.get(Calendar.MINUTE);
		if(day == 0){
			if((nowhour > hour) || (nowhour == hour && nowminute > minute))
				return false;
		}
		else{
			if(nowhour < hour || (nowhour == hour && nowminute < minute))
				return false;
		}
		return true;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.order, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
