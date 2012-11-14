package common.abst;

import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

/**
 * 手書きレイヤの抽象クラス
 * @author Reima
 *
 */
public abstract class AbstRunnablePanel extends JPanel implements Runnable{

	// 負荷テストしたい.2000~でほぼ遅延なし
	private static final int DEFAULT_FPS = 1500;

	int fps;
	 
    public AbstRunnablePanel(int fps) {
        this.fps = fps;
        setIgnoreRepaint(true);
    }
 
    public AbstRunnablePanel() {
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
    
    protected abstract void frameUpdate(int skipped);
    protected abstract void frameRender(Graphics2D g);
}

