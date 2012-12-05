package common.data;

/**
 * アプリケーション全体に影響する設定
 * @author Reima
 */
public interface Prefs {

	//利用するポート
	public static final int DEFAULT_PORT = 4038;
	
	//通信するパケットのサイズ
	public static final int BUFSIZE = 8192;

	//ログファイル名
	public static final String LOG_FILE_NAME = "logfile";

}
