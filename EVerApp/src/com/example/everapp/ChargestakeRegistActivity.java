package com.example.everapp;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.json.JSONObject;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.location.b.n;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.geocode.GeoCodeResult;
import com.baidu.mapapi.search.geocode.GeoCoder;
import com.baidu.mapapi.search.geocode.OnGetGeoCoderResultListener;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeOption;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeResult;
import com.baidu.navisdk.ui.routeguide.mapmode.subview.RGMMNaviQuitDialog.IntegralListener;
import com.example.util.MyHttpRequest;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.TimePickerDialog;
import android.app.ActionBar.LayoutParams;
import android.app.TimePickerDialog.OnTimeSetListener;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

public class ChargestakeRegistActivity extends Activity implements OnGetGeoCoderResultListener {// 车桩注册主界面
	private ImageButton stake_rebackbButton;
	private TextView titleText;
	private EditText province;
	private EditText city;
	private EditText address;
	private Spinner stakeType;
	private Spinner electricType;
	private EditText sTimeView, eTimeView;
	private EditText phoneView;
	private EditText priceView;
	private Button submitButton;

	private ImageView showMap;
	private static final int CHOOSE_LOCATION = 0;
	GeoCoder mSearch = null; // 搜索模块，也可去掉地图模块独立使用
	private Handler handler;
	private Map<String, Integer> carToId;
	private List<String> carTypeList = new ArrayList<String>();
	private static final int GET_CARTYPE_SUCCESS = 0;
	private static final int GET_CARTYPE_FAIL = 1;
	private ArrayAdapter<String> adapter;
	private PopupWindow popupWindow;
	private TimePicker timePicker;
	private TextView cancelButton;
	private TextView confirmButton;
	private int shour = 0, sminute = 0, ehour = 0, eminute = 0;
	private final int CHOOSE_STIME = 2;
	private final int CHOOSE_ETIME = 3;
	private int price = 0;
	private Double longtitude = 0.0;
	private Double latitude = 0.0;
	private int carTypeId = -1;
	private String carStr;
	private SharedPreferences preferences;
	private final int REGISTER_SUCCESS = 4;
	private final int REGISTER_FAIL = 5;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.charge_stake_regist);
		SDKInitializer.initialize(getApplicationContext());
		titleText = (TextView) findViewById(R.id.title);
		stake_rebackbButton = (ImageButton) findViewById(R.id.return_btn);
		address = (EditText) findViewById(R.id.detail_address);
		stakeType = (Spinner) findViewById(R.id.stake_style_spn);
		sTimeView = (EditText) findViewById(R.id.choose_stime);
		eTimeView = (EditText) findViewById(R.id.choose_etime);
		priceView = (EditText) findViewById(R.id.price_edt);
		submitButton = (Button) findViewById(R.id.stake_regist_btn);
		preferences = getSharedPreferences("ever", MODE_PRIVATE);
		titleText.setText("车桩注册");
		showMap = (ImageView) findViewById(R.id.map_show);
		handler = new Handler() {
			public void handleMessage(android.os.Message msg) {
				ChargestakeRegistActivity.this.handleMessage(msg);
			};
		};
		new Thread(new Runnable() {
			public void run() {
				loadCarType();
			}
		}).start();
		showMap.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				showMapOnClick();
			}
		});
		mSearch = GeoCoder.newInstance();
		mSearch.setOnGetGeoCodeResultListener(this);

		stake_rebackbButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				ChargestakeRegistActivity.this.finish();
			}
		});

		// fill in date

		sTimeView.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				initTimePickerPopupWindow(CHOOSE_STIME);
			}
		});
		eTimeView.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				initTimePickerPopupWindow(CHOOSE_ETIME);
			}
		});

		submitButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				submitOnClick();
			}
		});

	}

	private void initSpinner() {
		adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, carTypeList);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		stakeType.setAdapter(adapter);
		stakeType.setOnItemSelectedListener(new OnItemSelectedListener() {
			public void onItemSelected(android.widget.AdapterView<?> arg0, View arg1, int arg2, long arg3) {
				carStr = adapter.getItem(arg2);
				carTypeId = carToId.get(carStr);
				Toast.makeText(ChargestakeRegistActivity.this, "carType:" + carTypeId, Toast.LENGTH_SHORT).show();
			};

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub

			};
		});

	}

	private void handleMessage(Message msg) {
		Bundle b = msg.getData();
		int type = b.getInt("type");
		switch (type) {
		case GET_CARTYPE_SUCCESS:
			Toast.makeText(this, "获取车辆类型成功", Toast.LENGTH_SHORT).show();
			initSpinner();
			break;
		case GET_CARTYPE_FAIL:
			Toast.makeText(this, b.getString("errmsg"), Toast.LENGTH_SHORT).show();
			break;
		case REGISTER_SUCCESS:
			Toast.makeText(this, "注册车桩成功", Toast.LENGTH_SHORT).show();
			finish();
			break;
		case REGISTER_FAIL:
			Toast.makeText(this, b.getString("errmsg"), Toast.LENGTH_SHORT).show();
			break;
		default:
			break;
		}
	}

	private void loadCarType() {
		Bundle b = new Bundle();
		try {
			carToId = MyHttpRequest.getAllCartype();
			for (Map.Entry<String, Integer> entry : carToId.entrySet()) {
				carTypeList.add(entry.getKey());
			}
			b.putInt("type", GET_CARTYPE_SUCCESS);
			Message msg = handler.obtainMessage();
			msg.setData(b);
			handler.sendMessage(msg);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			b.putInt("type", GET_CARTYPE_FAIL);
			b.putString("errmsg", e.getMessage());
			Message msg = handler.obtainMessage();
			msg.setData(b);
			handler.sendMessage(msg);
		}
	}

	private void showMapOnClick() {
		Intent i = new Intent(ChargestakeRegistActivity.this, ChooseOnMapActivity.class);
		startActivityForResult(i, CHOOSE_LOCATION);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		if (requestCode == CHOOSE_LOCATION && resultCode == RESULT_OK) {
			longtitude = data.getDoubleExtra("longtitude", 0);
			latitude = data.getDoubleExtra("latitude", 0);
			mSearch.reverseGeoCode(new ReverseGeoCodeOption().location(new LatLng(latitude, longtitude)));
		} else {
			Toast.makeText(this, "fail", Toast.LENGTH_SHORT).show();
		}
	}

	@Override
	public void onGetGeoCodeResult(GeoCodeResult arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onGetReverseGeoCodeResult(ReverseGeoCodeResult result) {
		// TODO Auto-generated method stub
		if (result == null || result.error != SearchResult.ERRORNO.NO_ERROR) {
			Toast.makeText(this, "抱歉，未能找到结果", Toast.LENGTH_LONG).show();
			return;
		}

		address.setText(result.getAddress());

	}

	private void initTimePickerPopupWindow(final int type) {
		LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View popView = inflater.inflate(R.layout.timepicker_popupwindow, null);
		popupWindow = new PopupWindow(popView, LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
		popupWindow.setOutsideTouchable(true);
		popupWindow.setFocusable(true);
		popupWindow.setBackgroundDrawable(new BitmapDrawable());
		popupWindow.setAnimationStyle(R.style.mypopwindow_anim_style);
		popupWindow.showAtLocation(findViewById(R.id.charge_register_layout), Gravity.BOTTOM, 0, 0);

		timePicker = (TimePicker) popView.findViewById(R.id.timePicker);
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

				if (type == CHOOSE_STIME) {
					shour = timePicker.getCurrentHour().intValue();
					sminute = timePicker.getCurrentMinute().intValue();
					sTimeView.setText(shour + ":" + sminute);
				} else {
					ehour = timePicker.getCurrentHour().intValue();
					eminute = timePicker.getCurrentMinute().intValue();
					eTimeView.setText(ehour + ":" + eminute);
				}
				popupWindow.dismiss();
			}
		});
	}

	private boolean checkAddress() {
		String s = address.getText().toString();
		if (s.equals("")) {
			Toast.makeText(this, "地址不能为空", Toast.LENGTH_SHORT).show();
			return false;
		}else if(longtitude == 0.0 || latitude == 0.0){
			Toast.makeText(this, "请在地图上标注", Toast.LENGTH_SHORT).show();
			return false;
		}
		return true;

	}

	private boolean checkTime() {
		if (shour == 0 && sminute == 0) {
			Toast.makeText(this, "开始时间不能为空", Toast.LENGTH_SHORT).show();
			return false;
		}
		if (ehour == 0 && eminute == 0) {
			Toast.makeText(this, "开始时间不能为空", Toast.LENGTH_SHORT).show();
			return false;
		}
		if ((shour < ehour) || (shour == ehour && sminute < eminute))
			return true;
		else {
			Toast.makeText(this, "开始时间不能大于结束时间", Toast.LENGTH_SHORT).show();

			return false;
		}

	}

	private boolean checkPrice() {
		try {
			String p = priceView.getText().toString();
			if (p.equals("")) {
				Toast.makeText(this, "单价不能为空", Toast.LENGTH_SHORT).show();
				return false;
			}
			price = Integer.parseInt(p);
			if (price <= 0) {
				Toast.makeText(this, "请输入正数", Toast.LENGTH_SHORT).show();
				return false;
			}
		} catch (Exception e) {
			Toast.makeText(this, "请输入数字", Toast.LENGTH_SHORT).show();
			return false;
		}
		return true;
	}

	private void submitOnClick() {
		if (!checkAddress() || !checkTime() || !checkPrice())
			return;

		new Thread(new Runnable() {
			public void run() {
				registerCharge();
			}
		}).start();

	}

	private void registerCharge() {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm:ss");
		Calendar c = Calendar.getInstance();
		c.set(Calendar.HOUR_OF_DAY, shour);
		c.set(Calendar.MINUTE, sminute);
		c.set(Calendar.SECOND, 0);
		String sTime = simpleDateFormat.format(c.getTime());
		c.set(Calendar.HOUR_OF_DAY, ehour);
		c.set(Calendar.MINUTE, eminute);
		String eTime = simpleDateFormat.format(c.getTime());
		Bundle b = new Bundle();
		try {
			if (MyHttpRequest.chargeRegister(longtitude, latitude, carTypeId, sTime, eTime, price, carStr + "充电桩",
					address.getText().toString(), preferences.getString("token", ""))) {
				b.putInt("type", REGISTER_SUCCESS);
				Message msg = handler.obtainMessage();
				msg.setData(b);
				handler.sendMessage(msg);
			} else {
				b.putInt("type", REGISTER_FAIL);
				b.putString("errmsp", "i dont fucking know");
				Message msg = handler.obtainMessage();
				msg.setData(b);
				handler.sendMessage(msg);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			b.putInt("type", REGISTER_FAIL);
			b.putString("errmsp", e.getMessage());
			Message msg = handler.obtainMessage();
			msg.setData(b);
			handler.sendMessage(msg);
		}
	}

}
