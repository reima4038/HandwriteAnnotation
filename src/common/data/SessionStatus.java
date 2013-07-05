package common.data;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;

import common.util.Utl;

/**
 * セッション中の変数を保持するクラス
 * @author Reima
 */
public class SessionStatus {

	private static SessionStatus sStatus;
	
	/*------------------------------------------
	 *　for SocketConnection 
	 *------------------------------------------*/
	//自分のIPアドレス
	private String myInetAddress;
	//通信相手のIPアドレス
	private InetAddress sInetAddress;
	
	/*------------------------------------------
	 * for HandwriteLayerStatus
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
	
	/*------------------------------------------
	 * for Logging
	 *------------------------------------------*/
	//記録開始時間
	private int loggingStartTime;
	//記録終了時間
	private int loggingStopTime;
	
	
	
	private SessionStatus(){
		initValLayerPanel();
	}
	
	private void initValLayerPanel(){
		annoNum = 0;
		drawFlagOwn = false;
		drawFlagPartner = false;
		recordingFlag = false;
		windowAlpha = 255;
		latestLineRecord = new LineRecord();
		receivedLineRecord = new LineRecord();
		lineRecords = new ArrayList<LineRecord>();
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
		
		//自分のIPをセット
		try {
			myInetAddress = java.net.InetAddress.getLocalHost().toString();
		} catch (UnknownHostException e) {
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
	
}
