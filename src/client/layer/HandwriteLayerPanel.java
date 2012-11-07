package client.layer;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import common.util.CDraw;

/**
 * 手書きレイヤの本体
 * @author Reima
 */
public class HandwriteLayerPanel extends AbstLayerPanel implements MouseListener, MouseMotionListener{

	private static final Dimension PANEL_SIZE = new Dimension(800, 640);
	private static final Color PANEL_BACKGROUND = new Color(0, 0, 0, 50);

	private static final HandwriteLayerPanel hlPanel = new HandwriteLayerPanel();
	
	private HandwriteLayerPanel(){
		super();
		setPreferredSize(PANEL_SIZE);
		setLayout(null);
		setBackground(PANEL_BACKGROUND);
		setFocusable(true);
		addMouseListener(this);
		addMouseMotionListener(this);
		
		Thread th = new Thread(this);
		th.start();
	}
	
	@Override
	void frameUpdate(int skipped) {
		sortHandwriteAnnotation();
	}

	@Override
	void frameRender(Graphics2D g){
		g.setBackground(PANEL_BACKGROUND);
		g.clearRect(0, 0, PANEL_SIZE.width, PANEL_SIZE.height);
		drawHandwriteAnnotation(g);
		drawStatus(g);
	}
	
	/**
	 * 手書き注釈を格納している配列をタイムスタンプ順にソート
	 * 手書き注釈の描画順を正しくする
	 */
	private void sortHandwriteAnnotation(){
		//ソートすべきデータが無い場合（配列長1以下）ならソートしない
		//前回のソートから一定時間経過していないならソートしない
		//前回のソートから新しく注釈データが増えてないならソートしない
	}
	
	
	/**
	 * 手書き注釈レイヤ左上隅に表示するステータスを描画する
	 * 表示するのは画面上の注釈数, 自分が描画中か、 相手が描画中か, ログデータ記録中か
	 * @param g
	 */
	private void drawStatus(Graphics g){
		//TODO:SessionStatusクラスからデータ持ってくるように
		
		//画面上の注釈数を描画
		CDraw.drawString(g, "AnnotationNumber: " + 20, new Point(20, 20));
		//自分が描画中か
		CDraw.drawString(g, "now Drawing...", new Point(20, 40));
		//相手が描画中か
		CDraw.drawString(g, "Partner Drawing...", new Point(20, 60));
		//ログデータ記録中か
		CDraw.drawString(g, "REC", Color.red, new Point(20, 80));
	}
	
	/**
	 * 手書き注釈レイヤ上に手書き注釈を描画する
	 */
	private void drawHandwriteAnnotation(Graphics g){
		
	}
	
	public static HandwriteLayerPanel getInstance(){
		return hlPanel;
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
		
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		
	}

	@Override
	public void mouseDragged(MouseEvent arg0) {
		
	}

	@Override
	public void mouseMoved(MouseEvent arg0) {
		
	}


}
