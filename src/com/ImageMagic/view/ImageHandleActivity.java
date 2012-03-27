package com.ImageMagic.view;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;

public class ImageHandleActivity extends Activity {
	private Handler mHandler;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.handle_main);
		
		mHandler = new Handler(){
			public void handleMessage(Message message) {
				
			}
		};
	}
}
