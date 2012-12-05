package server.test;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import server.ui.only.ServerLancherFrame;
import server.ui.only.ServerLancherPanel;

public class ServerLauncherUITest {

	@Test
	/**
	 * Singletonが正しく実装されているか
	 * 2変数それぞれにServerLancherFrameClassのinstanceを取得し
	 * それらを比較する.
	 */
	public void ServerLancherFrameSingletonTest() {
		ServerLancherFrame sample1 = ServerLancherFrame.getInstance();
		ServerLancherFrame sample2 = ServerLancherFrame.getInstance();
		assertTrue(sample1 == sample2);
	}
	
	@Test
	/**
	 * Singletonが正しく実装されているか
	 * 2変数それぞれにServerLancherPanelClassのinstanceを取得し
	 * それらを比較する.
	 */
	public void ServerLauncherPanelSingletonTest(){
		ServerLancherPanel sample1 = ServerLancherPanel.getInstance();
		ServerLancherPanel sample2 = ServerLancherPanel.getInstance();
		assertTrue(sample1 == sample2);
	}

}
