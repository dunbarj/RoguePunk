package data;

import static helpers.Artist.*;
import java.util.ArrayList;

public class TileGrid {

	public Tile[][] map;
	public Tile[][] style;
	public Tile[][] background;
	public ArrayList<Enemy> enemyList;
	public ArrayList<Bullet> bullets;

	public TileGrid() {
		map = new Tile[20][15];
		for (int i = 0; i < map.length; i++) {
			for (int j = 0; j < map[i].length; j++) {
				map[i][j] = new Tile(i * 64, j * 64, 64, 64, TileType.Grass, "map");
			}
		}
		enemyList = new ArrayList<Enemy>();
		bullets = new ArrayList<Bullet>();
	}

	public TileGrid(int[][] array) {
		background = new Tile[20][15];
		for (int i = 0; i < background.length; i++) {
			for (int j = 0; j < background[i].length; j++) {
				switch (array[j][i]) {
				case 0:
					map[i][j] = new Tile(i * 64, j * 64, 64, 64, TileType.menuWhite, "menu");
					break;
				case 1:
					map[i][j] = new Tile(i * 64, j * 64, 64, 64, TileType.menuBlack, "menu");
					break;
				}
			}
		}
		enemyList = new ArrayList<Enemy>();
		bullets = new ArrayList<Bullet>();
	}

	public TileGrid(int[][] newMap, int[][] newStyle) {
		map = new Tile[20][15];
		for (int i = 0; i < map.length; i++) {
			for (int j = 0; j < map[i].length; j++) {
				switch (newMap[j][i]) {
				case 0:
					map[i][j] = new Tile(i * 64, j * 64, 64, 64, TileType.Air, "map");
					break;
				case 1:
					map[i][j] = new Tile(i * 64, j * 64, 64, 64, TileType.Grass, "map");
					break;
				case 2:	
					map[i][j] = new Tile(i * 64, j * 64, 64, 64, TileType.Grass2, "map");
					break;
				case 3:
					map[i][j] = new Tile(i * 64, j * 64, 64, 64, TileType.Water, "map");
					break;
				case 4:
					map[i][j] = new Tile(i * 64, j * 64, 64, 64, TileType.Desert, "map");
					break;
				case 6:
					map[i][j] = new Tile(i * 64, j * 64, 64, 64, TileType.Castle, "map");
					break;	
				case 7:
					map[i][j] = new Tile(i * 64, j * 64, 64, 64, TileType.CastleVine, "map");
					break;
				case 8:
					map[i][j] = new Tile(i * 64, j * 64, 64, 64, TileType.Shrub, "map");
					break;
				case 9:
					map[i][j] = new Tile(i * 64, j * 64, 64, 64, TileType.CaveLeft, "map");
					break;
				case 10:
					map[i][j] = new Tile(i * 64, j * 64, 64, 64, TileType.CaveRight, "map");
					break;
				}
			}
		}
		style = new Tile[20][15];
		for (int i = 0; i < style.length; i++) {
			for (int j = 0; j < style[i].length; j++) {
				switch (newStyle[j][i]) {
				case -1:
					style[i][j] = new Tile(i * 64, j * 64, 64, 64, TileType.blank, "map");
					break;
				case 63:
					style[i][j] = new Tile(i * 64, j * 64, 64, 64, TileType.rpgTile063, "map");
					break;
				case 64:
					style[i][j] = new Tile(i * 64, j * 64, 64, 64, TileType.rpgTile064, "map");
					break;
				case 172:
					style[i][j] = new Tile(i * 64, j * 64, 64, 64, TileType.rpgTile172, "map");
					break;
				case 175:
					style[i][j] = new Tile(i * 64, j * 64, 64, 64, TileType.rpgTile175, "map");
					break;
				case 179:
					style[i][j] = new Tile(i * 64, j * 64, 64, 64, TileType.rpgTile179, "map");
					break;
				case 177:
					style[i][j] = new Tile(i * 64, j * 64, 64, 64, TileType.rpgTile177, "map");
					break;
				case 195:
					style[i][j] = new Tile(i * 64, j * 64, 64, 64, TileType.rpgTile195, "map");
					break;
				case 197:
					style[i][j] = new Tile(i * 64, j * 64, 64, 64, TileType.rpgTile197, "map");
					break;
				case 199:
					style[i][j] = new Tile(i * 64, j * 64, 64, 64, TileType.rpgTile199, "map");
					break;
				}
			}
		}
		enemyList = new ArrayList<Enemy>();
		bullets = new ArrayList<Bullet>();
	}

	public void removeBullets() {
		bullets.clear();
	}

	public void SetTile(int xCoord, int yCoord, TileType type) {
		map[xCoord][yCoord] = new Tile(xCoord * 64, yCoord * 64, 64, 64, type, "map");
	}

	public Tile GetTile(int xPlace, int yPlace) {
		if (xPlace >= 20) {
			xPlace = 19;
		}
		if (yPlace >= 15) {
			yPlace = 14;
		}
		return this.map[xPlace][yPlace];
	}

	public void Draw() {
		for (int i = 0; i < map.length; i++) {
			for (int j = 0; j < map[i].length; j++) {
				Tile t = map[i][j];
				DrawQuadTex(t.getTexture(), t.getX(), t.getY(), t.getWidth(),
						t.getHeight());
			}
		}
	}

	public void DrawStyle() {
		for (int i = 0; i < style.length; i++) {
			for (int j = 0; j < style[i].length; j++) {
				Tile t = style[i][j];
				DrawQuadTex(t.getTexture(), t.getX(), t.getY(), t.getWidth(), t.getHeight());
			}
		}
	}

	public Tile[][] getMap() {
		return map;
	}
}
