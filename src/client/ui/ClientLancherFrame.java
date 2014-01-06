package client.ui;

import java.awt.BorderLayout;
import common.abst.LogExportListener;


import javax.swing.JFrame;

public class ClientLancherFrame extends JFrame{

	private static final String CLIENT_LANCHER_TITLE = "ClientLancher";
	private static ClientLancherFrame clFrame;
	
	private ClientLancherFrame(){
		setTitle(CLIENT_LANCHER_TITLE);
		setResizable(false);
		ClientLancherPanel clPanel = ClientLancherPanel.getInstance();
		getContentPane().add(clPanel, BorderLayout.CENTER);
		pack();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//windowを閉じた時に手書き注釈ログの書き出しを行う
		this.addWindowListener(new LogExportListener());
	}
	
	
	public static ClientLancherFrame getInstance(){
		if(clFrame == null){
			clFrame = new ClientLancherFrame();
		}
		return clFrame;
	}
	
	public static void main(String args[]){
		getInstance().setVisible(true);
	}

}
