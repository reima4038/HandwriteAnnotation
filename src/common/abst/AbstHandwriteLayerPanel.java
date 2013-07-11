package common.abst;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import common.data.LineRecord;
import common.data.SessionStatus;
import common.util.CDraw;

public abstract class AbstHandwriteLayerPanel extends AbstRunnablePanel implements MouseListener, MouseMotionListener{
	protected static Dimension PANEL_MAX_SIZE;
	protected static Color panelBackground;
	
	//描画する線の太さ
	private static final int DRAW_LINE_BOLD = 1;
	
	protected AbstHandwriteLayerPanel(){
		super();
		panelBackground = new Color(0, 0, 0, SessionStatus.getInstance().getWindowAlpha());
		
		/*
		 * パネルの可変できる最大のサイズはPreferredSizeに依存する。
		 * デスクトップのサイズをPreferredSizeに設定することで、画面上に存在するウインドウ全てに対応する。
		 */
		detectDesktop();
		setPreferredSize(PANEL_MAX_SIZE);
		
		setLayout(null);
		setBackground(panelBackground);
		setFocusable(true);
		addMouseListener(this);
		addMouseMotionListener(this);
	}
	
	/**
	 * デスクトップサイズを計測
	 */
	private void detectDesktop(){
		GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		GraphicsDevice[] gs = ge.getScreenDevices();
		GraphicsDevice gd = gs[0];
		GraphicsConfiguration[] gc = gd.getConfigurations();
		GraphicsConfiguration gc0 = gc[0];
		Rectangle rect = gc0.getBounds();
		PANEL_MAX_SIZE = new Dimension(rect.width, rect.height);
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

	
	/**
	 * マウスクリック時にタイムスタンプをとる
	 */
	private void clickTimeStamp(){
		LineRecord lr = SessionStatus.getInstance().getLatestLineRecord();
		if(lr.getClickTimeStamp() == LineRecord.DEFAULT_VALUE){
			lr.setClickTimeStampCurrentTime();
		}
	}
	
	/**
	 * マウスリリース時にタイムスタンプをとる
	 */
	private void releaseTimeStamp(){
		SessionStatus.getInstance().getLatestLineRecord().setReleaseStampCurrentTime();
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
		//タイムスタンプを取得
		releaseTimeStamp();
		latestLineRecordProccess();
	}

	@Override
	public void mouseDragged(MouseEvent ev) {
		//タイムスタンプを取得
		clickTimeStamp();
		// ドラッグした点をSessionStatusのlatestLineRecordsに格納
		SessionStatus.getInstance().getLatestLineRecord().getRecord().add(ev.getPoint());

	}

	@Override
	public void mouseMoved(MouseEvent ev) {
		
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
		g.clearRect(0, 0, this.getPreferredSize().width, this.getPreferredSize().height);
		drawHandwriteAnnotation(g);
		drawStatus(g);		
	}
	
}
