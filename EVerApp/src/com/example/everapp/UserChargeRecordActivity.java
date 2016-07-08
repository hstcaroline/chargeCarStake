package com.example.everapp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

import com.example.JsonToMap.JsonUtil;
import com.example.util.HttpUtil;

import android.R.integer;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.location.GpsStatus.Listener;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

public class UserChargeRecordActivity  extends Activity {
	private ListView charge_record_list;
	private ImageButton reback_btn;
	private List<Map<String, Object>> mData;
	private String token,record_id,stake_owner_id;//用于返回充电记录的token
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.user_charge_record);
		charge_record_list = (ListView) findViewById(R.id.user_charge_record_list);
		reback_btn = (ImageButton) findViewById(R.id.return_btn);
		TextView title = (TextView) findViewById(R.id.title);
		title.setText("充电记录");
		reback_btn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				UserChargeRecordActivity.this.finish();
			}
		});
		Intent intent=this.getIntent();
        token=intent.getStringExtra("token");//接收传来的token值
       // System.out.println(token+"*********************************");
		if(get_Record(token)){
		MyAdapter adapter = new MyAdapter(this);
		charge_record_list.setAdapter(adapter);
		}
	}
	// 获取动态数组数据 可以由其他地方传来
		protected boolean get_Record(String token){
			List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
			try {
				JSONObject jsonObject = new JSONObject(HttpUtil.getRequest(
						"api/user/chargeRecord.servlet",token));
				// 保存服务器发过来的返回值jsonarry解析成map
				String tokens=jsonObject.getString("token");
			if(tokens==null||"".equals(tokens.trim())){
				String date=jsonObject.getString("data");
				System.out.println(date);
				return false;
			}
			else{	
				JSONArray jsonArray=jsonObject.getJSONArray("data");
				for(int i=0;i<jsonArray.length();i++){
					JSONObject jsonObject2=(JSONObject)jsonArray.opt(i);
					Map<String, Object> map = new HashMap<String, Object>();
					map.put("price", jsonObject2.getDouble("price"));
					map.put("description",jsonObject2.getString("description"));
					map.put("end_time", jsonObject2.getString("end_time"));
					if(jsonObject2.getInt("status")==0){
						map.put("status", "正在使用");
					}
					else if(jsonObject2.getInt("status")==1){
						map.put("status", "已完成");
					}
				else{
					map.put("status", "异常");
				}
					map.put("car_type_name",jsonObject2.getString("car_type_name"));
					map.put("longitude",jsonObject2.getDouble("longitude"));
					map.put("latitude",jsonObject2.getDouble("latitude"));
					map.put("record_id",jsonObject2.getInt("id"));//chargerecord_id's values
					map.put("stakeOwnerId", jsonObject2.getInt("stakeOwnerId"));
					list.add(map);
				}
				mData=list;
				return true;
			}
				
			} catch (Exception e) {
				e.printStackTrace();
				return false;
			}
		
		}

		public class MyAdapter extends BaseAdapter {

			private LayoutInflater mInflater;

			public MyAdapter(Context context) {
				this.mInflater = LayoutInflater.from(context);
			}

			@Override
			public int getCount() {
				// TODO Auto-generated method stub
				return mData.size();
			}

			@Override
			public Object getItem(int position) {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public long getItemId(int position) {
				// TODO Auto-generated method stub
				return 0;
			}

			public View getView(final int position, View convertView, ViewGroup parent) {
				ViewHolder holder = null;
				if (convertView == null) {

					holder = new ViewHolder();

					// 可以理解为从vlist获取view 之后把view返回给ListView

					convertView = mInflater.inflate(R.layout.charge_record_item, null);
					holder.time = (TextView) convertView
							.findViewById(R.id.charge_item_use_time);
					holder.name = (TextView) convertView
							.findViewById(R.id.charge_item_name);
					holder.address = (TextView) convertView
							.findViewById(R.id.charge_item_address);
					holder.fee = (TextView) convertView
							.findViewById(R.id.charge_item_fee);
					holder.complainBtn = (Button) convertView
							.findViewById(R.id.complain_btn);
					convertView.setTag(holder);
				} else {
					holder = (ViewHolder) convertView.getTag();
				}

				holder.time.setText((String) mData.get(position).get("end_time"));
				holder.name.setText((String) mData.get(position).get("car_type_name")+mData.get(position).get("description"));
				holder.address.setText((String) mData.get(position).get("status"));
				holder.fee.setText(mData.get(position).get("price").toString());
				holder.complainBtn.setOnClickListener(new OnClickListener() {
					Intent intent=new Intent();
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						Log.v("test", position+" ");
						record_id=mData.get(position).get("record_id").toString();
						stake_owner_id=mData.get(position).get("stakeOwnerId").toString();
						intent.putExtra("token", token);
						intent.putExtra("record_id", record_id);
						intent.putExtra("stake_owner_id",stake_owner_id);
						//System.out.println(record_id+"*************");
						intent.setClass(UserChargeRecordActivity.this,UserComplaintActivity.class);
						startActivity(intent);
					}
				});
				// 给Button添加单击事件 添加Button之后ListView将失去焦点 需要的直接把Button的焦点去掉
               return convertView;
			}
		
		}



		public final class ViewHolder {
			public TextView time;
			public TextView name;
			public TextView address;
			public TextView fee;
			public Button complainBtn;
		}
 
}
