package com.hyxbiao.voicecontrol.server.manager;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;

public class UiControl {

	private final static String TAG = "UiControl";
	
	public static String QQ_OPEN_VIDEO = "com.hyxbiao.example.LaunchSettings#testOpen";
	public static String QQ_CLOSE_VIDEO = "com.hyxbiao.example.LaunchSettings#testClose";
	public static String QQ_SCREEN_MAX = "com.hyxbiao.example.LaunchSettings#testScreenMax";
	public static String QQ_SCREEN_MIN = "com.hyxbiao.example.LaunchSettings#testScreenMin";
	
	public static String SYSTEM_HOME = "com.hyxbiao.example.LaunchSettings#testExit";
	
	public static String exec(Context context, String name, Bundle bundle) {
		String outDir = context.getFilesDir().getAbsolutePath();
		String fileName = "uiautoTest.jar";
		File outFile = new File(outDir, fileName);
		StringBuilder sb = new StringBuilder();
		if(bundle != null) {
			for(String key : bundle.keySet()) {
				sb.append(String.format(" -e %s %s", key, bundle.get(key).toString()));
			}
		}
		String command = String.format("/system/bin/uiautomator runtest %s -c %s %s", outFile.getAbsolutePath(), name, sb.toString());
		Log.d(TAG, "command: " + command);
		try {
			Process process = Runtime.getRuntime().exec("su");
			BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
			BufferedReader errReader = new BufferedReader(new InputStreamReader(process.getErrorStream()));
			
			DataOutputStream outputStream = new DataOutputStream(process.getOutputStream());
   			outputStream.writeBytes(command + "\n");
   			outputStream.flush();
			
   			outputStream.writeBytes("exit\n");
   			outputStream.flush();
			
			StringBuffer out = new StringBuffer();
			int ret;
			char[] buffer = new char[1024];
			while((ret = reader.read(buffer)) > 0) {
				out.append(buffer, 0, ret);
			}
			reader.close();
			
			while((ret = errReader.read(buffer)) > 0) {
				out.append(buffer, 0, ret);
			}
			errReader.close();
			
			process.waitFor();
			
			return out.toString();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Log.d(TAG, "io error: ", e);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Log.d(TAG, "InterruptedException: ", e);
		}
		return null;
	}
}
