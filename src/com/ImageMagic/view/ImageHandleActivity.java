package com.ImageMagic.view;

import java.io.FileNotFoundException;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.view.Display;
import android.widget.ImageView;

public class ImageHandleActivity extends Activity {
	private Handler mHandler;
	private ImageView ImageV;
	Bitmap mBitmap;
	public static final String IMAGE_UNSPECIFIED = "image/*";
	public static final int IMAGECUT = 1;
	public Uri uri;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.handle_main);

		ImageV = (ImageView) findViewById(R.id.imageview);
		Intent intent = getIntent();
		String path = intent.getStringExtra("path");
		Display display = this.getWindowManager().getDefaultDisplay();

		BitmapFactory.Options options = new BitmapFactory.Options();
		mBitmap = BitmapFactory.decodeFile(path, options);
		uri = Uri.parse(MediaStore.Images.Media.insertImage(
				getContentResolver(), mBitmap, null, null));
		imageCut(uri);
		System.out.println(mBitmap);
		ImageV.setImageBitmap(mBitmap);

		mHandler = new Handler() {
			public void handleMessage(Message message) {

			}
		};
	}

	public void imageCut(Uri uri) {
		Intent intent = new Intent("com.android.camera.action.CROP");
		intent.setDataAndType(uri, IMAGE_UNSPECIFIED);
		intent.putExtra("crop", "true");
		// aspectX aspectY 是宽高的比例
		intent.putExtra("aspectX", 1);
		intent.putExtra("aspectY", 1.5);
		// outputX outputY 是裁剪图片宽高
		intent.putExtra("outputX", 450);
		intent.putExtra("outputY", 600);
		intent.putExtra("return-data", true);
		startActivityForResult(intent, IMAGECUT);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode == RESULT_OK && data != null) {
			switch (requestCode) {
			case IMAGECUT:

				try {
					Uri uri = null;
					Bitmap map1 = data.getParcelableExtra("data");
					uri = Uri.parse(MediaStore.Images.Media.insertImage(
							getContentResolver(), map1, null, null));
					int dw = this.getWindowManager().getDefaultDisplay()
							.getWidth();
					int dh = this.getWindowManager().getDefaultDisplay()
							.getHeight();
					BitmapFactory.Options op = new BitmapFactory.Options();
					op.inJustDecodeBounds = true;
					Bitmap pic;
					pic = BitmapFactory.decodeStream(this.getContentResolver()
							.openInputStream(uri), null, op);
					int wRatio = (int) Math.ceil(op.outWidth / (float) dw); // 计算宽度比例
					int hRatio = (int) Math.ceil(op.outHeight / (float) dh); //
					// 计算高度比例

					if (wRatio > 1 && hRatio > 1) {
						if (wRatio > hRatio) {
							op.inSampleSize = wRatio;
						} else {
							op.inSampleSize = hRatio;
						}
					}
					op.inJustDecodeBounds = false;
					pic = BitmapFactory.decodeStream(this.getContentResolver()
							.openInputStream(uri), null, op);

					ImageV.setImageBitmap(map1);
				} catch (FileNotFoundException e) {

					e.printStackTrace();
				}

				break;
			default:
				break;
			}
		}
		super.onActivityResult(requestCode, resultCode, data);
	}
}
