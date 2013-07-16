package common.abst;

import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

import common.ui.ControllerPanel;

/**
 * 手書きレイヤの抽象クラス
 * @author Reima
 *
 */
public abstract class AbstRunnablePanel extends JPanel implements Runnable{

	//スレッド休止間隔
	private static final int THREAD_SLEEP = 10;

    public AbstRunnablePanel() {
        setIgnoreRepaint(true);
    }
 
    /**
     * runメソッドの書き方を色々試してみる。
     */
    public void run(){
		while(true){
			frameUpdate(0); // 1フレーム進める
			repaint();
			try {
				Thread.sleep(THREAD_SLEEP);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
    }
    
    public void paintComponent(Graphics g){
    	super.paintComponents(g);
    	frameRender((Graphics2D)g);
    }
    
    protected abstract void frameUpdate(int skipped);
    protected abstract void frameRender(Graphics2D g);
}

