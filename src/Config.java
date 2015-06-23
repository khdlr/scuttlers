import java.awt.Color;


public class Config {
	
	public static World getWorld() {
		World w = new World();
		w.initRandom(4);
		return w;
	}
	
	public static final int
		WIDTH  = 300,
		HEIGHT = 300,
		AMT_BUFFERS = 3,
		PAUSE = 0;
	
	public static int color(int index) {
		if(index == 0)
			return 0xFF000000;
		else
			return Color.HSBtoRGB((index-1)/6f, 1, 1);
	}
	
	public static int stepCell(Neighboorhood n) {
		switch(n.center) {
		case 0:
			if(n.direct(1) == 1 && n.cross(2) == 1)
				return 1;
			else if(n.direct(2) >= 1 && n.cross(1) >= 2)
				return 2;
			else
				return 0;
		case 1:
			if(n.getGreatestDirect() <= 2 || n.getGreatestDirect() == 10)
				return 0;
			else
				return n.getGreatestCross();
		case 2:
			return n.getGreatestDirect();
		case 10:
			return 0;
		default:
			return n.getSmallestCross();
		/*default:
			if(n.getGreatestDirect() > 2) {
				if(n.direct(1) >= 1)
					return n.getGreatestDirect();
				else
					return 0;
			} else {
				return 0;
			} */
		}
	}	
}
