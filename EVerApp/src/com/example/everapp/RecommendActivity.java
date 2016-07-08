package com.example.everapp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.baidu.mapapi.map.Text;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class RecommendActivity extends Activity {
	
	//存储的信息
	private List<Map<String, Object>> mData;
	private ListView recommendList;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.recommend);
		mData = getData();
		recommendList = (ListView) findViewById(R.id.recommend_list);
		recommendList.setAdapter(new MyAdapter(this));
	}
	
	
	private List<Map<String, Object>> getData() {
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
 
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("name", "特斯拉一号充电桩");
        map.put("distance", "203.5m");
        list.add(map);
 
        map = new HashMap<String, Object>();
        map.put("name", "剑川特斯拉充电装");
        map.put("distance", "506.7m");
        list.add(map);
 
        map = new HashMap<String, Object>();
        map.put("name", "莲花南特斯拉");
        map.put("distance", "1.1km");
        list.add(map);
         
        return list;
    }
	
	private class ViewHolder{
		TextView name;
		TextView distance;
	}
	
	//适配器
	private class MyAdapter extends BaseAdapter{

		private LayoutInflater mInflater;
		public MyAdapter(Context context) {
			// TODO Auto-generated constructor stub
			this.mInflater = LayoutInflater.from(context);
		}
		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return mData.size();
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
		public View getView(int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			ViewHolder holder;
			if(convertView == null){
				convertView = mInflater.inflate(R.layout.recommend_item, null);
				holder = new ViewHolder();
				holder.name = (TextView) convertView.findViewById(R.id.electric_name);
				holder.distance = (TextView) convertView.findViewById(R.id.distance);
				convertView.setTag(holder);
			}
			else{
				holder = (ViewHolder) convertView.getTag();
			}
			//设置onclicklistener等
			holder.name.setText(mData.get(position).get("name").toString());
			holder.distance.setText(mData.get(position).get("distance").toString());
			return convertView;
		}
		
		
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.recommend, menu);
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
