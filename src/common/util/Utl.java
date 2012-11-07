package common.util;

public class Utl {

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
	
	public static boolean isNull(Object obj){
		if(obj == null){
			return false;
		}
		return true;
	}
}
