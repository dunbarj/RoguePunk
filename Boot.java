package data;

import java.awt.Rectangle;

//import helpers.Clock;



import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.newdawn.slick.opengl.Texture;

import static helpers.Artist.*;

public class Boot {

	public static boolean gameOver = false;
	public boolean quit = false;
	public boolean start = false;
	public TileGrid currentMap;
	public float timeHere;

	public static void main(String[] args) {
		new Boot();
	}
	
	public Boot() { 

		BeginSession();
		
		MapGenerator map = new MapGenerator(3);

		Rectangle startRect = getBounds(500, 400, 256, 64);
		Rectangle quitRect = getBounds(500, 550, 256, 64);
		
		while (!Display.isCloseRequested() && !this.quit) {
			map.currentMap.Draw();
			DrawQuadTex(QuickLoad("roguePunk"), 125, 120, 1024, 256);
			Texture startTex = QuickLoad("start");
			Texture quitTex = QuickLoad("quit");

			if (startRect.contains(Mouse.getX(), Display.getHeight() - Mouse.getY() - 1)) {
				startTex = QuickLoad("startSelected");
			}
			else if (quitRect.contains(Mouse.getX(), Display.getHeight() - Mouse.getY() - 1)) {
				quitTex = QuickLoad("quitSelected");
			}
			
			DrawQuadTex(startTex, 500, 400, 256, 64);
			DrawQuadTex(quitTex, 500, 550, 256, 64);
			
			if (Mouse.next()) {
				if (Mouse.isButtonDown(0)) {
					if (startRect.contains(Mouse.getX(), Display.getHeight() - Mouse.getY() - 1)) {
						start = true;
					}
					else if (quitRect.contains(Mouse.getX(), Display.getHeight() - Mouse.getY() - 1)) {
						quit = true;
					}
				}
			}
			
			if (start) {
				new LevelManager("Average", 1);
				try {
					Thread.sleep(500);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			start = false;
			
			Display.update();
			Display.sync(60);
		}

		Display.destroy();
	}

	public static void setGameOver(boolean newVal) {
		// TODO Auto-generated method stub
		gameOver = newVal;
	}
	
	public Rectangle getBounds(int xCoord, int yCoord, int xSize, int ySize) {
		return new Rectangle(xCoord, yCoord, xSize, ySize);
	}
}
