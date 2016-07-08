package com.example.everapp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

import com.example.util.HttpUtil;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.os.Bundle;
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

public class UserOrderActivity extends Activity{
	private ListView user_order_list;
	private ImageButton reback_btn;
	private SharedPreferences preferences;
	private String token;
	private List<Map<String, Object>> mData;
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.user_order);
		user_order_list = (ListView) findViewById(R.id.user_order_list);
		reback_btn = (ImageButton) findViewById(R.id.return_btn);
		TextView title = (TextView) findViewById(R.id.title);
		title.setText("我的预约");
		reback_btn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				UserOrderActivity.this.finish();
			}
		});
		// set listview
		preferences = this.getSharedPreferences("ever", this.MODE_PRIVATE);
		token=preferences.getString("token", "");
		if(get_Order(token)){
		MyAdapter adapter = new MyAdapter(this);
		user_order_list.setAdapter(adapter);
		}
		
	}
	
	// 获取动态数组数据 可以由其他地方传来
			protected boolean get_Order(String token){
				List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
				try {
					JSONObject jsonObject = new JSONObject(HttpUtil.getRequest(
							"api/user/stakeOrder.servlet",token));
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
						map.put("startTime",jsonObject2.getString("startTime"));
						map.put("endTime", jsonObject2.getString("endTime"));
						map.put("stake_description",jsonObject2.getString("stake_description"));
						map.put("stake_address",jsonObject2.getString("stake_address"));
						map.put("type", jsonObject2.getInt("type"));
						map.put("stake_id",jsonObject2.getString("id"));
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

				public View getView(int position, View convertView, ViewGroup parent) {
					ViewHolder holder = null;
					if (convertView == null) {

						holder = new ViewHolder();

						// 可以理解为从vlist获取view 之后把view返回给ListView

						convertView = mInflater.inflate(R.layout.user_order_item, null);
						holder.name = (TextView) convertView
								.findViewById(R.id.stake_name);
						holder.time = (TextView) convertView
								.findViewById(R.id.order_use_time);
						holder.address = (TextView) convertView
								.findViewById(R.id.order_address);
						holder.status = (TextView) convertView
								.findViewById(R.id.order_status);
						holder.leftTime = (TextView) convertView
								.findViewById(R.id.order_left_time);
						holder.cancelBtn = (Button) convertView
								.findViewById(R.id.cancel_btn);
						convertView.setTag(holder);
					} else {
						holder = (ViewHolder) convertView.getTag();
					}

					holder.name.setText((String) mData.get(position).get("stake_description"));
					holder.time.setText((String) mData.get(position).get("startTime"));
					holder.address.setText((String) mData.get(position).get("stake_address"));
					holder.status.setText("已预约");
					if(mData.get(position).get("type").toString().equals("0")){
						holder.leftTime.setText("正在进行");
						holder.leftTime.setTextColor(getResources().getColor(R.color.blue));
					}
					if(mData.get(position).get("type").toString().equals("1")){
						holder.leftTime.setText("已完成");
						holder.leftTime.setTextColor(getResources().getColor(R.color.darkgray));
					}
					if(mData.get(position).get("type").toString().equals("2")){
						holder.leftTime.setText("已过期");
						holder.leftTime.setTextColor(getResources().getColor(R.color.DeepPink));
					}
					
					holder.cancelBtn.setTag(position);
					// 给Button添加单击事件 添加Button之后ListView将失去焦点 需要的直接把Button的焦点去掉

					return convertView;
				}
			}



			public final class ViewHolder {
				public TextView name;
				public TextView time;
				public TextView address;
				public TextView status;
				public TextView leftTime;
				public Button cancelBtn;
			}
}
