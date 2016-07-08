package com.example.everapp;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

public class UserGuideActivity extends Activity{
	private ImageButton reback_btn;
	private TextView titleText;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.user_guide);
		reback_btn=(ImageButton)findViewById(R.id.return_btn);
		reback_btn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				UserGuideActivity.this.finish();
			}
		});
		 titleText = (TextView) findViewById(R.id.title);
		 titleText.setText("²Ù×÷Ö¸Òý");
	}
}
