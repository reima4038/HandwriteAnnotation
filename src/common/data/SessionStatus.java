package common.data;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;

import org.eclipse.swt.internal.win32.SCROLLINFO;

import common.util.Utl;

/**
 * セッション中の変数を保持するクラス
 * @author Reima
 */
public class SessionStatus implements Prefs{

	private static SessionStatus sStatus;
	
	/*------------------------------------------
	 *　for SocketConnection ソケット通信に関して
	 *------------------------------------------*/
	//自分のIPアドレス
	private String myInetAddress;
	//通信相手のIPアドレス
	private InetAddress sInetAddress;
	
	/*------------------------------------------
	 * for HandwriteLayerStatus　手書き注釈パネルに関して
	 *------------------------------------------*/
	//注釈の数
	private int annoNum;
	//描画中か否か
	private boolean drawFlagOwn;
	private boolean drawFlagPartner;
	//記録中か否か
	private boolean recordingFlag;
	
	//現在の描画色
	private int currentLineColor;

	//ウインドウの透明度
	private int windowAlpha;
	
	//自分で描画した最新レコード
	private LineRecord latestLineRecord;
	//受信したレコード
	private LineRecord receivedLineRecord;
	//統合したレコード
	private ArrayList<LineRecord> lineRecords;
	//エクスポート用のレコード
	private ArrayList<LineRecord> exportRecords;
	
	/*------------------------------------------
	 * for Logging 注釈ログの記録に関して
	 *------------------------------------------*/
	//記録開始時間
	private int loggingStartTime;
	//記録終了時間
	private int loggingStopTime;
	
	/*------------------------------------------
	 * for hwnd ウインドウハンドルに関して
	 *------------------------------------------*/
	//ウインドウハンドル
	private int hWnd;
	//縦スクロールバーの情報
	private SCROLLINFO scrV;
	
	/*------------------------------------------
	 *　for Desktop デスクトップに関して
	 *------------------------------------------*/
	private int dWidth;
	private int dHeight;
	
	private SessionStatus(){
		annoNum = 0;
		drawFlagOwn = false;
		drawFlagPartner = false;
		recordingFlag = false;
		windowAlpha = HWL_ALPHA_MIN;
		latestLineRecord = new LineRecord();
		receivedLineRecord = new LineRecord();
		lineRecords = new ArrayList<LineRecord>();
		exportRecords = new ArrayList<LineRecord>();
		
		currentLineColor = COLOR_RED;
		
		hWnd = -1;
		dWidth = -1;
		dHeight = -1;
		
		//自分のIPをセット
		try {
			myInetAddress = java.net.InetAddress.getLocalHost().getHostAddress();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
	}
	
	public void initValSocket(String sIPAddress){
		//相手のIPをセット
		try {
			sInetAddress = InetAddress.getByName(sIPAddress);
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (NullPointerException e){
			Utl.printlnErr("サーバのIPアドレスの初期化処理に失敗しました.\nサーバIPが指定されていません.");
			e.printStackTrace();
		}
	}
	
	public static SessionStatus getInstance(){
		if(sStatus == null){
			sStatus = new SessionStatus();
		}
		return sStatus;
	}
	
	/*
	 * getter, setter
	 */
	
	public String getMyInetAddress(){
		return myInetAddress;
	}
	
	public LineRecord getLatestLineRecord() {
		return latestLineRecord;
	}

	public LineRecord getReceivedLineRecord() {
		return receivedLineRecord;
	}
	
	public ArrayList<LineRecord> getLineRecords() {
		return lineRecords;
	}

	public boolean getRecordingFlag() {
		return recordingFlag;
	}
	
	public InetAddress getSInetAddress(){
		return sInetAddress;
	}
	
	public int getWindowAlpha(){
		return windowAlpha;
	}

	public int gethWnd() {
		return hWnd;
	}

	public int getdWidth() {
		return dWidth;
	}

	public int getdHeight() {
		return dHeight;
	}

	public SCROLLINFO getScrV() {
		return scrV;
	}

	public int getAnnoNum() {
		return annoNum;
	}

	public boolean isDrawFlagOwn() {
		return drawFlagOwn;
	}

	public boolean isDrawFlagPartner() {
		return drawFlagPartner;
	}

	public int getCurrentLineColor() {
		return currentLineColor;
	}

	public int getLoggingStartTime() {
		return loggingStartTime;
	}

	public int getLoggingStopTime() {
		return loggingStopTime;
	}

	public ArrayList<LineRecord> getExportRecords() {
		return exportRecords;
	}

	public void setExportRecords(ArrayList<LineRecord> exportRecords) {
		this.exportRecords = exportRecords;
	}

	public void setAnnoNum(int annoNum) {
		this.annoNum = annoNum;
	}

	public void setDrawFlagOwn(boolean drawFlagOwn) {
		this.drawFlagOwn = drawFlagOwn;
	}

	public void setDrawFlagPartner(boolean drawFlagPartner) {
		this.drawFlagPartner = drawFlagPartner;
	}

	public void setCurrentLineColor(int currentLineColor) {
		this.currentLineColor = currentLineColor;
	}

	public void setLoggingStartTime(int loggingStartTime) {
		this.loggingStartTime = loggingStartTime;
	}

	public void setLoggingStopTime(int loggingStopTime) {
		this.loggingStopTime = loggingStopTime;
	}

	public void setScrV(SCROLLINFO scrV) {
		this.scrV = scrV;
	}

	public void setdWidth(int dWidth) {
		this.dWidth = dWidth;
	}

	public void setdHeight(int dHeight) {
		this.dHeight = dHeight;
	}

	public void sethWnd(int hWnd) {
		this.hWnd = hWnd;
	}

	public void setLatestLineRecord(LineRecord latestLineRecord) {
		this.latestLineRecord = latestLineRecord;
	}
	
	public void setLineRecords(ArrayList<LineRecord> lineRecords) {
		this.lineRecords = lineRecords;
	}

	public void setReceivedLineRecord(LineRecord receivedLineRecord) {
		this.receivedLineRecord = receivedLineRecord;
	}
	
	public void setRecordingFlag(boolean recordingFlag){
		this.recordingFlag = recordingFlag;
	}
	
	public void setWindowAlpha(int windowAlpha){
		this.windowAlpha = windowAlpha;
	}
	
}
