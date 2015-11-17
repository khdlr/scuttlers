import static java.lang.Math.max;
import static java.lang.Math.min;

public class Neighboorhood {

	public int 
		center,
		north,
		east,
		south,
		west,
		northeast,
		northwest,
		southeast,
		southwest;
	
	public int direct(int number) {
		return (north==number?1:0) +
		       (south==number?1:0) +
		       (west ==number?1:0) +
		       (east ==number?1:0);
	}
	
	public int cross(int number) {
		return (northeast==number?1:0) +
		       (southeast==number?1:0) +
		       (northwest==number?1:0) +
		       (southwest==number?1:0);
	}
	
	public int total(int number) {
		return (north==number?1:0) +
			   (south==number?1:0) +
			   (west ==number?1:0) +
			   (east ==number?1:0) +
			   (northeast==number?1:0) +
			   (southeast==number?1:0) +
			   (northwest==number?1:0) +
			   (southwest==number?1:0);
	}

	public int getGreatestDirect() {
		return max(max(north, south), max(east, west));
	}
	
	public int getGreatestCross() {
		return max(max(northeast, southeast), max(northwest, southwest));
	}
	
	public int getGreatestTotal() {
		return max(getGreatestDirect(), getGreatestCross());
	}
	
	public int getSmallestDirect() {
		return min(min(north, south), min(east, west));
	}
	
	public int getSmallestCross() {
		return min(min(northeast, southeast), min(northwest, southwest));
	}
	
	public int getSmallestTotal() {
		return min(getSmallestDirect(), getSmallestCross());
	}
	
}
