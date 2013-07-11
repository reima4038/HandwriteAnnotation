package common.util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import au.com.bytecode.opencsv.CSVWriter;

import common.data.LineRecord;
import common.data.Prefs;

public class FileOutput implements Prefs{

	public FileOutput(){
	}
	
	/**
	 * デフォルト名でファイルへ書き込み
	 * @param text
	 */
	public static void output(ArrayList<LineRecord> lrs){
		Utl.dPrintln("ログのファイル書き込み開始");
		try {
			Utl.dPrintln("出力ファイル名: " + LOG_FILE_NAME);
			PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(new File(LOG_FILE_NAME + ".csv"))));
			CSVWriter writer = new CSVWriter(pw);
			
			for(int i = 0; i < lrs.size(); i++){
				writer.writeNext(formatLineRecordToWritableData(lrs.get(i)));
			}
			
			writer.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * ラインレコードをCSVWriterで書き込み可能なString[]に変換
	 * @param lr
	 * @return
	 */
	private static String[] formatLineRecordToWritableData(LineRecord lr){
		Utl.dPrintln("ラインレコードをCSVWriterで書き込み可能な形式に変換");
		
		//配列の大きさはLineRecordのメンバ変数の数(5)と描画点の数のの合計
		String[] data = new String[5 + lr.getRecord().size()];
		
		data[0] = String.valueOf(lr.getUserID());
		data[1] = String.valueOf(lr.getColor());
		data[2] = String.valueOf(lr.getClickTimeStamp());
		data[3] = String.valueOf(lr.getReleaseTimeStamp());
		data[4] = String.valueOf(lr.getRecord().size());
		for(int i = 0; i < lr.getRecord().size(); i++){
			data[5 + i] = lr.getRecord().get(i).x + "," + lr.getRecord().get(i).y; 
		}
		
		Utl.dPrintln(data.toString());
		
		return data;
	}
	
}
