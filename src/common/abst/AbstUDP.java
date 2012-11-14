package common.abst;

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

public abstract class AbstUDP implements Runnable, Prefs{
	
	// 負荷テストしたい.2000~でほぼ遅延なし
	private static final int DEFAULT_FPS = 1500;
	public static final int BUFSIZE = 1024;

	private int fps;
	
	protected byte[] buf;
	protected DatagramSocket socket;
	protected DatagramPacket recvPacket;
	
    public AbstUDP(int fps) {
        this.fps = fps;
        init();
    }
 
    public AbstUDP() {
        this(DEFAULT_FPS);
    }

	public void init(){
		buf = new byte[BUFSIZE];
		try {
			socket = new DatagramSocket(DEFAULT_PORT);
			recvPacket = new DatagramPacket(buf, BUFSIZE);
		} catch (SocketException e) {
			Utl.printlnErr("ソケットの開放に失敗しました.");
			e.printStackTrace();
		}
	}

	/**
	 * ソケットを閉じる
	 */
	public void close(){
		socket.close();
	}

	/**
	 * パケット送信
	 */
	public abstract void sendPacket(LineRecord lr);

	/**
	 * パケット受信
	 */
	public abstract void receivePacket();
	
	/**
	 * スレッドのループごとに呼ばれる処理関数
	 */
	protected abstract void frameUpdate();

	
	public void run() {
		long lasttime = System.currentTimeMillis();
		while (true) {
			
			frameUpdate();
			
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

}
