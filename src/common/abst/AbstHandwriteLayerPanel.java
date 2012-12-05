package common.abst;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import common.data.SessionStatus;
import common.util.CDraw;

public abstract class AbstHandwriteLayerPanel extends AbstRunnablePanel implements MouseListener, MouseMotionListener{
	protected static final Dimension PANEL_SIZE = new Dimension(800, 640);
	protected static final Color PANEL_BACKGROUND = new Color(0, 0, 0, 50);
	
	//描画する線の太さ
	private static final int DRAW_LINE_BOLD = 1;
	
	protected AbstHandwriteLayerPanel(){
		super();
		setPreferredSize(PANEL_SIZE);
		setLayout(null);
		setBackground(PANEL_BACKGROUND);
		setFocusable(true);
		addMouseListener(this);
		addMouseMotionListener(this);
	}
	
	/**
	 * 手書き注釈レイヤ左上隅に表示するステータスを描画する 表示するのは画面上の注釈数, 自分が描画中か、 相手が描画中か, ログデータ記録中か
	 * 
	 * @param g
	 */
	protected void drawStatus(Graphics g) {
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
	 * 描画色の設定
	 * @param g
	 */
	protected void setLineColor(Graphics g) {
		g.setColor(Color.black);
	}

	protected void drawHandwriteAnnotation(Graphics g){
		// 描画色の指定
		setLineColor(g);
		// 最新の注釈を描画
		drawLatestRecord(g);
		//履歴の注釈を全て描画
		drawLineRecords(g);
	}
	
	/**
	 * 最新の注釈を描画
	 * @param g
	 */
	protected void drawLatestRecord(Graphics g) {
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
	protected void drawLineRecords(Graphics g) {
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
	protected abstract void latestLineRecordProccess();
	
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

	}

	@Override
	public void mouseDragged(MouseEvent ev) {
	
	}

	@Override
	public void mouseMoved(MouseEvent ev) {

	}

}
