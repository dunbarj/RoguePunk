package data;

import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.tiled.TiledMap;

import java.awt.Rectangle;
import java.util.ArrayList;

import static helpers.Artist.DrawQuadTex;
import static helpers.Artist.QuickLoad;
import static helpers.Clock.Delta;

public class Bullet {
	
	private int bulletSpeed, width, height, maxRange, damage, bounces, maxBounces;
	private Texture texture;
	private boolean first = true;
	private boolean isEnemy;
	private String weapon;
	private float x, y, angle, distance;
	private TiledMap map;
	public Player player;
	public Level level;
	
	public Bullet(Level level, TiledMap map, Texture texture, float[] array, float mouseX, float mouseY, int bulletSpeed, 
			float skew, int range, boolean enemy, int damage, String weapon, int maxBounces, Player player) {
		this.level = level;
		this.map = map;
		this.player = player;
		this.texture = texture;
		this.bulletSpeed = bulletSpeed;
		this.x = array[0];
		this.y = array[1];
		this.width = 10;
		this.height = 10;
		this.angle = (float) (((Math.atan2(-((mouseY) - y),(mouseX - x)))) + Math.toRadians(skew));
		isEnemy = enemy;
		maxRange = range;
		distance = 0;
		this.damage = damage;
		this.weapon = weapon;
		if (weapon.equals("bounceGun")) {
			bounces = 0;
			this.maxBounces = maxBounces;
		}
	}

	public boolean Update() {
		if (first) {
			first = false;
		}
		else {
			float hyp = Math.abs(Delta() * (bulletSpeed));
			x += (Math.cos(this.angle) * hyp);
			y += -(Math.sin(this.angle) * hyp);
			distance += hyp;
			
			//Check for collision
			int[][] array = new int[4][2];
			array = this.getCornerPlaces();
			
			if (!isEnemy) {
				//Collision with enemies
				float[][] floatArray = getCornerCoords();
				ArrayList<Enemy> enemyList = level.enemyList;
				for (Enemy e: enemyList) {
					Rectangle r = e.getBounds((int) e.getX(), (int) e.getY(), 
							(int) e.getWidth(), (int) e.getHeight()); 
					for (int j = 0; j < floatArray.length; j++) {
						if (r.contains((double) floatArray[j][0], (double) floatArray[j][1])) {
							boolean kill = e.takeDamage(this.damage);
							if (kill && weapon.equals("sniperRifle")) {
								return true;
							}
							else {
								//Will be destroyed
								texture = QuickLoad("bulletExplosion");
								x -= 5;
								y -= 5;
								setWidth(30);
								setHeight(30);
								return false; //Destroys bullet
							}
						}
					}
				}
			}
			else {
				//Collision with Player
				float[][] floatArray = getCornerCoords();
					Rectangle r = getBounds((int) player.getX(), (int) player.getY(), (int) 35, (int) 64); 
					for (int j = 0; j < floatArray.length; j++) {
						if (r.contains((double) floatArray[j][0], (double) floatArray[j][1])) {
							Player.takeDamage(this.damage);
							return false; //Destroys bullet
							}
						}
			}
			
			//Collision with walls
			if (checkCorner(array[0]) || checkCorner(array[1])
				 || checkCorner(array[2]) || checkCorner(array[3])) {
				if (weapon.equals("bounceGun") && (bounces < maxBounces)) {
					bounces++;
					boolean bounceFail = changeAngle(weapon, array);
					return bounceFail;
				}
				else {
					//Will be destroyed
					texture = QuickLoad("bulletExplosion");
					x -= 5;
					y -= 5;
					setWidth(30);
					setHeight(30);
					return false;
				}
			} 
			
			//Check if it has reached max range
			if (distance > maxRange) {
				return false;
			}
		}
		return true;
	}
	
	public void Draw() {
		DrawQuadTex(this.texture, this.x, this.y, this.width, this.height);
	}
	 
	
	private boolean checkCorner(int[] array) {
		if (array[0] < 20 && array[1] < 15 && array[0] >= 0 && array[1] >= 0) {
			int index = map.getLayerIndex("Walls");
			int tileID = map.getTileId(array[0], array[1], index); 
			if (tileID != 0) {
				return true;
			}
			return false;
		}
		return true;
	}
	
	private int[][] getCornerPlaces() {
		int[][] array = new int[4][2];
		array[0][0] = (int) (getX() / 64);
		array[0][1] = (int) (getY() / 64);
		array[1][0] = (int) (getX() + getWidth()) / 64;
		array[1][1] = (int) (getY() / 64);
		array[2][0] = (int) (getX() / 64);
		array[2][1] = (int) (getY() + getHeight()) / 64;
		array[3][0] = (int) (getX() + getWidth()) / 64;
		array[3][1] = (int) (getY() + getHeight()) / 64;
		return array;
	}
	
	private float[][] getCornerCoords() {
		float[][] array = new float[4][2];
		array[0][0] = getX();
		array[0][1] = getY();
		array[1][0] = getX() + getWidth();
		array[1][1] = getY();
		array[2][0] = getX();
		array[2][1] = getY() + getHeight();
		array[3][0] = getX() + getWidth();
		array[3][1] = getY() + getHeight();
		return array;
	}
	
	public boolean changeAngle(String weapon, int array[][]) {
		if (weapon.equals("bounceGun")) {
			if (checkCorner(array[0]) && checkCorner(array[1]) && checkCorner(array[2]) && checkCorner(array[3])) {
				if (Math.abs(this.angle) > (Math.PI / 4) && Math.abs(this.angle) < (3 * Math.PI / 4)) {
					this.angle = -(this.angle);
					return true;
				}
				else {
					this.angle = -(this.angle) + (float) Math.PI;
					return true;
				}
				/**
				//Will be destroyed
				texture = QuickLoad("bulletExplosion");
				x -= 5;
				y -= 5;
				setWidth(30);
				setHeight(30);
				return false;
				*/
			}
			else if (checkCorner(array[0]) && checkCorner(array[1]) || checkCorner(array[2]) && checkCorner(array[3])) {
				this.angle = -(this.angle);
				return true;
			}
			else if (checkCorner(array[0]) && checkCorner(array[2]) || checkCorner(array[1]) && checkCorner(array[3])) {
				this.angle = -(this.angle) + (float) Math.PI;
				return true;
			}
		}
		return false;
	}
	
	public Rectangle getBounds(int xCoord, int yCoord, int xSize, int ySize) {
		 return new Rectangle(xCoord, yCoord, xSize, ySize);
	}
	
	public int getMaxBounces() {
		return maxBounces;
	}

	public void setMaxBounces(int maxBounces) {
		this.maxBounces = maxBounces;
	}

	public int getDamage() {
		return damage;
	}

	public void setDamage(int damage) {
		this.damage = damage;
	}

	public int getXPlace(float x) {
		return (int) x / 64;
	}

	public int getYPlace(float y) {
		return (int) y / 64;
	}

	public int getBulletSpeed() {
		return bulletSpeed;
	}

	public void setBulletSpeed(int bulletSpeed) {
		this.bulletSpeed = bulletSpeed;
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
}
