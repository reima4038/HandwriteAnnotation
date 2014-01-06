package common.ui;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import common.abst.LogExportListener;

/**
 * クライアント側で操作するアプリケーションのコントローラのフレーム
 * 
 * @author Reima
 * 
 */
public class ControllerFrame extends JFrame {
	private static final String CONTROLLER_LANCHER_TITLE = "ControllerFrame";
	private static ControllerFrame clFrame;

	private ControllerFrame() {
		setTitle(CONTROLLER_LANCHER_TITLE);
		setResizable(false);
		ControllerPanel clPanel = ControllerPanel.getInstance();
		getContentPane().add(clPanel, BorderLayout.CENTER);
		pack();
		//常に最上面に
		setAlwaysOnTop(true);
		setLocation(800, 400);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//windowを閉じた時に手書き注釈ログの書き出しを行う
		this.addWindowListener(new LogExportListener());
	}

	public static ControllerFrame getInstance() {
		if (clFrame == null) {
			clFrame = new ControllerFrame();
		}
		return clFrame;
	}

	public static void main(String args[]) {
		getInstance().setVisible(true);
	}
}
