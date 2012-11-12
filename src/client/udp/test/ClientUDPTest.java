package client.udp.test;

import static org.junit.Assert.fail;

import java.net.DatagramSocket;

import org.junit.Test;

import client.udp.ClientUDP;

import common.data.Prefs;
import common.util.Utl;

public class ClientUDPTest implements Prefs{
	
	@Test
	/**
	 * コンストラクタでソケット通信に必要な変数が初期化されているか
	 */
	public void initTest(){
		ClientUDP cUDP = ClientUDP.getInstance();
		cUDP.init();
		
		if(cUDP.getSocket() == null){
			fail("ソケットが初期化されていません");
		}
		if(cUDP.getSocket().getLocalPort() != Prefs.DEFAULT_PORT){
			fail("ポート番号がデフォルトの値と異なります" + 
					"expect: " + Prefs.DEFAULT_PORT + 
					", actual: " + cUDP.getSocket().getLocalPort());
		}
		if(cUDP.getRecvPacket() == null){
			fail("パケット受信用変数が初期化されていません");
		}
		if(cUDP.getSendPacket() != null){
			fail("パケット送信用変数が初期化されています");
		}
		if(cUDP.getBuf() == null){
			fail("送受信のバッファ用配列のインスタンスが作られていません");
		}
		else if(cUDP.getBuf().length != ClientUDP.BUFSIZE){
			fail("バッファ用配列の長さがデフォルトの値と異なります" + 
					"expect: " + ClientUDP.BUFSIZE + 
					", actual: " + cUDP.getBuf().length);
		}
	}

	@Test
	/**
	 * コンストラクタで初期化処理後、
	 * ソケット通信が開放されているかどうか
	 */
	public void socketOpenTest(){
		//UDPクラスのインスタンスを取得
		ClientUDP cUDP = ClientUDP.getInstance();
		cUDP.init();
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
		ClientUDP.getInstance().init();
		//ソケットを閉じる
		ClientUDP.getInstance().close();
		//ソケットが閉じられているか確認
		if(!ClientUDP.getInstance().getSocket().isClosed()){
			fail("ソケットを閉じる処理を実行しましたが、ソケットが閉じられていません.");
		}
	}

	
}
