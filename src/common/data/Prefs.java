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
	
	//手書き注釈レイヤのアルファ値上限
	public static final int HWL_ALPHA_MAX = 50;
	
	//手書き注釈レイヤのアルファ値下限
	public static final int HWL_ALPHA_MIN = 0;
}
