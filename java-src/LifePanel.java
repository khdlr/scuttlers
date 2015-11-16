import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class LifePanel extends JPanel {
	
	World world;
	BufferedImage[] bitmap;
	LifeThread thread;
	
	int imgindex = 0;
	int x,y,height;
	int lastNumber;
	
	public LifePanel() {
		makeWorld(Config.DEFAULT_WIDTH, Config.DEFAULT_HEIGHT);
	}
	
	public void makeWorld(int width, int height) {
		if(null != thread)
			thread.running = false;
		world = Config.getWorld();
		world.initRandom(4);
		bitmap = new BufferedImage[Config.AMT_BUFFERS];
		for(int i= 0; i<Config.AMT_BUFFERS; i++) 
			bitmap[i] = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		fillBitmap(world.on);
		repaint();
	}
	
	public void useWorld(World w) {
		if(null != thread)
			thread.running = false;
		world = w;
		bitmap = new BufferedImage[Config.AMT_BUFFERS];
		for(int i= 0; i<Config.AMT_BUFFERS; i++) 
			bitmap[i] = new BufferedImage(w.width, w.height, BufferedImage.TYPE_INT_ARGB);	
		fillBitmap(world.on);
		repaint();
	}
	
	public void fillBitmap(int number) {
		for(int x = 0; x<world.width; x++) {
			for(int y=0; y<world.height; y++) {
				bitmap[number].setRGB(x,y, Config.color(world.map[number][x][y]));
			}
		}
	    if(Config.outputFiles) {
	    	File outputfile = new File("/home/konrad/Desktop/critters/img"+String.format("%03d", imgindex++) +".png");
		    try {
				ImageIO.write(bitmap[number], "png", outputfile);
			} catch (IOException e) {
				e.printStackTrace();
			} 
	    }
		lastNumber = number;
	}
	
	@Override
	public void paint(Graphics g) {
		height = Math.min(getWidth(), getHeight());
		x = Math.max(0, getWidth() - height)/2;
		y = Math.max(0, getHeight() - height)/2;
		g.drawImage(bitmap[lastNumber], x, y, height, height, this);
	}

	public void rewindWorld() {
		world.rewind();
		fillBitmap(world.on);
		repaint();
	}
}
