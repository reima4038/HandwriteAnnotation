package common.data;

import java.util.ArrayList;

/**
 * セッション中の変数を保持するクラス
 * @author Reima
 */
public class SessionStatus {

	private final static SessionStatus sStatus = new SessionStatus();
	
	/*
	 * for HandwriteLayerStatus
	 */
	//注釈の数
	private int annoNum;
	//描画中か否か
	private boolean drawFlagOwn;
	private boolean drawFlagPartner;
	//記録中か否か
	private boolean recordingFlag;
	
	//自分のID
	private int ownID;
	//現在の描画色
	private int currentLineColor;

	//ウインドウの透明度
	private int windowAlpha;
	
	/*
	 * 注釈データ格納配列
	 * latestLineRecord: 自分で描画した最新レコード
	 * receivedLineRecord: 受信したレコード
	 * lineRecords: 統合したレコード全て
	 */
	private LineRecord latestLineRecord;
	private LineRecord receivedLineRecord;
	private ArrayList<LineRecord> lineRecords;
	
	private SessionStatus(){
		annoNum = 0;
		drawFlagOwn = false;
		drawFlagPartner = false;
		recordingFlag = false;
		windowAlpha = 0;
		latestLineRecord = new LineRecord();
		receivedLineRecord = new LineRecord();
		lineRecords = new ArrayList<LineRecord>();
	}
	
	public static SessionStatus getInstance(){
		return sStatus;
	}
	
	
	/*
	 * getter, setter
	 */
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
