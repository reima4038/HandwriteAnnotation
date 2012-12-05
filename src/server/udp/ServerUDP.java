package server.udp;

import java.awt.Point;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayList;

import common.abst.AbstUDP;
import common.data.LineRecord;
import common.data.Prefs;
import common.data.SessionStatus;
import common.util.Utl;


public class ServerUDP extends AbstUDP{
	
	private static final ServerUDP sUDP = new ServerUDP();
	
	//クライアントのアドレスリスト
	protected ArrayList<InetAddress> clientAddresses;

	protected ServerUDP() {
		super();
		clientAddresses = new ArrayList<InetAddress>();
	}
	

	@Override
	public void sendPacket(LineRecord lr) {
		try {
			for(int i = 0; i < clientAddresses.size(); i++){
				socket.send(recordToSendPacket(lr).get(i));
				Utl.dPrintln("IP:" + clientAddresses.get(i).getHostAddress() + " にパケット送信");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void receivePacket() {
		LineRecord lr = null;
		try {
			Utl.dPrintln("パケット受信待機中");
			socket.receive(recvPacket);
			Utl.dPrintln("パケット受信");
			//受信したパケットをラインレコードに変換
			lr = transPacketToLineRecord(recvPacket);
			//パケットを受信したらそのクライアントのIPを保存する
			preserveIP(recvPacket);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		//パケットを受信したら全クライアントにデータを送信する
		sendPacket(lr);
	}
	
	@Override
	protected void frameUpdate(){
		//サーバ側では常にパケットを待ち受ける
		receivePacket();
	}

	public static ServerUDP getInstance(){
		return sUDP;
	}

	/**
	 * ラインレコードを送信用パケットに変換
	 * @return
	 */
	public ArrayList<DatagramPacket> recordToSendPacket(LineRecord lr) {
		Utl.dPrintln("ラインレコードを送信用パケットに変換");
		
		ArrayList<DatagramPacket> sendPackets = new ArrayList<DatagramPacket>();
		byte[] sendData;
		//ラインレコードをByteBuffer型に変換
		ByteBuffer bBuf = transLineRecordToByteBuffer(lr);

		sendData = new byte[bBuf.position()];
		System.arraycopy(bBuf.array(), 0, sendData, 0, sendData.length);

		
		//接続中の全クライアントに送信するようにパケットを設定
		for(int i = 0; i < clientAddresses.size(); i++){
			sendPackets.add(new DatagramPacket(sendData, sendData.length, clientAddresses.get(i),
					Prefs.DEFAULT_PORT));

		}

		return sendPackets;
	}
	
	
	/**
	 * パケット送信元のIPを保存する
	 */
	private void preserveIP(DatagramPacket recvPacket){
		if(!isExistAddress(recvPacket.getAddress())){
			Utl.dPrintln("クライアントのIPを保存:　" + recvPacket.getAddress());
			clientAddresses.add(recvPacket.getAddress());
		}
	}
	
	/**
	 * 指定したアドレスが既にクライアントアドレスのリストに存在するか判断する
	 */
	private boolean isExistAddress(InetAddress ip){
		Utl.dPrintln("クライアントのIPがアドレスリストに存在するか確認");
		for(int i = 0; i < clientAddresses.size(); i++){
			if(recvPacket.getAddress() == clientAddresses.get(i)){
				Utl.dPrintln("アドレスリストの中に" + recvPacket.getAddress() + "を発見しました。");
				return true;
			}
		}
		Utl.dPrintln("アドレスリストの中に" + recvPacket.getAddress() + "は有りませんでした");
		return false;
	}
	
	/**
	 * クライアントのアドレスにIPを追加
	 */
	public void addressAdd(InetAddress ip){
		Utl.dPrintln("ClientIPAddress Added: " + ip);
		clientAddresses.add(ip);
	}
	
	/**
	 * クライアントのアドレスリストを初期化
	 */
	public void addressClear(){
		Utl.dPrintln("Clear ClientIPAddress");
		clientAddresses.clear();
	}

}
