package com.hyxbiao.voicecontrol.server;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;

import com.hyxbiao.voicecontrol.protocol.Packet;
import com.hyxbiao.voicecontrol.server.manager.Manager;
import com.hyxbiao.voicecontrol.server.manager.SystemManager;
import com.hyxbiao.voicecontrol.server.manager.VideoManager;

import android.content.Context;
import android.util.Log;

public class VoiceControlServer implements Runnable {
	private final static String TAG = "VoiceControlServer";

	private final int mPort = 8300;
	private ServerSocket mSocket;
	
	private Context mContext;
	public VoiceControlServer(Context context) {
		mContext = context;
	}

	@Override
	public void run() {
		try {
			mSocket = new ServerSocket(mPort);

			while(true) {
				Log.d(TAG, "start accept");
	
				Socket clientSocket = mSocket.accept();
	
				Log.d(TAG, "accept new client connection");
	
				Worker worker = new Worker(clientSocket);
				Thread clientThread = new Thread(worker);
				clientThread.start();
			}

		} catch (IOException e) {
			e.printStackTrace();
			Log.e(TAG, e.getMessage());
		}
		Log.d(TAG, "server exit");
	}
	
	private class Worker implements Runnable {
		private int READ_TIMEOUT = 10000;
		private Socket mSock;
		
		@SuppressWarnings("unused")
		public Worker(Socket sock) {
			mSock = sock;
			try {
				mSock.setSoTimeout(READ_TIMEOUT);
			} catch (SocketException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		@Override
		public void run() {
			try {
				DataInputStream in = new DataInputStream(mSock.getInputStream());
				int version = in.readInt();
				Log.d(TAG, "client version: " + version + ", server version: " + Packet.VERSION);
				
				int target = in.readInt();
				if(target != Packet.TARGET_TPMINI) {
					Log.e(TAG, "target is not match");
				}
				
				int type = in.readInt();
				int cmd = in.readInt();
				//remain
				in.readInt();
				
				String params = null;
				int bodyLen = (int) in.readLong();
				if(bodyLen > 0) {
					byte[] buf = new byte[bodyLen];
					in.read(buf, Packet.HEAD_LEN, bodyLen);
					params = new String(buf);
				}
				Log.d(TAG, "type: " + type + ", cmd: " + cmd + ", body_len: " + bodyLen);
				
				//manager execute
				Manager manager = null;
				switch(type) {
					case Packet.TYPE_SYSTEM:
						manager = new SystemManager(mContext);
						break;
					case Packet.TYPE_VIDEO:
						manager = new VideoManager();
						break;
					default:
						break;
				}
				String result = null;
				if(manager != null) {
					result = manager.execute(cmd, params);
				}
				
				if(result == null) {
					result = "Error";
				}
				//response
				PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(mSock.getOutputStream())));
				out.println(result);
				out.flush();

			} catch (IOException e) {
				e.printStackTrace();
				Log.w(TAG, "IOException: " + e);
			}
		}
		
	}
}
