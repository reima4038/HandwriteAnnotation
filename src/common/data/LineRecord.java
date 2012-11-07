package common.data;

import java.awt.Point;
import java.util.ArrayList;

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
		userID = DEFAULT_VALUE;
		color = DEFAULT_VALUE;
		clickTimeStamp = DEFAULT_VALUE;
		releaseTimeStamp = DEFAULT_VALUE;
		record = new ArrayList<Point>();
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

	/*
	 * getter
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

	/*
	 * setter
	 */
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
	}

	public void setReleaseTimeStamp(long releaseTimeStamp) {
		this.releaseTimeStamp = releaseTimeStamp;
	}
	
	/**
	 * 現在時刻をリリースした時刻としてセット
	 */
	public void setReleaseStampCurrentTime(){
		this.releaseTimeStamp = System.currentTimeMillis();
	}

	public void setRecord(ArrayList<Point> record) {
		this.record = record;
	}

}
