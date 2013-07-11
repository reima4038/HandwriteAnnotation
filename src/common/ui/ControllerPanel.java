package common.ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.PointerInfo;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

import org.eclipse.swt.internal.win32.OS;
import org.eclipse.swt.internal.win32.POINT;
import org.eclipse.swt.internal.win32.RECT;

import server.ui.integrated.IntegratedHandwriteLayerPanel;

import client.ui.HandwriteLayerPanel;

import com.melloware.jintellitype.HotkeyListener;
import com.melloware.jintellitype.JIntellitype;
import common.data.Prefs;
import common.data.SessionStatus;
import common.util.FileOutput;
import common.util.Utl;

/**
 * クライアント側で操作するアプリケーションのコントローラ
 * 
 * @author Reima
 * 
 */
public class ControllerPanel extends JPanel implements HotkeyListener,
		ActionListener, Prefs {

	private static final Dimension PANEL_SIZE = new Dimension(410, 90);
	private static final Color PANEL_BACKGROUND = Color.white;

	private static final String NAME_BTN_WM = "WriteMode";
	private static final String NAME_BTN_CW = "ClearWindow";
	private static final String NAME_BTN_CS = "ColorSet";
	private static final String NAME_BTN_LSTART = "LogStart";
	private static final String NAME_BTN_LSTOP = "LogStop";
	private static final String NAME_BTN_LEXPORT = "LogExport";

	private static final Dimension COMMON_BTN_SIZE = new Dimension(110, 20);

	private static final Dimension DM_BTN_WMC = COMMON_BTN_SIZE;
	private static final Dimension DM_BTN_CW = COMMON_BTN_SIZE;
	private static final Dimension DM_BTN_CS = COMMON_BTN_SIZE;
	private static final Dimension DM_BTN_LSTART = COMMON_BTN_SIZE;
	private static final Dimension DM_BTN_LSTOP = COMMON_BTN_SIZE;
	private static final Dimension DM_BTN_LEXPORT = COMMON_BTN_SIZE;

	// for Button Layout
	private static final int ROW_UPPER = 10;
	private static final int ROW_MIDDLE = 35;
	private static final int ROW_LOWER = 60;
	private static final int COL_LEFT = 10;
	private static final int COL_CENTER = 150;
	private static final int COL_RIGHT = 290;

	private static final Point P_BTN_WMC = new Point(COL_LEFT, ROW_UPPER);
	private static final Point P_BTN_CW = new Point(COL_LEFT, ROW_MIDDLE);
	private static final Point P_BTN_CS = new Point(COL_CENTER, ROW_UPPER);
	private static final Point P_BTN_LSTART = new Point(COL_CENTER, ROW_MIDDLE);
	private static final Point P_BTN_LSTOP = new Point(COL_CENTER, ROW_LOWER);
	private static final Point P_BTN_LEXPORT = new Point(COL_RIGHT, ROW_UPPER);

	// スクロールバーの太さ V:縦、W：横
	public static final int SCROLL_V = 16;
	public static final int SCROLL_W = 16;

	// HotKey
	private static final int HOTKEY_GET_WHND = 1;
	private static final int HOTKEY_WRITABLE = 2;
	private static final int HOTKEY_CLEAR = 3;
	private static final int HOTKEY_EXIT = 4;

	private static ControllerPanel cPanel;

	private JButton bWriteMode;
	private JButton bClearWindow;
	private JButton bColorSet;
	private JButton bLogStart;
	private JButton bLogStop;
	private JButton bLogExport;

	private ControllerPanel() {
		setPreferredSize(PANEL_SIZE);
		setLayout(null);
		setBackground(PANEL_BACKGROUND);
		setFocusable(true);

		// ホットキー登録処理 第一引数がonHotkeyに渡る
		JIntellitype.getInstance().registerHotKey(HOTKEY_GET_WHND,
				JIntellitype.MOD_WIN, (int) 'S');
		JIntellitype.getInstance().registerHotKey(HOTKEY_WRITABLE,
				JIntellitype.MOD_WIN, (int) 'W');
		JIntellitype.getInstance().registerHotKey(HOTKEY_CLEAR,
				JIntellitype.MOD_WIN, (int) 'C');
		JIntellitype.getInstance().registerHotKey(HOTKEY_EXIT,
				JIntellitype.MOD_WIN, (int) 'Q');
		JIntellitype.getInstance().addHotKeyListener(this);

		// makeComponent
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

		// setActionListenerToButton
		bWriteMode.addActionListener(this);
		bClearWindow.addActionListener(this);
		bColorSet.addActionListener(this);
		bLogStart.addActionListener(this);
		bLogStop.addActionListener(this);
		bLogExport.addActionListener(this);

		// addComponent
		this.add(bWriteMode);
		this.add(bClearWindow);
		this.add(bColorSet);
		this.add(bLogStart);
		this.add(bLogStop);
		this.add(bLogExport);

	}

	public static ControllerPanel getInstance() {
		if (cPanel == null) {
			cPanel = new ControllerPanel();
		}
		return cPanel;
	}

	@Override
	public void actionPerformed(ActionEvent ev) {
		if (ev.getActionCommand() == NAME_BTN_WM) {
			System.out.println("Button:WriteMode is Pressed.");
			changeWritable();
		} else if (ev.getActionCommand() == NAME_BTN_CW) {
			System.out.println("Button:ClearWindow is Pressed.");
			SessionStatus.getInstance().getLineRecords().clear();

		} else if (ev.getActionCommand() == NAME_BTN_CS) {
			System.out.println("Button:ColorSet is Pressed.");

		} else if (ev.getActionCommand() == NAME_BTN_LSTART) {
			System.out.println("Button:LoggingStart is Pressed.");

		} else if (ev.getActionCommand() == NAME_BTN_LSTOP) {
			System.out.println("Button:LoggingStop is Pressed.");

		} else if (ev.getActionCommand() == NAME_BTN_LEXPORT) {
			System.out.println("Button:LogExport is Pressed.");
			// ログの書き込み
			FileOutput.output(SessionStatus.getInstance().getLineRecords());
		}
	}

	/**
	 * ホットキー押下時の処理実装
	 */
	@Override
	public void onHotKey(int id) {
		switch (id) {
		case HOTKEY_GET_WHND:
			getHWnd();
			break;
		case HOTKEY_WRITABLE:
			changeWritable();
			break;
		case HOTKEY_CLEAR:
			SessionStatus.getInstance().getLineRecords().clear();
			break;
		case HOTKEY_EXIT:
			System.exit(0);
			break;
		}
	}

	/**
	 * 手書き注釈レイヤの書き込みの可不可を変更する
	 */
	private void changeWritable() {
		SessionStatus ss = SessionStatus.getInstance();
		if (ss.getWindowAlpha() == HWL_ALPHA_MIN) {
			ss.setWindowAlpha(HWL_ALPHA_MAX);
		} else {
			ss.setWindowAlpha(HWL_ALPHA_MIN);
		}
	}

	/**
	 * マウスポインタがある位置でウインドウハンドルを取得する
	 */
	private void getHWnd() {
		Utl.dPrintln("TargetMode:Trueでクリックを検知しました。ウインドウハンドルの取得を開始します");
		int hWnd = -1;
		POINT p = null;
		PointerInfo pi = null;
		RECT wRect = null;

		// マウスポインタ位置を取得
		pi = MouseInfo.getPointerInfo();

		p = new POINT();
		p.x = pi.getLocation().x;
		p.y = pi.getLocation().y;
		Utl.dPrintln("	マウスポインタの位置を取得しました。");
		Utl.dPrintln("		座標: " + p.x + " / " + p.y);

		// マウスポインタの位置にあるウインドウのハンドルを取得
		hWnd = OS.WindowFromPoint(p);
		wRect = new RECT();
		Utl.dPrintln("	ウインドウハンドルを取得しました。");

		// ウインドウハンドルからウインドウの高さ、幅、位置を取得
		OS.GetWindowRect(hWnd, wRect);
		Utl.dPrintln("	ウインドウハンドルから、ウインドウ情報取得しました。");
		Utl.dPrintln("		wRect " + wRect.left + " / " + wRect.top + " w: "
				+ (wRect.right - wRect.left) + " h: "
				+ (wRect.bottom - wRect.top));
		Rectangle r = new Rectangle(wRect.left, wRect.top,
				(wRect.right - wRect.left) - SCROLL_V,
				+(wRect.bottom - wRect.top) - SCROLL_W);

		// 手書き注釈レイヤの大きさを変更(手書き注釈レイヤがIntegrated版か否かをまず判断してから)
		if (HandwriteLayerPanel.isInstance()) {
			HandwriteLayerPanel.getInstance().setBounds(r);
		} else if (IntegratedHandwriteLayerPanel.isInstance()) {
			IntegratedHandwriteLayerPanel.getInstance().setBounds(r);
		} else {
			Utl.printlnErr("手書き注釈レイヤのインスタンスが生成されていません。");
		}
		Utl.dPrintln("	手書き注釈レイヤの大きさを変更しました。");

		// セッションステータスにhwndを登録
		SessionStatus.getInstance().sethWnd(hWnd);
		Utl.dPrintln("	セッションにハンドルを登録しました。");
	}

}
