
public class LifeThread extends Thread {
	private World w;
	private LifePanel p;
	
	public boolean running = true;
	
	public LifeThread(World w, LifePanel p) {
		this.w = w;
		this.p = p;
	}
	
	@Override
	public void run() {
		while(running) {
			p.fillBitmap(w.step());
			try {
				Thread.sleep(Config.PAUSE);
			} catch(Exception e) {
				System.err.println("Couldn't get any sleep :(");
			}
			p.repaint();
		}
	}
}
