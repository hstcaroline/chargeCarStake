package com.example.everapp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.R.integer;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.renderscript.Type;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import entity.ArgueMessageNotification;
import entity.ChargeMessageNotification;
import entity.NormalMessageNotification;
import entity.UserMessage;

public class MesNotifActivity extends Activity {

	private Intent intent;
	private int type;
	private ChargeMessageNotification chargeNotification;
	private ArgueMessageNotification argueNotification;
	private NormalMessageNotification normalNotification;
	private List<UserMessage> notificationList;
	
	private TextView title;
	private ImageButton  retButton;
	private ListView messageList;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_mes_notif);
		title = (TextView) findViewById(R.id.title);
		retButton = (ImageButton) findViewById(R.id.return_btn);
		messageList = (ListView) findViewById(R.id.user_message_list);
		title.setText("ÏûÏ¢");
		retButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				setResult(RESULT_OK);
				finish();
			}
		});
		intent = getIntent();
		type = intent.getIntExtra("type", -1);
		switch (type) {
		case 0:
			chargeNotification = (ChargeMessageNotification) intent.getSerializableExtra("chargemessage");
			notificationList = chargeNotification.getChargeMessage();
			break;
		case 1:
			argueNotification = (ArgueMessageNotification) intent.getSerializableExtra("arguemessage");
			notificationList = argueNotification.getArgueMessage();
			break;
		case 2:
			normalNotification = (NormalMessageNotification) intent.getSerializableExtra("normalmessage");
			notificationList = normalNotification.getNormalMessage();
			break;

		default:
			break;
		}
		
		setList();
		
	}
	
	private void setList(){
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		for(int i = 0; i < notificationList.size(); i++){
			UserMessage um = notificationList.get(i);
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("title", um.getTitle());
			map.put("content", um.getContent());
			map.put("time", um.getTime());
			list.add(map);
		}
		SimpleAdapter s = new SimpleAdapter(this, list, R.layout.notification_item,
				new String[] {"title", "content", "time"}, new int[] {R.id.title, R.id.content, R.id.time});
		messageList.setAdapter(s);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.mes_notif, menu);
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
