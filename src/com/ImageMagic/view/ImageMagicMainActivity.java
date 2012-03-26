package com.ImageMagic.view;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class ImageMagicMainActivity extends Activity {
    /** Called when the activity is first created. */
	Button takePhoto,localAlbum,networkAlubum;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        takePhoto = (Button)findViewById(R.id.btn1);
        localAlbum = (Button)findViewById(R.id.btn2);
        networkAlubum = (Button)findViewById(R.id.btn3);
        
        takePhoto.setOnClickListener(onClick);
        localAlbum.setOnClickListener(onClick);
        networkAlubum.setOnClickListener(onClick);
    }
    OnClickListener onClick = new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			switch (v.getId()) {
			case R.id.btn1:
					
				break;

			case R.id.btn2:
				Intent intent = new Intent();
				intent.setAction(Intent.ACTION_PICK);
				intent.setType("image/*");
				startActivityForResult(intent, 1);
				break;
			case R.id.btn3:
				
				break;
			}
		}
	};
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		
	};
    	
}