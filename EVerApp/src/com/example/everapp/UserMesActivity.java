package com.example.everapp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.example.everapp.MessageService.MyBinder;
import com.example.util.MyHttpRequest;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;
import entity.ArgueMessageNotification;
import entity.ChargeMessageNotification;
import entity.ElectricDrive;
import entity.MessageNotification;
import entity.NormalMessageNotification;

public class UserMesActivity extends Activity{
	private ListView message_list;
	private ImageButton mes_reback_btn;
	private TextView titleText;
	
	private Intent intent;
	private MessageNotification message;
	List<Map<String, Object>> mList = new ArrayList<Map<String, Object>>();
	
	private final int ITEM_NUM = 3;
	
	private static final int CHARGE_MESSAGE = 0;
	private static final int ARGUE_MESSAGE = 1;
	private static final int NORMAL_MESSAGE = 2;
	
	private SharedPreferences preferences;
	private String token;
	private MyBinder binder;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.user_message);
		preferences = getSharedPreferences("ever", MODE_PRIVATE);
		token = preferences.getString("token", "");
		
		intent = getIntent();
		message = (MessageNotification) intent.getSerializableExtra("message");
		binder = (MyBinder) intent.getSerializableExtra("binder");
		message_list=(ListView)findViewById(R.id.user_message_list);
		mes_reback_btn=(ImageButton)findViewById(R.id.return_btn);
		mes_reback_btn.setOnClickListener(new OnClickListener() {
		
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				binder.putMessage(message);
				Intent i = new Intent();
				i.putExtra("message", message);
				setResult(RESULT_OK, i);
				
				UserMesActivity.this.finish();
			}
		});
		//put the list_message items
		setlist();
		message_list.setAdapter(new MyAdapter(this));
		titleText = (TextView) findViewById(R.id.title);
		titleText.setText("消息通知");
		
	     
	}
    protected void setlist() {
    	String[] COUNTRIES=new String[]{"系统消息","投诉处理","车桩使用消息"};
		
		Map<String, Object> mMap = null;
		mMap = new HashMap<String, Object>();
		if(message.getNormalSize() != 0){
			mMap.put("logo", R.drawable.center_message2);
			mMap.put("have", 1);
		}
		else{
			mMap.put("logo", R.drawable.center_message);
			mMap.put("have", 0);
		}
		mMap.put("title", COUNTRIES[0]);
		mMap.put("type", 2);
		mList.add(mMap);

		mMap = new HashMap<String, Object>();
		if(message.getArgueSize() != 0){
			mMap.put("logo", R.drawable.icon_tousu2);
			mMap.put("have", 1);
		}
		else{
			mMap.put("logo", R.drawable.icon_tousu);
			mMap.put("have", 0);
		}
		mMap.put("title", COUNTRIES[1]);
		mMap.put("type", 1);
		mList.add(mMap);

		mMap = new HashMap<String, Object>();
		if(message.getChargeSize() != 0){
			mMap.put("logo", R.drawable.icon_chargemessage2);
			mMap.put("have", 1);
		}
		else{
			mMap.put("logo", R.drawable.icon_chargemessage);
			mMap.put("have", 0);
		}
		mMap.put("title", COUNTRIES[2]);
		mMap.put("type", 0);
		mList.add(mMap);

		
		
	}
    
    private class MyAdapter extends BaseAdapter{
    	private LayoutInflater mInflater;
    	public MyAdapter(Context context) {
			// TODO Auto-generated constructor stub
			this.mInflater = LayoutInflater.from(context);
			
		}
		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return ITEM_NUM;
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
		public View getView(final int postion, View convertView, ViewGroup arg2) {
			// TODO Auto-generated method stub
			final ViewHolder holder;
			if(convertView == null){
				convertView = mInflater.inflate(R.layout.user_message_item, null);
				holder = new ViewHolder();
				holder.item_image = (ImageView) convertView.findViewById(R.id.message_image_logo);
				holder.item_name = (TextView) convertView.findViewById(R.id.message_text_item);
				convertView.setTag(holder);
			}
			else{
				holder = (ViewHolder) convertView.getTag();
			}
			holder.item_image.setImageResource((int) mList.get(postion).get("logo"));
			holder.item_name.setText((String)mList.get(postion).get("title"));
			if((int)mList.get(postion).get("have") == 1){
				holder.item_name.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View arg0) {
						// TODO Auto-generated method stub
						Intent i = new Intent(UserMesActivity.this, MesNotifActivity.class);
						int type = (int) mList.get(postion).get("type");
						switch (type) {
						case 0:
							new Thread(new Runnable() {
								public void run() {
									try {
										MyHttpRequest.dealMessage(message.getChargeMessage(), token);
									} catch (Exception e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}
								}
							}).start();
							holder.item_image.setImageResource(R.drawable.icon_chargemessage);
							ChargeMessageNotification cmn = new ChargeMessageNotification();
							cmn.setChargeMessage(message.getChargeMessage());
							i.putExtra("chargemessage", cmn);
							i.putExtra("type", 0);
							startActivityForResult(i, CHARGE_MESSAGE);
							break;
						case 1:
							new Thread(new Runnable() {
								public void run() {
									try {
										MyHttpRequest.dealMessage(message.getArgueMessage(), token);
									} catch (Exception e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}
								}
							}).start();
							holder.item_image.setImageResource(R.drawable.icon_tousu);
							ArgueMessageNotification amn = new ArgueMessageNotification();
							amn.setArgueMessage(message.getArgueMessage());
							i.putExtra("arguemessage", amn);
							i.putExtra("type", 1);
							startActivityForResult(i, ARGUE_MESSAGE);
							break;
						case 2:
							new Thread(new Runnable() {
								public void run() {
									try {
										MyHttpRequest.dealMessage(message.getNormalMessage(), token);
									} catch (Exception e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}
								}
							}).start();
							holder.item_image.setImageResource(R.drawable.center_message);
							NormalMessageNotification nmn = new NormalMessageNotification();
							nmn.setNormalMessage(message.getNormalMessage());
							i.putExtra("normalmessage", nmn);
							i.putExtra("type", 2);
							startActivityForResult(i, NORMAL_MESSAGE);
							break;

						default:
							break;
						}
						
					}
				});
			}
			else{
				holder.item_name.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View arg0) {
						// TODO Auto-generated method stub
						Toast.makeText(UserMesActivity.this, "暂时没有消息", Toast.LENGTH_SHORT).show();
					}
				});
			}
			return convertView;
		}
    	
    }
    
    
    
    private class ViewHolder{
    	ImageView item_image;
    	TextView item_name;
    	
    }
    
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    	// TODO Auto-generated method stub
    	if(requestCode == CHARGE_MESSAGE && resultCode == RESULT_OK){
    		message.chargeMessageNotification.clear();
    		
    	}
    	else if(requestCode == ARGUE_MESSAGE && resultCode == RESULT_OK){
    		message.argueMessageNotification.clear();
    	}
    	else if(requestCode == NORMAL_MESSAGE && resultCode == RESULT_OK){
    		message.normalMessageNotification.clear();
    	}
    	setlist();
    	MyAdapter adapter = new MyAdapter(this);
    	message_list.setAdapter(adapter);
    }
}
