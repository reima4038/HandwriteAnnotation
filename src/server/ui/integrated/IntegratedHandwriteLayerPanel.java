package server.ui.integrated;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.MouseEvent;

import client.udp.ClientUDP;

import server.udp.IntegratedServerUDP;

import common.abst.AbstHandwriteLayerPanel;
import common.data.LineRecord;
import common.data.SessionStatus;
import common.util.CDraw;
import common.util.Utl;

public class IntegratedHandwriteLayerPanel extends AbstHandwriteLayerPanel{

	private static IntegratedHandwriteLayerPanel ihlPanel;

	private IntegratedHandwriteLayerPanel() {
		super();
		
		//サーバ側のUDP用スレッド
		Thread udpTh = new Thread(IntegratedServerUDP.getInstance());
		//メインループ用のスレッド
		Thread mainTh = new Thread(this);
		
		udpTh.start();
		mainTh.start();
	}

	public static IntegratedHandwriteLayerPanel getInstance() {
		if(ihlPanel == null){
			 ihlPanel = new IntegratedHandwriteLayerPanel();
		}
		return ihlPanel;
	}
	
	@Override
	protected void latestLineRecordProccess() {
		SessionStatus ss = SessionStatus.getInstance();
		LineRecord lr = ss.getLatestLineRecord().clone();

		//最新の注釈をUDPで相手に送信
		IntegratedServerUDP.getInstance().sendPacket(lr);
		
		//最新の注釈レコードを統合レコードに移行
		ss.getLineRecords().add(lr);
		
		//最新の注釈レコードをログ用レコードに移行
		ss.getExportRecords().add(lr);
		
		//最新の注釈を破棄
		ss.getLatestLineRecord().initDefaultValue();		
	}
	
	/**
	 * インスタンスが生成されているか
	 */
	public static boolean isInstance(){
		if(ihlPanel != null){
			return true;
		}
		return false;
	}
}
