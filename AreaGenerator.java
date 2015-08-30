package data;

import java.util.Random;

public class AreaGenerator {
	public int inner, outer;
	
	public int[][] generateBasic(String innerString, String outerString) {
		checkInner(innerString);
		checkOuter(outerString);
		int[][] map = new int[15][20];
		for (int i = 0; i < 15; i++) {
			for (int j = 0; j < 20; j++) {
				if (i == 0 || i == 14) {
					map[i][j] = outer;
				}
				else if (j == 0 || j == 19) {
					map[i][j] = outer;
				}
				else {
					if (inner == 1) {
						Random rand = new Random();
						map[i][j] = rand.nextInt(2) + 1;
					}
				}
			}
		}
		return map;
	}
	
	public int[][] generateGridArea(String innerString, String outerString, String orientation) {
		checkInner(innerString);
		checkOuter(outerString);
		int[][] map = new int[15][20];
		
		for (int i = 0; i < 15; i++) {
			for (int j = 0; j < 20; j++) {
				if (i == 0 || i == 14) {
					map[i][j] = outer;
				}
				else if (j == 0 || j == 19) {
					map[i][j] = outer;
				}
				else {
					if (inner == 1) {
						Random rand = new Random();
						map[i][j] = rand.nextInt(2) + 1;
					}
				}
			}
		}
		
		if (orientation.equals("ulcorner")) { //Upper-Left Corner Area
			map[14][9] = inner;
			map[14][10] = inner;
			map[6][19] = inner;
			map[7][19] = inner;
			map[8][19] = inner;
		}
		else if (orientation.equals("upper")) { //Upper-Center Area
			map[6][0] = inner;
			map[7][0] = inner;
			map[8][0] = inner;
			map[14][9] = inner;
			map[14][10] = inner;
			map[6][19] = inner;
			map[7][19] = inner;
			map[8][19] = inner;		
		}
		else if (orientation.equals("urcorner")) { //Upper-Right Corner Area
			map[6][0] = inner;
			map[7][0] = inner;
			map[8][0] = inner;
			map[14][9] = inner;
			map[14][10] = inner;
		}
		else if (orientation.equals("left")) { //Middle-Left Area
			map[0][9] = inner;
			map[0][10] = inner;
			map[14][9] = inner;
			map[14][10] = inner;
			map[6][19] = inner;
			map[7][19] = inner;
			map[8][19] = inner;
		}
		else if (orientation.equals("center")) { //Middle-Center Area
			map[0][9] = inner;
			map[0][10] = inner;
			map[6][0] = inner;
			map[7][0] = inner;
			map[8][0] = inner;
			map[14][9] = inner;
			map[14][10] = inner;
			map[6][19] = inner;
			map[7][19] = inner;
			map[8][19] = inner;		
		}
		else if (orientation.equals("right")) { //Middle-Right Area
			map[0][9] = inner;
			map[0][10] = inner;
			map[6][0] = inner;
			map[7][0] = inner;
			map[8][0] = inner;
			map[14][9] = inner;
			map[14][10] = inner;	
		}
		else if (orientation.equals("llcorner")) { //Lower-Left Corner Area
			map[0][9] = inner;
			map[0][10] = inner;
			map[6][19] = inner;
			map[7][19] = inner;
			map[8][19] = inner;	
		}
		else if (orientation.equals("lower")) { //Lower-Center Area
			map[0][9] = inner;
			map[0][10] = inner;
			map[6][0] = inner;
			map[7][0] = inner;
			map[8][0] = inner;
			map[6][19] = inner;
			map[7][19] = inner;
			map[8][19] = inner;	
		}
		else if (orientation.equals("lrcorner")) { //Lower-Right Corner Area
			map[0][9] = inner;
			map[0][10] = inner;
			map[6][0] = inner;
			map[7][0] = inner;
			map[8][0] = inner;
		}
		else if (orientation.equals("uppersingle")) { //Upper Single Area
			map[14][9] = inner;
			map[14][10] = inner;
		}
		else if (orientation.equals("lowersingle")) { //Lower Single Area
			map[0][9] = inner;
			map[0][10] = inner;
		}
		else if (orientation.equals("rightsingle")) { //Right Single Area
			map[6][0] = inner;
			map[7][0] = inner;
			map[8][0] = inner;
		}
		else if (orientation.equals("leftsingle")) { //Left Single Area
			map[6][19] = inner;
			map[7][19] = inner;
			map[8][19] = inner;	
		}
		else if (orientation.equals("lrstraight")) { //Left to Right Area
			map[6][0] = inner;
			map[7][0] = inner;
			map[8][0] = inner;
			map[6][19] = inner;
			map[7][19] = inner;
			map[8][19] = inner;	
		}
		else if (orientation.equals("udstraight")) { //Up to Down Area
			map[0][9] = inner;
			map[0][10] = inner;
			map[14][9] = inner;
			map[14][10] = inner;
		}
		
		return map;
	}
	
	public void checkInner(String innerString) {
		if (innerString.equals("grass")) {
			inner = 1;
		}
	}
	
	public void checkOuter(String outerString) {
		if (outerString.equals("shrub")) {
			outer = 8;
		}
		else if (outerString.equals("castle")) {
			outer = 6;
		}
	}
}
