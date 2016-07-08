package com.example.dialog;

import com.baidu.pano.platform.http.v;
import com.example.everapp.R;

import android.app.ActionBar.LayoutParams;
import android.app.Dialog;
import android.content.Context;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class CustomDialog extends Dialog{
	 private EditText editText,pswText;
	    private Button positiveButton, negativeButton;
	    private TextView title;
	 
	    public CustomDialog(Context context) {
	        super(context,R.style.Dialog);
	        setCustomDialog();
	    }
	    
	    private void setCustomDialog() {
	        View mView = LayoutInflater.from(getContext()).inflate(R.layout.money_dialog, null);
	        title = (TextView) mView.findViewById(R.id.money_dialog_title);
	        editText = (EditText) mView.findViewById(R.id.money_dialog_message);
	        pswText=(EditText)mView.findViewById(R.id.psw_dialog_message);
	        positiveButton = (Button) mView.findViewById(R.id.money_dialog_confirm);
	        negativeButton = (Button) mView.findViewById(R.id.money_dialog_back);
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
	    public View getEditText(){	
	        return editText;
	    }
	     public void setview(int i){
	    	 if(i==0){
	    		editText.setVisibility(View.VISIBLE); 
	    		pswText.setVisibility(View.GONE);
	    	 }
	    	 if(i==1){
	    		 editText.setVisibility(View.GONE);
	    		 pswText.setVisibility(View.VISIBLE);
	    	 }
	     }
	     @Override
	    public void setContentView(int layoutResID) {
	    }
	 
	   
	 
	    @Override
	    public void setContentView(View view) {
	    	
	    }
	 
	    /**
	     * È·¶¨¼ü¼àÌýÆ÷
	     * @param listener
	     */ 
	    public void setOnPositiveListener(View.OnClickListener listener){ 
	        positiveButton.setOnClickListener(listener); 
	    } 
	    /**
	     * È¡Ïû¼ü¼àÌýÆ÷
	     * @param listener
	     */ 
	    public void setOnNegativeListener(View.OnClickListener listener){ 
	        negativeButton.setOnClickListener(listener); 
	    }
	    
}
