import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;


public class World {
	public boolean locked;
	public int[][] initialstate;
	public int[][][] map;
	public int on = 0, off;

	public int width, height;
	
	public World(int width, int height) {
		this.width = width;
		this.height = height;
		map = new int[Config.AMT_BUFFERS][width][height];
	}
	
	public void rewind() {
		on = 1; off = 0;
		map[on] = arraycopy(initialstate);
	}
	
	public void init() {
		on = 1; off = 0;
		for(int x = 0; x<width; x++) {
			for(int y = 0; y<height; y++) {
				map[on][x][y] = 0;
			}	
		}
		initialstate=arraycopy(map[on]);
	}
	
	public void initTop() {
		on = 1; off = 0;
		for(int x = 0; x<width; x++) {
			for(int y = 0; y<height; y++) {
				map[on][x][y] = 0;
			}	
		}
		map[on][width/2][0] = 1;
		initialstate=arraycopy(map[on]);
	}
	
	public void initOneDim(int max) {
		Random r = new Random();
		on = 1; off = 0;
		for(int x = 0; x<width; x++) {
			map[on][x][0] = r.nextInt(max+1);
			for(int y = 1; y<height; y++) {
				map[on][x][y] = 0;
			}	
		}
		map[on][width/2][0] = 1;
		initialstate=arraycopy(map[on]);
	}
	
	public void initCenter() {
		on = 1; off = 0;
		for(int x = 0; x<width; x++) {
			for(int y = 0; y<height; y++) {
				map[on][x][y] = 0;
			}	
		}
		map[on][width/2][height/2] = 1;
		initialstate=arraycopy(map[on]);
	}
	
	public void initRandom(int max) {
		Random r = new Random();
		on = 1; off = 0;
		for(int x = 0; x<width; x++) {
			for(int y = 0; y<height; y++) {
				map[on][x][y] = r.nextInt(max+1);
			}	
		}
		initialstate=arraycopy(map[on]);
	}
	
	public void init12() {
		on = 1; off = 0;
		for(int x = 0; x<width; x++) {
			for(int y = 0; y<height; y++) {
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
		initialstate=arraycopy(map[on]);
	}
	
	public int step() {
		off = on;
		on = (on+1) % Config.AMT_BUFFERS;
		
		int east, west, north, south;
		Neighboorhood n = new Neighboorhood();
		for(int x = 0; x<width; x++) {
			for(int y = 0; y<height; y++) {
				east = (x+1)%width;
				west = (width+x-1)%width;
				north = (height+y-1)%height;
				south = (y+1) % height;
				
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
	
	public static World load(File file) throws IOException {
		BufferedReader br = new BufferedReader(new FileReader(file));
		ArrayList<String> lines = new ArrayList<>();
		String line = br.readLine();
		int linelength = line.length();
		while(null != line) {
			lines.add(line);
			line = br.readLine();
		}
		br.close();		
		World w = new World(lines.size(), linelength);
		System.out.println("Loaded World with "+w.width+" columns and "+w.height+" rows");
		w.on = 1; w.off = 0;
		for(int x = 0; x<w.width; x++) {
			for(int y = 0; y<w.height; y++) {
				w.map[w.on][x][y] = (int) lines.get(y).charAt(x);
			}
		}
		w.initialstate=arraycopy(w.map[w.on]);
		return w;
	}
	
	private static int[][] arraycopy(int[][] input) {
		int[][] output = new int[input.length][input[0].length];
		for(int x = 0;x<output.length;x++) {
			for(int y = 0;y<output[x].length;y++) {
				output[x][y] = input[x][y];
			}
		}
		return output;
	}
	
	public void saveCurrent(File file) throws IOException {
		int writeon = on;
		BufferedWriter bw = new BufferedWriter(new FileWriter(file));
		for(int y = 0; y<height; y++) {
			for(int x = 0; x<width; x++) {
				bw.append((char) map[writeon][x][y]);
			}
			bw.newLine();
		}
		bw.close();
	}
	
	public void saveInitial(File file) throws IOException {
		BufferedWriter bw = new BufferedWriter(new FileWriter(file));
		for(int y = 0; y<height; y++) {
			for(int x = 0; x<width; x++) {
				bw.append((char) initialstate[x][y]);
			}
			bw.newLine();
		}
		bw.close();
	}
}
