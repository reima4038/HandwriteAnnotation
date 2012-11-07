package common.data;

import java.awt.Point;

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
	//ウインドウの透明度
	private int windowAlpha;
	//注釈データ格納配列
	
	
	private SessionStatus(){
		
	}
	
	public SessionStatus getInstance(){
		return sStatus;
	}
}
