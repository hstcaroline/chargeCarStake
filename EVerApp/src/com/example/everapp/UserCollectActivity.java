package com.example.everapp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.prefs.Preferences;

import javax.security.auth.PrivateCredentialPermission;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.model.LatLng;
import com.baidu.navisdk.adapter.BNRoutePlanNode;
import com.baidu.navisdk.adapter.BaiduNaviManager;
import com.baidu.navisdk.adapter.BNRoutePlanNode.CoordinateType;
import com.baidu.navisdk.adapter.BaiduNaviManager.RoutePlanListener;
import com.baidu.pano.platform.http.r;
import com.example.everapp.NearbyActivity.DemoRoutePlanListener;
import com.example.util.MyHttpRequest;

import android.R.string;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;
import entity.ElectricDrive;

public class UserCollectActivity extends Activity {
	public static final String ROUTE_PLAN_NODE = "routePlanNode";
	private LocationClient mLocationClient;
	private BDLocationListener myListener;
	private LatLng lastLoc;
	private boolean firstLocation = true;
	private ListView collectList;
	private TextView titleText;
	private ImageButton collect_back_btn;// 从收藏界面返回到个人中心界面
	private List<ElectricDrive> mData;
	private String[] mExample = new String[] { "特斯拉1号充电桩", "15-11-06", "上海市闵行区东川路800号" };// listview_item

	private SharedPreferences preferences;
	private static final int LOAD_COLLECT_DATA_SUCCESS = 0;
	private static final int LOAD_COLLECT_DATA_FAIL = 1;
	private Handler handler;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.user_collect);
		collectList = (ListView) findViewById(R.id.collect_list);
		collect_back_btn = (ImageButton) findViewById(R.id.return_btn);
		titleText = (TextView) findViewById(R.id.title);
		
		titleText.setText("收藏");
		preferences = getSharedPreferences("ever", MODE_PRIVATE);
		System.out.println("init***********");
		initLocation();
		System.out.println("location***********");
		handler = new Handler() {
			public void handleMessage(Message msg) {
				UserCollectActivity.this.handleMessage(msg);
			};
		};

		collect_back_btn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				UserCollectActivity.this.finish();
			}
		});
		// set listview
		new Thread(new Runnable() {
			public void run() {
				getData();
			}
		}).start();

		

	}

	// 获取动态数组数据 可以由其他地方传来
	private void getData() {
		Bundle b = new Bundle();
		try {
			mData = MyHttpRequest.getAllCollection(preferences.getString("token", ""));
			b.putInt("type", LOAD_COLLECT_DATA_SUCCESS);
			Message msg = handler.obtainMessage();
			msg.setData(b);
			handler.sendMessage(msg);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			b.putInt("type", LOAD_COLLECT_DATA_FAIL);
			b.putString("errmsg", e.getMessage());
			Message msg = handler.obtainMessage();
			msg.setData(b);
			handler.sendMessage(msg);
		}
	}

	private void handleMessage(Message msg) {
		Bundle b = msg.getData();
		int type = b.getInt("type");
		switch (type) {
		case LOAD_COLLECT_DATA_SUCCESS:
			MyAdapter adapter = new MyAdapter(this);
			collectList.setAdapter(adapter);
			if(mData.size() == 0)
				Toast.makeText(this, "无收藏信息", Toast.LENGTH_SHORT).show();
			break;
		case LOAD_COLLECT_DATA_FAIL:
			Toast.makeText(this, "获取收藏信息失败", Toast.LENGTH_SHORT).show();
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

				convertView = mInflater.inflate(R.layout.collect_item, null);
				holder.name = (TextView) convertView.findViewById(R.id.charge_name);
				holder.address = (TextView) convertView.findViewById(R.id.charge_address);
				holder.goBtn = (DrawableCenterTextView) convertView.findViewById(R.id.go_to_charge_btn);
				holder.orderBtn = (DrawableCenterTextView) convertView.findViewById(R.id.order_charge_btn);
				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}

			holder.name.setText((String) mData.get(position).getDescription());
			holder.address.setText((String) mData.get(position).getAddress());
			holder.goBtn.setTag(position);
			holder.orderBtn.setTag(position);
			// 给Button添加单击事件 添加Button之后ListView将失去焦点 需要的直接把Button的焦点去掉
			holder.goBtn.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
					if (BaiduNaviManager.isNaviInited()) {
						BNRoutePlanNode sNode = new BNRoutePlanNode(lastLoc.longitude, lastLoc.latitude, "我的位置",
								null, CoordinateType.GCJ02);
						BNRoutePlanNode eNode = new BNRoutePlanNode(mData.get(position).getLongtitude(), mData.get(position).getLatitude(), "目的地", null,
								CoordinateType.GCJ02);

						routeplanToNavi(sNode, eNode);
					}
				}
			});
			
			holder.orderBtn.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
					Intent i = new Intent(UserCollectActivity.this, OrderActivity.class);
					i.putExtra("stakeId", mData.get(position).getId());
					startActivity(i);
				}
			});

			return convertView;
		}
	}

	// the button clickListener
	private void initLocation() {
		// 设置定位监听
		mLocationClient = new LocationClient(getApplicationContext());
		myListener = new BDLocationListener() {

			@Override
			public void onReceiveLocation(BDLocation location) {
				// TODO Auto-generated method stub
				baiduMapLocationListener(location);
			}
		};
		mLocationClient.registerLocationListener(myListener);
		LocationClientOption option = new LocationClientOption();
		option.setCoorType("bd09ll");
		option.setScanSpan(1000);
		option.setOpenGps(true);
		mLocationClient.setLocOption(option);
		mLocationClient.start();
	}

	// 定位监听函数
	private void baiduMapLocationListener(BDLocation location) {
		lastLoc = new LatLng(location.getLatitude(), location.getLongitude());
		
		if (firstLocation) {
			firstLocation = false;
			mLocationClient.stop();
		}
	}

	public final class ViewHolder {
		public TextView name;
		public TextView address;

		public DrawableCenterTextView goBtn;
		public DrawableCenterTextView orderBtn;
	}
	
	private void routeplanToNavi(BNRoutePlanNode sNode, BNRoutePlanNode eNode) {
		if (sNode != null && eNode != null) {
			List<BNRoutePlanNode> list = new ArrayList<BNRoutePlanNode>();
			list.add(sNode);
			list.add(eNode);
			BaiduNaviManager.getInstance().launchNavigator(this, list, 1, true,
					new DemoRoutePlanListener(sNode));
		}

	}
	public class DemoRoutePlanListener implements RoutePlanListener {

		private BNRoutePlanNode mBNRoutePlanNode = null;

		public DemoRoutePlanListener(BNRoutePlanNode node) {
			mBNRoutePlanNode = node;
		}

		@Override
		public void onJumpToNavigator() {
			Intent intent = new Intent(UserCollectActivity.this, NaviActivity.class);
			Bundle bundle = new Bundle();
			bundle.putSerializable(ROUTE_PLAN_NODE, (BNRoutePlanNode) mBNRoutePlanNode);
			intent.putExtras(bundle);
			startActivity(intent);
		}

		@Override
		public void onRoutePlanFailed() {
			// TODO Auto-generated method stub

		}
	}
}
