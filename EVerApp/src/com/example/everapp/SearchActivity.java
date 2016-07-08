package com.example.everapp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.baidu.mapapi.map.Text;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.utils.DistanceUtil;
import com.baidu.navisdk.adapter.BNRoutePlanNode;
import com.baidu.navisdk.adapter.BaiduNaviManager;
import com.baidu.navisdk.adapter.BNRoutePlanNode.CoordinateType;
import com.baidu.navisdk.adapter.BaiduNaviManager.RoutePlanListener;
import com.example.everapp.NearbyActivity.DemoRoutePlanListener;
import com.example.util.MyHttpRequest;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import entity.ElectricDrive;

public class SearchActivity extends Activity {
	public static final String ROUTE_PLAN_NODE = "routePlanNode";
	private List<ElectricDrive> mData;
	private ListView resultList;
	private Button searchButton;
	private EditText searchInput;
	private ImageView searchDelete;
	private ImageButton retButton;
	private Handler handler;
	private static final int LOAD_DATA_SUCCESS = 0;
	private static final int LOAD_DATA_FAIL = 1;
	private Double longitude, latitude;
	private Intent intent;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_search);
		resultList = (ListView) findViewById(R.id.result_list);
		searchButton = (Button) findViewById(R.id.search_btn);
		searchInput = (EditText) findViewById(R.id.search_et_input);
		searchDelete = (ImageView) findViewById(R.id.search_iv_delete);
		retButton = (ImageButton) findViewById(R.id.return_btn);
		intent = getIntent();
		longitude = intent.getDoubleExtra("longitude", 0);
		latitude = intent.getDoubleExtra("latitude", 0);
		handler = new Handler(){
			public void handleMessage(android.os.Message msg) {
				SearchActivity.this.handleMessage(msg);
			};
		};
		new Thread(new Runnable() {
			public void run() {
				getData();
			}
		}).start();
		retButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				finish();
			}
		});
		searchInput.addTextChangedListener(watcher);
		searchDelete.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				searchDelete.setVisibility(View.GONE);
				searchInput.setText("");
			}
		});
		
		

	}

	private void searchButtonOnClick() {
		String searchStr = searchInput.getText().toString();
		List<ElectricDrive> searchResult = new ArrayList<ElectricDrive>();
		if (searchStr.equals("")) {
			Toast.makeText(this, "请输入检索内容！", Toast.LENGTH_SHORT).show();
			return;
		}
		for (ElectricDrive m : mData) {
			if (m.getDescription().contains(searchStr) 
					|| m.getAddress().contains(searchStr)) {
				searchResult.add(m);
			}
		}

		SearchAdapter adapter = new SearchAdapter(this, searchResult);
		resultList.setAdapter(adapter);
	}

	private TextWatcher watcher = new TextWatcher() {

		@Override
		public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
			// TODO Auto-generated method stub
			int n = searchInput.getText().toString().length();
			if (n != 0 && searchDelete.getVisibility() == View.GONE) {
				searchDelete.setVisibility(View.VISIBLE);
			}
			if (n == 0 && searchDelete.getVisibility() != View.GONE) {
				searchDelete.setVisibility(View.GONE);
			}
		}

		@Override
		public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
			// TODO Auto-generated method stub

		}

		@Override
		public void afterTextChanged(Editable arg0) {
			// TODO Auto-generated method stub

		}
	};

	private void handleMessage(Message msg){
		Bundle b = msg.getData();
		int type = b.getInt("type");
		switch (type) {
		case LOAD_DATA_SUCCESS:
			searchButton.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
					searchButtonOnClick();
				}
			});
			break;
		case LOAD_DATA_FAIL:
			Toast.makeText(this, b.getString("errmsg"), Toast.LENGTH_SHORT).show();
			break;
		default:
			break;
		}
	}
	private void getData() {
		Bundle b = new Bundle();
		try {
			mData = MyHttpRequest.getElectricDrives();
			Message msg = handler.obtainMessage();
			b.putInt("type", LOAD_DATA_SUCCESS);
			msg.setData(b);
			handler.sendMessage(msg);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			Message msg = handler.obtainMessage();
			b.putInt("type", LOAD_DATA_FAIL);
			b.putString("errmsg", e.getMessage());
			msg.setData(b);
			handler.sendMessage(msg);
		}
	}

	private class Viewholder {
		TextView name;
		TextView location;
		TextView distance;
		DrawableCenterTextView navi;
		DrawableCenterTextView order;
	}

	private class SearchAdapter extends BaseAdapter {

		private List<ElectricDrive> searchResult;
		private LayoutInflater mInflater;
		public SearchAdapter(Context context, List<ElectricDrive> list) {
			// TODO Auto-generated constructor stub
			searchResult = list;
			mInflater = LayoutInflater.from(context);
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return searchResult.size();
		}

		@Override
		public Object getItem(int arg0) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public long getItemId(int arg0) {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public View getView(final int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			Viewholder holder;
			if (convertView == null) {
				convertView = mInflater.inflate(R.layout.search_item, null);
				holder = new Viewholder();
				holder.name = (TextView) convertView.findViewById(R.id.charge_name);
				holder.location = (TextView) convertView.findViewById(R.id.charge_address);
				holder.distance = (TextView) convertView.findViewById(R.id.charge_distance);
				holder.navi = (DrawableCenterTextView) convertView.findViewById(R.id.navi_btn);
				holder.order = (DrawableCenterTextView) convertView.findViewById(R.id.order_btn);
				convertView.setTag(holder);
			} else {
				holder = (Viewholder) convertView.getTag();
			}

			holder.name.setText(searchResult.get(position).getDescription());
			holder.location.setText(searchResult.get(position).getAddress());
			holder.distance.setText(Math.round(DistanceUtil.getDistance(new LatLng(latitude, longitude), 
					new LatLng(searchResult.get(position).getLatitude(), searchResult.get(position).getLongtitude())))
					+ "米");
			holder.navi.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
					if (BaiduNaviManager.isNaviInited()) {
						BNRoutePlanNode sNode = new BNRoutePlanNode(longitude, latitude, "我的位置",
								null, CoordinateType.GCJ02);
						BNRoutePlanNode eNode = new BNRoutePlanNode(searchResult.get(position).getLongtitude(), searchResult.get(position).getLatitude(), "目的地", null,
								CoordinateType.GCJ02);

						routeplanToNavi(sNode, eNode);
					}
				}
			});
			holder.order.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
					Intent intent = new Intent(SearchActivity.this, OrderActivity.class);
					intent.putExtra("stakeId", searchResult.get(position).getId());
					startActivity(intent);
				}
			});
			return convertView;
		}

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
			Intent intent = new Intent(SearchActivity.this, NaviActivity.class);
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
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.search, menu);
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
