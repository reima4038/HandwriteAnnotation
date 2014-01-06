package client.ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import client.udp.ClientUDP;

import common.abst.AbstHandwriteLayerPanel;
import common.data.LineRecord;
import common.data.SessionStatus;
import common.util.CDraw;
import common.util.Utl;

/**
 * 手書きレイヤの本体
 * 
 * @author Reima
 */
public class HandwriteLayerPanel extends AbstHandwriteLayerPanel{

	private static HandwriteLayerPanel hlPanel = null;

	private HandwriteLayerPanel() {
		super();
		
		//クライアント側のUDP用スレッド
		Thread udpTh = new Thread(ClientUDP.getInstance());
		//メインループ用のスレッド
		Thread mainTh = new Thread(this);
		
		udpTh.start();
		mainTh.start();
	}

	/**
	 * マウスリリース後の最新の手書き注釈に関する処理
	 * ・相手に注釈データを送信
	 * ・最新注釈を統合レコードに移行
	 * ・最新注釈を初期化
	 */
	@Override
	protected void latestLineRecordProccess() {
		SessionStatus ss = SessionStatus.getInstance();
		LineRecord lr = ss.getLatestLineRecord().clone();

		//最新の注釈をUDPで相手に送信
		ClientUDP.getInstance().sendPacket(lr);
		
		//最新の注釈レコードを統合レコードに移行
		ss.getLineRecords().add(lr);
		
		//最新の注釈レコードをログ用レコードに移行
		ss.getExportRecords().add(lr);
		
		//最新の注釈を破棄
		ss.getLatestLineRecord().initDefaultValue();
	}

	public static HandwriteLayerPanel getInstance() {
		if(hlPanel == null){
			 hlPanel = new HandwriteLayerPanel();
		}
		return hlPanel;
	}
	
	/**
	 * インスタンスが生成されているか
	 * @return
	 */
	public static boolean isInstance(){
		if(hlPanel != null){
			return true;
		}
		return false;
	}

}
