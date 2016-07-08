package com.example.everapp;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

import com.baidu.pano.platform.http.t;
import com.example.everapp.UserChargeRecordActivity.ViewHolder;
import com.example.util.HttpUtil;

import android.R.bool;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.Settings.System;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AbsListView;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class ChargestakeShareActivity extends Activity {
	private ExpandableListView stake_list;
	private ImageButton stake_share_btn;
	private String token;
	private TextView titleText;
	private SharedPreferences preferences;
	private String imag_url;
	private List<Map<String, Object>> mList = new ArrayList<Map<String, Object>>();
	private Handler handler;
	private static final int GET_DATA_SUCCESS = 0;
	private static final int GET_DATA_FAIL = -1;
	private static final int GET_DATA_EMPTY =1;
	private static final int POST_SHARE_SUCCESS =2;
	
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.charge_stak_share);
		titleText = (TextView) findViewById(R.id.title);
		stake_list = (ExpandableListView) findViewById(R.id.share_expandlist);
		stake_share_btn=(ImageButton)findViewById(R.id.return_btn);
      //set reback action
		stake_share_btn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
		titleText.setText("车桩分享");
		//token=getIntent().getStringExtra("token");
		//Log.e("token", token);
		preferences = this.getSharedPreferences("ever", this.MODE_PRIVATE);
		Log.e("test",preferences.getString("token", null));
		token=preferences.getString("token", null);
		 handler = new Handler(){
				public void handleMessage(Message msg) {
					
					try {
						ChargestakeShareActivity.this.handleMessage(msg);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				};
			};
              new Thread(new Runnable(){
				
				public void run() {
		    Get_share_list(token);
			
		}}).start();
		
	}
	private void handleMessage(Message msg) throws InterruptedException{
		Bundle b = msg.getData();
		int res = b.getInt("result");
		switch (res) {
		case GET_DATA_SUCCESS:
			Toast.makeText(ChargestakeShareActivity.this, "获取车桩数据", Toast.LENGTH_SHORT).show();		
			ExpandableListAdapter adapter = new ExpandableListAdapter(this);//初始化expandlistview
		    stake_list.setAdapter(adapter);
			break;
		case GET_DATA_FAIL:
			Toast.makeText(ChargestakeShareActivity.this, "网络异常", Toast.LENGTH_SHORT).show();
			break;
		case GET_DATA_EMPTY:
			Toast.makeText(ChargestakeShareActivity.this, "车桩数据为空", Toast.LENGTH_SHORT).show();			
			break;
		case POST_SHARE_SUCCESS:
			Toast.makeText(ChargestakeShareActivity.this, "车桩分享成功！", Toast.LENGTH_SHORT).show();
			finish();
			break;
		
		default:
			break;
		}
	}
	class ExpandableListAdapter extends BaseExpandableListAdapter {
		private final String TAG = "ExpAdapter";
		private Context context;
		//String arrGroupelements[] = { "特斯拉1号桩", "剑川路特斯拉2号桩" };

		// String arrChildelements[];
		public ExpandableListAdapter(Context context) {
			this.context = context;

			Log.i(TAG, "Adapter created.");
		}

		@Override
		public Object getChild(int groupPosition, int childPosition) {
			return null;
		}

		@Override
		public long getChildId(int groupPosition, int childPosition) {

			return 0;
		}

		@Override
		public View getChildView(final int groupPosition, final int childPosition,
				boolean isLastChild, View convertView, ViewGroup parent) {
			ViewHolder holder = null;
			if (convertView == null) {
				holder=new ViewHolder();
				LayoutInflater inflater = (LayoutInflater) context
						.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				convertView = inflater.inflate(R.layout.share_stake_item, null);
				holder.codeView=(ImageView) convertView
						.findViewById(R.id.share_stake_codeimg);// 分享车桩二维码图像
				holder.priceText=(TextView)convertView.findViewById(R.id.price_tag);//车桩价格
				holder.startText=(TextView)convertView.findViewById(R.id.start_time_tag);
				holder.endText=(TextView)convertView.findViewById(R.id.end_time_tag);//分享车桩结束时间*/
				holder.share_btn=(Button) convertView.findViewById(R.id.stake_share_btn);// 分享车桩按钮
				convertView.setTag(holder);
			}
			else {
				holder = (ViewHolder) convertView.getTag();
			}
			holder.priceText.setText("计费价格:"+mList.get(groupPosition).get("price").toString()+"元/h");
			holder.startText.setText("开始时间:"+mList.get(groupPosition).get("availableStime").toString());
			holder.endText.setText("结束时间:"+mList.get(groupPosition).get("availableEtime").toString());
			//从网络上下载二维码图像
			try {
				String stake_id=mList.get(groupPosition).get("stakeId").toString();
				String path="img/QRcode/"+stake_id+".jpg";
			    Log.e("path", path);
				Bitmap bitmap=HttpUtil.getBitmap(path);
				if(bitmap!=null){
					holder.codeView.setImageBitmap(bitmap);
					Toast.makeText(ChargestakeShareActivity.this, "图像下载成功！", Toast.LENGTH_SHORT).show();
				}
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				//e.printStackTrace();
					// TODO Auto-generated catch block
					//e1.printStackTrace();		
				Toast.makeText(ChargestakeShareActivity.this, "请等待", Toast.LENGTH_SHORT).show();				
			}

			holder.share_btn.setOnClickListener(new OnClickListener() {// 点击分享按钮事件监听
				@Override
				public void onClick(View v) {
					
					new Thread(new Runnable() {
						
						@Override
						public void run() {
							// TODO Auto-generated method stub
						
							String stake_id=mList.get(groupPosition).get("stakeId").toString();
							Share_a_stake(token, stake_id);
						}
					}).start();		
					
				}
			});
			
			return convertView;
		}

		@Override
		public int getChildrenCount(int groupPosition) {
			return 1;
		}

		@Override
		public Object getGroup(int groupPosition) {
			return null;
		}

		@Override
		public int getGroupCount() {
			return mList.size();
		}

		@Override
		public long getGroupId(int groupPosition) {
			return 0;
		}

		@Override
		public View getGroupView(int groupPosition, boolean isExpanded,
				View convertView, ViewGroup parent) {

			// 定义一个LinearLayout用于存放TextView
			LinearLayout ll = new LinearLayout(ChargestakeShareActivity.this);
			// 设置子控件的显示方式为水平
			ll.setOrientation(0);
			AbsListView.LayoutParams lparParams = new AbsListView.LayoutParams(
					96, 46);
			TextView textView = getTextView();
			textView.setTextSize(20);
			textView.setText(mList.get(groupPosition).get("description").toString());
			ll.addView(textView);
			return ll;
		}

		@Override
		public boolean hasStableIds() {
			return false;
		}

		@Override
		public boolean isChildSelectable(int groupPosition, int childPosition) {
			return true;
		}
		 //定义一个显示文字信息的方法 
        TextView getTextView(){ 
            AbsListView.LayoutParams lp=new AbsListView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,64); 
            TextView textView=new TextView(ChargestakeShareActivity.this); 
            //设置 textView控件的布局 
            textView.setLayoutParams(lp); 
            //设置该textView中的内容相对于textView的位置 
            textView.setGravity(Gravity.CENTER_VERTICAL); 
            //设置txtView的内边距 
            textView.setPadding(36, 0, 0, 0); 
            //设置文本颜色 
            textView.setTextColor(Color.BLACK); 
            return textView;             
               
        } 
        public final class ViewHolder {
			public ImageView codeView;
			public TextView  priceText;
			public TextView  startText;
			public TextView  endText;
			public Button  share_btn;
		}
	}
	protected boolean  Get_share_list(String token) {
		Bundle bundle=new Bundle();
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		Map<String, String> map = new HashMap<String, String>();
		map.put("status","2");
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
			mList=list;
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
	protected boolean Share_a_stake(String token,String stake_id){
		Bundle bundle=new Bundle();
		Map<String, String> map = new HashMap<String, String>();
		map.put("stake_id", stake_id);
		map.put("status","0");
		map.put("type","2");
		try{
			JSONObject jsonObject = new JSONObject(HttpUtil.postRequest(
					"api/user/stakeShare.servlet",map,token));
			String tokens=jsonObject.getString("token");
			if(tokens!=null){
             Log.e("share_ok", tokens);
         	bundle.putInt("result", POST_SHARE_SUCCESS);
			Message msg = handler.obtainMessage();
			msg.setData(bundle);
			handler.sendMessage(msg);
			return true;
			}
			else{
				bundle.putInt("result", GET_DATA_FAIL);
				Message msg = handler.obtainMessage();
				msg.setData(bundle);
				handler.sendMessage(msg);
				return false;
			}
		}
		catch(Exception e){
			//e.printStackTrace();
			bundle.putInt("result", GET_DATA_FAIL);
			Message msg = handler.obtainMessage();
			msg.setData(bundle);
			handler.sendMessage(msg);
			return false;
		}
	}
	
}
