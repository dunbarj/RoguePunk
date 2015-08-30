package data;

import static helpers.Artist.DrawQuadTex;
import static helpers.Artist.QuickLoad;
import static helpers.Clock.Delta;

import java.awt.Rectangle;
//import java.util.ArrayList;




import java.util.ArrayList;
import java.util.Random;

import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.tiled.TiledMap;

public class Enemy {
	private int width, height, health, bulletSpeed, fireRate, bulletDamage;
	private float x, y, angle, xDelta, yDelta, origX, origY, timeSinceLastBurst;
	float speed;
	private float[][] diffCheck = new float[4][2];
	private Texture texture;
	private Tile startTile;
	private boolean first = true;
	private boolean topLeft, topRight, bottomLeft, bottomRight;
	public ArrayList<Bullet> bullets = new ArrayList<Bullet>();
	private TiledMap map;
	private Player player;
	private Level level;
	//private enemyWeapon w;

	public Enemy(Texture texture, float x, float y, TiledMap map, int width,
			int height, float speed, int health, int bulletSpeed, int bulletDamage,
			Player player, Level level) {
		this.level = level;

		//Tile stuff
		this.map = map;
		this.player = player;

		this.texture = texture;
		this.x = x;
		this.y = y;
		origX = x;
		origY = y;
		xDelta = 0;
		yDelta = 0;

		//Collision check stuff
		for (int i = 0; i < diffCheck.length; i++) {
			for (int j = 0; j < diffCheck[i].length; j++) {
				diffCheck[i][j] = 0;
			}
		}

		this.width = width;
		this.height = height;
		this.speed = speed;
		this.health = health;
		this.bulletSpeed = bulletSpeed;
		fireRate = 20;
		this.bulletDamage = bulletDamage;
		timeSinceLastBurst = 0;
	}

	public void Update() {
		if (first) {
			first = false;
		} 
		else {
			if (bulletSpeed > 0) {
				fireBurst();
			}
			origX = x;
			origY = y;
			float hyp = Math.abs(Delta() * (speed));
			this.angle = (float) ((Math.atan2(-((player.getY()) - this.y),(player.getX() - this.x))));
			x += (Math.cos(this.angle)* hyp);
			y += -(Math.sin(this.angle) * hyp);

			//Values to determine direction of travel
			xDelta = (float) (Math.cos(this.angle)* hyp);
			yDelta = (float) -(Math.sin(this.angle) * hyp);
			//Finding the directions of travel
			String[] direction = checkDirections();

			//Corner collision detection method
			float[][] cornerArray = getFloatCorners();
			topLeft = checkCorner(cornerArray[0]);
			topRight = checkCorner(cornerArray[1]);
			bottomLeft = checkCorner(cornerArray[2]);
			bottomRight = checkCorner(cornerArray[3]);
			updateDiffCheck();
			changeDirection(direction, hyp);

			if (this.hitPlayer(cornerArray)) {
				level.gameOver = true;
			}
		}
	}

	public boolean hitPlayer(float[][] cornerArray) {
		for (int i = 0; i < cornerArray.length; i++) {
			if (this.player.getRect().contains((int) cornerArray[i][0], (int) cornerArray[i][1])) {
				return true;
			}
		}
		return false;
	}

	public void updateBullets() {
		for (int i = 0; i < bullets.size(); i++) {
			boolean check = bullets.get(i).Update();
			bullets.get(i).Draw();
			if(!check) {
				bullets.remove(i);
			}
		}
	}

	public void fireBurst() {
		float[] array = getCenter();
		if (timeSinceLastBurst > fireRate) {
			timeSinceLastBurst = 0;
			Random r = new Random();
			int diff = r.nextInt(30);
			for (int i = 0; i < 5; i++) {
				bullets.add(new Bullet(level, map, QuickLoad("enemyBullet"), getCenter(), array[0] + 50, array[1], bulletSpeed, (i * 72) + diff, 500, true, bulletDamage, "burster", 0, player));
			}
		}
		else {
			timeSinceLastBurst += Delta();
		}
	}

	public String[] checkDirections() {
		String[] array = new String[2];
		if (xDelta > 0) {
			array[0] = "right";
		}
		else if (xDelta < 0) {
			array[0] = "left";
		}
		else {
			array[0] = "none";
		}
		if (yDelta > 0) {
			array[1] = "down";
		}
		else if (yDelta < 0) {
			array[1] = "up";
		}
		else {
			array[1] = "none";
		}	
		return array;
	}

	public void changeDirection(String[] direction, float hyp) {
		if (topLeft) {
			if (topRight) {
				y = origY;
				if (direction[0].equals("right"))
					x = origX + hyp;
				else
					x = origX - hyp;
			}
			if (bottomLeft) {
				x = origX;
				if (!topRight) {
					if (direction[1].equals("down"))
						y = origY + hyp;
					else 
						y = origY - hyp;
				}
			}
			if (bottomRight) {
				x = origX;
				y = origY;
			}
			if (!topRight && !bottomLeft && !bottomRight) {
				if (direction[0].equals("right")) { //If traveling right, must be traveling up
					y = origY;
					x = origX + hyp;
				}
				else if (direction[1].equals("down")) { //If traveling down, must be traveling left
					x = origX;
					y = origY + hyp;
				}
				else {
					if (diffCheck[0][0] < diffCheck[0][1]) {
						y = origY;
						x = origX - hyp;
					}
					else {
						x = origX;
						y = origY - hyp;
					}
				}
			}
		}	
		else if (topRight) {
			if (bottomRight) {
				x = origX;
				if (direction[1].equals("down"))
					y = origY + hyp;
				else 
					y = origY - hyp;
			}
			if (bottomLeft) {
				y = origY;
				if (!bottomRight) {
					if (direction[0].equals("right"))
						x = origX + hyp;
					else {
						x = origX - hyp;
					}
				}
			}
			if (!bottomRight && !bottomLeft) {
				if (direction[0].equals("left")) {
					y = origY;
					x = origX - hyp;
				}
				else if (direction[1].equals("down")) {
					x = origX;
					y = origY + hyp;
				}
				else {
					if (diffCheck[1][0] < diffCheck[1][1]) {
						y = origY;
						x = origX + hyp;
					}
					else {
						x = origX;
						y = origY - hyp;
					}
				}
			}
		}
		else if (bottomLeft) {
			if (bottomRight) {
				y = origY;
				if (direction[0].equals("right"))
					x = origX + hyp;
				else {
					x = origX - hyp;
				}
			}
			else {
				if (direction[0].equals("right")) {
					y = origY;
					x = origX + hyp;
				}
				else if (direction[1].equals("up")) {
					x = origX;
					y = origY - hyp;
				}
				else {
					if (diffCheck[2][0] < diffCheck[2][1]) {
						y = origY;
						x = origX - hyp;
					}
					else {
						x = origX;
						y = origY + hyp;
					}
				}
			}
		}
		else if (bottomRight) {
			if (direction[0].equals("left")) {
				y = origY;
				x = origX - hyp;
			}
			else if (direction[1].equals("up")) {
				x = origX;
				y = origY - hyp;
			}
			else {
				if (diffCheck[3][0] < diffCheck[3][1]) {
					y = origY;
					x = origX + hyp;
				}
				else {
					x = origX;
					y = origY + hyp;
				}
			}
		}
	}

	private void updateDiffCheck() {
		//TODO Replace with A* pathfinding for enemies eventually
		float[] array = new float[2];
		if (topLeft) {
			array[0] = (float) (getXPlace(x + 1) + (map.getTileWidth() / 2.0));
			array[1] = (float) (getYPlace(y + 1) + (map.getTileHeight() / 2.0));
			diffCheck[0][0] = Math.abs(array[0] - (x + (width / 2)));
			diffCheck[0][1] = Math.abs(array[1] - (y + (height / 2)));
		}
		if (topRight) {
			array[0] = (float) (getXPlace(x + this.width - 1) + (map.getTileWidth() / 2.0));
			array[1] = (float) (getYPlace(y + 1) + (map.getTileHeight() / 2.0));
			diffCheck[1][0] = Math.abs(array[0] - (x + (width / 2)));
			diffCheck[1][1] = Math.abs(array[1] - (y + (height / 2)));
		}
		if (bottomLeft) {
			array[0] = (float) (getXPlace(x + 1) + (map.getTileWidth() / 2.0));
			array[1] = (float) (getYPlace(y + this.height - 1) + (map.getTileHeight() / 2.0));
			diffCheck[2][0] = Math.abs(array[0] - (x + (width / 2)));
			diffCheck[2][1] = Math.abs(array[1] - (y + (height / 2)));
		}
		if (bottomRight) {
			array[0] = (float) (getXPlace(x + this.width - 1) + (map.getTileWidth() / 2.0));
			array[1] = (float) (getYPlace(y + this.height - 1) + (map.getTileHeight() / 2.0));
			diffCheck[3][0] = Math.abs(array[0] - (x + (width / 2)));
			diffCheck[3][1] = Math.abs(array[1] - (y + (height / 2)));
		}
	}

	private boolean checkCorner(float[] array) {
		int index = map.getLayerIndex("Walls");
		int tileID = map.getTileId(getXPlace(array[0]), getYPlace(array[1]), index);
		if (tileID == 0) {
			return false;
		}
		return true;
	}

	private float[][] getFloatCorners() {
		float[][] array = new float[4][2];
		array[0][0] = (float) getX()+1;
		array[0][1] = (float) getY()+1;
		array[1][0] = (float) getX() + getWidth()-1;
		array[1][1] = (float) getY()+1;
		array[2][0] = (float) getX()+1;
		array[2][1] = (float) getY() + getHeight()-1;
		array[3][0] = (float) getX() + getWidth()-1;
		array[3][1] = (float) getY() + getHeight()-1;
		return array;
	}

	public boolean takeDamage(int damage) {
		this.health -= damage;
		if (this.health <= 0) {
			this.destroy(this);
			return true;
		}
		return false;
	}

	public void destroy(Enemy e) {
		level.enemyList.remove(level.enemyList.indexOf(e));
	}

	public int getBulletDamage() {
		return bulletDamage;
	}

	public void setBulletDamage(int bulletDamage) {
		this.bulletDamage = bulletDamage;
	}

	/*
	 * getCenter() - Returns the center of this Enemy in the form of an X,Y coordinates stored in a float array.
	 */
	public float[] getCenter() {
		float[] array = new float[2];
		array[0] = (float) getX() + (width / 2);
		array[1] = (float) getY() + (height / 2);
		return array;
	}

	public int getBulletSpeed() {
		return bulletSpeed;
	}

	public void setBulletSpeed(int bulletSpeed) {
		this.bulletSpeed = bulletSpeed;
	}

	public Rectangle getBounds(int xCoord, int yCoord, int xSize, int ySize) {
		return new Rectangle(xCoord, yCoord, xSize, ySize);
	}

	public int getXPlace(float x) {
		return (int) x / 64;
	}

	public int getYPlace(float y) {
		return (int) y / 64;
	}

	public void Draw() {
		DrawQuadTex(texture, x, y, width, height);
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public int getHealth() {
		return health;
	}

	public void setHealth(int health) {
		this.health = health;
	}

	public float getSpeed() {
		return speed;
	}

	public void setSpeed(float speed) {
		this.speed = speed;
	}

	public float getX() {
		return x;
	}

	public void setX(float x) {
		this.x = x;
	}

	public float getY() {
		return y;
	}

	public void setY(float y) {
		this.y = y;
	}

	public Texture getTexture() {
		return texture;
	}

	public void setTexture(Texture texture) {
		this.texture = texture;
	}

	public Tile getStartTile() {
		return startTile;
	}

	public void setStartTile(Tile startTile) {
		this.startTile = startTile;
	}

	public boolean isFirst() {
		return first;
	}

	public void setFirst(boolean first) {
		this.first = first;
	}
}
