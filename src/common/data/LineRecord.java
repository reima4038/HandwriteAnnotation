package common.data;

import java.awt.Point;
import java.util.ArrayList;

import common.util.Utl;

/**
 * 手書き注釈付記における一回のアクション（クリック、ドラッグ、リリース）間の情報を格納するクラス
 * 
 * @author Reima
 * 
 */
public class LineRecord {

	private static final int DEFAULT_VALUE = -1;
	
	// 付記したユーザのID
	private int userID;
	// 注釈の色
	private int color;
	// クリック時の時刻
	private long clickTimeStamp;
	// リリース時の時刻
	private long releaseTimeStamp;
	// 線分の座標の配列
	private ArrayList<Point> record;

	public LineRecord() {
		//初期化処理
		initDefaultValue();
	}
	
	public LineRecord(int userID, int color, long clickTimeStamp,
			long releaseTimeStamp, ArrayList<Point> record){
		//初期化処理
		this.userID = userID;
		this.color = color;
		this.clickTimeStamp = clickTimeStamp;
		this.releaseTimeStamp = releaseTimeStamp;
		this.record = record;
	}
	
	/**
	 * 初期化処理を行う
	 */
	public void initDefaultValue() {
		userID = DEFAULT_VALUE;
		color = DEFAULT_VALUE;
		clickTimeStamp = DEFAULT_VALUE;
		releaseTimeStamp = DEFAULT_VALUE;
		record = new ArrayList<Point>();
	}
	
	/**
	 * ディープコピーを生成して返す
	 * @return
	 */
	public LineRecord clone(){
		return new LineRecord(userID, color, clickTimeStamp, releaseTimeStamp, (ArrayList<Point>) record.clone());
	}
	
	/**
	 * レコードの内容をコンソールに表示する
	 * @return
	 */
	public void show(){
		Utl.dPrintln("/*--------Record Data-------*/");
		Utl.dPrintln("userID: " + userID);
		Utl.dPrintln("color: " + color);
		Utl.dPrintln("clickTimeStamp: " + clickTimeStamp);
		Utl.dPrintln("releaseTimeStamp: " + releaseTimeStamp);
		for(int i = 0; i < record.size(); i++){
			Utl.dPrintln("point" + i + ": " + record.get(i).x + "/" + record.get(i).y);
		}
		Utl.dPrintln("/*--------------------------*/");
	}
	
	
	/*
	 * getter, setter
	 */
	public int getUserID() {
		return userID;
	}

	public int getColor() {
		return color;
	}

	public long getClickTimeStamp() {
		return clickTimeStamp;
	}

	public long getReleaseTimeStamp() {
		return releaseTimeStamp;
	}

	public ArrayList<Point> getRecord() {
		return record;
	}

	public void setUserID(int userID) {
		this.userID = userID;
	}

	public void setColor(int color) {
		this.color = color;
	}

	public void setClickTimeStamp(long clickTimeStamp) {
		this.clickTimeStamp = clickTimeStamp;
	}
	
	/**
	 * 現在時刻をクリックした時刻としてセット
	 */
	public void setClickTimeStampCurrentTime(){
		this.clickTimeStamp = System.currentTimeMillis();
		Utl.dPrintln("ClickTimeStamp: " + clickTimeStamp);
	}

	public void setReleaseTimeStamp(long releaseTimeStamp) {
		this.releaseTimeStamp = releaseTimeStamp;
	}
	
	/**
	 * 現在時刻をリリースした時刻としてセット
	 */
	public void setReleaseStampCurrentTime(){
		this.releaseTimeStamp = System.currentTimeMillis();
		Utl.dPrintln("ReleaseTimeStamp: " + releaseTimeStamp);
	}

	public void setRecord(ArrayList<Point> record) {
		this.record = record;
	}

}
