package com.example.everapp;



import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;



import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;

import com.example.dialog.PayDialog;
import com.example.util.HttpUtil;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class PayForStakeActivity extends Activity {
	private RoundProgressBar mRoundProgressBar1;//��������
	private Button stakeButton;//������簴ť
	private String MoneyString;//����Ǯ����Ŀ
	private String pswString;//��������
	private int progress = 0;//��������ʼ��
	private int tag;//���ճ�����ʱ���
	private Intent intent;
	private String startime,user_record_id,endtime;//���Ŀ�ʼʱ��.ʹ�ü�¼��ID
	private double price;//���ÿСʱ�ĵ���
	private double Moneycount;
	private TextView payText;
	private SharedPreferences preferences;
	private String token;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.payforstake);
		//�����û�token
		preferences = this.getSharedPreferences("ever", this.MODE_PRIVATE);
		token=preferences.getString("token", "");//�õ��û���tokenֵ
		//���մ�ǰһ�����洫��������
		tag=Integer.parseInt(getIntent().getStringExtra("during"));
		tag=tag*60;
		startime=getIntent().getStringExtra("starttime");
		user_record_id=getIntent().getStringExtra("user_record_id");
		price=Double.parseDouble(getIntent().getStringExtra("price"));
		//��ʼ������
		mRoundProgressBar1 = (RoundProgressBar) findViewById(R.id.roundProgressBar1);
		stakeButton = (Button) findViewById(R.id.end_button);
		payText=(TextView)findViewById(R.id.pay_text);
		mRoundProgressBar1.setMax(tag);
		new TimeThread().start(); // �����µ��߳�
		new MoneyThread().start();//���������߳�
		new ControlThread().start();//��������߳�
		stakeButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {

				 new Thread(new Runnable() {
					 public void run() {
							//�������ʹ�øó��׮
						 //dialog();
						 Message msg = new Message();
		                    msg.what = 2;
		                    mHandler.sendMessage(msg);
						}
				 }).start();
				
			}
		});
	
	}
	class TimeThread extends Thread {
		@Override
		public void run() {

			while (progress <= tag) {
				progress = progress + 1;
				// System.out.println(progress);
				try {
					Thread.sleep(1000);
					mRoundProgressBar1.setProgress(progress);
					
				} catch (InterruptedException e) {
					e.printStackTrace();
				}

			}
			System.out.println(progress);
		}
	
	}
	class ControlThread extends Thread{
		public void run(){
			do{
			if(progress>=tag){
				
				  try {
					Thread.sleep(2000);
					 Message msg = new Message();
	                 msg.what = 3;
	                 mHandler.sendMessage(msg);
					System.err.println("okmamaammamama");
					break;
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			}while(true);
					
			
			}
			
		}
	class MoneyThread extends Thread{
		   public void run() {
			   int j=0;
	            do {
	            	
	                try {
	                	 j++;
	                    Thread.sleep(10000);
	                    Message msg = new Message();
	                    msg.what = 1;  //��Ϣ(һ������ֵ)
	                    mHandler.sendMessage(msg);// ÿ��10�뷢��һ��msg��mHandler
	                   
	                } catch (InterruptedException e) {
	                    e.printStackTrace();
	                }
	            } while (j<tag/10);	            
	         
	        }
	}
	//�����߳����洦����Ϣ������UI����
    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
          super.handleMessage(msg);
            switch (msg.what) {
            case 1:
            	
            	//String string="2016-1-6 10:40:34";
            	SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            	Date startDate;
				try {
					startDate = sDateFormat.parse(startime);
					System.out.println(sDateFormat.format(startDate));//�����ʼʱ��
	        		Date curDate = new Date(System.currentTimeMillis());//�õ���ǰʱ��
	        		endtime=sDateFormat.format(curDate);//�õ���ǰʱ��Ϊ������ʱ��
	        	     long during =curDate.getTime()-startDate.getTime();
	        	     Log.e("during",during+"");
	        	      double time=Double.parseDouble(during+"");
	        	      time=(time)/(60*60*1000);//ת����Сʱ
	        	     Moneycount=price*time; //����۸�
	        	     DecimalFormat df = new DecimalFormat("##.##");  
	        	    System.out.println("--------------price:"+df.format(Moneycount)+"----------"); 
	        	    MoneyString=df.format(Moneycount)+"";
	                payText.setText(MoneyString);
	                System.out.println(MoneyString+"**********");
	                 //���³��۸�
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
            	
                  break;
            case 2://���������簴ť����Ӧ
            	   dialog();
            	break;
            case 3://ʱ�䵽���Ժ�ֱ�ӿ�Ǯ�����۽���
            	//System.out.println("hhhhhhhhhhhh");
            	if(PayforMoney(token, user_record_id, startime, endtime, Moneycount)){
            		intent=new Intent();
            		intent.putExtra("userstakeId", user_record_id);
            		intent.setClass(PayForStakeActivity.this, AStakeCommentActivity.class);
            		startActivity(intent);
            		finish();
            	}
            	else{
            		Toast.makeText(PayForStakeActivity.this, "����ʧ��", Toast.LENGTH_SHORT).show();
            	}
            	break;
            case 4://������ʱ����ǰ����Ҳ�������۽���
            	intent=new Intent();
        		intent.putExtra("userstakeId", user_record_id);
        		intent.setClass(PayForStakeActivity.this, AStakeCommentActivity.class);
        		startActivity(intent);
        		finish();
            	break;
            case 5:
            	Toast.makeText(PayForStakeActivity.this, "��Ǯʧ��", Toast.LENGTH_SHORT).show();
            	break;
             default:
            	 break;
            }
        }
    };
    
    private void dialog() {
	    final PayDialog dialogs = new PayDialog(PayForStakeActivity.this);
	    final EditText editText = (EditText) dialogs.getpswText();//�������༭��
	    dialogs.setTitle("֧������");
	    dialogs.setCancelable(false);
	    dialogs.show();
	    dialogs.setOnPositiveListener(new OnClickListener() {
	        @Override
	        public void onClick(View v) {
	            //dosomething youself
	        	pswString=editText.getText().toString();
	        	if(pswString==null||pswString.equals("")){
	        		Toast.makeText(PayForStakeActivity.this, "����������", Toast.LENGTH_SHORT).show();
	        		}
	        	else{
	        		System.out.println(pswString+"***********");
	        		System.out.println(MoneyString+"++++++++++++");
	        		if(VaildatePsw(token, pswString)){
	        			Toast.makeText(PayForStakeActivity.this, "������ȷ", Toast.LENGTH_SHORT).show();
	        		 if(PayforMoney(token, user_record_id, startime, endtime, Moneycount)){
	        			 Message msg = new Message();
		                 msg.what = 4;
		                 mHandler.sendMessage(msg);
		                 return;
	        		 }
	        		 else{
	        			 Message msg = new Message();
		                 msg.what = 5;
		                 mHandler.sendMessage(msg);
		                 return;
	        		 }
	        		}
	        		else{
	        			Toast.makeText(PayForStakeActivity.this, "�����������", Toast.LENGTH_SHORT).show();
	        			return;
	        		}
	        	  
	        
	        	}
	           
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
    protected boolean PayforMoney(String token,String user_record_id,String startTime,String endTime,Double price){
  	   Map<String, String>map=new HashMap<String,String>();
  	   map.put("useStakeId",user_record_id);
  	   map.put("startTime", startTime);
  	   map.put("endTime", endTime);
  	   map.put("totalPrice", price+"");
  	   try {
  			  JSONObject jsonObject = new JSONObject(HttpUtil.putRequest(
  						"api/user/chargeRecord.servlet",map,token));
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
   
}
