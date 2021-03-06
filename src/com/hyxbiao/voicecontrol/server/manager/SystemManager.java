package com.hyxbiao.voicecontrol.server.manager;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Messenger;
import android.util.Log;
import android.view.KeyEvent;

import com.hyxbiao.voicecontrol.protocol.Packet;

public class SystemManager extends Manager {
	
	private final static String TAG = "SystemManager";
	
	private Context mContext;
	private int mTarget;
	private Messenger mMessenger = null;
	
	public SystemManager(Context context, int target, Messenger messenger) {
		mContext = context;
		mTarget = target;
		mMessenger = messenger;
	}
	
	@Override
	public String execute(int cmd, String params) {
		String result = null;
		switch(cmd) {
			case Packet.CMD_SYSTEM_OPEN_QQ:
				result = open(Packet.CMD_SYSTEM_OPEN_QQ);
				break;
			case Packet.CMD_SYSTEM_HOME:
				UiControl.execRootCommand("input keyevent " + KeyEvent.KEYCODE_HOME);
				break;
			case Packet.CMD_SYSTEM_BACK:
				UiControl.execRootCommand("input keyevent " + KeyEvent.KEYCODE_BACK);
				break;
			case Packet.CMD_SYSTEM_VOLUME_UP:
				UiControl.execRootCommand("input keyevent " + KeyEvent.KEYCODE_VOLUME_UP);
				break;
			case Packet.CMD_SYSTEM_VOLUME_DOWN:
				UiControl.execRootCommand("input keyevent " + KeyEvent.KEYCODE_VOLUME_DOWN);
				break;
			default:
				result = "unknown command";
				break;
		}
		return result;
	}
	
	private String open(int code) {
		String result = "open qq";
		String packageName = "com.tencent.mobileqq";
		if(mTarget == Packet.TARGET_TPMINI) {
			packageName = "com.tencent.hd.qq";
		}
		Intent LaunchIntent = mContext.getPackageManager().getLaunchIntentForPackage(packageName);
		mContext.startActivity(LaunchIntent);
		
//		String output = UiControl.exec(mContext, UiControl.QQ_OPEN_VIDEO, null);
//		Log.d(TAG, "command output: " + output);
		
		return result;
	}
}
