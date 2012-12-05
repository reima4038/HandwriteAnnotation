package client.layer;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import client.udp.ClientUDP;

import common.abst.AbstRunnablePanel;
import common.data.LineRecord;
import common.data.SessionStatus;
import common.util.CDraw;
import common.util.Utl;

/**
 * 手書きレイヤの本体
 * 
 * @author Reima
 */
public class HandwriteLayerPanel extends AbstRunnablePanel implements
		MouseListener, MouseMotionListener {

	private static final Dimension PANEL_SIZE = new Dimension(800, 640);
	private static final Color PANEL_BACKGROUND = new Color(0, 0, 0, 50);
	
	//描画する線の太さ
	private static final int DRAW_LINE_BOLD = 1;

	private static final HandwriteLayerPanel hlPanel = new HandwriteLayerPanel();

	private HandwriteLayerPanel() {
		super();
		setPreferredSize(PANEL_SIZE);
		setLayout(null);
		setBackground(PANEL_BACKGROUND);
		setFocusable(true);
		addMouseListener(this);
		addMouseMotionListener(this);
		
		//クライアント側のUDP用スレッド
		Thread udpTh = new Thread(ClientUDP.getInstance());
		//メインループ用のスレッド
		Thread mainTh = new Thread(this);
		
		udpTh.start();
		mainTh.start();
	}


	@Override
	protected void frameUpdate(int skipped) {
		sortHandwriteAnnotation();
	}

	@Override
	protected void frameRender(Graphics2D g) {
		
		g.setBackground(PANEL_BACKGROUND);
		g.clearRect(0, 0, PANEL_SIZE.width, PANEL_SIZE.height);
		drawHandwriteAnnotation(g);
		drawStatus(g);
	}
	

	/**
	 * 手書き注釈を格納している配列をタイムスタンプ順にソート 手書き注釈の描画順を正しくする
	 */
	private void sortHandwriteAnnotation() {
		// ソートすべきデータが無い場合（配列長1以下）ならソートしない
		// 前回のソートから一定時間経過していないならソートしない
		// 前回のソートから新しく注釈データが増えてないならソートしない
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
		// 描画色の指定
		setLineColor(g);
		// 最新の注釈を描画
		drawLatestRecord(g);
		 //履歴の注釈を全て描画
		drawLineRecords(g);
	}

	/**
	 * 描画色の設定
	 * @param g
	 */
	private void setLineColor(Graphics g) {
		g.setColor(Color.black);
	}
	
	/**
	 * 最新の注釈を描画
	 * @param g
	 */
	private void drawLatestRecord(Graphics g) {
		SessionStatus ss = SessionStatus.getInstance();
		Point p;
		Point pp;
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
				lineDrawing(g, p, pp);
			}
		}
	}

	/**
	 * 履歴の注釈を全て描画
	 * @param g
	 */
	private void drawLineRecords(Graphics g) {
		SessionStatus ss = SessionStatus.getInstance();
		Point p;
		Point pp;
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
					lineDrawing(g, p, pp);
				}
			}
		}
	}
	
	/**
	 * 太さの分だけy座標をずらしつつ線描
	 * @param g
	 * @param p
	 * @param pp
	 */
	private void lineDrawing(Graphics g, Point p, Point pp) {
		for(int i = 0; i < DRAW_LINE_BOLD; i++){
			g.drawLine(p.x + i, p.y + i, pp.x + i, pp.y + i);
		}
	}
	
	/**
	 * マウスリリース後の最新の手書き注釈に関する処理
	 * ・相手に注釈データを送信
	 * ・最新注釈を統合レコードに移行
	 * ・最新注釈を初期化
	 */
	private void latestLineRecordProccess() {
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
		return hlPanel;
	}

	@Override
	public void mouseClicked(MouseEvent ev) {

	}

	@Override
	public void mouseEntered(MouseEvent ev) {

	}

	@Override
	public void mouseExited(MouseEvent ev) {

	}

	@Override
	public void mousePressed(MouseEvent ev) {

	}

	@Override
	public void mouseReleased(MouseEvent ev) {
		//最新の注釈を処理
		latestLineRecordProccess();
	}

	@Override
	public void mouseDragged(MouseEvent ev) {
		// ドラッグした点をSessionStatusのlatestLineRecordsに格納
		SessionStatus.getInstance().getLatestLineRecord().getRecord().add(ev.getPoint());
	}

	@Override
	public void mouseMoved(MouseEvent ev) {

	}

}
