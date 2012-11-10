package client.layer;

import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

/**
 * 手書きレイヤの抽象クラス
 * @author Reima
 *
 */
public abstract class AbstLayerPanel extends JPanel implements Runnable{

	private static final int DEFAULT_FPS = 100;

	int fps;
	 
    public AbstLayerPanel(int fps) {
        this.fps = fps;
        setIgnoreRepaint(true);
    }
 
    public AbstLayerPanel() {
        this(DEFAULT_FPS);
    }
 
    public void run() {
        long lasttime = System.currentTimeMillis();
        for(;;) {
            frameUpdate(0); // 1フレーム進める
            repaint(); // 描画する
            long nowtime = System.currentTimeMillis();
            if (lasttime-nowtime < 1000/fps) {
                try {
                    // 割り算の誤差などいろいろ正確ではない
                    Thread.sleep(1000/fps-(lasttime-nowtime));
                } catch (InterruptedException e) {}
            }
            else {
                Thread.yield();
            }
            lasttime = nowtime;
        }
    }
    
    public void paintComponent(Graphics g){
    	super.paintComponents(g);
    	frameRender((Graphics2D)g);
    }
    
    abstract void frameUpdate(int skipped);
    abstract void frameRender(Graphics2D g);
}

