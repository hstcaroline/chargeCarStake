package com.example.everapp;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;

import com.example.SerialMap.SerializableMap;
import com.example.dialog.CustomDialog;
import com.example.dialog.MyDialog;
import com.example.dialog.MyDialog.Builder;
import com.example.util.HttpUtil;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.preference.DialogPreference;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class UserMoneyActivity extends Activity{
	private static final int DIALOG_EXIT = 0;
	private ImageButton user_money_reback_btn;
	private TextView titleText;
	private TextView moneyCount;
	private Button deposit;
	private Button draw;
	private Double amount;
	private SerializableMap serializableMap;
	private String token,moneyString,pswString;
	private int type=0;
	private HashMap<String,Object>userMap;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.user_money);
		//接收userfragement传来的数据usermap
				Bundle bundle=new Bundle();
				bundle = this.getIntent().getExtras();
				serializableMap = (SerializableMap) bundle.get("usermap");
				userMap=serializableMap.getMap();
		
		token=userMap.get("token").toString();//存储登录用户的token便于后面更新操作
		Log.e("token", token);
		user_money_reback_btn=(ImageButton)findViewById(R.id.return_btn);
		user_money_reback_btn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent=new Intent();
				intent.putExtra("remaining",userMap.get("remaining").toString());
				UserMoneyActivity.this.setResult(RESULT_OK, intent);
				UserMoneyActivity.this.finish();
			}
		});
		titleText = (TextView) findViewById(R.id.title);
		moneyCount = (TextView) findViewById(R.id.money_count);
		deposit = (Button) findViewById(R.id.recharge);
		draw = (Button) findViewById(R.id.withdraw_cash);
		
		titleText.setText("我的钱包");
		moneyCount.setText(userMap.get("remaining").toString() +"个");
		deposit.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				type=1;
				   dialog(type);
			}
		});
		
		draw.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				type=-1;
				dialog(type);
			}
		});
	}
	private void dialog(final int type) {
	    final CustomDialog dialogs = new CustomDialog(UserMoneyActivity.this);
	    final EditText editText = (EditText) dialogs.getEditText();//方法在CustomDialog中实现\
	    if(type==1){
	    	 dialogs.setTitle("充值界面");
	    }
	    if(type==-1){
	    	 dialogs.setTitle("提现界面");
	    }
	    dialogs.setview(0);
	    dialogs.show();
	    dialogs.setOnPositiveListener(new OnClickListener() {
	        @Override
	        public void onClick(View v) {
	            //dosomething youself
	        	moneyString=editText.getText().toString();
	        	if(moneyString==null||moneyString.equals("")){
	        		Toast.makeText(UserMoneyActivity.this, "请输入金额", Toast.LENGTH_SHORT).show();
	        		}
	        	else{
	        		System.out.println(moneyString);
	        		dialog2(type);
	        		dialogs.dismiss();
	        		
	        	}
	           
	        }
	    });
	    dialogs.setOnNegativeListener(new OnClickListener() {
	        @Override
	        public void onClick(View v) {
	            dialogs.dismiss();
	        }
	    });
	    
	}
   private void dialog2(final int type){
	   final CustomDialog dialogpsw = new CustomDialog(UserMoneyActivity.this);
	   dialogpsw.setTitle("输入支付密码");
	   dialogpsw.setbutton("确定");
	   dialogpsw.setview(1);
	  final EditText editText = (EditText) dialogpsw.getpswText();//方法在CustomDialog中实现
	    dialogpsw.show();
	    dialogpsw.setOnPositiveListener(new OnClickListener() {
	        @Override
	        public void onClick(View v) {
	            //dosomething youself
	        	pswString=editText.getText().toString();
	        	if(pswString==null||pswString.equals("")){
	        		Toast.makeText(UserMoneyActivity.this, "密码不能为空", Toast.LENGTH_SHORT).show();
	        		}
		        	else{
		        		System.out.println(pswString);
		        		if(VaildatePsw(token, pswString)){
		        			String string=Compute_money(userMap.get("remaining").toString(), moneyString,type);
		        			if(string!=null){
		        				if(Modify_Money(token, string)){
		        				if(type==1){
		        					Toast.makeText(UserMoneyActivity.this, "充值成功", Toast.LENGTH_SHORT).show();
		        				}
		        				if(type==-1){
		        					Toast.makeText(UserMoneyActivity.this, "提现成功", Toast.LENGTH_SHORT).show();
		        				}
		        					userMap.put("remaining", string);
		        					moneyCount.setText(string+"个");
		        					dialogpsw.dismiss();
									return;
		        				}
		        			}
		        			else{
		        				Toast.makeText(UserMoneyActivity.this, "操作错误", Toast.LENGTH_SHORT).show();
		        				dialogpsw.dismiss();
		        				return;
		        			}
		        		}
		        		else {
							Toast.makeText(UserMoneyActivity.this, "密码输入错误", Toast.LENGTH_SHORT).show();
							return;
						}
		        	}
	        }
	    });
	    dialogpsw.setOnNegativeListener(new OnClickListener() {
	        @Override
	        public void onClick(View v) {
	            dialogpsw.dismiss();
	        }
	    }); 
   }
   protected boolean VaildatePsw(String token,String psw){
	   Map<String, String>map=new HashMap<String,String>();
	   map.put("type", "0");
	   map.put("password", psw);
	   try {
			  JSONObject jsonObject = new JSONObject(HttpUtil.postRequest(
						"api/account/updatePsw.servlet",map,token));
			  String tokens=jsonObject.getString("token");
			  if(token==null||tokens.equals("")){
				return false;
			  }
			  else {
				 // Log.e("testtttt",tokens);
					 return true;
			}
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return false;
		}
   }
   protected boolean Modify_Money(String token,String moneycount){
	   
	   Map<String, String>map=new HashMap<String,String>();
	   map.put("remaining",moneycount);
	   try {
			  JSONObject jsonObject = new JSONObject(HttpUtil.putRequest(
						"api/user/update.servlet",map,token));
			  String tokens=jsonObject.getString("token");
			  if(token==null||tokens.equals("")){
				return false;
			  }
			  else {
				//  Log.e("testtttt",tokens);
					 return true;
			}
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return false;
		}
   }
   protected String Compute_money(String oldcount,String newcount,int type){
	   String moneycount=null;
	   if(type==1){//充值操作
		   Double oldDoubled=Double.parseDouble(oldcount);
		   if(newcount.startsWith(".")){
			   newcount="0"+newcount;
		   }
		   Double newdDouble=Double.parseDouble(newcount);
		   Double countDouble=oldDoubled+newdDouble;
		  moneycount=countDouble+"";
		  return moneycount;
	   }
	   if(type==-1){//提现操作
		   Double oldDoubled=Double.parseDouble(oldcount);
		   if(newcount.startsWith(".")){
			   newcount="0"+newcount;
		   }
		   Double newdDouble=Double.parseDouble(newcount);
		   Double countDouble=oldDoubled-newdDouble;
		   if(countDouble<0){
			   return moneycount;
		   }
		   else{
			   moneycount=countDouble+"";
				   return moneycount;
		   }
		 
	   }
	   return moneycount;
   }
 
}
