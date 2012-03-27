package com.ImageMagic.view;

import org.w3c.dom.Text;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.MediaStore.Audio.Media;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class ImageMagicMainActivity extends Activity {
	/** Called when the activity is first created. */
	private Button takePhoto, localAlbum, networkAlubum;
	private static final int FLAG_CHOOSE = 1;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		takePhoto = (Button) findViewById(R.id.btn1);
		localAlbum = (Button) findViewById(R.id.btn2);
		networkAlubum = (Button) findViewById(R.id.btn3);

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
				intent.setAction(intent.ACTION_PICK);
				intent.setType("image/*");
				startActivityForResult(intent, FLAG_CHOOSE);
				break;
			case R.id.btn3:

				break;
			}
		}
	};

	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode == RESULT_OK && data != null) {
			switch (requestCode) {
			case FLAG_CHOOSE:
				Uri uri = data.getData();
				Log.d("LLH===>",
						"uri=" + uri + ",authority=" + uri.getAuthority()+" "+uri.getPath());
				if (!TextUtils.isEmpty(uri.getAuthority())) {
					Cursor cursor = getContentResolver().query(uri,
							new String[] { MediaStore.Images.Media.DATA },
							null, null, null);
					Log.d("LLH===>", MediaStore.Images.Media.DATA);
					if (cursor == null) {
						Toast.makeText(this, R.string.no_found,
								Toast.LENGTH_SHORT).show();
						return;
					}
					cursor.moveToFirst();
					String path = cursor.getString(cursor
							.getColumnIndex(MediaStore.Images.Media.DATA));
					Log.d("LLH===>", path+"  ");
					Intent intent = new Intent(this,ImageHandleActivity.class);
					intent.putExtra("path", path);
					startActivity(intent);
				}else{
					Intent intent = new Intent(this,ImageHandleActivity.class);
					intent.putExtra("path", uri.getPath());
					startActivity(intent);
				}
				break;
			}
		}
	};

}