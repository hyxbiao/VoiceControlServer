package com.hyxbiao.voicecontrol.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;

import android.util.Log;

public class VoiceControlServer implements Runnable {
	private final static String TAG = "VoiceControlServer";

	private final int mPort = 8300;
	private ServerSocket mSocket;
	
	public VoiceControlServer() {
	}

	@Override
	public void run() {
		while(true) {
			try {
				mSocket = new ServerSocket(mPort);
				
				Socket clientSocket = mSocket.accept();
				
				Log.d(TAG, "accept new client connection");
				
				Worker worker = new Worker(clientSocket);
				Thread clientThread = new Thread(worker);
				clientThread.start();
				
			} catch (IOException e) {
				e.printStackTrace();
				Log.e(TAG, e.getMessage());
				break;
			}
		}
		Log.d(TAG, "server exit");
	}
	
	private class Worker implements Runnable {
		private Socket mSock;
		
		@SuppressWarnings("unused")
		public Worker(Socket sock) {
			mSock = sock;
		}
		@Override
		public void run() {
			try {
				BufferedReader in = new BufferedReader(new InputStreamReader(mSock.getInputStream()));
				String msg = in.readLine();
				Log.d(TAG, "receive content: " + msg);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
	}
}
