package client.ui.test;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import client.layer.HandwriteLayerPanel;

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
	 * 	UDP通信でパートナーからの描画データを受け取れているか
	 */
	public void isReceiveDataViaUDP(){
		
	}
	
	@Test
	/**
	 * 自分の描画したデータを受け取れているか
	 */
	public void isReceiveOwnData(){
		
	}
	
	@Test
	/**
	 * 背景のアルファ値のデフォルト値が0出ないか
	 */
	public void defaultBackgroundAlpha(){
		
	}

}
