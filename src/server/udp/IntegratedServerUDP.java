package server.udp;

import java.awt.Point;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import common.abst.AbstUDP;
import common.data.LineRecord;
import common.data.Prefs;
import common.data.SessionStatus;
import common.util.Utl;

public class IntegratedServerUDP extends AbstUDP {

	private static final IntegratedServerUDP isUDP = new IntegratedServerUDP();

	// クライアントのアドレス
	private InetAddress clientAddress;

	private IntegratedServerUDP() {
		super();
		clientAddress = null;
	}

	public static IntegratedServerUDP getInstance() {
		return isUDP;
	}

	@Override
	public void sendPacket(LineRecord lr) {
		try {
			socket.send(recordToSendPacket(lr));
			Utl.dPrintln("IP:" + clientAddress.getHostAddress() + " にパケット送信");

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
			if (socket == null) {
				Utl.println("socket is null");
			}
			if (recvPacket == null) {
				Utl.println("recvPacket is null");
			}
			Utl.dPrintln("パケット受信待機中");
			socket.receive(recvPacket);
			Utl.dPrintln("パケット受信");

			//受信したパケットをラインレコードに変換
			lr = transPacketToLineRecord(recvPacket);
			//パケット送信元のIPを保存（初回の通信のみ）
			preserveIP(recvPacket);

			// 受信したラインレコードをセッションステータスに反映
			Utl.dPrintln("ラインレコードをセッションステータスに反映");
			SessionStatus.getInstance().getLineRecords().add(lr);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	protected void frameUpdate() {
		Utl.dPrintln("UDPスレッドフレーム更新");
		receivePacket();
	}

	/**
	 * ラインレコードを送信用パケットに変換
	 * 
	 * @return
	 */
	public DatagramPacket recordToSendPacket(LineRecord lr) {
		Utl.dPrintln("ラインレコードを送信用パケットに変換");
		DatagramPacket sendPacket;
		byte[] sendData;
		//ラインレコードをByteBuffer型に変換
		ByteBuffer bBuf = transLineRecordToByteBuffer(lr);

		sendData = new byte[bBuf.position()];
		System.arraycopy(bBuf.array(), 0, sendData, 0, sendData.length);

		// 接続中のクライアントに送信するようにパケットを設定
		sendPacket = new DatagramPacket(sendData, sendData.length,
				clientAddress, Prefs.DEFAULT_PORT);

		return sendPacket;
	}
	
	/**
	 * パケット送信元のIPを保存する
	 */
	private void preserveIP(DatagramPacket recvPacket){
		// パケットを受信したらそのクライアントのIPを保存する
		if (clientAddress == null) {
			Utl.dPrintln("クライアントのIPを保存:　" + recvPacket.getAddress().toString());
			clientAddress = recvPacket.getAddress();
		}
	}
	
}
