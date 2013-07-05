package server.ui.integrated;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.IllegalComponentStateException;

import javax.swing.JFrame;


import client.ui.HandwriteLayerFrame;
import client.ui.HandwriteLayerPanel;

public class IntegratedHandwriteLayerFrame extends JFrame{
	private static IntegratedHandwriteLayerFrame ihlFrame;

	private IntegratedHandwriteLayerFrame() {
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

		IntegratedHandwriteLayerPanel ihlPanel = IntegratedHandwriteLayerPanel.getInstance();
		getContentPane().add(ihlPanel, BorderLayout.CENTER);
		pack();
	}

	public static IntegratedHandwriteLayerFrame getInstance() {
		if(ihlFrame == null){
			 ihlFrame = new IntegratedHandwriteLayerFrame();
		}
		return ihlFrame;
	}
	
	public static void main (String[] args){
		getInstance().setVisible(true);
	}
}
