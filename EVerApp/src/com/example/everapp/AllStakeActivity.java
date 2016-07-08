package com.example.everapp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

import com.example.everapp.UserChargeRecordActivity.MyAdapter;
import com.example.everapp.UserChargeRecordActivity.ViewHolder;
import com.example.util.HttpUtil;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class AllStakeActivity extends Activity{
	private TextView title;
	private ImageButton reback_btn;
	private SharedPreferences preferences;
	private String token;
	private ListView stake_list;
	private List<Map<String, Object>> mData;
	private Handler handler;
	private static final int GET_DATA_SUCCESS = 0;
	private static final int GET_DATA_FAIL = -1;
	private static final int GET_DATA_EMPTY =1;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.charge_stake_modify);
		title = (TextView) findViewById(R.id.title);
		title.setText("车桩修改");
		reback_btn = (ImageButton) findViewById(R.id.return_btn);
		stake_list=(ListView)findViewById(R.id.modify_stakelist);
		reback_btn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
		
		preferences = this.getSharedPreferences("ever", this.MODE_PRIVATE);
		token=preferences.getString("token", "");//获取用户的token值
		Log.e("test for token", token);
		 handler = new Handler(){
				public void handleMessage(Message msg) {
					
					AllStakeActivity.this.handleMessage(msg);
				};
			};
			new Thread(new Runnable(){
				
				public void run() {
		 Get_all_Stake(token);
			
		}}).start();	
	}
	private void handleMessage(Message msg){
		Bundle b = msg.getData();
		int res = b.getInt("result");
		switch (res) {
		case GET_DATA_SUCCESS:
			Toast.makeText(AllStakeActivity.this, "获取车桩数据", Toast.LENGTH_SHORT).show();	
			MyAdapter adapter = new MyAdapter(this);
			stake_list.setAdapter(adapter);
			stake_list.setOnItemClickListener(new OnItemClickListener() {
				@Override
				public void onItemClick(AdapterView<?> parent, View view,
						int position, long id) {
					// TODO Auto-generated method stub
					String stake_name=mData.get(position).get("description").toString();
					String stake_id=mData.get(position).get("stakeId").toString();
					Intent intent=new Intent();
					intent.putExtra("description", stake_name);
					intent.putExtra("stake_id", stake_id);
					intent.setClass(AllStakeActivity.this, ChargestakeModifyActivity.class);
					startActivity(intent);
					finish();
				}
			});
			break;
		case GET_DATA_FAIL:
			Toast.makeText(AllStakeActivity.this, "网络异常", Toast.LENGTH_SHORT).show();
			break;
		case GET_DATA_EMPTY:
			Toast.makeText(AllStakeActivity.this, "车桩数据为空", Toast.LENGTH_SHORT).show();			
			break;
		default:
			break;
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

				convertView = mInflater.inflate(R.layout.my_charge_item, null);
				holder.stake_name = (TextView) convertView
						.findViewById(R.id.charge_text_item);
				holder.arrow = (ImageView) convertView
						.findViewById(R.id.charge_image_item);
				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}

			holder.stake_name.setText(mData.get(position).get("description").toString());
			Log.e("description",holder.stake_name.getText().toString());			
           return convertView;
		}
	
	}



	public final class ViewHolder {
		public TextView stake_name;
		public ImageView arrow;
	}
	protected boolean Get_all_Stake(String token){
		Bundle bundle=new Bundle();
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		Map<String, String> map = new HashMap<String, String>();
		map.put("status","0");
		map.put("type","0");
		try{
			JSONObject jsonObject = new JSONObject(HttpUtil.postRequest(
					"api/user/stakeShare.servlet",map,token));
			// 保存服务器发过来的返回值jsonarray解析成map
			String tokens=jsonObject.getString("token");
			if(tokens==null||"".equals(tokens.trim())){
				Log.e("token",tokens);
				String date=jsonObject.getString("data");
				Log.e("test!!", date);
				bundle.putInt("result", GET_DATA_EMPTY);
				Message msg = handler.obtainMessage();
				msg.setData(bundle);
				handler.sendMessage(msg);
			return false;
			}
			else{
				Log.e("token",tokens);
			JSONArray jsonArray=jsonObject.getJSONArray("data");
			for(int i=0;i<jsonArray.length();i++){
				JSONObject jsonObject2=(JSONObject)jsonArray.opt(i);
				Map<String, Object> maps = new HashMap<String, Object>();
				maps.put("price", jsonObject2.getDouble("price"));
				maps.put("description",jsonObject2.getString("description"));
				maps.put("availableEtime", jsonObject2.getString("availableEtime"));
				maps.put("availableStime", jsonObject2.getString("availableStime"));
				maps.put("stakeId", jsonObject2.getString("id"));
				list.add(maps);
			}
			mData=list;
			bundle.putInt("result", GET_DATA_SUCCESS);
			Message msg = handler.obtainMessage();
			msg.setData(bundle);
			handler.sendMessage(msg);
			return true;
			}
			
		} catch (Exception e) {
			//e.printStackTrace();
			bundle.putInt("result", GET_DATA_FAIL);
			Message msg = handler.obtainMessage();
			msg.setData(bundle);
			handler.sendMessage(msg);
			return false;
	}
	}

}
