package com.hyxbiao.voicecontrol.server;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;


public class LaunchServiceReceiver extends BroadcastReceiver {
	private final static String TAG = "LaunchServiceReceiver";

	@Override
	public void onReceive(Context context, Intent intent) {
		Log.d(TAG, "onReceive, intent: " + intent);
		Intent serviceIntent = new Intent(context, MainService.class);
		
		context.startService(serviceIntent);
	}

}
