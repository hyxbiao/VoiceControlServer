package com.hyxbiao.voicecontrol.server;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

public class MainService extends Service {

	private final static String TAG = "MainService";
	
	private VoiceControlServer mVoiceControlServer;
	private Thread mHandle;
	@Override
	public IBinder onBind(Intent intent) {
		Log.d(TAG, "service onBind, intent: " + intent);
		return null;
	}
	
	@Override
	public void onCreate() {
		Log.d(TAG, "service onCreate");
		Context context = getApplicationContext();
		mVoiceControlServer = new VoiceControlServer(context);
		mHandle = new Thread(mVoiceControlServer);
		mHandle.start();
	}
	
	@Override
	public void onDestroy() {
		if(mHandle != null) {
			mHandle = null;
		}
	}
}
