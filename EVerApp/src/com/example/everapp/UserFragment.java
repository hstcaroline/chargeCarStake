package com.example.everapp;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.conn.ManagedClientConnection;





import org.json.JSONObject;

import com.example.JsonToMap.JsonUtil;
import com.example.SerialMap.SerializableMap;
import com.example.everapp.MessageService.MyBinder;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.NotificationManager;
import android.content.ComponentName;

import com.example.util.HttpUtil;

import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;
import entity.MessageNotification;


public class UserFragment extends Fragment {

	private static final String[] COUNTRIES = new String[] { "我的充电记录", "我的车桩",
			"我的钱包", "操作指引" };
	private ListView center_listview;
	private Button  setting_btn, login_btn, collect_btn, order_btn,
			message_btn, user_info_btn;
    
	private boolean islogin=false ;//是否登录判断
	
	
	private static final int USER_LOGIN = 1;//用户登录判断
	private static final int Cancel_LOGIN=0;
	private static final int User_Detail=2;//用户个人信息修改判断;
	private static final int USER_MESSAGE = 5;
	private static final int User_Money=3;//用户钱包信息修改判断
	private HashMap<String, Object> usermap;//接收传过来的用户map数据
	private  SerializableMap serializableMap;
	private static SerializableMap myMap = new SerializableMap();
	private  String username,token;//存储用户昵称和token
	private Handler handler;
	private Intent i;
	private static final int GET_DATA_SUCCESS = 0;
	private static final int GET_DATA_FAIL = 1;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.user_fragment, null);
	}

	private MyBinder binder;
	private ServiceConnection connection = new ServiceConnection() {
		
		@Override
		public void onServiceDisconnected(ComponentName arg0) {
			// TODO Auto-generated method stub
			
		}
		
		@Override
		public void onServiceConnected(ComponentName arg0, IBinder arg1) {
			// TODO Auto-generated method stub
			binder = (MyBinder) arg1;
		}
	};
	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onViewCreated(view, savedInstanceState);
		
		Intent intent = new Intent(getActivity(), MessageService.class);
		getActivity().bindService(intent, connection, getActivity().BIND_AUTO_CREATE);
		center_listview = (ListView) view.findViewById(R.id.center_list);
		
        //初始化界面
		String[] mFrom = new String[] { "logo", "title", "img" };
		int[] mTo = new int[] { R.id.image_logo, R.id.text_item,
				R.id.image_item };
		List<Map<String, Object>> mList = new ArrayList<Map<String, Object>>();
		Map<String, Object> mMap = null;
		mMap = new HashMap<String, Object>();
		mMap.put("logo", R.drawable.icon_charge);
		mMap.put("title", COUNTRIES[0]);
		mMap.put("img", R.drawable.go);
		mList.add(mMap);

		mMap = new HashMap<String, Object>();
		mMap.put("logo", R.drawable.icon_details_terminal);
		mMap.put("title", COUNTRIES[1]);
		mMap.put("img", R.drawable.go);
		mList.add(mMap);

		mMap = new HashMap<String, Object>();
		mMap.put("logo", R.drawable.icon_electricity_hint);
		mMap.put("title", COUNTRIES[2]);
		mMap.put("img", R.drawable.go);
		mList.add(mMap);

		mMap = new HashMap<String, Object>();
		mMap.put("logo", R.drawable.icon_guide);
		mMap.put("title", COUNTRIES[3]);
		mMap.put("img", R.drawable.go);
		mList.add(mMap);

		SimpleAdapter mAdapter = new SimpleAdapter(getActivity(), mList,
				R.layout.center_list_item, mFrom, mTo);
		center_listview.setAdapter(mAdapter);
		setting_btn = (Button) view.findViewById(R.id.center_settting_btn);
		collect_btn = (Button) view.findViewById(R.id.user_like_btn);
		login_btn = (Button) view.findViewById(R.id.user_login_btn);
		user_info_btn = (Button) view.findViewById(R.id.user_info_btn);
		order_btn = (Button) view.findViewById(R.id.user_order_btn);
		message_btn = (Button) view.findViewById(R.id.user_message_btn);

		// setting action
		login_btn.setOnClickListener(new MyClickListener());
		setting_btn.setOnClickListener(new MyClickListener());
		collect_btn.setOnClickListener(new MyClickListener());
		order_btn.setOnClickListener(new MyClickListener());
		message_btn.setOnClickListener(new MyClickListener());
       center_listview.setOnItemClickListener(new MyitemListener());
       user_info_btn.setOnClickListener(new MyClickListener());    
       setTopLayout();
       //create thread
       handler = new Handler(){
			public void handleMessage(Message msg) {
				
				UserFragment.this.handleMessage(msg);
			};
		};
	}
	private void handleMessage(Message msg){
		Bundle b = msg.getData();
		int res = b.getInt("result");
		switch (res) {
		case GET_DATA_SUCCESS:
			Toast.makeText(getContext(), "获取数据成功", Toast.LENGTH_SHORT).show();			
			break;
		case GET_DATA_FAIL:
			Toast.makeText(getContext(), "网络异常", Toast.LENGTH_SHORT).show();
			break;
		default:
			break;
		}
	}
	class MyClickListener implements OnClickListener {
		public void onClick(View v) {
			// TODO Auto-generated method stub
			Intent intent = new Intent();
            if(islogin){
			switch (v.getId()) {
			
			case R.id.center_settting_btn:
				
                 intent.setClass(getActivity(), SettingActivity.class);
                 startActivityForResult(intent,Cancel_LOGIN);
				
				break;
			case R.id.user_login_btn:
				intent.setClass(getActivity(), UserLoginActivity.class);
				startActivityForResult(intent, USER_LOGIN);
				break;
			case R.id.user_like_btn:
				intent.setClass(getActivity(), UserCollectActivity.class);
				 startActivity(intent);
				break;
			case R.id.user_order_btn:
				intent.setClass(getActivity(), UserOrderActivity.class);
				startActivity(intent);
				
				break;
			case R.id.user_message_btn:
				NotificationManager manager = (NotificationManager) getActivity().getSystemService(getActivity().NOTIFICATION_SERVICE);
				manager.cancel(0);
				intent.setClass(getActivity(), UserMesActivity.class);
				intent.putExtra("message", binder.getMessage());
				intent.putExtra("binder", binder);
				startActivityForResult(intent, USER_MESSAGE);
				
				break;
			case R.id.user_info_btn:
				new Thread(new Runnable(){
				
					public void run() {
						i=new Intent();
				if(All_UserInfo(usermap.get("token").toString())){
				i.setClass(getActivity(), UserDetailActivity.class);
				myMap.setMap(usermap);// 将map数据添加到封装的myMap<span></span>中
				Bundle bundle = new Bundle();
				bundle.putSerializable("usermap", myMap);
				i.putExtras(bundle);// bundle封装传递map对象*/
				startActivityForResult(i,User_Detail);
				
				}
					}
				}).start();
				break;
			default:
				break;
			}
            }
            else{
            	
            	intent.setClass(getActivity(), UserLoginActivity.class);
				startActivityForResult(intent, USER_LOGIN);
            }
		}

	}
    class MyitemListener implements OnItemClickListener{
    	Intent intent =new Intent();
    	@Override
    	
    	public void onItemClick(AdapterView<?> parent, View view, int position,
    			long id) {
    		// TODO Auto-generated method stub
    		if(islogin){
    	    switch (position) {
			case 0:
				intent.putExtra("token", usermap.get("token").toString());
				intent.setClass(getActivity(), UserChargeRecordActivity.class);
				startActivity(intent);
				break;
			case 1:
				//intent.putExtra("token", usermap.get("token").toString());
				intent.setClass(getActivity(), UserChargeStakeActivity.class);
				startActivity(intent);
			 break;
			case 2:
				new Thread(new Runnable(){
					
					public void run() {
					i=new Intent();
				if(All_UserInfo(usermap.get("token").toString())){
				myMap.setMap(usermap);// 将map数据添加到封装的myMap<span></span>中
				Bundle bundle = new Bundle();
				bundle.putSerializable("usermap", myMap);
				i.putExtras(bundle);// bundle封装传递map对象*/
				i.setClass(getActivity(), UserMoneyActivity.class);
				startActivityForResult(i,User_Money);
				}
					}
				}).start();
				break;
				
				
			case 3:
				intent.setClass(getActivity(), UserGuideActivity.class);
				startActivity(intent);
				break;
			default:
				break;
			}
    		}
    		else{
    			intent.setClass(getActivity(), UserLoginActivity.class);
				startActivityForResult(intent, USER_LOGIN);
    		}
    	}
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
    	// TODO Auto-generated method stub
    	System.out.println("-------------------------------:"+requestCode+"****"+resultCode);
    	if(requestCode == USER_LOGIN && resultCode == Activity.RESULT_OK){
    		//接收从登录传来的单一数据
    		 Bundle UserBuddle = data.getExtras();  
           serializableMap = (SerializableMap)UserBuddle.get("map");
           usermap=serializableMap.getMap();
           username=usermap.get("username").toString();
           System.out.println(username);
            
    	    islogin=true;//判断是否登录成功
    	    user_info_btn.setText(username);//初始登录显示
    		user_info_btn.setVisibility(View.VISIBLE);
    		login_btn.setVisibility(View.GONE);
    		//Toast.makeText(getActivity(), "登录成功", Toast.LENGTH_SHORT).show();
    		return;
    	}
    	else if(requestCode == Cancel_LOGIN && resultCode == Activity.RESULT_OK){
    		islogin=false;//判断是否退出成功
    		user_info_btn.setVisibility(View.GONE);
    		login_btn.setVisibility(View.VISIBLE);
    		Toast.makeText(getActivity(), "退出成功", Toast.LENGTH_SHORT).show();
    		return;
    	}
    	else if(requestCode==User_Detail&& resultCode == Activity.RESULT_OK){
    		//判断用户是否修改昵称
    		System.out.println("in detail");
    		 Bundle UserBuddle = data.getExtras();  
             serializableMap = (SerializableMap)UserBuddle.get("usermap");
             usermap=serializableMap.getMap();
             String new_username=usermap.get("username").toString();
    		 if(!new_username.equals(username)){
    			 username=new_username;
    			 user_info_btn.setText(new_username);
    			 user_info_btn.setVisibility(View.VISIBLE);
    			 login_btn.setVisibility(View.GONE);
    			 return;
    		 }
    		 else {
    			 
    			 user_info_btn.setVisibility(View.VISIBLE);
    			 login_btn.setVisibility(View.GONE);
			}
    		 return;
    	}
    	else if(requestCode == USER_MESSAGE && resultCode == Activity.RESULT_OK){
    		binder.putMessage((MessageNotification) data.getSerializableExtra("message"));
    		return;
    	}
    	else if(requestCode==User_Money&& resultCode == Activity.RESULT_OK){
			//System.out.println("User_Money!!!!");
			String moneycount=data.getStringExtra("remaining");
			//Log.e("remaining", moneycount);
			usermap.put("remaining",moneycount);
			return;
		}
    }
    
    private void setTopLayout(){
    	
    	if(islogin){
    		user_info_btn.setText(username);//显示用户名字在用户个人界面
    		user_info_btn.setVisibility(View.VISIBLE);
    		login_btn.setVisibility(View.GONE);
    	}
    	else{
    		user_info_btn.setVisibility(View.GONE);
    		login_btn.setVisibility(View.VISIBLE);
    	}
    }
    @Override
    public void onDestroy() {
    	// TODO Auto-generated method stub
    	super.onDestroy();
    	getActivity().unbindService(connection);
    }
    protected boolean All_UserInfo(String token){
             Bundle bundle=new Bundle();
		try {
			JSONObject jsonObject = new JSONObject(HttpUtil.getRequest(
					"api/user/userInfo.servlet", token));
			String tokens = jsonObject.getString("token");
			
		
			if (tokens != null) {
				String date=jsonObject.getString("data");
				Object hello = JsonUtil.jsonParse(date);
				if (hello instanceof Map) {
					usermap = (HashMap<String, Object>) hello;// map存放返回的用户数据
					usermap.put("token", tokens);
				}
				bundle.putInt("result", GET_DATA_SUCCESS);
				Message msg = handler.obtainMessage();
				msg.setData(bundle);
				handler.sendMessage(msg);
				return true;
			}
			else {
				bundle.putInt("result", GET_DATA_FAIL);
				Message msg = handler.obtainMessage();
				msg.setData(bundle);
				handler.sendMessage(msg);
				return false;
			}
		} catch (Exception e) {
			//e.printStackTrace();
			//Toast.makeText(getActivity(), "网络异常", Toast.LENGTH_SHORT).show();
			bundle.putInt("result", GET_DATA_FAIL);
			Message msg = handler.obtainMessage();
			msg.setData(bundle);
			handler.sendMessage(msg);
			return false;
		}
		
    }
}
