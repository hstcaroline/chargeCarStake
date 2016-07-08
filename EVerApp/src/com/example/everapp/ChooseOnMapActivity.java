package com.example.everapp;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BaiduMap.OnMapClickListener;
import com.baidu.mapapi.map.BaiduMap.OnMarkerClickListener;
import com.baidu.mapapi.map.BaiduMap.OnMarkerDragListener;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapPoi;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.MarkerOptions.MarkerAnimateType;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.model.LatLng;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class ChooseOnMapActivity extends Activity {

	private MapView mapView;
	private LocationClient mLocationClient;
	private BDLocationListener myListener;
	private LatLng lastLoc;
	private BaiduMap baiduMap;
	private boolean firstLocation = true;
	private Marker marker;

	private Button cancel;
	private Button confirm;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		SDKInitializer.initialize(getApplicationContext());
		setContentView(R.layout.activity_choose_on_map);
		initMap();
		cancel = (Button) findViewById(R.id.cancel);
		confirm = (Button) findViewById(R.id.confirm);
		cancel.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				cancelOnClick();
			}
		});
		confirm.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				confirmOnClick();
			}
		});

		initLocation();
	}

	private void cancelOnClick() {
		setResult(RESULT_CANCELED);
		finish();
	}

	private void confirmOnClick() {
		Intent intent = new Intent();
		intent.putExtra("longtitude", marker.getPosition().longitude);
		intent.putExtra("latitude", marker.getPosition().latitude);
		setResult(RESULT_OK, intent);
		finish();
	}

	// 初始化基本地图
	private void initMap() {
		mapView = (MapView) findViewById(R.id.mapview);
		mapView.showScaleControl(false);
		mapView.showZoomControls(false);
		baiduMap = mapView.getMap();
		baiduMap.setMapType(BaiduMap.MAP_TYPE_NORMAL);
		baiduMap.setOnMapClickListener(new OnMapClickListener() {

			@Override
			public boolean onMapPoiClick(MapPoi arg0) {
				// TODO Auto-generated method stub
				return false;
			}

			@Override
			public void onMapClick(LatLng clicklac) {
				// TODO Auto-generated method stub
				marker.remove();
				lastLoc = clicklac;
				BitmapDescriptor bitmap = BitmapDescriptorFactory.fromResource(R.drawable.icon_mark);
				MarkerOptions options = new MarkerOptions().position(clicklac).icon(bitmap).zIndex(9).draggable(true);

				options.animateType(MarkerAnimateType.grow);

				marker = (Marker) baiduMap.addOverlay(options);
			}
		});

		baiduMap.setMapStatus(MapStatusUpdateFactory.zoomTo(15));
		baiduMap.setMaxAndMinZoomLevel(19, 3);

	}

	// 初始化定位
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
		baiduMap.setMyLocationEnabled(true);
		MyLocationData data = new MyLocationData.Builder().accuracy(location.getRadius()).direction(100)
				.latitude(location.getLatitude()).longitude(location.getLongitude()).build();
		baiduMap.setMyLocationData(data);
		if (firstLocation) {
			firstLocation = false;

			baiduMap.animateMapStatus(MapStatusUpdateFactory.newLatLng(lastLoc));
			BitmapDescriptor bitmap = BitmapDescriptorFactory.fromResource(R.drawable.icon_mark);
			OverlayOptions options = new MarkerOptions().position(lastLoc).icon(bitmap).zIndex(9).draggable(true);
			marker = (Marker) baiduMap.addOverlay(options);
			mLocationClient.stop();

		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.choose_on_map, menu);
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
