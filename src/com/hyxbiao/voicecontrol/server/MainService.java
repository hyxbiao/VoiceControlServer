package com.hyxbiao.voicecontrol.server;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

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
	
	private void init(Context context) {
		String outDir = context.getFilesDir().getAbsolutePath();
		String fileName = "uiautoTest.jar";
		File outFile = new File(outDir, fileName);
		Log.d(TAG, "outfile name: " + outFile.getAbsolutePath());
		
		try {
			
			OutputStream out = new BufferedOutputStream(new FileOutputStream(outFile));
			InputStream in = context.getAssets().open(fileName);
			byte[] buffer = new byte[1024];
			int ret;
			while((ret = in.read(buffer)) != -1) {
				out.write(buffer, 0, ret);
			}
			out.flush();
			in.close();
			out.close();
			
			Runtime.getRuntime().exec("chmod 0644 " + outFile.getAbsolutePath());
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Log.d(TAG, "write file error", e);
		}
	}
	@Override
	public void onCreate() {
		Log.d(TAG, "service onCreate");
		Context context = getApplicationContext();
		
		//init for copy assets
		init(context);
		
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
