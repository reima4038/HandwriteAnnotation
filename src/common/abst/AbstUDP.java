package common.abst;

import java.awt.Point;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import common.data.LineRecord;
import common.data.Prefs;
import common.util.Utl;

public abstract class AbstUDP implements Runnable, Prefs{
	
	//スレッドスリープ
	private static final int THREAD_SLEEP = 500;

	private int fps;
	
	protected byte[] buf;
	protected DatagramSocket socket;
	protected DatagramPacket recvPacket;
	
    public AbstUDP(int fps) {
        this.fps = fps;
        init();
    }
 
    public AbstUDP() {
        this(THREAD_SLEEP);
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
	 * ラインレコードをByteBufferに変換
	 * @param lr
	 * @return
	 */
	public ByteBuffer transLineRecordToByteBuffer(LineRecord lr){
		ByteBuffer bBuf = ByteBuffer.allocate(BUFSIZE);
		
		//バイトコードの記法を指定
		bBuf.order(ByteOrder.BIG_ENDIAN);
		
		//送信用データをバッファに設定
		bBuf.putInt(lr.getUserID());
		bBuf.putInt(lr.getColor());
		bBuf.putLong(lr.getClickTimeStamp());
		bBuf.putLong(lr.getReleaseTimeStamp());
		bBuf.putInt(lr.getRecord().size());
		for(int i = 0; i < lr.getRecord().size(); i++){
			bBuf.putInt(lr.getRecord().get(i).getLocation().x);
			bBuf.putInt(lr.getRecord().get(i).getLocation().y);
		}

		/**
		 * Debug
		 */
//		Utl.println("----------------------------------------------");
//		Utl.println("byteBuf Position" + bBuf.position());
//		Utl.println("byteBuf Limit" + bBuf.limit());
//		Utl.println("byteBuf Capacity" + bBuf.capacity());
//		Utl.println("----------------------------------------------");
		
		return bBuf;
	}
	
	/**
	 * パケットをラインレコードに変換
	 */
	public LineRecord transPacketToLineRecord(DatagramPacket recvPacket){
		Utl.dPrintln("受信したパケットをラインレコードに変換");
		
		LineRecord lr = new LineRecord();
		byte[] recvData = recvPacket.getData();
		ByteBuffer bBuf = ByteBuffer.wrap(recvData);

		// バイトコードの記法はビッグエンディアンに指定
		bBuf.order(ByteOrder.BIG_ENDIAN);

		// バッファからデータを取り出してLineRecordクラスのメンバに代入
		lr.setUserID(bBuf.getInt());
		lr.setColor(bBuf.getInt());
		lr.setClickTimeStamp(bBuf.getLong());
		lr.setReleaseTimeStamp(bBuf.getLong());

		int recordSize = bBuf.getInt();
		int x, y;
		for (int i = 0; i < recordSize; i++) {
			x = bBuf.getInt();
			y = bBuf.getInt();
			lr.getRecord().add(new Point(x, y));
		}
		
		/**
		 * Debug
		 */
//		Utl.println("----------------------------------------------");
//		Utl.println("byteBuf Position" + bBuf.position());
//		Utl.println("byteBuf Limit" + bBuf.limit());
//		Utl.println("byteBuf Capacity" + bBuf.capacity());
//		Utl.println("----------------------------------------------");
		
		return lr;
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
		while (true) {
			frameUpdate();
			try {
				Thread.sleep(THREAD_SLEEP);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

	}

}
