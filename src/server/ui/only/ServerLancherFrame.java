package server.ui.only;

import java.awt.BorderLayout;

import javax.swing.JFrame;

public class ServerLancherFrame extends JFrame{

	private static final String SERVER_LANCHR_TITLE = "ServerLancher";
	private static final ServerLancherFrame slFrame = new ServerLancherFrame();
	
	private ServerLancherFrame(){
		setTitle(SERVER_LANCHR_TITLE);
		setResizable(false);
		ServerLancherPanel slPanel = ServerLancherPanel.getInstance();
		getContentPane().add(slPanel, BorderLayout.CENTER);
		pack();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	
	public static ServerLancherFrame getInstance(){
		return slFrame;
	}
	
	public static void main(String args[]){
		getInstance().setVisible(true);
	}

}
