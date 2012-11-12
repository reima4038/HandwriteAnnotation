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
			fail("ソケット開放処理を行いましたが、ソケットが開放されていません.");
		}
	}
	
	@Test
	/**
	 * アプリケーションの終了後、
	 * ソケット通信が閉じられているか
	 */
	public void socketCloseTest() {
		/*
		 * ソケットの開放
		 * Singletonのインスタンス生成タイミングは初めてgetInstanceが呼ばれた時
		 * インスタンスを呼び出すことでコンストラクタ内に書かれたソケット開放処理を実行する
		 */
		ClientUDP.getInstance();
		//ソケットを閉じる
		ClientUDP.getInstance().close();
		//ソケットが閉じられているか確認
		if(!ClientUDP.getInstance().getSocket().isClosed()){
			fail("ソケットを閉じる処理を実行しましたが、ソケットが閉じられていません.");
		}
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
