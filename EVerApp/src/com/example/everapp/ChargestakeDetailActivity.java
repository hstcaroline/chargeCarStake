package com.example.everapp;

import java.util.List;
import java.util.concurrent.ExecutionException;

import org.json.JSONException;

import com.baidu.mapapi.map.Text;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.utils.DistanceUtil;
import com.baidu.navisdk.adapter.BNRoutePlanNode;
import com.baidu.navisdk.adapter.BaiduNaviManager;
import com.baidu.navisdk.adapter.BNRoutePlanNode.CoordinateType;
import com.example.qr_codescan.MipcaActivityCapture;
import com.example.util.MyHttpRequest;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTabHost;
import android.support.v4.widget.ListViewAutoScrollHelper;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.TabHost.TabSpec;
import entity.Comment;
import entity.ElectricDrive;

public class ChargestakeDetailActivity extends Activity {

	private TextView titleText;
	private ImageButton retButton;

	private Double longitude;
	private Double latitude;
	private TabHost tabHost;
	private Intent intent;
	private ElectricDrive stake;
	private TextView stakeName, stakeAddress, stakeDistance, useTime, stakeStatus, stakePrice;
	private ListView commentList;
	private List<Comment> comments;
	private Handler handler;
	private static final int LOAD_COMMENT_DATA_SUCCESS = 0;
	private static final int LOAD_COMMENT_DATA_FAIL = 1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.electric_detail);
		titleText = (TextView) findViewById(R.id.title);
		retButton = (ImageButton) findViewById(R.id.return_btn);
		tabHost = (TabHost) findViewById(R.id.tabhost);
		stakeName = (TextView) findViewById(R.id.stake_name);
		stakeAddress = (TextView) findViewById(R.id.stake_address);
		stakeDistance = (TextView) findViewById(R.id.stake_distance);
		useTime = (TextView) findViewById(R.id.usetime_text);
		stakeStatus = (TextView) findViewById(R.id.state_text);
		stakePrice = (TextView) findViewById(R.id.price_text);
		commentList = (ListView) findViewById(R.id.view3);
		titleText.setText("充电桩详情");
		retButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				finish();
			}
		});

		handler = new Handler(){
			public void handleMessage(android.os.Message msg) {
				ChargestakeDetailActivity.this.handleMessage(msg);
			};
		};
		intent = getIntent();
		stake = (ElectricDrive) intent.getSerializableExtra("stake");
		longitude = intent.getDoubleExtra("longitude", 0.0);
		latitude = intent.getDoubleExtra("latitude", 0.0);

		tabHost.setup();
		tabHost.addTab(tabHost.newTabSpec("one").setIndicator("详情").setContent(R.id.view1));
		tabHost.addTab(tabHost.newTabSpec("two").setIndicator("图片").setContent(R.id.view2));
		tabHost.addTab(tabHost.newTabSpec("three").setIndicator("评论").setContent(R.id.view3));

		setDetail();
		new Thread(new Runnable() {
			public void run() {
				getComments();
			}
		}).start();
	}

	private void setDetail() {
		stakeName.setText(stake.getDescription());
		stakeAddress.setText(stake.getAddress());
		stakeDistance.setText(Math.round(DistanceUtil.getDistance(new LatLng(latitude, longitude),
				new LatLng(stake.getLatitude(), stake.getLongtitude()))) + "米");
		useTime.setText("可用时间：" + stake.getAvailableStime() + "——" + stake.getAvailableEtime());
		stakePrice.setText(stake.getPrice() + "元/小时");
		switch (stake.getStatus()) {
		case 0:
			stakeStatus.setText("当前状态：可用");
			break;
		case 1:
			stakeStatus.setText("当前状态：正在使用");
			break;
		case 2:
			stakeStatus.setText("当前状态：未分享");
			break;
		case 3:
			stakeStatus.setText("当前状态：异常");
			break;
		case 4:
			stakeStatus.setText("当前状态：未审核");
			break;
		case 5:
			stakeStatus.setText("当前状态：审核未通过");
			break;

		default:
			break;
		}
	}
	
	private void handleMessage(Message msg){
		Bundle b = msg.getData();
		int type = b.getInt("type");
		switch (type) {
		case LOAD_COMMENT_DATA_SUCCESS:
			MyAdapter adapter = new MyAdapter(this);
			commentList.setAdapter(adapter);
			break;
		case LOAD_COMMENT_DATA_FAIL:
			Toast.makeText(this, "无评价", Toast.LENGTH_SHORT).show();
			break;
		default:
			break;
		}
	}
	private void getComments(){
		Bundle b = new Bundle();
		try {
			comments = MyHttpRequest.getComment(stake.getId());
			b.putInt("type", LOAD_COMMENT_DATA_SUCCESS);
			Message msg = handler.obtainMessage();
			msg.setData(b);
			handler.sendMessage(msg);
		} catch (JSONException | InterruptedException | ExecutionException e) {
			// TODO Auto-generated catch block
			b.putInt("type", LOAD_COMMENT_DATA_FAIL);
			Message msg = handler.obtainMessage();
			msg.setData(b);
			handler.sendMessage(msg);
		}
		
	}

	// 适配器
	private class MyAdapter extends BaseAdapter {

		private LayoutInflater mInflater;

		public MyAdapter(Context context) {
			// TODO Auto-generated constructor stub
			this.mInflater = LayoutInflater.from(context);
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return comments.size();
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
				convertView = mInflater.inflate(R.layout.comment_item, null);
				holder = new ViewHolder();
				holder.name = (TextView) convertView.findViewById(R.id.user_name);
				holder.content = (TextView) convertView.findViewById(R.id.content);
				holder.grade = (RatingBar) convertView.findViewById(R.id.ratingBar);
				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}
			// 设置onclicklistener等
			holder.name.setText(comments.get(position).getUserName());
			holder.content.setText(comments.get(position).getContent());
			holder.grade.setRating(comments.get(position).getGrade());
			return convertView;
		}

	}

	private class ViewHolder {
		TextView name;
		TextView content;
		RatingBar grade;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.chargestake_detail, menu);
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
