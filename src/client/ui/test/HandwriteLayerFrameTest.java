package client.ui.test;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import client.ui.HandwriteLayerFrame;

public class HandwriteLayerFrameTest {

	
	/**
	 * 手書き注釈レイヤはタイトルバーなし
	 */
	@Test
	public void noTitleBar() {
		assertTrue(HandwriteLayerFrame.getInstance().isUndecorated());
	}
	
	/**
	 * 手書き注釈レイヤは常に最前面に位置する
	 */
	@Test
	public void alwaysOnTop(){
		assertTrue(HandwriteLayerFrame.getInstance().isAlwaysOnTop());
	}
	
}
