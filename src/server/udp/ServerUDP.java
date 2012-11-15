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
	private ArrayList<InetAddress> clientAddress;

	private ServerUDP() {
		super();
		clientAddress = new ArrayList<InetAddress>();
	}
	

	@Override
	public void sendPacket(LineRecord lr) {
		try {
			for(int i = 0; i < clientAddress.size(); i++){
				socket.send(recordToSendPacket(lr).get(i));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void receivePacket() {
		LineRecord lr = null;
		try {
			socket.receive(recvPacket);
			Utl.println("パケット受信");
			lr = this.recordFromRecvPacket(recvPacket);
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
		Utl.println("ラインレコードを送信用パケットに変換");

		
		ArrayList<DatagramPacket> sendPackets = new ArrayList<DatagramPacket>();
		byte[] sendData;
		ByteBuffer bBuf = ByteBuffer.allocate(8192);
		bBuf.order(ByteOrder.BIG_ENDIAN);

		bBuf.putInt(lr.getUserID());
		bBuf.putInt(lr.getColor());
		bBuf.putLong(lr.getClickTimeStamp());
		bBuf.putLong(lr.getReleaseTimeStamp());
		bBuf.putInt(lr.getRecord().size());
		for (int i = 0; i < lr.getRecord().size(); i++) {
			bBuf.putInt(lr.getRecord().get(i).getLocation().x);
			bBuf.putInt(lr.getRecord().get(i).getLocation().y);
		}

		sendData = new byte[bBuf.position()];
		System.arraycopy(bBuf.array(), 0, sendData, 0, sendData.length);

		
		//接続中の全クライアントに送信するようにパケットを設定
		for(int i = 0; i < clientAddress.size(); i++){
			InetAddress ip = SessionStatus.getInstance().getSInetAddress();
			sendPackets.add(new DatagramPacket(sendData, sendData.length, clientAddress.get(i),
					Prefs.DEFAULT_PORT));

		}

		return sendPackets;
	}
	
	/**
	 * 受信したパケットをラインレコードに変換
	 * 
	 * @return
	 */
	public LineRecord recordFromRecvPacket(DatagramPacket recvPacket) {
		Utl.println("受信したパケットをラインレコードに変換");
		
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
		for (int i = 0; i < recordSize; i++) {
			x = bBuf.getInt();
			y = bBuf.getInt();
			lr.getRecord().add(new Point(x, y));
		}
		
		//パケットを受信したらそのクライアントのIPを保存する
		if(!isExistAddress(recvPacket.getAddress())){
			clientAddress.add(recvPacket.getAddress());
		}
		
		return lr;
	}
	
	/**
	 * 指定したアドレスが既にクライアントアドレスのリストに存在するか判断する
	 */
	private boolean isExistAddress(InetAddress ip){
		for(int i = 0; i < clientAddress.size(); i++){
			if(recvPacket.getAddress() == clientAddress.get(i)){
				return true;
			}
		}
		return false;
	}
	
	/**
	 * クライアントのアドレスにIPを追加
	 */
	public void addressAdd(InetAddress ip){
		Utl.println("ClientIPAddress Added: " + ip);
		clientAddress.add(ip);
	}
	
	/**
	 * クライアントのアドレスリストを初期化
	 */
	public void addressClear(){
		Utl.println("Clear ClientIPAddress");
		clientAddress.clear();
	}

}
