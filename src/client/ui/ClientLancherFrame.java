package client.ui;

import java.awt.BorderLayout;

import javax.swing.JFrame;

public class ClientLancherFrame extends JFrame{

	private static final String CLIENT_LANCHER_TITLE = "ClientLancher";
	private static final ClientLancherFrame clFrame = new ClientLancherFrame();
	
	private ClientLancherFrame(){
		setTitle(CLIENT_LANCHER_TITLE);
		setResizable(false);
		ClientLancherPanel clPanel = ClientLancherPanel.getInstance();
		getContentPane().add(clPanel, BorderLayout.CENTER);
		pack();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	
	public static ClientLancherFrame getInstance(){
		return clFrame;
	}
	
	public static void main(String args[]){
		getInstance().setVisible(true);
	}

}
