package server.ui.integrated;

import java.awt.BorderLayout;

import javax.swing.JFrame;

import server.ui.only.ServerLancherPanel;

public class IntegratedServerLancherFrame extends JFrame{
	
	private static final String SERVER_LANCHR_INTEGRATED_TITLE = "ServerLancherIntegrated";
	private static IntegratedServerLancherFrame sliFrame;
	
	private IntegratedServerLancherFrame(){
		setTitle(SERVER_LANCHR_INTEGRATED_TITLE);
		setResizable(false);
		IntegratedServerLancherPanel sliPanel = IntegratedServerLancherPanel.getInstance();
		getContentPane().add(sliPanel, BorderLayout.CENTER);
		pack();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	public static IntegratedServerLancherFrame getInstance(){
		if(sliFrame == null){
			sliFrame = new IntegratedServerLancherFrame();
		}
		return sliFrame;
	}
	
	public static void main(String args[]){
		getInstance().setVisible(true);
	}

}
