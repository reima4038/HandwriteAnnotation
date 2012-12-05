package common.util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import au.com.bytecode.opencsv.CSVWriter;

import common.data.Prefs;

public class FileOutput implements Prefs{

	public FileOutput(){
	}
	
	/**
	 * デフォルト名でファイルへ書き込み
	 * @param text
	 */
	public static void output(ArrayList<String[]> text){
		try {
			PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(new File(LOG_FILE_NAME))));
			CSVWriter writer = setData(text, pw);
			writer.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * ファイルへの書き込み
	 * @param fileName
	 * @param text
	 */
	public static void output(String fileName, ArrayList<String[]> text){
		try {
			PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(new File(fileName))));
			CSVWriter writer = setData(text, pw);
			writer.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * データのセット
	 * @param text
	 * @param pw
	 * @return
	 */
	private static CSVWriter setData(ArrayList<String[]> text, PrintWriter pw) {
		CSVWriter writer = new CSVWriter(pw);
		for(int i = 0; i < text.size(); i++){
			writer.writeNext(text.get(i));
		}
		return writer;
	}
}
