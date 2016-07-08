package com.example.everapp;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import org.json.JSONObject;

import com.example.JsonToMap.JsonUtil;
import com.example.util.HttpUtil;
import com.example.util.MyHttpRequest;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

public class AStakeCommentActivity extends Activity{
	
	private ImageButton retButton;
	private TextView title;
	private RatingBar ratebar;
	private Button comment_btn;
	private TextView stake_name_txt;
	private EditText comment_edit;
	private Handler handler;
	private SharedPreferences preferences;
	private String token,userstakeId;
	private Intent intent;
	private float rate=0;
	private static final int POST_comment_SUCCESS = 0;
	private static final int POST_comment_FAIL = 1;
	private boolean isComment=true,isRating=false;
@Override
protected void onCreate(Bundle savedInstanceState) {
	// TODO Auto-generated method stub
	super.onCreate(savedInstanceState);
	requestWindowFeature(Window.FEATURE_NO_TITLE);
	setContentView(R.layout.electric_comment);
	title=(TextView)findViewById(R.id.title);
	retButton = (ImageButton) findViewById(R.id.return_btn);
	stake_name_txt=(TextView)findViewById(R.id.stake_name_txt);//使用车桩名字
	ratebar=(RatingBar)findViewById(R.id.ratingbar);//打分
	comment_edit=(EditText)findViewById(R.id.a_stake_comment);//车桩评论
	comment_btn=(Button)findViewById(R.id.commit_comment_btn);//提交评论
	preferences = this.getSharedPreferences("ever", this.MODE_PRIVATE);
	token=preferences.getString("token", "");//得到用户的token值
	userstakeId=getIntent().getStringExtra("userstakeId");
	//userstakeId="1";
	handler = new Handler(){
		public void handleMessage(Message msg) {
			AStakeCommentActivity.this.handleMessage(msg);
		};
	};
	initActivity();
}
private void handleMessage(Message msg){
	Bundle b = msg.getData();
	int res = b.getInt("result");
	switch (res) {
	case POST_comment_SUCCESS:
		Toast.makeText(this, "评价成功", Toast.LENGTH_SHORT).show();
		finish();
		break;
	case POST_comment_FAIL:
		Toast.makeText(this, "评价失败：" + b.getString("errmsg"), Toast.LENGTH_SHORT).show();
		break;
	default:
		break;
	}
}
private void initActivity() {
	title.setText("评价界面");
	retButton.setOnClickListener(new OnClickListener() {

		@Override
		public void onClick(View arg0) {
			// TODO Auto-generated method stub
		comment_btn.setBackgroundColor(getResources().getColor(R.color.darkgray));
			finish();
		}
	});
	 ratebar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener(){

		   public void onRatingChanged(RatingBar ratingBar, float rating,
		     boolean fromUser) {
			
		   rate=rating;
		   ratebar.setRating(rate);
			 
			  if(rate!=0){
				  isRating=true;
			  }
			  initCommitButton();
		   }}); 
	comment_btn.setOnClickListener(new OnClickListener() {
		
		@Override
		public void onClick(View arg0) {
			// TODO Auto-generated method stub
			 String temp_comment=comment_edit.getText().toString();
			 if(temp_comment==null||temp_comment.equals("")){
				 Toast.makeText(AStakeCommentActivity.this, "评论不能为空", Toast.LENGTH_SHORT).show();
				 return;
			 }
			 else {
				 new Thread(new Runnable() {
						public void run() {
							String grade=rate+"";
							System.out.println(grade);
						Stake_comment(token, userstakeId, comment_edit.getText().toString(),grade);	
							//提交评论给服务器
						}
					}).start();
			
			 }
			
			
		}
	});
	comment_btn.setBackgroundColor(getResources().getColor(R.color.darkgray));
	comment_btn.setClickable(false);
	
}
private void Stake_comment(String token,String  userstakeId, String content, String grade){
	Bundle bundle = new Bundle();
	try {
		String Time;
		SimpleDateFormat sDateFormat=   new  SimpleDateFormat("yyyy-MM-dd HH:mm:ss",Locale.getDefault());       
		 Time= sDateFormat.format(new Date());//获取当前时间
		if(ElectircComment(token, userstakeId, content,grade,Time)){
			bundle.putInt("result", POST_comment_SUCCESS);
			Message msg = handler.obtainMessage();
			msg.setData(bundle);
			handler.sendMessage(msg);
		}
		else{
			bundle.putInt("result", POST_comment_FAIL);
			bundle.putString("errmsg", "something wrong");
			Message msg = handler.obtainMessage();
			msg.setData(bundle);
			handler.sendMessage(msg);
		}
		
	} catch (Exception e) {
		// TODO Auto-generated catch block
		bundle.putInt("result", POST_comment_FAIL);
		bundle.putString("errmsg", e.getMessage());
		Message msg = handler.obtainMessage();
		msg.setData(bundle);
		handler.sendMessage(msg);
	}

}
private boolean ElectircComment(String token, String  userstakeId, String content,
		String grade, String time) {
	// TODO Auto-generated method stub
	Map<String, String> map = new HashMap<String, String>();
	map.put("useStakeId", userstakeId);
	map.put("content", content);
	map.put("grade", grade);
	map.put("time", time);
	try {
		JSONObject jsonObject = new JSONObject(HttpUtil.postRequest(
				"api/user/judgeAdd.servlet",map,token));
		String result=jsonObject.getString("data");
		if(result.equals("success")){
			return true;
		}
		else {
			return false;
		}
		}
	catch (Exception e) {
		e.printStackTrace();	
		return false;
	}
	
}
private void initCommitButton(){
	if(isComment && isRating){
		comment_btn.setBackgroundColor(getResources().getColor(R.color.orange));
		comment_btn.setClickable(true);
		
	}
	else{
		comment_btn.setBackgroundColor(getResources().getColor(R.color.darkgray));
		comment_btn.setClickable(false);
	}
	
}
}
