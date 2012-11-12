package client.udp.test;

import static org.junit.Assert.*;

import java.net.DatagramSocket;

import org.junit.Test;

import client.udp.ClientUDP;

public class ClientUDPTest {

	@Test
	/**
	 * コンストラクタで初期化処理後、
	 * ソケット通信が開放されているかどうか
	 */
	public void socketOpenTest(){
		//UDPクラスのインスタンスを取得
		ClientUDP cUDP = ClientUDP.getInstance();
		//通信を行うDatagramSocketを取得
		DatagramSocket socket = cUDP.getSocket();
		//ソケットが開放されているか確認
		if(socket.isClosed()){
			fail("ソケットが開放されていません.");
		}
	}
	
	@Test
	/**
	 * アプリケーションの終了後、
	 * ソケット通信が閉じられているか
	 */
	public void socketCloseTest() {
		fail("Not yet implemented");
	}
	
	/**
	 * パケットが正常に送信されているか
	 */
	public void packetSendTest() {
		fail("Not yet implemented");
	}
	
	/**
	 * パケットを正常に受信できたか
	 */
	public void packetReceiveTest() {
		fail("Not yet implemented");
	}
	
	/**
	 * 受信したパケットのバイトコードをデコードできるか
	 */
	public void packetDecodeTest() {
		fail("Not yet implemented");
	}
	
	/**
	 * 送信するパケットをバイトコードにできるか
	 */
	public void packetCodeTest() {
		fail("Not yet implemented");
	}
	
}
