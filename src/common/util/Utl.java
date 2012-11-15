package common.util;

public class Utl {

	//デバッグ用のコメントをコンソールに出力するか否か
	private static final boolean DEBUG_MODE = true;
	
	private Utl(){}
	
	/**
	 * 引数の文字列をコンソールに表示
	 * System.out.printlnのエイリアス
	 * @param str
	 */
	public static void println(String str){
		System.out.println(str);
	}
	
	/**
	 * 引数の文字列をエラーとしてコンソールに表示
	 * System.err.plintlnのエイリアス
	 * @param str
	 */
	public static void printlnErr(String str){
		System.err.println(str);
	}
	
	/**
	 * DEBUG_MODEがtrueの時、
	 * 引数の文字列をコンソールに表示
	 * System.out.printlnのエイリアス
	 */
	public static void dPrintln(String str){
		if(DEBUG_MODE){
			System.out.println(str);
		}
	}
	
	/**
	 * オブジェクトがNULLかどうかを判断する
	 * @param obj
	 * @return
	 */
	public static boolean isNull(Object obj){
		if(obj == null){
			return false;
		}
		return true;
	}
	
}
