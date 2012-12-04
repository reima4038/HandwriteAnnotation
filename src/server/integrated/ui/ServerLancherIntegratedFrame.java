package server.integrated.ui;

import java.awt.BorderLayout;

import javax.swing.JFrame;

import server.only.ui.ServerLancherPanel;

public class ServerLancherIntegratedFrame extends JFrame{
	
	private static final String SERVER_LANCHR_INTEGRATED_TITLE = "ServerLancherIntegrated";
	private static final ServerLancherIntegratedFrame sliFrame = new ServerLancherIntegratedFrame();
	
	private ServerLancherIntegratedFrame(){
		setTitle(SERVER_LANCHR_INTEGRATED_TITLE);
		setResizable(false);
		ServerLancherIntegratedPanel sliPanel = ServerLancherIntegratedPanel.getInstance();
		getContentPane().add(sliPanel, BorderLayout.CENTER);
		pack();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	public static ServerLancherIntegratedFrame getInstance(){
		return sliFrame;
	}
	
	public static void main(String args[]){
		getInstance().setVisible(true);
	}

}
