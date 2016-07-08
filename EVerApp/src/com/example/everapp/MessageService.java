package com.example.everapp;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.example.util.MyHttpRequest;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Binder;
import android.os.IBinder;
import entity.MessageNotification;
import entity.UserMessage;

public class MessageService extends Service {
	private Notification mNotification;
	private NotificationManager mManager;
	private MessageNotification message = new MessageNotification();
	private SharedPreferences preferences;
	private MyBinder binder = new MyBinder();
	private boolean quit = false;

	public MessageService() {
	}

	public class MyBinder extends Binder implements Serializable{
		public MessageNotification getMessage(){
			return message;
		}
		public void putMessage(MessageNotification m){
			message = m;
		}
	}

	@Override
	public IBinder onBind(Intent intent) {
		// TODO: Return the communication channel to the service.
		
		return binder;
	}

	@Override
	public void onCreate() {
		initNotifiManager();
		preferences = getSharedPreferences("ever", MODE_PRIVATE);
		// TODO Auto-generated method stub
//		new Thread(new Runnable() {
//			public void run() {
//
//				while (!quit) {
//					try {
//						Thread.sleep(60000);
//					} catch (InterruptedException e1) {
//						// TODO Auto-generated catch block
//						e1.printStackTrace();
//					}
//					if (!preferences.getString("token", "").equals("")) {
//						try {
//							message.setChargeMessage(MyHttpRequest.getMessages(0, preferences.getString("token", "")));
//							message.setArgueMessage(MyHttpRequest.getMessages(1, preferences.getString("token", "")));
//							message.setNormalMessage(MyHttpRequest.getMessages(2, preferences.getString("token", "")));
//						} catch (Exception e) {
//							e.printStackTrace();
//						}
//						if(message.getArgueSize() != 0 || message.getChargeSize() != 0 || message.getNormalSize() != 0)
//							showNotification();
//					}
//					else{
//						message.clear();
//					}
//				}
//			}
//		}).start();
	}
	//初始化通知栏配置
		private void initNotifiManager() {
			mManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
			int icon = R.drawable.ic_launcher;
			mNotification = new Notification();
			mNotification.icon = icon;
			mNotification.tickerText = "您有未处理的消息";
			mNotification.defaults |= Notification.DEFAULT_SOUND;
			mNotification.flags = Notification.FLAG_AUTO_CANCEL;
		}

		//弹出Notification
		private void showNotification() {
			mNotification.when = System.currentTimeMillis();
			//Navigator to the new activity when click the notification title
			Intent i = new Intent(this, UserMesActivity.class);
			i.putExtra("message", message);
			i.putExtra("binder", binder);
			PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, i,
					Intent.FLAG_ACTIVITY_NEW_TASK);
			mNotification.setLatestEventInfo(this,
					getResources().getString(R.string.app_name), "你有未处理的消息!", pendingIntent);
			mManager.notify(0, mNotification);
		}

		@Override
		public void onDestroy() {
			// TODO Auto-generated method stub
			super.onDestroy();
			this.quit = true;
		}
		
		

}
