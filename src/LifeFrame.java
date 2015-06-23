import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;


public class LifeFrame extends JFrame {
	public static void main(String[] args) {
		LifeFrame f = new LifeFrame();
	}
	
	public LifeFrame() {
		super("Life");
		setContentPane(new LifePanel());
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(400, 400);
		setVisible(true);
	}
	
}
