package data;

//import static helpers.Artist.BeginSession;
import static helpers.Artist.DrawQuadTex;
import static helpers.Artist.QuickLoad;
import helpers.Clock;

import java.awt.Rectangle;
import java.util.ArrayList;

import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.newdawn.slick.Music;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.tiled.TiledMap;

public class Level {

	public TiledMap map;
	private LevelManager manager;
	public ArrayList<Player> playerList = new ArrayList<Player>();
	public ArrayList<Enemy> enemyTypes = new ArrayList<Enemy>();
	public ArrayList<Enemy> enemyList = new ArrayList<Enemy>();
	public ArrayList<Wave> waveList = new ArrayList<Wave>();
	public ArrayList<Bullet> bullets = new ArrayList<Bullet>();
	public Wave wave;
	public boolean gameOver = false;
	public boolean quitLevel = false;
	public float x, y;
	public int mapX, mapY;
	private Music music;
	
	public Level(LevelManager manager, String mapType, String difficulty) {
		this.manager = manager;
		manager.currentLevel = this;
		try {
			map = new TiledMap("src/res/maps/basic/maze.tmx");
		} catch (SlickException e) {
			e.printStackTrace();
		}

		//Creates the player that draws/updates the character and handles the bullets (See Player class if you want to change the arguments)
		Player player = new Player(manager, map, QuickLoad("playerForward"), 32, 60, 60);
		playerList.add(player);
		
		this.addEnemies(mapType);
		
		for (Enemy enemy : enemyTypes) {
			//Assigns the robot enemy to the wave, so that the Wave class spawns robots. (See Wave class if you want to change the arguments)
			this.wave = setDifficulty(difficulty, enemy, map, player);
			waveList.add(wave);
		}
		
		try {
			music = new Music("src/res/music.wav");
			music.loop();
		} catch (SlickException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/*
	 * play() - This is the main game loop for each level. Receives input, updates the Player and Enemies, and draws stuff to the display.
	 */
	public void play() {
		while (!Display.isCloseRequested() && !quitLevel) {
			if (gameOver) {
				gameOverUpdate();
			}
			else {
				//Update time which is used to update the distance that the character/enemies/bullets travel each frame
				Clock.update();
				
				//Draws the map each frame based on where the player is
				map.render((int) x - map.getTileWidth(), (int) y - map.getTileHeight(), mapX, mapY, mapX + 25, mapY + 25);
				
				//Updates the wave which updates each enemy's position and draws them
				for (Wave wave : waveList) {
					wave.Update();
				}
				
				//Updates the character/bullets
				for(Player player : playerList) {
					player.Update();
					//Draws the character
					player.Draw();
				}
				
	
				//LWJGL Display stuff
				Display.update();
				Display.sync(60);
			}
		}
	}
	
	
	/*
	 * gameOverUpdate() - Updates the screen in the case of a game over (player death). Allows user to choose to restart or go to the main menu.
	 */
	public void gameOverUpdate() {
		map.render(0, 0, 0, 0, 15, 20);
		//Updates the wave which updates each enemy's position and draws them
		for (Wave wave : waveList) {
			wave.Update();
		}
		playerList.get(0).Draw();
		
		DrawQuadTex(QuickLoad("gameOver"), 125, 120, 1024, 256);
		Rectangle restartRect = getBounds(500, 400, 256, 64);
		Rectangle menuRect = getBounds(500, 550, 256, 64);
		
		Texture restartTex = QuickLoad("restart");
		Texture menuTex = QuickLoad("menu");

		if (restartRect.contains(Mouse.getX(), Display.getHeight() - Mouse.getY() - 1)) {
			restartTex = QuickLoad("restartSelected");
		}
		else if (menuRect.contains(Mouse.getX(), Display.getHeight() - Mouse.getY() - 1)) {
			menuTex = QuickLoad("menuSelected");
		}
		
		DrawQuadTex(restartTex, 375, 400, 512, 64);
		DrawQuadTex(menuTex, 500, 550, 256, 64);
		
		if (Mouse.next()) {
			if (Mouse.isButtonDown(0)) {
				if (restartRect.contains(Mouse.getX(), Display.getHeight() - Mouse.getY() - 1)) {
					manager.restart = true;
					this.quitLevel = true;
				}
				else if (menuRect.contains(Mouse.getX(), Display.getHeight() - Mouse.getY() - 1)) {
					this.quitLevel = true;
					music.stop();
				}
			}
		}
		
		Display.update();
		Display.sync(60);
	}
	
	
	/*
	 * addEnemies() - Adds enemies to the level based on the type of map it is, so that the Level will create the appropriate amount of Waves.
	 */
	private void addEnemies(String mapType) {
		//Creates the basic robot enemy (See Enemy class if you want to change the arguments)
		Enemy enemy = new Enemy(QuickLoad("enemy"), 0, 0, map, 64, 64, 12, 100, 0, 10, playerList.get(0), this);
		enemyTypes.add(enemy);
	}
	
	
	/*
	 * setDifficulty() - Creates a wave based on the difficulty of the Level.
	 */
	private Wave setDifficulty(String difficulty, Enemy e, TiledMap map, Player player) {
		Wave wave = null;
		if (difficulty.equals("Easy")) {
			wave = new Wave(this, 60, e, map, player);
		}
		else if (difficulty.equals("Average")) {
			wave = new Wave(this, 45, e, map, player);
		}
		else if (difficulty.equals("Hard")) {
			wave = new Wave(this, 20, e, map, player);
		}
		else if (difficulty.equals("Insane")) {
			wave = new Wave(this, 5, e, map, player);
		}
		return wave;
	}
	
	public Rectangle getBounds(int xCoord, int yCoord, int xSize, int ySize) {
		return new Rectangle(xCoord, yCoord, xSize, ySize);
	}
}
