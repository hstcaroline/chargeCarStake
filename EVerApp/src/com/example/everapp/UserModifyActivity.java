package com.example.everapp;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;

import com.example.JsonToMap.JsonUtil;
import com.example.SerialMap.SerializableMap;
import com.example.util.HttpUtil;

import android.R.integer;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class UserModifyActivity extends Activity {

	private Button saveButton;// ����button
	private ImageButton rebackButton;
	private EditText user_modify;
	private TextView titleText;
	private int tag = -1;
	private String token, username, userphone, modifyString;
	private HashMap<String, Object> usermap;
	private SerializableMap serializableMap;
	private Intent intents;
	final SerializableMap myMap = new SerializableMap();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.modify_use_name);
		titleText = (TextView) findViewById(R.id.title);// ҳ��������
		rebackButton = (ImageButton) findViewById(R.id.return_btn);// �����ϸ�ҳ��
		user_modify = (EditText) findViewById(R.id.icon_name);// �޸���Ϣ�༭��
		saveButton = (Button) findViewById(R.id.save_btn);// ���水ť
		intents = getIntent();
		tag = Integer.parseInt(intents.getStringExtra("tag"));
		Bundle bundle = new Bundle();
		bundle = this.getIntent().getExtras();
		serializableMap = (SerializableMap) bundle.get("usermap");
		usermap = serializableMap.getMap();
		token = usermap.get("token").toString();// �洢��¼�û���token���ں�����²���
		username = usermap.get("username").toString();
		userphone = usermap.get("telephone").toString();
		if (token != null && tag == 0) {
			titleText.setText("�ǳ�");
			user_modify.setText(username);
		}
		if (token != null && tag == 1) {
			titleText.setText("�ֻ�����");
			user_modify.setText(userphone);
		}
		rebackButton.setOnClickListener(new OnClickListener() {
			Intent intent = new Intent();

			@Override
			public void onClick(View v) {

				// TODO Auto-generated method stub
				myMap.setMap(usermap);// ��map������ӵ���װ��myMap<span></span>��
				Bundle bundle = new Bundle();
				bundle.putSerializable("usermap", myMap);
				intent.putExtras(bundle);// bundle��װ����map����*/
				intent.setClass(UserModifyActivity.this,
						UserDetailActivity.class);
				startActivity(intent);
				finish();
			}

		});
		saveButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				modifyString = user_modify.getText().toString();
				Intent intent = new Intent();
				if (modifyString != null) {
					if (!modifyString.equals(username) && tag == 0) {
						Toast.makeText(UserModifyActivity.this, "username",
								Toast.LENGTH_SHORT).show();
						// ���²���
						if (modifyUser(token, modifyString, tag)) {
							myMap.setMap(usermap);// ��map������ӵ���װ��myMap<span></span>��
							Bundle bundle = new Bundle();
							bundle.putSerializable("usermap", myMap);
							intent.putExtras(bundle);// bundle��װ����map����*/
							intent.setClass(UserModifyActivity.this,
									UserDetailActivity.class);
							startActivity(intent);
							intents.putExtras(bundle);
							setResult(Activity.RESULT_OK, intents);
							finish();
						} else {
							Toast.makeText(UserModifyActivity.this, "����ʧ��",
									Toast.LENGTH_SHORT).show();
						}
					}
					if (!modifyString.equals(userphone) && tag == 1) {
						Toast.makeText(UserModifyActivity.this, "telephone",
								Toast.LENGTH_SHORT).show();
						
							if (modifyUser(token, modifyString, tag)) {
								myMap.setMap(usermap);// ��map������ӵ���װ��myMap<span></span>��
								Bundle bundle = new Bundle();
								bundle.putSerializable("usermap", myMap);
								intent.putExtras(bundle);// bundle��װ����map����*/
								intent.setClass(UserModifyActivity.this,
										UserDetailActivity.class);
								startActivity(intent);
								intents.putExtras(bundle);
								setResult(Activity.RESULT_OK, intents);
								finish();
							}
							
						
						else {
							Toast.makeText(UserModifyActivity.this, "����ʧ��",
									Toast.LENGTH_SHORT).show();
						}

					}

				}

				else {
					saveButton.setEnabled(false);
				}
			}
		});
	}


	private boolean modifyUser(String tokens, String modifyString, int tag) {

		Map<String, String> map = new HashMap<String, String>();
		if (tag == 0) {
			map.put("username", modifyString);
		}
		if (tag == 1) {
			map.put("telephone", modifyString);
		}
		try {
			JSONObject jsonObject = new JSONObject(HttpUtil.putRequest(
					"api/user/update.servlet",map, tokens));
			String restoken = jsonObject.getString("token");
			String json = jsonObject.getString("data");
			
			Log.e("test", json);
			if(restoken==null||"".equals((restoken.trim())))
			{
				Toast.makeText(UserModifyActivity.this, "�ú����Ѿ�����!",
						Toast.LENGTH_SHORT).show();
				return false;
			}
			else{
			// ����������������ķ���ֵ
			Object hello = JsonUtil.jsonParse(json);
			if (hello instanceof Map) {
				usermap = (HashMap<String, Object>) hello;// map��ŷ��ص��û�����
				usermap.put("token", restoken);
				for (java.util.Map.Entry<String, Object> entry : usermap
						.entrySet()) {
					System.out.println("key= " + entry.getKey()
							+ " and value= " + entry.getValue());
					return true;
				}
			}
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return false;
	}
}
