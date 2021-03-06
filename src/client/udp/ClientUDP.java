package client.udp;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.nio.ByteBuffer;

import common.abst.AbstUDP;
import common.data.LineRecord;
import common.data.Prefs;
import common.data.SessionStatus;
import common.ui.ControllerPanel;
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
		try {
			socket.send(recordToSendPacket(lr));
		} catch (IOException e) {
			e.printStackTrace();
		} catch (NullPointerException e){
			Utl.printlnErr("パケット送信先が見つかりません.");
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
			Utl.dPrintln("パケット受信待機");
			socket.receive(recvPacket);
			Utl.dPrintln("パケット受信");

			// 受信したパケットをラインレコードに変換
			lr = transPacketToLineRecord(recvPacket);
			lr.show();

			//削除、アンドゥ、注釈付与をUserIDから判断
			switch(lr.getUserID()){
			case LineRecord.USERID_SYSTEM_COMMAND_REMOVE:
				Utl.dPrintln("通信相手からの注釈削除命令受信");
				ControllerPanel.getInstance().clearAnnotationForRecvPacket();
				break;
			case LineRecord.USERID_SYSTEM_COMMAND_UNDO:
				Utl.dPrintln("通信相手からのアンドゥ命令受信");
				ControllerPanel.getInstance().undoAnnotationForRecvPacket();
				break;
			default:
				// 受信したラインレコードをセッションステータスに反映
				// SessionStatus.getInstance().setReceivedLineRecord(lr);　　//直接LineRecordsに入れればReceivedLineRecordは必要ないかも……
				Utl.dPrintln("受信したラインレコードをセッションステータスに反映");
				SessionStatus.getInstance().getLineRecords().add(lr);
				SessionStatus.getInstance().getExportRecords().add(lr);
			}
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
		if (cUDP == null) {
			cUDP = new ClientUDP();
		}
		return cUDP;
	}
	
	/**
	 * インスタンスがあるか
	 * @return
	 */
	public static boolean isInstance(){
		if(cUDP == null) return false;
		else return true;
	}

	/**
	 * ラインレコードを送信用パケットに変換
	 * 
	 * @return
	 */
	public DatagramPacket recordToSendPacket(LineRecord lr) {
		DatagramPacket sendPacket;
		byte[] sendData;
		// ラインレコードをByteBuffer型に変換
		ByteBuffer bBuf = transLineRecordToByteBuffer(lr);

		// 送信用データを用意
		sendData = new byte[bBuf.position()];
		System.arraycopy(bBuf.array(), 0, sendData, 0, sendData.length);

		// サーバーのアドレスをセッションステータスから取得
		Utl.dPrintln("PacketPreparating.Sending to: "
				+ SessionStatus.getInstance().getSInetAddress());
		InetAddress ip = SessionStatus.getInstance().getSInetAddress();

		// 送信用データ、アドレス、ポートの情報を元に送信用パケットを用意
		sendPacket = new DatagramPacket(sendData, sendData.length, ip,
				Prefs.DEFAULT_PORT);

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
