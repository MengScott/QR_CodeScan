package com.example.qr_codescan;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.example.qr_codescan.adapter.UidShowAdapter;
import com.example.qr_codescan.data.DataManager;
import com.example.qr_codescan1.R;

public class MainActivity extends Activity {
	/**
	 * 显示扫描结果
	 */
	private ListView mListview;
	private UidShowAdapter mUidShowAdapter;
	private AlertDialog mAlertDialog;
	private AlertDialog.Builder mBuilder;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		mListview = (ListView) findViewById(R.id.uid_listview);
		mUidShowAdapter = new UidShowAdapter(this);
		mListview.setAdapter(mUidShowAdapter);

		// 打开二维码扫描
		findViewById(R.id.scan_btn).setOnClickListener(
				new android.view.View.OnClickListener() {
					@Override
					public void onClick(View v) {
						Intent intent = new Intent();
						intent.setClass(MainActivity.this,
								MipcaActivityCapture.class);
						intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
						startActivity(intent);
					}
				});

		mBuilder = new AlertDialog.Builder(this);
		mBuilder.setTitle("确定要删除文件吗？");
		mBuilder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
			}
		});
		mBuilder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				try {
					DataManager.getInstance(MainActivity.this).deleteFile();
					Toast.makeText(MainActivity.this, "删除成功", Toast.LENGTH_LONG)
							.show();
				} catch (Exception e) {
					Toast.makeText(MainActivity.this, "删除失败", Toast.LENGTH_LONG)
							.show();
					e.printStackTrace();
				}
			}
		});
		// 删除文件btn
		findViewById(R.id.delete_btn).setOnClickListener(
				new android.view.View.OnClickListener() {
					@Override
					public void onClick(View v) {
						mBuilder.show();
					}
				});
	}

}
