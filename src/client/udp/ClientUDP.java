package client.udp;

import java.awt.Point;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import common.abst.AbstUDP;
import common.data.LineRecord;
import common.data.Prefs;
import common.data.SessionStatus;
import common.util.Utl;

public class ClientUDP extends AbstUDP{

	private static final ClientUDP cUDP = new ClientUDP();
	
	private ClientUDP() {
		super();
	}
	
	/**
	 * パケット送信
	 */
	public void sendPacket(LineRecord lr) {
		try {
			socket.send(recordToSendPacket(lr));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * パケット受信
	 */
	public void receivePacket() {
		LineRecord lr = null;
		try {
			socket.receive(recvPacket);
			lr = this.recordFromRecvPacket(recvPacket);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		//ラインレコードをセッションステータスの受信したレコードに反映
		SessionStatus.getInstance().setReceivedLineRecord(lr);
		
	}
	
	@Override
	protected void frameUpdate() {
		//パケットの受信待機
		receivePacket();		
	}
	
	public static ClientUDP getInstance() {
		return cUDP;
	}

	
	/**
	 * ラインレコードを送信用パケットに変換
	 * @return
	 */
	public DatagramPacket recordToSendPacket(LineRecord lr){
		DatagramPacket sendPacket;
		byte[] sendData;
		ByteBuffer bBuf = ByteBuffer.allocate(8192);
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
		
		//送信用データを用意
		sendData = new byte[bBuf.position()];
		System.arraycopy(bBuf.array(), 0, sendData, 0, sendData.length);
		
		//サーバーのアドレスをセッションステータスから取得
		InetAddress ip = SessionStatus.getInstance().getSInetAddress();
		//送信用データ、アドレス、ポートの情報を元に送信用パケットを用意
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
		//バイトコードの記法はビッグエンディアンに指定
		bBuf.order(ByteOrder.BIG_ENDIAN);
		
		//バッファからデータを取り出してLineRecordクラスのメンバに代入
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

	public byte[] getBuf() {
		return buf;
	}

	public void setBuf(byte[] buf) {
		this.buf = buf;
	}

	public void setRecvPacket(DatagramPacket recvPacket) {
		this.recvPacket = recvPacket;
	}
	
}
