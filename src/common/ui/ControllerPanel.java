package common.ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

/**
 * クライアント側で操作するアプリケーションのコントローラ
 * @author Reima
 *
 */
public class ControllerPanel extends JPanel implements ActionListener{
	
	private static final Dimension PANEL_SIZE = new Dimension(410, 90);
	private static final Color PANEL_BACKGROUND = Color.white;
	
	private static final String NAME_BTN_WM = "WriteMode";
	private static final String NAME_BTN_CW = "ClearWindow";
	private static final String NAME_BTN_TM = "TargetMode";
	private static final String NAME_BTN_CS = "ColorSet";
	private static final String NAME_BTN_LSTART = "LogStart";
	private static final String NAME_BTN_LSTOP = "LogStop";
	private static final String NAME_BTN_LEXPORT = "LogExport";
	
	private static final Dimension COMMON_BTN_SIZE = new Dimension(110, 20);
	
	private static final Dimension DM_BTN_WMC = COMMON_BTN_SIZE;
	private static final Dimension DM_BTN_CW = COMMON_BTN_SIZE;
	private static final Dimension DM_BTN_TMC = COMMON_BTN_SIZE;
	private static final Dimension DM_BTN_CS = COMMON_BTN_SIZE;
	private static final Dimension DM_BTN_LSTART = COMMON_BTN_SIZE;
	private static final Dimension DM_BTN_LSTOP = COMMON_BTN_SIZE;
	private static final Dimension DM_BTN_LEXPORT = COMMON_BTN_SIZE;
	
	//for Button Layout
	private static final int ROW_UPPER = 10;
	private static final int ROW_MIDDLE = 35;
	private static final int ROW_LOWER = 60;
	private static final int COL_LEFT = 10;
	private static final int COL_CENTER = 150;
	private static final int COL_RIGHT = 290;

	private static final Point P_BTN_WMC = new Point(COL_LEFT, ROW_UPPER);
	private static final Point P_BTN_CW = new Point(COL_LEFT, ROW_MIDDLE);
	private static final Point P_BTN_TMC = new Point(COL_LEFT, ROW_LOWER);
	private static final Point P_BTN_CS = new Point(COL_CENTER, ROW_UPPER);
	private static final Point P_BTN_LSTART = new Point(COL_CENTER,ROW_MIDDLE);
	private static final Point P_BTN_LSTOP = new Point(COL_CENTER, ROW_LOWER);
	private static final Point P_BTN_LEXPORT = new Point(COL_RIGHT, ROW_UPPER);

	private static final ControllerPanel cPanel = new ControllerPanel();
	
	private JButton bWriteMode;
	private JButton bClearWindow;
	private JButton bTargetMode;
	private JButton bColorSet;
	private JButton bLogStart;
	private JButton bLogStop;
	private JButton bLogExport;
	
	private ControllerPanel(){
		setPreferredSize(PANEL_SIZE);
		setLayout(null);
		setBackground(PANEL_BACKGROUND);
		setFocusable(true);
		
		//makeComponent
		bWriteMode = new JButton(NAME_BTN_WM);
		bWriteMode.setSize(DM_BTN_WMC);
		bWriteMode.setLocation(P_BTN_WMC);
		bWriteMode.setFocusable(true);
		bWriteMode.setVisible(true);
		
		bClearWindow = new JButton(NAME_BTN_CW);
		bClearWindow.setSize(DM_BTN_CW);
		bClearWindow.setLocation(P_BTN_CW);
		bClearWindow.setFocusable(true);
		bClearWindow.setVisible(true);
		
		bTargetMode = new JButton(NAME_BTN_TM);
		bTargetMode.setSize(DM_BTN_TMC);
		bTargetMode.setLocation(P_BTN_TMC);
		bTargetMode.setFocusable(true);
		bTargetMode.setVisible(true);
		
		bColorSet = new JButton(NAME_BTN_CS);
		bColorSet.setSize(DM_BTN_CS);
		bColorSet.setLocation(P_BTN_CS);
		bColorSet.setFocusable(true);
		bColorSet.setVisible(true);
		
		bLogStart = new JButton(NAME_BTN_LSTART);
		bLogStart.setSize(DM_BTN_LSTART);
		bLogStart.setLocation(P_BTN_LSTART);
		bLogStart.setFocusable(true);
		bLogStart.setVisible(true);
		
		bLogStop = new JButton(NAME_BTN_LSTOP);
		bLogStop.setSize(DM_BTN_LSTOP);
		bLogStop.setLocation(P_BTN_LSTOP);
		bLogStop.setFocusable(true);
		bLogStop.setVisible(true);
		
		bLogExport = new JButton(NAME_BTN_LEXPORT);
		bLogExport.setSize(DM_BTN_LEXPORT);
		bLogExport.setLocation(P_BTN_LEXPORT);
		bLogExport.setFocusable(true);
		bLogExport.setVisible(true);
		
		//setActionListenerToButton
		bWriteMode.addActionListener(this);
		bClearWindow.addActionListener(this);
		bTargetMode.addActionListener(this);
		bColorSet.addActionListener(this);
		bLogStart.addActionListener(this);
		bLogStop.addActionListener(this);
		bLogExport.addActionListener(this);

		//addComponent
		this.add(bWriteMode);
		this.add(bClearWindow);
		this.add(bTargetMode);
		this.add(bColorSet);
		this.add(bLogStart);
		this.add(bLogStop);
		this.add(bLogExport);

	}
	
	public static ControllerPanel getInstance(){
		return cPanel;
	}

	@Override
	public void actionPerformed(ActionEvent ev) {
		if(ev.getActionCommand() == NAME_BTN_WM){
			System.out.println("Button:WriteMode is Pressed.");
			
		}else if(ev.getActionCommand() == NAME_BTN_CW){
			System.out.println("Button:ClearWindow is Pressed.");

		}else if(ev.getActionCommand() == NAME_BTN_TM){
			System.out.println("Button:TargetMode is Pressed.");
			
		}else if(ev.getActionCommand() == NAME_BTN_CS){
			System.out.println("Button:ColorSet is Pressed.");

		}else if(ev.getActionCommand() == NAME_BTN_LSTART){
			System.out.println("Button:LoggingStart is Pressed.");
			
		}else if(ev.getActionCommand() == NAME_BTN_LSTOP){
			System.out.println("Button:LoggingStop is Pressed.");
			
		}else if(ev.getActionCommand() == NAME_BTN_LEXPORT){
			System.out.println("Button:LogExport is Pressed.");
			
		}
	}
}
