package common.util;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Point;

public class CDraw {

	public static final int FONT_SIZE_S = 8;
	public static final int FONT_SIZE_M = 12;
	public static final int FONT_SIZE_L = 18;

	public static final Font FONT_SELIF_S = new Font("Selif", Font.PLAIN, FONT_SIZE_S);
	public static final Font FONT_SELIF_M = new Font("Selif", Font.PLAIN, FONT_SIZE_M);
	public static final Font FONT_SELIF_L = new Font("Selif", Font.PLAIN, FONT_SIZE_L);

	private static final Color DEFAULT_COLOR = Color.black;
	private static final Font DEFAULT_FONT = FONT_SELIF_M;
	
	private CDraw(){}
	

	/**
	 * 文字描画
	 * @param str　描画する文字列
	 * @param x　描画位置のx座標
	 * @param y　描画位置のy座標
	 */
	public static void drawString(Graphics g, String str, int x, int y){
		g.setColor(DEFAULT_COLOR);
		g.setFont(DEFAULT_FONT);
		g.drawString(str, x, y);
	}
	
	/**
	 * 文字描画
	 * @param str 描画する文字列
	 * @param p 描画位置(Pointクラスでの指定)
	 */
	public static void drawString(Graphics g, String str, Point p){
		g.setColor(DEFAULT_COLOR);
		g.setFont(DEFAULT_FONT);
		g.drawString(str, p.x, p.y);
	}
	
	/**
	 *　文字描画 
	 * @param str　描画する文字列
	 * @param f 文字列に適用するフォント
	 * @param x 描画位置のx座標
	 * @param y　描画位置のy座標
	 */
	public static void drawString(Graphics g, String str, Color c, int x, int y){
		g.setColor(c);
		g.setFont(DEFAULT_FONT);
		g.drawString(str, x, y);
	}
	
	/**
	 *　文字描画 
	 * @param str　描画する文字列
	 * @param f 文字列に適用するフォント
	 * @param p 描画位置(Pointクラスでの指定)
	 */
	public static void drawString(Graphics g, String str, Color c, Point p){
		g.setColor(c);
		g.setFont(DEFAULT_FONT);
		g.drawString(str, p.x, p.y);
	}
	
	/**
	 *　文字描画 
	 * @param str　描画する文字列
	 * @param f 文字列に適用するフォント
	 * @param x 描画位置のx座標
	 * @param y　描画位置のy座標
	 */
	public static void drawString(Graphics g, String str, Font f, int x, int y){
		g.setColor(DEFAULT_COLOR);
		g.setFont(f);
		g.drawString(str, x, y);
	}
	
	/**
	 *　文字描画 
	 * @param str　描画する文字列
	 * @param f 文字列に適用するフォント
	 * @param p 描画位置(Pointクラスでの指定)
	 */
	public static void drawString(Graphics g, String str, Font f, Point p){
		g.setColor(DEFAULT_COLOR);
		g.setFont(f);
		g.drawString(str, p.x, p.y);
	}
	
	/**
	 *　文字描画 
	 * @param str　描画する文字列
	 * @param f 文字列に適用するフォント
	 * @param c 文字列の色
	 * @param x 描画位置のx座標
	 * @param y　描画位置のy座標
	 */
	public static void drawString(Graphics g, String str, Font f, Color c, int x, int y){
		g.setColor(c);
		g.setFont(f);
		g.drawString(str, x, y);
	}
	
	/**
	 *　文字描画 
	 * @param str　描画する文字列
	 * @param f 文字列に適用するフォント
	 * @param c 文字列の色
	 * @param p 描画位置(Pointクラスでの指定)
	 */
	public static void drawString(Graphics g, String str, Font f, Color c, Point p){
		g.setColor(c);
		g.setFont(f);
		g.drawString(str, p.x, p.y);
	}
	
}
