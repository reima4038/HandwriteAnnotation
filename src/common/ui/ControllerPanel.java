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
import org.eclipse.swt.internal.win32.SCROLLINFO;

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
	private static final String NAME_BTN_CS = "ColorChange";
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
	private static final int HOTKEY_DISWRITABLE = 3;
	private static final int HOTKEY_CLEAR = 4;
	private static final int HOTKEY_UNDO = 5;
	private static final int HOTKEY_EXIT = 6;

	// シングルトン
	private static ControllerPanel cPanel;

	// コンポーネント
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
		JIntellitype.getInstance().registerHotKey(HOTKEY_DISWRITABLE,
				JIntellitype.MOD_WIN + JIntellitype.MOD_SHIFT, (int) 'W');
		JIntellitype.getInstance().registerHotKey(HOTKEY_CLEAR,
				JIntellitype.MOD_WIN, (int) 'C');
		JIntellitype.getInstance().registerHotKey(HOTKEY_UNDO,
				JIntellitype.MOD_WIN, (int) 'Z');
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
			changeHWLMode();
		} else if (ev.getActionCommand() == NAME_BTN_CW) {
			System.out.println("Button:ClearWindow is Pressed.");
			clearAnnotation();

		} else if (ev.getActionCommand() == NAME_BTN_CS) {
			System.out.println("Button:ColorChange is Pressed.");
			//ボタンを押す度に描画色青と赤を切り替え
			if (SessionStatus.getInstance().getCurrentLineColor() == COLOR_RED) {
				SessionStatus.getInstance().setCurrentLineColor(COLOR_BLUE);
			} else {
				SessionStatus.getInstance().setCurrentLineColor(COLOR_RED);
			}

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
		if (id == HOTKEY_GET_WHND)
			getHWnd();
		else if (id == HOTKEY_WRITABLE)
			writable();
		else if (id == HOTKEY_DISWRITABLE)
			disWritable();
		else if (id == HOTKEY_CLEAR)
			clearAnnotation();
		else if (id == HOTKEY_UNDO)
			undoAnnotation();
		else if (id == HOTKEY_EXIT)
			System.exit(0);

	}

	/**
	 * 手書き注釈を全消去
	 */
	private void clearAnnotation() {
		SessionStatus.getInstance().getLineRecords().clear();
	}

	/**
	 * 手書き注釈のアンドゥ
	 */
	private void undoAnnotation() {
		SessionStatus ss = SessionStatus.getInstance();
		int lrSize = ss.getLineRecords().size();
		if (lrSize > 0) {
			// 末尾の要素を削除
			ss.getLineRecords().remove(lrSize - 1);
		}
	}

	/**
	 * 手書き注釈レイヤの書き込みの可不可を変更する
	 */
	private void changeHWLMode() {
		if (SessionStatus.getInstance().getWindowAlpha() == HWL_ALPHA_MIN) {
			writable();
		} else {
			disWritable();
		}
	}

	private void writable() {
		SessionStatus.getInstance().setWindowAlpha(HWL_ALPHA_MAX);
	}

	private void disWritable() {
		SessionStatus.getInstance().setWindowAlpha(HWL_ALPHA_MIN);
	}

	/**
	 * マウスポインタがある位置でウインドウハンドルを取得する
	 */
	private void getHWnd() {
		Utl.dPrintln("ウインドウハンドルの取得を開始します");
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

		// スクロールバーの現在の状況を取得・初期化
		getScrollbarInfoALL();

	}

	/**
	 * 縦スクロールバーの情報を全て取得する Win32API
	 */
	public void getScrollbarInfoALL() {
		SessionStatus ss = SessionStatus.getInstance();
		if (ss.getScrV() == null) {
			ss.setScrV(new SCROLLINFO());
		}
		ss.getScrV().cbSize = SCROLLINFO.sizeof;
		ss.getScrV().fMask = OS.SIF_ALL;
		OS.GetScrollInfo(ss.gethWnd(), OS.SB_VERT, ss.getScrV());
	}

	/**
	 * 縦スクロールバーの情報のうち、現在の位置だけ取得する Win32API
	 */
	public void getScrollInfoTrackPos() {
		SessionStatus ss = SessionStatus.getInstance();
		if (ss.getScrV() == null) {
			ss.setScrV(new SCROLLINFO());
		}
		ss.getScrV().cbSize = SCROLLINFO.sizeof;
		ss.getScrV().fMask = OS.SIF_TRACKPOS;
		OS.GetScrollInfo(ss.gethWnd(), OS.SB_VERT, ss.getScrV());
	}
}
