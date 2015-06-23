import java.util.Random;


public class World {
	public boolean locked;
	public int[][][] map;
	public int on = 0, off;

	
	public World() {
		map = new int[Config.AMT_BUFFERS][Config.WIDTH][Config.HEIGHT];
	}
	
	public void init() {
		on = 1; off = 0;
		for(int x = 0; x<Config.WIDTH; x++) {
			for(int y = 0; y<Config.HEIGHT; y++) {
				map[on][x][y] = 0;
			}	
		}
	}
	
	public void initTop() {
		on = 1; off = 0;
		for(int x = 0; x<Config.WIDTH; x++) {
			for(int y = 0; y<Config.HEIGHT; y++) {
				map[on][x][y] = 0;
			}	
		}
		map[on][Config.WIDTH/2][0] = 1;
	}
	
	public void initOneDim(int max) {
		Random r = new Random();
		on = 1; off = 0;
		for(int x = 0; x<Config.WIDTH; x++) {
			map[on][x][0] = r.nextInt(max+1);
			for(int y = 1; y<Config.HEIGHT; y++) {
				map[on][x][y] = 0;
			}	
		}
		map[on][Config.WIDTH/2][0] = 1;
	}
	
	public void initCenter() {
		on = 1; off = 0;
		for(int x = 0; x<Config.WIDTH; x++) {
			for(int y = 0; y<Config.HEIGHT; y++) {
				map[on][x][y] = 0;
			}	
		}
		map[on][Config.WIDTH/2][Config.HEIGHT/2] = 1;
	}
	
	public void initRandom(int max) {
		Random r = new Random();
		on = 1; off = 0;
		for(int x = 0; x<Config.WIDTH; x++) {
			for(int y = 0; y<Config.HEIGHT; y++) {
				map[on][x][y] = r.nextInt(max+1);
			}	
		}
	}
	
	public void init12() {
		on = 1; off = 0;
		for(int x = 0; x<Config.WIDTH; x++) {
			for(int y = 0; y<Config.HEIGHT; y++) {
				map[on][x][y] = 0;
			}	
		}

		map[on][20][20] = 1;
		map[on][20][21] = 10;
		map[on][20][22] = 1;
		map[on][21][20] = 1;
		map[on][21][21] = 3;
		map[on][21][22] = 1;
		map[on][22][20] = 1;
		map[on][22][21] = 2;
		map[on][22][22] = 1;
	}
	
	public int step() {
		off = on;
		on = (on+1) % Config.AMT_BUFFERS;
		
		int east, west, north, south;
		Neighboorhood n = new Neighboorhood();
		for(int x = 0; x<Config.WIDTH; x++) {
			for(int y = 0; y<Config.HEIGHT; y++) {
				east = (x+1)%Config.WIDTH;
				west = (Config.WIDTH+x-1)%Config.WIDTH;
				north = (Config.HEIGHT+y-1)%Config.HEIGHT;
				south = (y+1) % Config.HEIGHT;
				
				n.center    = map[off][   x][    y];
				n.east      = map[off][east][    y];
				n.west      = map[off][west][    y];
				n.south     = map[off][   x][south];
				n.north     = map[off][   x][north];
				n.northeast = map[off][east][north];
				n.northwest = map[off][west][north];
				n.southeast = map[off][east][south];
				n.southwest = map[off][west][south];
				
				map[on][x][y] = Config.stepCell(n);
			}
		}	
		return on;
	}
}
