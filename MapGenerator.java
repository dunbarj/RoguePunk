package data;

import java.util.Random;
//import java.util.Random;

public class MapGenerator {
	public TileGrid[][] map;
	public TileGrid currentMap;
	public int x, y;
	
	public MapGenerator() {
		
	}
	
	public MapGenerator(int size) {
		map = new TileGrid[size][size];
		generateBasicMap();
	}
	
	public void generateBasicMap() {
		int[][] style = {
				{ -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1 },
				{ -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1 },
				{ -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1 },
				{ -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1 },
				{ -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1 },
				{ -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1 },
				{ -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1 },
				{ -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1 },
				{ -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1 },
				{ -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1 },
				{ -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1 },
				{ -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1 },
				{ -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1 },
				{ -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1 },
				{ -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1 } };
		
		AreaGenerator areaGen = new AreaGenerator();
		
		map[0][0] = new TileGrid(areaGen.generateGridArea("grass", "castle", "ulcorner"), style);
		map[0][1] = new TileGrid(areaGen.generateGridArea("grass", "castle", "upper"), style);
		map[0][2] = new TileGrid(areaGen.generateGridArea("grass", "castle", "urcorner"), style);
		map[1][0] = new TileGrid(areaGen.generateGridArea("grass", "castle", "left"), style);
		map[1][1] = new TileGrid(areaGen.generateGridArea("grass", "castle", "center"), style);
		map[1][2] = new TileGrid(areaGen.generateGridArea("grass", "castle", "right"), style);
		map[2][0] = new TileGrid(areaGen.generateGridArea("grass", "castle", "llcorner"), style);
		map[2][1] = new TileGrid(areaGen.generateGridArea("grass", "castle", "lower"), style);
		map[2][2] = new TileGrid(areaGen.generateGridArea("grass", "castle", "lrcorner"), style);
		
		currentMap = this.getRandomArea();
	}
	
	public void changeMap(int changeX, int changeY) {
		this.x += changeX;
		this.y += changeY;
		currentMap = map[x][y];
	}
	
	public TileGrid getRandomArea() {
		Random rand = new Random();
		this.x = rand.nextInt(map.length);
		this.y = rand.nextInt(map[x].length);
		return this.map[x][y];
	}
}
