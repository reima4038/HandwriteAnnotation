package client.udp;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

import common.data.Prefs;
import common.data.SessionStatus;
import common.util.Utl;

public class ClientUDP implements Runnable, Prefs{

	private static final int DEFAULT_FPS = 1500;
	public static final int BUFSIZE = 1024;
	private static final ClientUDP cUDP = new ClientUDP();
	
	private int fps;

	private byte[] buf;
	private DatagramSocket socket;
	private DatagramPacket recvPacket;
	private DatagramPacket sendPacket;

	public ClientUDP(int fps) {
		this.fps = fps;
		init();
	}

	public ClientUDP() {
		this(DEFAULT_FPS);
		
	}
	
	/**
	 * 初期化処理
	 */
	public void init(){
		buf = new byte[BUFSIZE];
		
		try {
			socket = new DatagramSocket(DEFAULT_PORT);
			recvPacket = new DatagramPacket(buf, BUFSIZE);
			sendPacket = null;
		} catch (SocketException e) {
			Utl.printlnErr("ソケットの開放に失敗しました.");
			e.printStackTrace();
		}
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

	public DatagramPacket getRecvPacket() {
		return recvPacket;
	}

	public DatagramPacket getSendPacket() {
		return sendPacket;
	}

	public byte[] getBuf() {
		return buf;
	}

	public void setBuf(byte[] buf) {
		this.buf = buf;
	}

	public void setRecvPacket(DatagramPacket recvPacket) {
		this.recvPacket = recvPacket;
	}

	public void setSendPacket(DatagramPacket sendPacket) {
		this.sendPacket = sendPacket;
	}
	
}
