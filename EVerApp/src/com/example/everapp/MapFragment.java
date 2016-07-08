package com.example.everapp;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.zip.Inflater;

import org.apache.http.params.HttpAbstractParamBean;
import org.json.JSONException;
import org.json.JSONObject;

import com.baidu.a.a.a.b;
import com.baidu.a.a.a.c;
import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.location.b.m;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BaiduMap.OnMapClickListener;
import com.baidu.mapapi.map.BaiduMap.OnMarkerClickListener;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapPoi;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.map.Text;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.utils.DistanceUtil;
import com.baidu.navisdk.adapter.BNOuterTTSPlayerCallback;
import com.baidu.navisdk.adapter.BNRoutePlanNode;
import com.baidu.navisdk.adapter.BNRoutePlanNode.CoordinateType;
import com.baidu.navisdk.adapter.BaiduNaviManager;
import com.baidu.navisdk.adapter.BaiduNaviManager.NaviInitListener;
import com.baidu.navisdk.adapter.BaiduNaviManager.RoutePlanListener;
import com.baidu.navisdk.model.params.MsgDefine;
import com.baidu.navisdk.ui.routeguide.fsm.RouteGuideFSM.IFSMDestStateListener;
import com.example.util.HttpUtil;
import com.example.util.MyHttpRequest;

import android.support.v4.app.FragmentTabHost;
import android.app.ActionBar.LayoutParams;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.media.Image;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.ArrayRes;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.PopupWindow.OnDismissListener;
import android.widget.TextView;
import android.widget.Toast;
import entity.ElectricDrive;

/*
 * 地图界面，包括定位、marker、导航。
 * 可先初始化基本地图并绑定markerListener，
 * 初始化定位、导航。
 * 之后在第一次定位时请求数据，
 * 成功后在地图上加上marker。
 */
public class MapFragment extends Fragment {

	public static final String ROUTE_PLAN_NODE = "routePlanNode";

	private SharedPreferences preferences;
	private List<ElectricDrive> electricDrives = new ArrayList<ElectricDrive>();
	private List<Marker> markers = new ArrayList<Marker>();
	private LinearLayout mElectricInfo;
	private FragmentTabHost tabhost;
	private TextView mElectricName;
	private TextView mElectricAddress;
	private TextView mNaviButton;
	private TextView mPreButton;
	private TextView mPriceText;
	private ImageView mCollect;
	private DrawableCenterTextView mDistanceText;
	private PopupWindow popupWindow;

	private View rootView;
	private MapView mMapView;
	private BaiduMap baiduMap;
	private ImageView locIcon;
	private TextView searchText;
	private ImageButton recommendButton;
	private LocationClient mLocationClient;
	private BDLocationListener myListener;
	private ImageView detailImage;

	private boolean firstLocation = true;
	private LatLng lastLoc;

	private String mSDCardPath = null;
	private static final String APP_FOLDER_NAME = "BNSDKDemo";

	private Handler handler;
	private static final int LOAD_ELEC_SUCCESS = 1;
	private static final int LOAD_ELEC_FAIL = 2;
	private static final int COLLECT_ELEC_SUCCESS = 3;
	private static final int COLLECT_ELEC_FAIL = 4;
	private static final int CANCEL_COLLECT_SUCCESS = 5;
	private static final int CANCEL_COLLECT_FAIL = 6;

	private String token = "";
	
	private List<ElectricDrive> collection = new ArrayList<ElectricDrive>();

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		SDKInitializer.initialize(getActivity().getApplicationContext());
		if (rootView == null) {
			rootView = inflater.inflate(R.layout.map_fragment, null);
			locIcon = (ImageView) rootView.findViewById(R.id.location_icon);
			searchText = (TextView) rootView.findViewById(R.id.search_text);
			tabhost = (FragmentTabHost) getActivity().findViewById(android.R.id.tabhost);
			// mElectricInfo = (LinearLayout)
			// getActivity().findViewById(R.id.electric_info);
			preferences = getActivity().getSharedPreferences("ever", getActivity().MODE_PRIVATE);
			recommendButton = (ImageButton) rootView.findViewById(R.id.recommend_btn);
			initMap();
			System.out.println("地图初始化完成");
			initLocation();
			System.out.println("定位初始化完成");
			// 导航引擎初始化
			if (initDirs())
				initNavi();
			System.out.println("导航初始化完成");

			handler = new Handler() {
				@Override
				public void handleMessage(Message msg) {
					// TODO Auto-generated method stub
					MapFragment.this.handleMessage(msg);
				}
			};
			new Thread(new Runnable() {
				public void run() {
					getElectricData();
				}
			}).start();

			//

			searchText.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
					Intent intent = new Intent(getActivity(), SearchActivity.class);
					intent.putExtra("longitude", lastLoc.longitude);
					intent.putExtra("latitude", lastLoc.latitude);
					startActivity(intent);

				}
			});
			recommendButton.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
					Intent intent = new Intent(getActivity(), NearbyActivity.class);
					intent.putExtra("latitude", lastLoc.latitude);
					intent.putExtra("longtitude", lastLoc.longitude);
					startActivity(intent);
				}
			});

			locIcon.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
					locItemOnclick();
				}
			});
		}
		ViewGroup parent = (ViewGroup) rootView.getParent();
		if (parent != null) {
			parent.removeView(rootView);
		}

		return rootView;

	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onViewCreated(view, savedInstanceState);

	}

	private void getElectricData() {
		Bundle b = new Bundle();
		try {
			electricDrives = MyHttpRequest.getElectricDrives();
			b.putInt("type", LOAD_ELEC_SUCCESS);
			Message message = handler.obtainMessage();
			message.setData(b);
			handler.sendMessage(message);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			b.putInt("type", LOAD_ELEC_FAIL);
			b.putString("errmsg", e.getMessage());
			Message message = handler.obtainMessage();
			message.setData(b);
			handler.sendMessage(message);
		}

	}

	private void handleMessage(Message message) {
		final Bundle bundle = message.getData();
		
		int res = bundle.getInt("type");
		switch (res) {
		case LOAD_ELEC_SUCCESS:
			Toast.makeText(getActivity(), "充电桩信息加载成功！", Toast.LENGTH_SHORT).show();

			setMarkerOnMap();
			System.out.println("充电桩坐标初始化完成");
			break;

		case LOAD_ELEC_FAIL:
			Toast.makeText(getActivity(), "充电桩信息加载失败！" + bundle.getString("errmag"), Toast.LENGTH_SHORT).show();
			break;
			
		case COLLECT_ELEC_SUCCESS:
			ElectricDrive ne = new ElectricDrive();
			ne.setId(bundle.getInt("stakeId"));
			collection.add(ne);
			mCollect.setImageResource(R.drawable.favs_red);
			mCollect.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
					
					new Thread(new Runnable() {
						public void run() {
							cancelCollect(bundle.getInt("stakeId"));
						}
					}).start();
				}
			});
//			popupWindow.update();
			break;
		case COLLECT_ELEC_FAIL:
			Toast.makeText(getActivity(), "充电桩信息加载失败！" + bundle.getString("errmag"), Toast.LENGTH_SHORT).show();
			break;
		case CANCEL_COLLECT_SUCCESS:
			final int sid = bundle.getInt("stakeId");
			for(int i = 0; i < collection.size(); i++){
				if(collection.get(i).getId() == sid){
					collection.remove(i);
					break;
				}
			}
			mCollect.setImageResource(R.drawable.favs);
			mCollect.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
					new Thread(new Runnable() {
						public void run() {
							addCollect(sid);
						}
					}).start();
				}
			});
			break;
		case CANCEL_COLLECT_FAIL:
			Toast.makeText(getActivity(), bundle.getString("errmsg"), Toast.LENGTH_SHORT).show();
			break;
		default:
			break;
		}

	}

	private boolean initDirs() {
		mSDCardPath = getSdcardDir();
		if (mSDCardPath == null) {
			return false;
		}
		File f = new File(mSDCardPath, APP_FOLDER_NAME);
		if (!f.exists()) {
			try {
				f.mkdir();
			} catch (Exception e) {
				e.printStackTrace();
				return false;
			}
		}
		return true;
	}

	String authinfo = null;

	// 初始化导航
	private void initNavi() {
		BaiduNaviManager.getInstance().setNativeLibraryPath(mSDCardPath + "/BaiduNaviSDK_SO");
		BaiduNaviManager.getInstance().init(getActivity(), mSDCardPath, APP_FOLDER_NAME, new NaviInitListener() {
			@Override
			public void onAuthResult(int status, String msg) {
				if (0 == status) {
					// authinfo = "key校验成功!";
				} else {
					authinfo = "key校验失败, " + msg;
				}
				getActivity().runOnUiThread(new Runnable() {

					@Override
					public void run() {
						// Toast.makeText(getActivity(), authinfo,
						// Toast.LENGTH_LONG).show();
					}
				});

			}

			public void initSuccess() {
				// Toast.makeText(getActivity(), "百度导航引擎初始化成功",
				// Toast.LENGTH_SHORT).show();
			}

			public void initStart() {
				// Toast.makeText(getActivity(), "百度导航引擎初始化开始",
				// Toast.LENGTH_SHORT).show();
			}

			public void initFailed() {
				Toast.makeText(getActivity(), "百度导航引擎初始化失败", Toast.LENGTH_SHORT).show();
			}
		}, null /* mTTSCallback */);
	}

	private String getSdcardDir() {
		if (Environment.getExternalStorageState().equalsIgnoreCase(Environment.MEDIA_MOUNTED)) {
			return Environment.getExternalStorageDirectory().toString();
		}
		return null;
	}

	// 初始化基本地图
	private void initMap() {
		mMapView = (MapView) rootView.findViewById(R.id.mapview);
		mMapView.showScaleControl(false);
		mMapView.showZoomControls(false);
		baiduMap = mMapView.getMap();
		baiduMap.setMapType(BaiduMap.MAP_TYPE_NORMAL);
		baiduMap.setOnMarkerClickListener(new OnMarkerClickListener() {

			@Override
			public boolean onMarkerClick(Marker m) {
				// TODO Auto-generated method stub
				return baiduMarkerOnClick(m);
			}
		});

		baiduMap.setMapStatus(MapStatusUpdateFactory.zoomTo(15));
		baiduMap.setMaxAndMinZoomLevel(19, 3);

	}

	// 在地图上加充电桩marker
	private void setMarkerOnMap() {
		for (ElectricDrive e : electricDrives) {
			LatLng point = new LatLng(e.getLatitude(), e.getLongtitude());
			BitmapDescriptor markerBitmap = BitmapDescriptorFactory.fromResource(R.drawable.icon_mark);
			OverlayOptions option = new MarkerOptions().position(point).icon(markerBitmap)
					.title(String.valueOf(e.getId()));
			Marker marker = (Marker) baiduMap.addOverlay(option);
			markers.add(marker);
		}

	}

	// 初始化定位
	private void initLocation() {
		// 设置定位监听
		mLocationClient = new LocationClient(getActivity().getApplicationContext());
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
		baiduMap.setMyLocationEnabled(true);
		MyLocationData data = new MyLocationData.Builder().accuracy(location.getRadius()).direction(100)
				.latitude(location.getLatitude()).longitude(location.getLongitude()).build();
		baiduMap.setMyLocationData(data);
		if (firstLocation) {
			firstLocation = false;
			baiduMap.animateMapStatus(MapStatusUpdateFactory.newLatLng(lastLoc));
		}
	}

	private void locItemOnclick() {
		baiduMap.setMapStatus(MapStatusUpdateFactory.zoomTo(15));
		baiduMap.animateMapStatus(MapStatusUpdateFactory.newLatLng(lastLoc));
	}

	private boolean baiduMarkerOnClick(Marker m) {
		if(preferences.getString("token", "").equals("")){
			token = "";
			collection.clear();
		}
		if(!preferences.getString("token", "").equals("") && collection.size() == 0){
			token = preferences.getString("token", "");
			try {
				collection = MyHttpRequest.getAllCollection(token);
				Toast.makeText(getActivity(), "获取收藏信息成功", Toast.LENGTH_SHORT).show();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();

			}
		}
		for (final ElectricDrive d : electricDrives) {
			if (d.getId() == (Integer.parseInt(m.getTitle()))) {
				initPopupWindow();
				
				if(!token.equals("")){
					
					final int dId = d.getId();
					mCollect.setOnClickListener(new OnClickListener() {
						
						@Override
						public void onClick(View arg0) {
							// TODO Auto-generated method stub
							new Thread(new Runnable() {
								public void run() {
									addCollect(dId);
								}
							}).start();
						}
					});
					for(ElectricDrive e : collection){
						if(e.getId() == dId){
							mCollect.setImageResource(R.drawable.favs_red);
							mCollect.setOnClickListener(new OnClickListener() {
								
								@Override
								public void onClick(View arg0) {
									// TODO Auto-generated method stub
									new Thread(new Runnable() {
										public void run() {
											cancelCollect(dId);
										}
									}).start();
								}
							});
							break;
						}
						
					}
				}else{
				mCollect.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View arg0) {
						// TODO Auto-generated method stub
						Toast.makeText(getActivity(), "请登录", Toast.LENGTH_SHORT).show();
					}
				});
				}
				mElectricName.setText(d.getDescription());
				mElectricAddress.setText(d.getAddress());
				mPriceText.setText(d.getPrice() + "元每小时");
				mDistanceText.setText(
						Math.round(DistanceUtil.getDistance(lastLoc, new LatLng(d.getLatitude(), d.getLongtitude())))
								+ "米");
				// mDistanceText.setText("1");
				detailImage.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View arg0) {
						// TODO Auto-generated method stub
						Intent i = new Intent(getActivity(), ChargestakeDetailActivity.class);
						i.putExtra("stake", d);
						i.putExtra("longitude", lastLoc.longitude);
						i.putExtra("latitude", lastLoc.latitude);
						startActivity(i);
					}
				});
				mPreButton.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View arg0) {
						// TODO Auto-generated method stub
						if (!preferences.getString("token", "").equals("")) {
							
							Intent i = new Intent(getActivity(), OrderActivity.class);
							i.putExtra("stakeId", d.getId());
							startActivity(i);
						} else {
							Toast.makeText(getActivity(), "请登录", Toast.LENGTH_SHORT).show();
						}
					}
				});
				mNaviButton.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View arg0) {
						// TODO Auto-generated method stub
						if (BaiduNaviManager.isNaviInited()) {
							BNRoutePlanNode sNode = new BNRoutePlanNode(lastLoc.longitude, lastLoc.latitude, "我的位置",
									null, CoordinateType.GCJ02);
							BNRoutePlanNode eNode = new BNRoutePlanNode(d.getLongtitude(), d.getLatitude(), "目的地", null,
									CoordinateType.GCJ02);

							routeplanToNavi(sNode, eNode);
						}
					}
				});

				return true;
			}
		}
		return false;
	}

	private void initPopupWindow() {
		LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View popView = inflater.inflate(R.layout.map_popupwindow, null);
		popupWindow = new PopupWindow(popView, LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
		popupWindow.setOutsideTouchable(true);
		popupWindow.setFocusable(true);
		popupWindow.setBackgroundDrawable(new BitmapDrawable());

		popupWindow.setAnimationStyle(R.style.mypopwindow_anim_style);
		popupWindow.showAtLocation(getActivity().findViewById(R.id.main), Gravity.BOTTOM, 0, 0);

		mElectricName = (TextView) popView.findViewById(R.id.electric_name);
		mElectricAddress = (TextView) popView.findViewById(R.id.electric_address);
		mNaviButton = (TextView) popView.findViewById(R.id.navi_button);
		mPreButton = (TextView) popView.findViewById(R.id.pre_button);
		detailImage = (ImageView) popView.findViewById(R.id.detail_image);
		mPriceText = (TextView) popView.findViewById(R.id.price_txt);
		mDistanceText = (DrawableCenterTextView) popView.findViewById(R.id.distance);
		mCollect = (ImageView) popView.findViewById(R.id.collect_icon);
		

		
		

		popupWindow.update();
		popupWindow.setOnDismissListener(new OnDismissListener() {

			@Override
			public void onDismiss() {
			}
		});

	}

	private void routeplanToNavi(BNRoutePlanNode sNode, BNRoutePlanNode eNode) {
		if (sNode != null && eNode != null) {
			List<BNRoutePlanNode> list = new ArrayList<BNRoutePlanNode>();
			list.add(sNode);
			list.add(eNode);
			BaiduNaviManager.getInstance().launchNavigator(getActivity(), list, 1, true,
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
			Intent intent = new Intent(getActivity(), NaviActivity.class);
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
	
	private void cancelCollect(int stakeId){
		Bundle b = new Bundle();
		try {
			if(MyHttpRequest.cancelCollect(stakeId, token)){
				Message msg = handler.obtainMessage();
				b.putInt("type", CANCEL_COLLECT_SUCCESS);
				b.putInt("stakeId", stakeId);
				
				msg.setData(b);
				handler.sendMessage(msg);
			}
			else{
				Message msg = handler.obtainMessage();
				b.putInt("type", CANCEL_COLLECT_FAIL);
				b.putString("errmsg", "i dont fucking know");
				msg.setData(b);
				handler.sendMessage(msg);
			}
		} catch (JSONException | InterruptedException | ExecutionException e) {
			// TODO Auto-generated catch block
			Message msg = handler.obtainMessage();
			b.putInt("type", CANCEL_COLLECT_FAIL);
			b.putString("errmsg", e.getMessage());
			msg.setData(b);
			handler.sendMessage(msg);
		}
	}

	private void addCollect(int stakeId){
		Calendar c = Calendar.getInstance();
		SimpleDateFormat s = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String time = s.format(c.getTime());
		Bundle b = new Bundle();
		try {
			if(MyHttpRequest.collectStake(stakeId, time, token)){
				Message msg = handler.obtainMessage();
				b.putInt("type", COLLECT_ELEC_SUCCESS);
				b.putInt("stakeId", stakeId);
				msg.setData(b);
				handler.sendMessage(msg);
			}
			else{
				Message msg = handler.obtainMessage();
				b.putInt("type", COLLECT_ELEC_FAIL);
				b.putString("errmsg", "i dont fucking know");
				msg.setData(b);
				handler.sendMessage(msg);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			Message msg = handler.obtainMessage();
			b.putInt("type", COLLECT_ELEC_FAIL);
			b.putString("errmsg", e.getMessage());
			msg.setData(b);
			handler.sendMessage(msg);
		}
	}
	private BNOuterTTSPlayerCallback mTTSCallback = new BNOuterTTSPlayerCallback() {

		@Override
		public void stopTTS() {
			// TODO Auto-generated method stub

		}

		@Override
		public void resumeTTS() {
			// TODO Auto-generated method stub

		}

		@Override
		public void releaseTTSPlayer() {
			// TODO Auto-generated method stub

		}

		@Override
		public int playTTSText(String speech, int bPreempt) {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public void phoneHangUp() {
			// TODO Auto-generated method stub

		}

		@Override
		public void phoneCalling() {
			// TODO Auto-generated method stub

		}

		@Override
		public void pauseTTS() {
			// TODO Auto-generated method stub

		}

		@Override
		public void initTTSPlayer() {
			// TODO Auto-generated method stub

		}

		@Override
		public int getTTSState() {
			// TODO Auto-generated method stub
			return 0;
		}
	};

	@Override
	public void onDestroyView() {
		// TODO Auto-generated method stub
		super.onDestroyView();
	}

	@Override
	public void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		mMapView.onPause();
	}

	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		mMapView.onResume();
	}

}
