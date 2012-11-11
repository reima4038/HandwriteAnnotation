package client.udp;

public class ClientUDP implements Runnable {

	private static final int DEFAULT_FPS = 1500;
	private static final ClientUDP cUDP = new ClientUDP();

	private int fps;

	public ClientUDP(int fps) {
		this.fps = fps;
	}

	public ClientUDP() {
		this(DEFAULT_FPS);
	}

	public void sendPacket() {

	}

	public void ReceivePacket() {

	}

	public void close() {

	}

	public static ClientUDP getInstance() {
		return cUDP;
	}

	@Override
	public void run() {
		long lasttime = System.currentTimeMillis();
		while (true) {
			long nowtime = System.currentTimeMillis();
			if (lasttime - nowtime < 1000 / fps) {
				try {
					// 割り算の誤差などいろいろ正確ではない
					Thread.sleep(1000 / fps - (lasttime - nowtime));
				} catch (InterruptedException e) {
				}
			} else {
				Thread.yield();
			}
			lasttime = nowtime;
		}

	}
}
