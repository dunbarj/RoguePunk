package data;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;

import java.awt.Rectangle;

import static helpers.Clock.Delta;
import static helpers.Artist.DrawQuadTex;
import static helpers.Artist.QuickLoad;

/**
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.AbstractAction;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.KeyStroke;
 */


//import org.lwjgl.input.Mouse;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.tiled.TiledMap;


public class Player {

	private int width;
	private int height;
	private static int health;
	public float x, y, originX, originY, mapRelativeX, mapRelativeY;
	private float speed;
	private Texture texture;
	private boolean first = true;
	private Weapon w;
	private TiledMap map;
	private LevelManager manager;
	public boolean playerMoveX, playerMoveY;

	public Player(LevelManager manager, TiledMap map, Texture texture, int width,
			int height, float speed) {
		this.manager = manager;
		this.map = map;

		this.texture = texture;
		this.x = (Display.getWidth() / 2) - (width / 2);
		this.y = (Display.getHeight() / 2) - (height / 2);
		originX = this.x;
		originY = this.y;
		setWidth(width);
		setHeight(height);
		this.speed = speed;
		health = 100;

		//Initial weapon values for player
		w = new Weapon(map, "pistol", this, this.manager.currentLevel);
	}

	/**
	 * Updates the position, direction texture, and weapon of 
	 * the Player according to key inputs.
	 */
	public void Update() {
		if (first) {
			first = false;
		}
		else {
			//Checks direction of movement
			boolean[] direction = new boolean[4];
			for (int i = 0; i < direction.length; i++) {
				direction[i] = false;
			}
			if (Keyboard.isKeyDown(Keyboard.KEY_UP) || Keyboard.isKeyDown(Keyboard.KEY_W)){
				MovePlayer("up");
				direction[0] = true;
			}
			if (Keyboard.isKeyDown(Keyboard.KEY_RIGHT) || Keyboard.isKeyDown(Keyboard.KEY_D)){
				MovePlayer("right");
				direction[1] = true;
			}
			if (Keyboard.isKeyDown(Keyboard.KEY_DOWN) || Keyboard.isKeyDown(Keyboard.KEY_S)){
				MovePlayer("down");
				direction[2] = true;
			}
			if (Keyboard.isKeyDown(Keyboard.KEY_LEFT) || Keyboard.isKeyDown(Keyboard.KEY_A)){
				MovePlayer("left");
				direction[3] = true;
			}
			if (Mouse.isButtonDown(0)) {
				//Play shoot sound!
				w.fireWeapon();
			}

			//Check for single key presses to switch weapon
			while (Keyboard.next()) {
				if (Keyboard.isKeyDown(Keyboard.KEY_1)){
					switchWeapon("pistol");
				}
				if (Keyboard.isKeyDown(Keyboard.KEY_2)){
					switchWeapon("shotgun");
				}
				if (Keyboard.isKeyDown(Keyboard.KEY_3)){
					switchWeapon("minigun");
				}
				if (Keyboard.isKeyDown(Keyboard.KEY_4)){
					switchWeapon("sniperRifle");
				}
				if (Keyboard.isKeyDown(Keyboard.KEY_5)){
					switchWeapon("bounceGun");
				}
				if (Keyboard.isKeyDown(Keyboard.KEY_6)){
					switchWeapon("laserGun");
				}
			}

			//Changes texture based on the direction of the mouse from the Player
			float angle = (float) -Math.toDegrees(Math.atan2(Display.getHeight() - Mouse.getY() - 1 - (y + height / 2),(Mouse.getX() - (x + width / 2))));
			changeTexture(angle);

			//Updates the weapon
			w.Update();
		}
	}

	/**
	 * Moves the Player in the direction specified
	 * @param direction
	 */
	private void MovePlayer(String direction) {
		float origX = x;
		float origY = y;
		
		//Changes x/y position based on direction of movement
		if (direction.equals("right")) {
			if (playerMoveX) {
				this.x += Delta() * (speed / 3);
				this.mapRelativeX  = this.x;
			}
			else {
				float temp = Delta() * (speed / 3);
				manager.currentLevel.x -= temp;
				this.mapRelativeX += temp;
			}
		}
		if (direction.equals("up")) {
			if (playerMoveY) {
				this.y -= Delta() * (speed / 3);
			}
			else {
				manager.currentLevel.y += Delta() * (speed / 3);
			}
		}
		if (direction.equals("left")) {
			if (playerMoveX) {
				this.x -= Delta() * (speed / 3);
			}
			else {
				manager.currentLevel.x += Delta() * (speed / 3);
			}
		}
		if (direction.equals("down")) {
			if (playerMoveY) {
				this.y += Delta() * (speed / 3);
			}
			else {
				manager.currentLevel.y -= Delta() * (speed / 3);
			}
		}

		if (manager.currentLevel.x < 0) {
			manager.currentLevel.mapX++;
			manager.currentLevel.x = map.getTileWidth();
		}
		if (manager.currentLevel.x > map.getTileWidth()) {
			manager.currentLevel.mapX--;
			manager.currentLevel.x = 0;
		}
		if (manager.currentLevel.y < 0) {
			manager.currentLevel.mapY++;
			manager.currentLevel.y = map.getTileHeight();
		}
		if (manager.currentLevel.y > map.getTileHeight()) {
			manager.currentLevel.mapY--;
			manager.currentLevel.y = 0;
		}
		
		//Check to stop scrolling at map edges...may implement later.
		/*
		if (this.x > originX) {
			playerMoveX = false;
			x = originX;
		}
		else {
			if (manager.currentLevel.mapX > map.getWidth()) {
				playerMoveX = true;
			}
			if (manager.currentLevel.mapX < 0) {
				playerMoveX = true;
			}
		}
		if (this.y > originY) {
			playerMoveY = false;
			y = originY;
		}
		else {
			if (manager.currentLevel.mapY > map.getHeight()) {
				playerMoveY = true;
			}
			if (manager.currentLevel.mapY < 0) {
				playerMoveY = true;
			}
		}
		*/
		
		/**
		 * Checks the corners and resets the new x/y to the 
		 * original x/y if at least one corner of the player 
		 * is inside a non-walkable tile.
		 */
		float[][] array = new float[4][2];
		array = getCorners();
		if (checkCorner(array[0]) || checkCorner(array[1])
				|| checkCorner(array[2]) || checkCorner(array[3])) {
			x = origX;
			y = origY;
		}
	}

	/**
	 * Changes the texture based on the direction of aiming.
	 */
	private void changeTexture(float angle) {
		if (angle > -22.5 && angle <= 22.5) {
			setTexture(QuickLoad("playerRight"));
		}
		if (angle > 22.5 && angle <= 67.5) {
			setTexture(QuickLoad("playerBackRight"));
		}
		if (angle > 67.5 && angle <= 112.5) {
			setTexture(QuickLoad("playerBack"));
		}
		if (angle > 112.5 && angle <= 157.5) {
			setTexture(QuickLoad("playerBackLeft"));
		}
		if (angle > 157.5 || angle <= -157.5) {
			setTexture(QuickLoad("playerLeft"));
		}
		if (angle > -157.5 && angle <= -112.5) {
			setTexture(QuickLoad("playerForwardLeft"));
		}
		if (angle > -112.5 && angle <= -67.5) {
			setTexture(QuickLoad("playerForward"));
		}
		if (angle > -67.5 && angle <= -22.5) {
			setTexture(QuickLoad("playerForwardRight"));
		}
	}

	/**
	 * Checks a corner with a set of x/y coordinates
	 * and sees if it intersects with a non-walkable tile
	 */
	private boolean checkCorner(float[] array) {
		int index = map.getLayerIndex("Walls");
		int tileID = map.getTileId(getXPlace(array[0]), getYPlace(array[1]), index);
		if (tileID == 0) {
			return false;
		}
		return true;
	}

	/**
	 * Returns a float array of the coordinates of the Player
	 */
	private float[][] getCorners() {
		float[][] array = new float[4][2];
		array[0][0] = (float) x;
		array[0][1] = (float) y;
		array[1][0] = (float) x + width;
		array[1][1] = (float) y;
		array[2][0] = (float) x;
		array[2][1] = (float) y + height;
		array[3][0] = (float) x + width;
		array[3][1] = (float) y + height;
		return array;
	}

	/**
	 * Returns a float array of the center coordinates of the Player
	 */
	public float[] getCenter() {
		float[] array = new float[2];
		array[0] = (float) x + (width / 2);
		array[1] = (float) y + (height / 2);
		return array;
	}

	/**
	 * Changes the Player's weapon to the one specified
	 * as an argument
	 */
	public void switchWeapon(String weapon) {
		w = null;
		w = new Weapon(map, weapon, this, manager.currentLevel);
	}

	/**
	 * Reduces the Player's health by the amount
	 * specified by the argument 'damage'
	 * @param damage
	 */
	public static void takeDamage(int damage) {
		health -= damage;
		if (health <= 0) {
			health = 100;
			System.out.println("Game Over");
		}
	}

	public Rectangle getRect() {
		return getBounds((int) this.x, (int) this.y, this.height, this.width);
	}
	
	//Creates a new rectangle based on the coordinates of the Player
	public Rectangle getBounds(int xCoord, int yCoord, int xSize, int ySize) {
		return new Rectangle(xCoord, yCoord, xSize, ySize);
	}

	public void Draw() {
		DrawQuadTex(texture, x, y, width, height);
	}

	public int getXPlace(float x) {
		return (int) x / 64;
	}

	public int getYPlace(float y) {
		return (int) y / 64;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int newWidth) {
		width = newWidth;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int newHeight) {
		height = newHeight;
	}

	public static int getHealth() {
		return health;
	}

	public static void setHealth(int newHealth) {
		health = newHealth;
	}

	public float getSpeed() {
		return speed;
	}

	public void setSpeed(float d) {
		this.speed = d;
	}

	public float getX() {
		return x;
	}

	public void setX(float xNew) {
		x = xNew;
	}

	public float getY() {
		return y;
	}

	public void setY(float yNew) {
		y = yNew;
	}

	public Texture getTexture() {
		return texture;
	}

	public void setTexture(Texture texture) {
		this.texture = texture;
	}

	public boolean isFirst() {
		return first;
	}

	public void setFirst(boolean first) {
		this.first = first;
	}
}