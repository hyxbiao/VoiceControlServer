package com.hyxbiao.voicecontrol.server;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;


public class LaunchServiceReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		Intent serviceIntent = new Intent(context, MainService.class);
		
		context.startService(serviceIntent);
	}

}
