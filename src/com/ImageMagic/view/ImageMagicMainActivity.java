package com.ImageMagic.view;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
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
	private static final int PHOTOGRAPH = 2;
	public static final String IMAGE_UNSPECIFIED = "image/*";

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
				Intent intent_potograph = new Intent(
						MediaStore.ACTION_IMAGE_CAPTURE);
				ContentValues values = new ContentValues(); 
				values.put(MediaStore.Images.Media.DISPLAY_NAME, "testing"); 
				startActivityForResult(intent_potograph, PHOTOGRAPH);
				break;

			case R.id.btn2:
				Intent intent = new Intent();
				intent.setAction(intent.ACTION_GET_CONTENT);
				intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
						IMAGE_UNSPECIFIED);
				startActivityForResult(intent, FLAG_CHOOSE);
				break;
			case R.id.btn3:

				break;
			}
		}
	};

	protected void onActivityResult(int requestCode, int resultCode, Intent data) {

		System.out.println("requestCode:" + requestCode + "resultCode:"
				+ resultCode + "data:" + data);
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode == RESULT_OK && data != null) {
			switch (requestCode) {

			case FLAG_CHOOSE:
				Uri uri = data.getData();
				Log.d("LLH===>",
						"uri=" + uri + ",authority=" + uri.getAuthority() + " "
								+ uri.getPath());
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
					Log.d("LLH===>", path + "  ");
					Intent intent = new Intent(this, ImageHandleActivity.class);
					intent.putExtra("path", path);
					Toast.makeText(this, path, Toast.LENGTH_LONG).show();
					startActivity(intent);
				} else {
					Intent intent = new Intent(this, ImageHandleActivity.class);
					intent.putExtra("path", uri.getPath());
					Toast.makeText(this, uri.getPath(), Toast.LENGTH_LONG)
							.show();
					startActivity(intent);
				}
				break;

			case PHOTOGRAPH:
				
				Bitmap photo = data.getParcelableExtra("data");
				Uri uriP = Uri.parse(MediaStore.Images.Media.insertImage(
						getContentResolver(), photo, null, null));
				
				String[] projection = { MediaStore.Images.Media.DATA };
				Cursor ActualImageCursor = managedQuery(uriP, projection, null,
						null, null);
				
				int column_index = ActualImageCursor
						.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
				
				ActualImageCursor.moveToFirst();
				String path = ActualImageCursor.getString(column_index);
				
				Intent intent = new Intent(ImageMagicMainActivity.this,
						ImageHandleActivity.class);
				
				intent.putExtra("path", path);
				startActivity(intent);
				break;
			}
		}
	}
}