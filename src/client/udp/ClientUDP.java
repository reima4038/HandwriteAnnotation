package client.udp;

import java.awt.Point;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.nio.BufferUnderflowException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import common.abst.AbstUDP;
import common.data.LineRecord;
import common.data.Prefs;
import common.data.SessionStatus;
import common.util.Utl;

public class ClientUDP extends AbstUDP {

	private static ClientUDP cUDP;

	private ClientUDP() {
		super();
	}

	/**
	 * パケット送信
	 */
	public void sendPacket(LineRecord lr) {
		Utl.dPrintln("SendPacket");
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
			if (socket == null){
				Utl.println("socket is null");
			}
			if (recvPacket == null){
				Utl.println("recvPacket is null");
			}
			Utl.dPrintln("パケット受信待機");
			socket.receive(recvPacket);
			Utl.dPrintln("パケット受信");
			
			//受信したパケットをラインレコードに変換
			lr = transPacketToLineRecord(recvPacket);
			lr.show();

			// 受信したラインレコードをセッションステータスに反映
//			SessionStatus.getInstance().setReceivedLineRecord(lr);　　//直接LineRecordsに入れればReceivedLineRecordは必要ないかも……
			Utl.dPrintln("受信したラインレコードをセッションステータスに反映");
			SessionStatus.getInstance().getLineRecords().add(lr);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	protected void frameUpdate() {
		// パケットの受信待機
		receivePacket();
	}

	public static ClientUDP getInstance() {
		if(cUDP == null){
			cUDP = new ClientUDP();
		}
		return cUDP;
	}

	/**
	 * ラインレコードを送信用パケットに変換
	 * 
	 * @return
	 */
	public DatagramPacket recordToSendPacket(LineRecord lr){
		DatagramPacket sendPacket;
		byte[] sendData;
		//ラインレコードをByteBuffer型に変換
		ByteBuffer bBuf = transLineRecordToByteBuffer(lr);
		
		//送信用データを用意
		sendData = new byte[bBuf.position()];
		System.arraycopy(bBuf.array(), 0, sendData, 0, sendData.length);
		
		//サーバーのアドレスをセッションステータスから取得
		Utl.dPrintln("PacketPreparating.Sending to: " + SessionStatus.getInstance().getSInetAddress());
		InetAddress ip = SessionStatus.getInstance().getSInetAddress();
		
		//送信用データ、アドレス、ポートの情報を元に送信用パケットを用意
		sendPacket = new DatagramPacket(sendData, sendData.length, ip, Prefs.DEFAULT_PORT);
		
		return sendPacket;
	}


	/*
	 * getter, setter
	 */

	public DatagramSocket getSocket() {
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
