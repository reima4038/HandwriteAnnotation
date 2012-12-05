package common.ui;

import java.awt.BorderLayout;

import javax.swing.JFrame;


/**
 * クライアント側で操作するアプリケーションのコントローラのフレーム
 * @author Reima
 *
 */
public class ControllerFrame extends JFrame{
	private static final String CONTROLLER_LANCHER_TITLE = "ControllerFrame";
	private static final ControllerFrame clFrame = new ControllerFrame();
	
	private ControllerFrame(){
		setTitle(CONTROLLER_LANCHER_TITLE);
		setResizable(false);
		ControllerPanel clPanel = ControllerPanel.getInstance();
		getContentPane().add(clPanel, BorderLayout.CENTER);
		pack();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	
	public static ControllerFrame getInstance(){
		return clFrame;
	}
	
	public static void main(String args[]){
		getInstance().setVisible(true);
	}
}
