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

/**
 * 手書きレイヤの本体
 * 
 * @author Reima
 */
public class HandwriteLayerPanel extends AbstHandwriteLayerPanel{

	private static HandwriteLayerPanel hlPanel;

	private HandwriteLayerPanel() {
		super();
		
		//クライアント側のUDP用スレッド
		Thread udpTh = new Thread(ClientUDP.getInstance());
		//メインループ用のスレッド
		Thread mainTh = new Thread(this);
		
		udpTh.start();
		mainTh.start();
	}


	@Override
	protected void frameUpdate(int skipped) {

	}

	@Override
	protected void frameRender(Graphics2D g) {
		//ウィンドウの透明度が変わったかを判定
		if(panelBackground.getAlpha() != SessionStatus.getInstance().getWindowAlpha()){
			panelBackground = new Color(0, 0, 0, SessionStatus.getInstance().getWindowAlpha());
		}
		g.setBackground(panelBackground);
		g.clearRect(0, 0, PANEL_SIZE.width, PANEL_SIZE.height);
		drawHandwriteAnnotation(g);
		drawStatus(g);
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
		
		//最新の注釈を破棄
		ss.getLatestLineRecord().initDefaultValue();
	}

	public static HandwriteLayerPanel getInstance() {
		if(hlPanel == null){
			 hlPanel = new HandwriteLayerPanel();
		}
		return hlPanel;
	}

}
