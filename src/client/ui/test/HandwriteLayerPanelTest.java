package client.ui.test;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.awt.Point;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import org.junit.Test;

import client.layer.HandwriteLayerPanel;

import common.data.LineRecord;
import common.data.SessionStatus;
import common.util.Utl;

public class HandwriteLayerPanelTest {

	@Test
	/**
	 * シングルトンであるか
	 */
	public void HandwriteLayerPanelSingletonTest() {
		HandwriteLayerPanel var1 = HandwriteLayerPanel.getInstance();
		HandwriteLayerPanel var2 = HandwriteLayerPanel.getInstance();
		assertTrue(var1 == var2);
	}

	@Test
	/**
	 * ドラッグしたポイントを適切に格納できているか
	 */
	public void takeDraggedPointIntoTempLineRecords() {
		// 適当な直線データを用意
		Point[] draggedLine = new Point[30];
		for (int i = 0; i < draggedLine.length; i++) {
			draggedLine[i] = new Point(30, 20 + i);
		}

		// マウスドラッグで直線データを入力する
		HandwriteLayerPanel var = HandwriteLayerPanel.getInstance();
		for (int i = 0; i < draggedLine.length; i++) {
			var.mouseDragged(new MouseEvent(var, -1, -1, -1, draggedLine[i].x,
					draggedLine[i].y, 0, false));
		}

		/*
		 * latestLineRecordsに入っているか確認
		 */
		LineRecord lr = SessionStatus.getInstance().getLatestLineRecord();

		// データ長の照合
		if (draggedLine.length != lr.getRecord().size()) {
			fail("入力データが全てlatestLineRecordsに格納されていません.\n" + "expect: "
					+ draggedLine.length + "actual: " + lr.getRecord().size());
		}

		// データの照合
		for (int i = 0; i < draggedLine.length; i++) {
			if (draggedLine[i].getX() != lr.getRecord().get(i).getX()
					|| draggedLine[i].getY() != lr.getRecord().get(i).getY()) {
				fail("ドラッグした座標データと格納された座標データが一致しません.\n" + "index: " + i
						+ " draggedLine: " + draggedLine[i].getX() + " / "
						+ draggedLine[i].getY() + " LatestLineRecord: "
						+ draggedLine[i].getX() + " / " + draggedLine[i].getY());
			}
		}

	}

	@Test
	/**
	 * マウスリリースした後、最新の注釈が破棄されるか
	 * このテストはtakeDraggedPointIntoTempLineRecordsの後に行う
	 */
	public void eraseLatestLinebyMouseRelease() {
		// マウスリスナーがimplementされたテスト対象クラスのインスタンスを用意
		HandwriteLayerPanel var = HandwriteLayerPanel.getInstance();
		SessionStatus ss = SessionStatus.getInstance();
		
		//レコード長さの初期化
		if(ss.getLatestLineRecord().getRecord().size() > 0){
			ss.getLatestLineRecord().initDefaultValue();
		}

		// 適当な直線データを用意
		Point[] draggedLine = new Point[30];
		for (int i = 0; i < draggedLine.length; i++) {
			draggedLine[i] = new Point(30, 20 + i);
		}

		// マウスドラッグで直線データを入力する
		for (int i = 0; i < draggedLine.length; i++) {
			var.mouseDragged(new MouseEvent(var, -1, -1, -1, draggedLine[i].x,
					draggedLine[i].y, 0, false));
		}
		
		//最初からレコード長が0ならテスト失敗
		if (ss.getLatestLineRecord().getRecord().size() == 0) {
			fail("テスト前提条件を満たしていません. テスト名:takeDraggedPointIntoTempLineRecordsの後に行われているか確認してください.\n"
					+ " expect: RecordSize over 0 "
					+ " actual: RecordeSize is"
					+ ss.getLatestLineRecord().getRecord().size());
		}

		// マウスリリースイベントを送信
		var.mouseReleased(new MouseEvent(var, -1, -1, -1, 0, 0, 0, false));

		// 最新の注釈が破棄されたか検証
		if (ss.getLatestLineRecord().getRecord().size() != 0) {
			fail("注釈の破棄が行われていません\n" + " RecordSize: "
					+ ss.getLatestLineRecord().getRecord().size());
		}
	}

	@Test
	/**
	 * マウスをリリースした後、最新の注釈が履歴に移行されるか
	 */
	public void moveLatestLineToLineRecords() {
		// マウスリスナーがimplementされたテスト対象クラスのインスタンスを用意
		HandwriteLayerPanel var = HandwriteLayerPanel.getInstance();
		SessionStatus ss = SessionStatus.getInstance();
		
		//レコード長さの初期化
		if(ss.getLatestLineRecord().getRecord().size() > 0){
			ss.getLatestLineRecord().initDefaultValue();
		}

		// 適当な直線データを用意
		Point[] draggedLine = new Point[30];
		for (int i = 0; i < draggedLine.length; i++) {
			draggedLine[i] = new Point(30, 20 + i);
		}

		// マウスドラッグで直線データを入力する
		for (int i = 0; i < draggedLine.length; i++) {
			var.mouseDragged(new MouseEvent(var, -1, -1, -1, draggedLine[i].x,
					draggedLine[i].y, 0, false));
		}
		
		//最初からレコード長が0ならテスト失敗
		if (ss.getLatestLineRecord().getRecord().size() == 0) {
			fail("テスト前提条件を満たしていません. テスト名:takeDraggedPointIntoTempLineRecordsの後に行われているか確認してください.\n"
					+ " expect: RecordSize over 0 "
					+ " actual: RecordSize is"
					+ ss.getLatestLineRecord().getRecord().size());
		}
		
		// 最新注釈レコードのクローンを確保（マウスリリースされると最新の注釈レコードが初期化されるため）
		LineRecord latestRecordClone = ss.getLatestLineRecord().clone();

		// マウスリリースイベントを送信
		var.mouseReleased(new MouseEvent(var, -1, -1, -1, 0, 0, 0, false));

		/*
		 *  最新の注釈レコードを統合レコードに移行できているか
		 */
		//計算用変数 lineRecordsSize, lineRecordsLastIndex
		int lRsize = 0;
		int lRLastIndex = 0;
		
		if(ss.getLineRecords().size() > 0){
			lRsize = ss.getLineRecords().size();
		}
		else{
			fail("最新の注釈レコードが統合レコードに移行されていません.\n" + 
					"統合レコードがありません.");
		}
		
		if(lRsize <= 0){
			fail("最新の注釈レコードが記録されていないか, 移行が行われていません.\n");
		}
		else{
			lRLastIndex = lRsize - 1;
		}

		for (int i = 0; i < lRsize; i++) {
			if (ss.getLineRecords().get(lRLastIndex).getRecord().get(i) != latestRecordClone.getRecord().get(i)) {
				fail("最新の注釈レコードが統合レコードに移行されていません.\n"
						+ "errorIndex: " + i
						+ " expectValue: "
						+ ss.getLatestLineRecord().getRecord().get(i)
						+ " actual: "
						+ ss.getLineRecords().get(lRLastIndex).getRecord().get(i));
			}
		}

		// 最新の注釈レコードが削除されているかどうか
		if(ss.getLatestLineRecord().getRecord().size() != 0){
			fail("最新の注釈レコードが削除されていません\n" + 
					"expect: the size of LatestLineRecord is 0" + 
					" actual: the size of LatestLineRecord is " + ss.getLatestLineRecord().getRecord().size());
		}

	}

}
