package client.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.IllegalComponentStateException;

import javax.swing.JFrame;


public class HandwriteLayerFrame extends JFrame {

	private static HandwriteLayerFrame hlFrame;

	private HandwriteLayerFrame() {
		try {
			// タイトルバーを隠す
			setUndecorated(true);
			// フレームを透明化
			setBackground(new Color(0x00000000, true));
		} catch (IllegalComponentStateException e) {
			System.err.println("[JFrame構成設定前にframeがdisplayable]\n	setUndecorated()によるタイトルバー非表示, setBackground()による背景透過処理が実行されていません");
		}
		// フレームを最前面に
		setAlwaysOnTop(true);
		setResizable(false);

		HandwriteLayerPanel hlPanel = HandwriteLayerPanel.getInstance();
		getContentPane().add(hlPanel, BorderLayout.CENTER);
		pack();
	}

	public static HandwriteLayerFrame getInstance() {
		if(hlFrame == null){
			hlFrame = new HandwriteLayerFrame();
		}
		return hlFrame;
	}
	
	public static void main (String[] args){
		getInstance().setVisible(true);
	}
}
