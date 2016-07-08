package com.example.everapp;

import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.utils.DistanceUtil;
import com.baidu.navisdk.adapter.BNRoutePlanNode;
import com.baidu.navisdk.adapter.BaiduNaviManager;
import com.baidu.navisdk.adapter.BNRoutePlanNode.CoordinateType;
import com.baidu.navisdk.adapter.BaiduNaviManager.RoutePlanListener;
import com.example.everapp.MapFragment.DemoRoutePlanListener;
import com.example.util.MyHttpRequest;

import android.R.menu;
import android.accounts.Account;
import android.accounts.OnAccountsUpdateListener;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Address;
import android.os.Bundle;
import android.os.DropBoxManager.Entry;
import android.os.Handler;
import android.os.Message;
import android.renderscript.Type;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.PopupMenu.OnMenuItemClickListener;
import entity.ElectricDrive;
import android.widget.TextView;
import android.widget.Toast;

public class NearbyActivity extends Activity {
	public static final String ROUTE_PLAN_NODE = "routePlanNode";
	// 存储的信息
	private List<ElectricDrive> electricDrives;
	private Map<String, Integer> carType;
	private ListView nearbyList;
	private ImageView filter;
	private ImageButton retButton;

	private PopupMenu popup = null;
	private final int NO_CHOICE = 0;
	private final int CARTYPE = 1;
	private final int FREE = 2;
	private final int DISTANCE = 3;
	private int menuchoice = NO_CHOICE;

	private Handler handler;
	private static final int LOAD_ELEC_SUCCESS = 1;
	private static final int LOAD_ELEC_FAIL = 2;
	private static final int LOAD_CARTYPE_SUCCESS = 3;
	private static final int LOAD_CARTYPE_FAIL = 4;

	private Intent i;
	private Double latitude;
	private Double longtitude;
	private LatLng lastLoc;

	private String selectedType = "";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.electric_nearby);
		i = getIntent();
		latitude = i.getDoubleExtra("latitude", 0);
		longtitude = i.getDoubleExtra("longtitude", 0);
		lastLoc = new LatLng(latitude, longtitude);
		handler = new Handler() {
			public void handleMessage(android.os.Message msg) {
				NearbyActivity.this.handleMessage(msg);
			};
		};
		nearbyList = (ListView) findViewById(R.id.nearby_list);
		filter = (ImageView) findViewById(R.id.filter_image);
		retButton = (ImageButton) findViewById(R.id.return_btn);
		new Thread(new Runnable() {
			public void run() {
				getElecData();
				getCarType();
			}
		}).start();

		retButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				finish();
			}
		});

		filter.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				onPopupButtonClick(v);
			}
		});
	}

	private void setDistance() {
		for (int i = 0; i < electricDrives.size(); i++) {
			ElectricDrive e = electricDrives.get(i);
			e.setDistance(Math.round(DistanceUtil.getDistance(new LatLng(latitude, longtitude),
					new LatLng(e.getLatitude(), e.getLongtitude()))));

		}
	}

	private void sortByDistance() {
		int minId = 0;
		long minValue = Long.MAX_VALUE;
		ElectricDrive minE = new ElectricDrive();
		List<ElectricDrive> result = new ArrayList<ElectricDrive>();
		while (electricDrives.size() != 0) {
			for (int i = 0; i < electricDrives.size(); i++) {
				ElectricDrive e = electricDrives.get(i);
				if (e.getDistance() < minValue) {
					minValue = e.getDistance();
					minId = i;
					minE = e;
				}
			}
			result.add(minE);
			electricDrives.remove(minId);
			minValue = Long.MAX_VALUE;
		}
		electricDrives = result;
	}

	private void handleMessage(Message message) {
		Bundle bundle = new Bundle();
		bundle = message.getData();
		int res = bundle.getInt("result");
		switch (res) {
		case LOAD_ELEC_SUCCESS:
			Toast.makeText(this, "充电桩信息加载成功！", Toast.LENGTH_SHORT).show();
			nearbyList.setAdapter(new MyAdapter(this, electricDrives));
			System.out.println("充电桩坐标初始化完成");
			break;

		case LOAD_ELEC_FAIL:
			Toast.makeText(this, "充电桩信息加载失败！" + bundle.getString("errmag"), Toast.LENGTH_SHORT).show();
			break;
		case LOAD_CARTYPE_SUCCESS:
			Toast.makeText(this, "车桩类型加载成功！", Toast.LENGTH_SHORT).show();
			break;
		case LOAD_CARTYPE_FAIL:
			Toast.makeText(this, "车桩类型加载失败！", Toast.LENGTH_SHORT).show();
			break;
		default:
			break;
		}
	}

	private void getCarType() {
		Bundle b = new Bundle();
		try {
			carType = MyHttpRequest.getAllCartype();
			b.putInt("type", LOAD_CARTYPE_SUCCESS);
			Message msg = handler.obtainMessage();
			msg.setData(b);
			handler.sendMessage(msg);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			b.putInt("type", LOAD_CARTYPE_FAIL);
			b.putString("errmsg", e.getMessage());
			Message msg = handler.obtainMessage();
			msg.setData(b);
			handler.sendMessage(msg);
		}
	}

	private void getElecData() {
		Bundle b = new Bundle();
		try {
			electricDrives = MyHttpRequest.getElectricDrives();
			b.putInt("result", LOAD_ELEC_SUCCESS);
			Message message = handler.obtainMessage();
			message.setData(b);
			handler.sendMessage(message);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			b.putInt("result", LOAD_ELEC_FAIL);
			b.putString("errmsg", e.getMessage());
			Message message = handler.obtainMessage();
			message.setData(b);
			handler.sendMessage(message);
		}
	}

	private void singleChoiceAlert() {
		List<String> carTypeList = new ArrayList<String>();
		for (java.util.Map.Entry<String, Integer> e : carType.entrySet()) {
			carTypeList.add(e.getKey());
		}
		final String arr[] = (String[]) carTypeList.toArray(new String[carTypeList.size()]);
		selectedType = arr[0];
		AlertDialog.Builder builder = new AlertDialog.Builder(this).setTitle("选择车型").setIcon(R.drawable.applogo)
				.setSingleChoiceItems(arr, 0, new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface arg0, int arg1) {
						// TODO Auto-generated method stub
						selectedType = arr[arg1];
					}
				}).setPositiveButton("确定", new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface arg0, int arg1) {
						// TODO Auto-generated method stub
						
						menuchoice = CARTYPE;
						List<ElectricDrive> list = new ArrayList<ElectricDrive>();
						for (ElectricDrive e : electricDrives) {
							if (e.getType() == carType.get(selectedType))
								list.add(e);
						}
						MyAdapter adapter = new MyAdapter(NearbyActivity.this, list);
						nearbyList.setAdapter(adapter);
					}
				}).setNegativeButton("取消", new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface arg0, int arg1) {
						// TODO Auto-generated method stub
						selectedType = arr[0];
					}
				});
		builder.create().show();
	}

	private void onPopupButtonClick(View v) {
		// Context wrapper = new ContextThemeWrapper(this, R.style.popup_style);
		popup = new PopupMenu(this, v);
		popup.inflate(R.menu.nearby_filter_menu);

		if (menuchoice == CARTYPE) {
			popup.getMenu().findItem(R.id.menu_cartype).setChecked(true);
		} else if (menuchoice == FREE)
			popup.getMenu().findItem(R.id.menu_free).setChecked(true);
		else if (menuchoice == DISTANCE)
			popup.getMenu().findItem(R.id.menu_distance).setChecked(true);
		popup.setOnMenuItemClickListener(new OnMenuItemClickListener() {

			@Override
			public boolean onMenuItemClick(MenuItem item) {
				// TODO Auto-generated method stub
				switch (item.getItemId()) {
				case R.id.menu_free:
					if (menuchoice == FREE) {
						MyAdapter adapter = new MyAdapter(NearbyActivity.this, electricDrives);
						nearbyList.setAdapter(adapter);
						menuchoice = NO_CHOICE;
					} else {
						List<ElectricDrive> list = new ArrayList<ElectricDrive>();

						for (ElectricDrive e : electricDrives) {
							if (e.getStatus() == 2) {
								list.add(e);
							}
						}
						MyAdapter adapter = new MyAdapter(NearbyActivity.this, list);
						nearbyList.setAdapter(adapter);
						menuchoice = FREE;

					}
					break;

				case R.id.menu_cartype:
					singleChoiceAlert();
					break;
				case R.id.menu_distance:
					if (menuchoice == DISTANCE) {
						menuchoice = NO_CHOICE;
					} else {
						setDistance();
						sortByDistance();
						MyAdapter adapter = new MyAdapter(NearbyActivity.this, electricDrives);
						nearbyList.setAdapter(adapter);
						menuchoice = DISTANCE;
					}
					break;
				}
				return true;
			}
		});
		popup.show();

	}

	private class ViewHolder {
		TextView name;
		TextView distance;
		TextView address;
		DrawableCenterTextView naviButton;
		DrawableCenterTextView orderButton;
	}

	// 适配器
	private class MyAdapter extends BaseAdapter {

		private LayoutInflater mInflater;
		private List<ElectricDrive> list;

		public MyAdapter(Context context, List<ElectricDrive> list) {
			// TODO Auto-generated constructor stub
			this.mInflater = LayoutInflater.from(context);
			this.list = list;
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return list.size();
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
			ViewHolder holder;
			if (convertView == null) {
				convertView = mInflater.inflate(R.layout.electric_nearby_item, null);
				holder = new ViewHolder();
				holder.name = (TextView) convertView.findViewById(R.id.electric_name);
				holder.distance = (TextView) convertView.findViewById(R.id.distance);
				holder.address = (TextView) convertView.findViewById(R.id.electric_address);
				holder.naviButton = (DrawableCenterTextView) convertView.findViewById(R.id.navi_btn);
				holder.orderButton = (DrawableCenterTextView) convertView.findViewById(R.id.order_btn);
				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}
			// 设置onclicklistener等
			holder.name.setText(list.get(position).getDescription().toString());
			holder.distance
					.setText(Math
							.round(DistanceUtil.getDistance(lastLoc,
									new LatLng(list.get(position).getLatitude(), list.get(position).getLongtitude())))
					+ "米");
			holder.address.setText(list.get(position).getAddress());
			holder.naviButton.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
					if (BaiduNaviManager.isNaviInited()) {
						BNRoutePlanNode sNode = new BNRoutePlanNode(lastLoc.longitude, lastLoc.latitude, "我的位置", null,
								CoordinateType.GCJ02);
						BNRoutePlanNode eNode = new BNRoutePlanNode(list.get(position).getLongtitude(),
								list.get(position).getLatitude(), "目的地", null, CoordinateType.GCJ02);

						routeplanToNavi(sNode, eNode);
					}
				}
			});
			holder.orderButton.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
					Intent intent = new Intent(NearbyActivity.this, OrderActivity.class);
					intent.putExtra("stakeId", list.get(position).getId());
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
			BaiduNaviManager.getInstance().launchNavigator(this, list, 1, true, new DemoRoutePlanListener(sNode));
		}

	}

	public class DemoRoutePlanListener implements RoutePlanListener {

		private BNRoutePlanNode mBNRoutePlanNode = null;

		public DemoRoutePlanListener(BNRoutePlanNode node) {
			mBNRoutePlanNode = node;
		}

		@Override
		public void onJumpToNavigator() {
			Intent intent = new Intent(NearbyActivity.this, NaviActivity.class);
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
		getMenuInflater().inflate(R.menu.nearby, menu);
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
