package common.util.test;

import java.awt.Point;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import org.junit.Test;
import static org.junit.Assert.*;

import au.com.bytecode.opencsv.CSVReader;

import common.data.LineRecord;
import common.data.Prefs;
import common.util.FileOutput;

public class FileOutputTest implements Prefs {

	/**
	 * 適切に文字列が書き込みされているか
	 */
	@Test
	public void writingTest() {
		// ラインレコード生成
		ArrayList<Point> testLine = new ArrayList<Point>();
		for (int i = 0; i < 20; i++) {
			testLine.add(new Point(i, 11));
		}
		LineRecord testRecord = new LineRecord(111, 0, 0, 0, testLine);
		ArrayList<LineRecord> lrs = new ArrayList<LineRecord>();
		lrs.add(testRecord);
		// ファイル書き込み
		FileOutput.output(lrs);
		// 書き込んだファイルを確認
		try {
			CSVReader reader = new CSVReader(new FileReader(LOG_FILE_NAME + ".csv"));
			String[] nextLine;
			while ((nextLine = reader.readNext()) != null){
				if(!nextLine[0].equals("" + testRecord.getUserID())){
					fail("ユーザー名が一致しません。" + "expect:" + testRecord.getUserID() 
							+ " actual:" + nextLine[0]);
				}
				if(!nextLine[1].equals("" + testRecord.getColor())){
					fail("色が一致しません" + "expect:" + testRecord.getColor() 
							+ " actual:" + nextLine[1]);
				}
				if(!nextLine[2].equals("" + testRecord.getClickTimeStamp())){
					fail("クリック時のタイムスタンプが一致しません" + "expect:" + testRecord.getClickTimeStamp() 
							+ " actual:" + nextLine[2]);
				}
				if(!nextLine[3].equals("" + testRecord.getReleaseTimeStamp())){
					fail("マウスリリース時のタイムスタンプが一致しません" + "expect:" + testRecord.getReleaseTimeStamp() 
							+ " actual:" + nextLine[3]);
				}
				if(!nextLine[4].equals("" + testRecord.getRecord().size())){
					fail("レコード数が一致しません" + "expect:" + testRecord.getRecord().size()
							+ " actual:" + nextLine[4]);
				}
				for(int i = 0; i < 20; i++){
					if(!nextLine[5 + i].equals(i + "," + 11)) fail("レコードの値が一致しません");
				}
			}
		} catch (IOException e) {
			System.out.println(e);
		}

	}
}
