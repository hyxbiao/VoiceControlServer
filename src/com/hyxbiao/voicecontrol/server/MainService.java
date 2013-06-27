package com.hyxbiao.voicecontrol.server;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Messenger;
import android.util.Log;

public class MainService extends Service {

	private final static String TAG = "MainService";
	
	private VoiceControlServer mServerThread = null;
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
			Bundle bundle = intent.getExtras();
			if(bundle != null) {
				Messenger messenger = (Messenger) intent.getExtras().get("handler");
				mServerThread.setMessenger(messenger);
			}
			//Handler handler = new Handler(Looper.getMainLooper());
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
