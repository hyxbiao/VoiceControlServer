package com.hyxbiao.voicecontrol.server.manager;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Messenger;
import android.util.Log;

import com.hyxbiao.voicecontrol.protocol.Packet;

public class QQManager extends Manager {
	
	private final static String TAG = "QQManager";
	
	private Context mContext;
	private int mTarget;
	private Messenger mMessenger = null;
	
	public QQManager(Context context, int target, Messenger messenger) {
		mContext = context;
		mTarget = target;
		mMessenger = messenger;
	}
	
	@Override
	public String execute(int cmd, String params) {
		String result = null;
		switch(cmd) {
			case Packet.CMD_QQ_VIDEO_BAOBAO:
			case Packet.CMD_QQ_VIDEO_TEST:
				result = openVideo(cmd);
				break;
			case Packet.CMD_QQ_VIDEO_SCREEN_MAX:
				UiControl.exec(mContext, UiControl.QQ_SCREEN_MAX, null);
				break;
			case Packet.CMD_QQ_VIDEO_SCREEN_MIN:
				UiControl.exec(mContext, UiControl.QQ_SCREEN_MIN, null);
				break;
			case Packet.CMD_QQ_VIDEO_CLOSE:
				UiControl.exec(mContext, UiControl.QQ_CLOSE_VIDEO, null);
				break;
			default:
				result = "unknown command";
				break;
		}
		return result;
	}
	
	private String openVideo(int code) {
		String result = "open qq video";
		
		Bundle bundle = null;
		if(code == Packet.CMD_QQ_VIDEO_BAOBAO) {
			bundle = new Bundle();
			bundle.putString("search", "tieshuyi");
			bundle.putString("name", "铁树一叶");
		}
		String output = UiControl.exec(mContext, UiControl.QQ_OPEN_VIDEO, bundle);
		Log.d(TAG, "command output: " + output);
		
		return result;
	}
}
