package server.ui.integrated;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

import common.data.SessionStatus;
import common.ui.ControllerFrame;
import common.util.Utl;

public class IntegratedServerLancherPanel extends JPanel implements
		ActionListener {

	private static final Dimension PANEL_SIZE = new Dimension(190, 110);
	private static final Color PANEL_BACKGROUND = Color.white;

	private static final String TEXT_WELCOME_MESSAGE = "For Server Integrated Ver.0.1";
	private static final Point P_TEXT_WELCOME_MESSAGE = new Point(10, 20);
	private static final String TEXT_BESIDE_OWN_IP = "Own IP: ";
	private static final Point P_TEXT_BESIDE_OWN_IP = new Point(10, 50);

	
	private static final String NAME_BTN_LAUNCH = "Launch";
	private static final String NAME_BTN_END = "End";

	private static final Dimension DM_BTN_LAUNCH = new Dimension(80, 20);
	private static final Dimension DM_BTN_END = new Dimension(80, 20);

	private static final Point P_BTN_LAUNCH = new Point(10, 70);
	private static final Point P_BTN_END = new Point(100, 70);

	private static IntegratedServerLancherPanel sliPanel;

	private JButton bLaunch;
	private JButton bEnd;

	private IntegratedServerLancherPanel() {
		setPreferredSize(PANEL_SIZE);
		setLayout(null);
		setBackground(PANEL_BACKGROUND);
		setFocusable(true);

		// makeComponent
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

		// setActionListenerToButton
		bLaunch.addActionListener(this);
		bEnd.addActionListener(this);

		// addComponent
		this.add(bLaunch);
		this.add(bEnd);
	}

	public static IntegratedServerLancherPanel getInstance() {
		if(sliPanel == null){
			sliPanel = new IntegratedServerLancherPanel();
		}
		return sliPanel;
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		// Draw WelcomeMessage, Description
		g.drawString(TEXT_WELCOME_MESSAGE, P_TEXT_WELCOME_MESSAGE.x,
				P_TEXT_WELCOME_MESSAGE.y);
		g.drawString(TEXT_BESIDE_OWN_IP + SessionStatus.getInstance().getMyInetAddress(), P_TEXT_BESIDE_OWN_IP.x, P_TEXT_BESIDE_OWN_IP.y);
	}

	@Override
	public void actionPerformed(ActionEvent ev) {
		if (ev.getActionCommand() == NAME_BTN_LAUNCH) {
			Utl.dPrintln("LaunchButton is Pressed.");
			//HandwriteLayer Launch
			IntegratedHandwriteLayerFrame.getInstance().setVisible(true);
			//Controller Launch
			ControllerFrame.getInstance().setVisible(true);

		} else if (ev.getActionCommand() == NAME_BTN_END) {
			Utl.dPrintln("EndButton is Pressed.");
			// System out
			System.exit(0);
		}
	}

}