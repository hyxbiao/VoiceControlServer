package com.hyxbiao.voicecontrol.server.manager;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;

import com.hyxbiao.voicecontrol.protocol.Packet;

public class SystemManager extends Manager {
	
	private Context mContext;
	public SystemManager(Context context) {
		mContext = context;
	}
	
	@Override
	public String execute(int cmd, String params) {
		String result = null;
		switch(cmd) {
			case Packet.CMD_SYSTEM_OPEN_QQ:
				result = open(Packet.CMD_SYSTEM_OPEN_QQ);
				break;
			default:
				result = "unknown command";
				break;
		}
		return result;
	}
	
	private String open(int code) {
		String result = "open qq";
		Intent LaunchIntent = mContext.getPackageManager().getLaunchIntentForPackage("com.tencent.hd.qq");
//		Intent LaunchIntent = mContext.getPackageManager().getLaunchIntentForPackage("com.tencent.mobileqq");
		mContext.startActivity(LaunchIntent);
		return result;
	}
}
