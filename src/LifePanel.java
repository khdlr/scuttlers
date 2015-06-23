import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
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
		world = Config.getWorld();
		bitmap = new BufferedImage[Config.AMT_BUFFERS];
		for(int i= 0; i<Config.AMT_BUFFERS; i++) 
			bitmap[i] = new BufferedImage(Config.WIDTH, Config.HEIGHT, BufferedImage.TYPE_INT_ARGB);
		thread = new LifeThread(world, this);
		
		thread.start();
	}
	
	public void fillBitmap(int number) {
		for(int x = 0; x<Config.WIDTH; x++) {
			for(int y=0; y<Config.HEIGHT; y++) {
				bitmap[number].setRGB(x,y, Config.color(world.map[number][x][y]));
			}
		}
	   /* File outputfile = new File("/home/konrad/Desktop/critters/img"+String.format("%03d", imgindex++) +".png");
	    try {
			ImageIO.write(bitmap[number], "png", outputfile);
		} catch (IOException e) {
			e.printStackTrace();
		} */
		lastNumber = number;
	}
	
	@Override
	public void paint(Graphics g) {
		height = Math.min(getWidth(), getHeight());
		x = Math.max(0, getWidth() - height)/2;
		y = Math.max(0, getHeight() - height)/2;
		g.drawImage(bitmap[lastNumber], x, y, height, height, this);
	}
}
