package client.ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;


import common.data.SessionStatus;
import common.ui.ControllerFrame;
import common.util.Utl;

public class ClientLancherPanel extends JPanel implements ActionListener{
	
	private static final Dimension PANEL_SIZE = new Dimension(190, 110);
	private static final Color PANEL_BACKGROUND = Color.white;
	
	private static final String TEXT_WELCOME_MESSAGE = "For ClientSide Version0.1";
	private static final String TEXT_BESIDE_TF_IP = "IP:";
	private static final Point P_TEXT_WELCOME_MESSAGE = new Point(10, 20);
	private static final Point P_TEXT_BESIDE_TF_IP = new Point(10, 50);
	
	private static final String NAME_BTN_LAUNCH = "Launch";
	private static final String NAME_BTN_END = "End";
	
	private static final Dimension DM_TF_IP = new Dimension(130, 20);
	private static final Dimension DM_BTN_LAUNCH = new Dimension(80, 20);
	private static final Dimension DM_BTN_END = new Dimension(80, 20);

	private static final Point P_TF_IP = new Point(50, 37);
	private static final Point P_BTN_LAUNCH = new Point(10, 70);
	private static final Point P_BTN_END = new Point(100, 70);
	
	private static final ClientLancherPanel clPanel = new ClientLancherPanel();
	
	private JTextField tfAddress;
	private JButton bLaunch;
	private JButton bEnd;
	
	private ClientLancherPanel(){
		setPreferredSize(PANEL_SIZE);
		setLayout(null);
		setBackground(PANEL_BACKGROUND);
		setFocusable(true);
		
		//makeComponent
		tfAddress = new JTextField();
		tfAddress.setSize(DM_TF_IP);
		tfAddress.setLocation(P_TF_IP);
		tfAddress.setFocusable(true);
		tfAddress.setVisible(true);
		
		bLaunch = new JButton(NAME_BTN_LAUNCH);
		bLaunch.setSize(DM_BTN_LAUNCH);
		bLaunch.setLocation(P_BTN_LAUNCH);
		bLaunch.setFocusable(true);
		bLaunch.setVisible(true);
		
		bEnd = new JButton(NAME_BTN_END);
		bEnd.setSize(DM_BTN_END);
		bEnd.setLocation(P_BTN_END);
		bEnd.setFocusable(true);
		bEnd.setVisible(true);
		
		//setActionListenerToButton
		bLaunch.addActionListener(this);
		bEnd.addActionListener(this);
		
		//addComponent
		this.add(tfAddress);
		this.add(bLaunch);
		this.add(bEnd);
	}
	
	public static ClientLancherPanel getInstance(){
		return clPanel;
	}
	
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		//Draw WelcomeMessage, Description
		g.drawString(TEXT_WELCOME_MESSAGE, P_TEXT_WELCOME_MESSAGE.x, P_TEXT_WELCOME_MESSAGE.y);
		g.drawString(TEXT_BESIDE_TF_IP, P_TEXT_BESIDE_TF_IP.x, P_TEXT_BESIDE_TF_IP.y);
	}
	
	@Override
	public void actionPerformed(ActionEvent ev) {
		if(ev.getActionCommand() == NAME_BTN_LAUNCH){
			Utl.println("LaunchButton is Pressed.");
			//テキストフィールドに入力されたIPをサーバアドレスとして保存
			Utl.println("ServerAddress: " + tfAddress.getText());
			SessionStatus.getInstance().initValSocket(tfAddress.getText());
			//HandwriteLayer Launch
			HandwriteLayerFrame.getInstance().setVisible(true);
			//Controller Launch
			ControllerFrame.getInstance().setVisible(true);

			
		}else if(ev.getActionCommand() == NAME_BTN_END){
			System.out.println("EndButton is Pressed.");
			//System out
			System.exit(0);
		}
	}
	

}
