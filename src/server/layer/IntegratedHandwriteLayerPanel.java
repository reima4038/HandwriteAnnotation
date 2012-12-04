package server.layer;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import client.udp.ClientUDP;

import server.udp.IntegratedServerUDP;

import common.abst.AbstRunnablePanel;
import common.data.LineRecord;
import common.data.SessionStatus;
import common.util.CDraw;
import common.util.Utl;

public class IntegratedHandwriteLayerPanel extends AbstRunnablePanel implements
		MouseListener, MouseMotionListener {

	private static final Dimension PANEL_SIZE = new Dimension(800, 640);
	private static final Color PANEL_BACKGROUND = new Color(0, 0, 0, 50);
	
	//描画する線の太さ
	private static final int DRAW_LINE_BOLD = 1;

	private static final IntegratedHandwriteLayerPanel ihlPanel = new IntegratedHandwriteLayerPanel();

	private IntegratedHandwriteLayerPanel() {
		super();
		setPreferredSize(PANEL_SIZE);
		setLayout(null);
		setBackground(PANEL_BACKGROUND);
		setFocusable(true);
		addMouseListener(this);
		addMouseMotionListener(this);
		
		//クライアント側のUDP用スレッド
		Thread udpTh = new Thread(IntegratedServerUDP.getInstance());
		//メインループ用のスレッド
		Thread mainTh = new Thread(this);
		
		udpTh.start();
		mainTh.start();
	}

	public static IntegratedHandwriteLayerPanel getInstance() {
		return ihlPanel;
	}
	
	@Override
	protected void frameUpdate(int skipped) {
		//for Debug:現在描画中のレインレコードの数
//		Utl.dPrintln("lineRecords:" + SessionStatus.getInstance().getLineRecords().size());
	}

	@Override
	protected void frameRender(Graphics2D g) {
		g.setBackground(PANEL_BACKGROUND);
		g.clearRect(0, 0, PANEL_SIZE.width, PANEL_SIZE.height);
		drawHandwriteAnnotation(g);
		drawStatus(g);		
	}
	
	/**
	 * 手書き注釈レイヤ左上隅に表示するステータスを描画する 表示するのは画面上の注釈数, 自分が描画中か、 相手が描画中か, ログデータ記録中か
	 * 
	 * @param g
	 */
	private void drawStatus(Graphics g) {
		// TODO:SessionStatusクラスからデータ持ってくるように

		// 画面上の注釈数を描画
		CDraw.drawString(g, "AnnotationNumber: " + 20, new Point(20, 20));
		// 自分が描画中か
		CDraw.drawString(g, "now Drawing...", new Point(20, 40));
		// 相手が描画中か
		CDraw.drawString(g, "Partner Drawing...", new Point(20, 60));
		// ログデータ記録中か
		CDraw.drawString(g, "REC", Color.red, new Point(20, 80));
	}

	/**
	 * 手書き注釈レイヤ上に手書き注釈を描画する
	 */
	private void drawHandwriteAnnotation(Graphics g) {
		SessionStatus ss = SessionStatus.getInstance();
		Point p = null;
		Point pp = null; //prev point

		// 色の指定
		g.setColor(Color.black);

		// 最新の注釈を描画
		for (int i = 0; i < ss.getLatestLineRecord().getRecord().size(); i++) {
			/*
			 * 点と点の隙間を無くすため、最初の一点は指定座標に点を打つ
			 * 二番目以降の点はn番目とn-1番目の点でラインを引く
			 */
			if (i == 0) {
				p = ss.getLatestLineRecord().getRecord().get(i).getLocation();
				g.drawRect(p.x, p.y, DRAW_LINE_BOLD, DRAW_LINE_BOLD);
			}
			else if(i >= 1){
				p = ss.getLatestLineRecord().getRecord().get(i).getLocation();
				pp = ss.getLatestLineRecord().getRecord().get(i - 1).getLocation(); 
				
				//太さの分だけy座標をずらしつつ線描
				for(int j = 0; j < DRAW_LINE_BOLD; j++){
					g.drawLine(p.x + j, p.y + j, pp.x + j, pp.y + j);
				}
			}
		}

		 //履歴の注釈を全て描画
		for(int i = 0; i < ss.getLineRecords().size(); i++){
			for(int j = 0; j < ss.getLineRecords().get(i).getRecord().size(); j++){
				if(j == 0){
					p = ss.getLineRecords().get(i).getRecord().get(j).getLocation();
					g.drawRect(p.x, p.y, DRAW_LINE_BOLD, DRAW_LINE_BOLD);
				}
				else if(j >= 1){
					p = ss.getLineRecords().get(i).getRecord().get(j).getLocation();
					pp = ss.getLineRecords().get(i).getRecord().get(j - 1).getLocation();
					//太さの分だけy座標をずらしつつ線描
					for(int k = 0; k < DRAW_LINE_BOLD; k++){
						g.drawLine(p.x + k, p.y + k, pp.x + k, pp.y + k);
					}
				}
			}
			
		}
		
	}

	@Override
	public void mouseDragged(MouseEvent ev) {
		// ドラッグした点をSessionStatusのlatestLineRecordsに格納
		SessionStatus.getInstance().getLatestLineRecord().getRecord().add(ev.getPoint());		
	}

	@Override
	public void mouseMoved(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		SessionStatus ss = SessionStatus.getInstance();
		LineRecord lr = ss.getLatestLineRecord().clone();

		//最新の注釈をUDPで相手に送信
		IntegratedServerUDP.getInstance().sendPacket(lr);
		
		//最新の注釈レコードを統合レコードに移行
		ss.getLineRecords().add(lr);
		
		//最新の注釈を破棄
		ss.getLatestLineRecord().initDefaultValue();
		
	}

}
