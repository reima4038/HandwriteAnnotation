package client.udp;

import java.net.DatagramSocket;
import java.net.SocketException;

import common.data.Prefs;
import common.util.Utl;

public class ClientUDP implements Runnable, Prefs{

	private static final int DEFAULT_FPS = 1500;
	private static final ClientUDP cUDP = new ClientUDP();
	
	private DatagramSocket socket;

	private int fps;

	public ClientUDP(int fps) {
		this.fps = fps;
		
		try {
			socket = new DatagramSocket(DEFAULT_PORT);
		} catch (SocketException e) {
			Utl.printlnErr("ソケットの開放に失敗しました.");
			e.printStackTrace();
		}
	}

	public ClientUDP() {
		this(DEFAULT_FPS);
	}

	public void sendPacket() {
		
	}

	public void ReceivePacket() {

	}

	/**
	 * ソケットを閉じる
	 */
	public void close() {
		socket.close();
	}

	public static ClientUDP getInstance() {
		return cUDP;
	}

	@Override
	public void run() {
		long lasttime = System.currentTimeMillis();
		while (true) {
			long nowtime = System.currentTimeMillis();
			if (lasttime - nowtime < 1000 / fps) {
				try {
					// 割り算の誤差などいろいろ正確ではない
					Thread.sleep(1000 / fps - (lasttime - nowtime));
				} catch (InterruptedException e) {
				}
			} else {
				Thread.yield();
			}
			lasttime = nowtime;
		}

	}
	
	/*
	 * getter, setter
	 */
	
	public DatagramSocket getSocket(){
		return socket;
	}
	
}
