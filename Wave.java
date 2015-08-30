package data;

import java.util.ArrayList;
import java.util.Random;

import org.newdawn.slick.tiled.TiledMap;

import static helpers.Clock.*;

public class Wave {

	private Level level;
	private float timeSinceLastSpawn, spawnTime;
	private Enemy enemyType;
	private static ArrayList<Enemy> enemyList;
	public TileGrid grid;
	public TiledMap map;
	public Player player;
	
	public Wave(Level level, float spawnTime, Enemy enemyType, TiledMap map, Player player) {
		this.level = level;
		this.player = player;
		this.enemyType = enemyType;
		this.spawnTime = spawnTime;
		timeSinceLastSpawn = 0;
		enemyList = new ArrayList<Enemy>();
		this.map = map;
	}
	
	public void Update() {
		timeSinceLastSpawn += Delta();
		if (timeSinceLastSpawn > spawnTime && !level.gameOver) {
			Spawn();
			timeSinceLastSpawn = 0;
		}
		for (Enemy e : level.enemyList) {
			if (!level.gameOver) {
				e.Update();
				e.updateBullets();
			}
			e.Draw();
		}
	}
	
	public void Spawn() {
		//TODO Change to reflect new TiledMap changes
		Random rand = new Random();
		int index = map.getLayerIndex("Walls");
		int i, j;
		do {
			i = rand.nextInt(map.getWidth());
			j = rand.nextInt(map.getHeight());
		} while (map.getTileId(i, j, index) != 0);
		level.enemyList.add(new Enemy(enemyType.getTexture(), i * 64, j * 64, map, 64, 64, 
				enemyType.speed, enemyType.getHealth(),
				enemyType.getBulletSpeed(), enemyType.getBulletDamage(), this.player, this.level));
	}
	
	public static ArrayList<Enemy> getEnemyList() {
		return enemyList;
	}
}
