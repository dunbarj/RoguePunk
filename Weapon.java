package data;

import static helpers.Artist.QuickLoad;

import java.util.ArrayList;
import java.util.Random;

import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.newdawn.slick.tiled.TiledMap;

import static helpers.Clock.Delta;
//import static helpers.Artist.DrawQuadTex;

public class Weapon {

	private int bulletSpeed, fireRate, clipSize, maxClipSize, reloadTime, maxRange, damage;
	private float timeSinceLastShot = 0;
	private String currentWeapon;
	private boolean reloading = false; 
	private boolean unlimited = true;
	TileGrid grid;
	public Player owner = null;
	public TiledMap map;
	public Level level;

	public Weapon(TiledMap map, String type, Player p, Level level) {
		this.level = level;
		currentWeapon = type;
		owner = p;
		this.map = map;

		//Sets initial weapon values based on 'type' argument
		if (type.equals("pistol")) {
			//Set initial pistol values
			bulletSpeed = 60;
			fireRate = 5;
			clipSize = 16;
			maxClipSize = clipSize;
			reloadTime = 10;
			maxRange = 1000;
			damage = 20;
		}
		else if (type.equals("shotgun")) {
			//Set initial shotgun values
			bulletSpeed = 60;
			fireRate = 8;
			clipSize = 4;
			maxClipSize = clipSize;
			reloadTime = 10;
			maxRange = 500;
			damage = 15;
		}
		else if (type.equals("minigun")) {
			//Set initial minigun values
			bulletSpeed = 80;
			fireRate = 1;
			clipSize = 50;
			maxClipSize = clipSize;
			reloadTime = 20;
			maxRange = 500;
			damage = 10;
		}
		else if (type.equals("sniperRifle")) {
			//Set initial sniperRifle values
			bulletSpeed = 100;
			fireRate = 10;
			clipSize = 5;
			maxClipSize = clipSize;
			reloadTime = 20;
			maxRange = 1500;
			damage = 50;
		}
		else if (type.equals("bounceGun")) {
			//Set initial bounceGun values
			bulletSpeed = 80;
			fireRate = 2;
			clipSize = 20;
			maxClipSize = clipSize;
			reloadTime = 20;
			maxRange = 99999999;
			damage = 15;
		}
		else if (type.equals("laserGun")) {
			//Set initial laserGun values
			bulletSpeed = 100;
			fireRate = 0;
			clipSize = 1000;
			maxClipSize = clipSize;
			reloadTime = 20;
			maxRange = 250;
			damage = 2;
		}
	}

	//Fires the current weapon if enough time has passed
	public void fireWeapon() {
		if (!reloading) {
			if (currentWeapon.equals("pistol") && timeSinceLastShot > fireRate) {
				timeSinceLastShot = 0;
				pistol();
			}
			else if (currentWeapon.equals("shotgun") && timeSinceLastShot > fireRate) {
				timeSinceLastShot = 0;
				shotgun();
			}
			else if (currentWeapon.equals("minigun") && timeSinceLastShot > fireRate) {
				timeSinceLastShot = 0;
				minigun();
			}
			else if (currentWeapon.equals("sniperRifle") && timeSinceLastShot > fireRate) {
				timeSinceLastShot = 0;
				sniperRifle();
			}
			else if (currentWeapon.equals("bounceGun") && timeSinceLastShot > fireRate) {
				timeSinceLastShot = 0;
				bounceGun();
			}
			else if (currentWeapon.equals("laserGun") && timeSinceLastShot > fireRate) {
				timeSinceLastShot = 0;
				laserGun();
			}
		}
	}

	/**
	 * Updates the bullets fired by the Player
	 * and shoots upon mouse click if enough
	 * time has passed since the last shot.
	 */
	public void Update() {
		ArrayList<Bullet> removeList = new ArrayList<Bullet>();
		timeSinceLastShot += Delta();
		for (Bullet b : level.bullets) {
			boolean check = b.Update();
			b.Draw();
			if(!check) {
				removeList.add(b);
			}
		}
		for (Bullet b : removeList) {
			level.bullets.remove(b);
		}
		if (reloading) {
			if (timeSinceLastShot > reloadTime) {
				reloading = false;
				clipSize = maxClipSize;
			}
		}
	}

	//Fires a pistol round if there are remaining rounds in the clip
	public void pistol() {
		if (clipSize > 0) {
			level.bullets.add(new Bullet(level, map, QuickLoad("bullet"), owner.getCenter(), 
					Mouse.getX(), Display.getHeight() - Mouse.getY() - 1, 
					bulletSpeed, 0, maxRange, false, damage, "pistol", 0, owner));
			if (!unlimited) {
				clipSize--;
			}
		}
		else {
			reloading = true;
		}
	}

	//Fires a shotgun round if there are remaining rounds in the clip
	public void shotgun() {
		if (clipSize > 0) {
			for (int i = -10; i <= 10; i += 5) {
				level.bullets.add(new Bullet(level, map, QuickLoad("bullet"), owner.getCenter(), Mouse.getX(), Display.getHeight() - Mouse.getY() - 1, 
						bulletSpeed, i, maxRange, false, damage, "shotgun", 0, owner));
			}
			if (!unlimited) {
				clipSize--;
			}
		}
		else {
			reloading = true;
		}
	}

	//Fires a minigun round if there are remaining rounds in the clip
	public void minigun() {
		if (clipSize > 0) {
			Random r = new Random();
			int skew = r.nextInt(6);
			int sign = r.nextInt(2);
			if (sign == 0) {
				skew *= -1;
			}
			level.bullets.add(new Bullet(level, map, QuickLoad("bullet"), owner.getCenter(), Mouse.getX(), Display.getHeight() - Mouse.getY() - 1, 
					bulletSpeed, skew, maxRange, false, damage, "minigun", 0, owner));
			if (!unlimited) {
				clipSize--;
			}
		}
		else {
			reloading = true;
		}
	}

	//Fires a sniper round if there are remaining rounds in the clip
	public void sniperRifle() {
		if (clipSize > 0) {
			level.bullets.add(new Bullet(level, map, QuickLoad("bullet"), owner.getCenter(), 
					Mouse.getX(), Display.getHeight() - Mouse.getY() - 1, 
					bulletSpeed, 0, maxRange, false, damage, "sniperRifle", 0, owner));
			if (!unlimited) {
				clipSize--;
			}
		}
		else {
			reloading = true;
		}
	}

	public void bounceGun() {
		if (clipSize > 0) {
			level.bullets.add(new Bullet(level, map, QuickLoad("bullet"), owner.getCenter(), 
					Mouse.getX(), Display.getHeight() - Mouse.getY() - 1, 
					bulletSpeed, 0, maxRange, false, damage, "bounceGun", 2, owner));
		}
		else {
			reloading = true;
		}
	}

	public void laserGun() {
		if (clipSize > 0) {
			level.bullets.add(new Bullet(level, map, QuickLoad("laser"), owner.getCenter(), 
					Mouse.getX(), Display.getHeight() - Mouse.getY() - 1, 
					bulletSpeed, 0, maxRange, false, damage, "laserGun", 0, owner));
		}
		else {
			reloading = true;
		}
	}
}
