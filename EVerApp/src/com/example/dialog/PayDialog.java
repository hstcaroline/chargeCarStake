package com.example.dialog;




import com.example.everapp.R;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;



public class PayDialog extends Dialog{
		 private EditText pswText;
		    private Button positiveButton;
		    private TextView title;
		 
		    public PayDialog(Context context) {
		        super(context,R.style.Dialog);
		        setCustomDialog();
		    }
		    
		    private void setCustomDialog() {
		        View mView = LayoutInflater.from(getContext()).inflate(R.layout.moneytest, null);
		        title = (TextView) mView.findViewById(R.id.pay_dialog_title);//����
		        pswText=(EditText)mView.findViewById(R.id.psw_pay_edit);//����
		        positiveButton = (Button) mView.findViewById(R.id.pay_dialog_confirm);//ȷ�ϰ�ť
		        super.setContentView(mView);
		    }
		     
		    public void setTitle(String title)
		    {
		    	this.title.setText(title);
		    }
		    public void setbutton(String conString){
		    	this.positiveButton.setText(conString);
		    }
		    public View getpswText(){
		    	return pswText;
		    }
		   
		    
		     @Override
		    public void setContentView(int layoutResID) {
		    }
		 
		   
		 
		    @Override
		    public void setContentView(View view) {
		    	
		    }
		 
		    /**
		     * ȷ����������
		     * @param listener
		     */ 
		    public void setOnPositiveListener(View.OnClickListener listener){ 
		        positiveButton.setOnClickListener(listener); 
		    } 
		    /**
		     * ȡ����������
		     * @param listener
		     */ 
		   /* public void setOnNegativeListener(View.OnClickListener listener){ 
		        negativeButton.setOnClickListener(listener); 
		    }*/
		    
	
}
