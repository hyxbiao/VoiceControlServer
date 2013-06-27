package com.hyxbiao.voicecontrol.server;

import com.hyxbiao.voicecontrol.server.R;

import android.os.Bundle;
import android.os.Handler;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

public class MainActivity extends Activity implements OnClickListener{
	private final static String TAG = "MainActivity";
	
	TextView mResultTextView;
	
	private Handler mHandle = new Handler();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		mResultTextView = (TextView) this.findViewById(R.id.result);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public void onClick(View v) {
		int id = v.getId();
		switch(id) {
			case R.id.start:
				Log.d(TAG, "click button start");
				Intent serviceIntent = new Intent(this, MainService.class);
				serviceIntent.putExtra("background", false);
				startService(serviceIntent);
				updateText("start server");
				break;
			default:
				break;
		}
	}

	public void updateText(String msg) {
		mResultTextView.setText(mResultTextView.getText() + "\n" + msg);
	}
}
