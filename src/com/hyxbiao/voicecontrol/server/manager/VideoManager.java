package com.hyxbiao.voicecontrol.server.manager;

import android.util.Log;

import com.hyxbiao.voicecontrol.protocol.Packet;

public class VideoManager extends Manager {
	private final static String TAG = "Server.VideoManager";

	public VideoManager() {
		
	}
	
	@Override
	public String execute(int cmd, String params) {
		String result = null;
		switch(cmd) {
			case Packet.CMD_VIDEO_PLAY:
				result = play(params);
				break;
			default:
				result = "unknown command";
				break;
		}
		return result;
	}
	
	public String play(String params) {
		String result = "video play";
		if(params == null || params.isEmpty()) {
		}
		Log.d(TAG, "video play");
		return result;
	}
}
