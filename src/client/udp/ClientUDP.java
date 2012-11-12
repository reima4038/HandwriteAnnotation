package client.udp;

import java.awt.Point;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import common.data.LineRecord;
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

	/**
	 * パケット送信
	 */
	public void sendPacket() {
	}

	/**
	 * パケット受信
	 */
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
	
	/**
	 * ラインレコードを送信用パケットに変換
	 * @return
	 */
	public DatagramPacket recordToSendPacket(LineRecord lr){
		DatagramPacket sendPacket;
		byte[] sendData;
		ByteBuffer bBuf = ByteBuffer.allocate(8192);
		bBuf.order(ByteOrder.BIG_ENDIAN);
		
		bBuf.putInt(lr.getUserID());
		bBuf.putInt(lr.getColor());
		bBuf.putLong(lr.getClickTimeStamp());
		bBuf.putLong(lr.getReleaseTimeStamp());
		bBuf.putInt(lr.getRecord().size());
		for(int i = 0; i < lr.getRecord().size(); i++){
			bBuf.putInt(lr.getRecord().get(i).getLocation().x);
			bBuf.putInt(lr.getRecord().get(i).getLocation().y);
		}
		
		sendData = new byte[bBuf.position()];
		System.arraycopy(bBuf.array(), 0, sendData, 0, sendData.length);
		
		InetAddress ip = SessionStatus.getInstance().getSInetAddress();
		sendPacket = new DatagramPacket(sendData, sendData.length, ip, Prefs.DEFAULT_PORT);
		
		return sendPacket;
	}
	
	/**
	 * 受信したパケットをラインレコードに変換
	 * @return
	 */
	public LineRecord recordFromRecvPacket(DatagramPacket recvPacket){
		LineRecord lr = new LineRecord();
		byte[] recvData = recvPacket.getData();
		ByteBuffer bBuf = ByteBuffer.wrap(recvData);
		bBuf.order(ByteOrder.BIG_ENDIAN);
		
		lr.setUserID(bBuf.getInt());
		lr.setColor(bBuf.getInt());
		lr.setClickTimeStamp(bBuf.getLong());
		lr.setReleaseTimeStamp(bBuf.getLong());
		
		int recordSize = bBuf.getInt();
		int x, y;
		for(int i = 0; i < recordSize; i++){
			x = bBuf.getInt();
			y = bBuf.getInt();
			lr.getRecord().add(new Point(x, y));
		}
		
		return lr;
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
