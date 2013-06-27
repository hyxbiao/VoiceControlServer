package com.hyxbiao.voicecontrol.server;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

public class MainService extends Service {

	private final static String TAG = "MainService";
	
	private Thread mServerThread = null;
	@Override
	public IBinder onBind(Intent intent) {
		Log.d(TAG, "service onBind, intent: " + intent);
		return null;
	}
	
	@Override
	public void onCreate() {
		Log.d(TAG, "service onCreate");
		Context context = getApplicationContext();
		if(mServerThread == null) {
			Log.d(TAG, "thread is created");
			mServerThread = new VoiceControlServer(context);
			mServerThread.start();
		} else {
			Log.d(TAG, "thread is running");
		}
	}
	
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		if(mServerThread == null) {
			Log.d(TAG, "mServerThread is null");
		} else {
			Log.d(TAG, "mServerThread is running");
			boolean isBackGround = intent.getBooleanExtra("background", true);
		}
		return super.onStartCommand(intent, flags, startId);
	}
	
	@Override
	public void onDestroy() {
		if(mServerThread != null) {
			Thread tmpThread = mServerThread;
			mServerThread = null;
			tmpThread.interrupt();
		}
	}
}
