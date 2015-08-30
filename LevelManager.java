package data;

import java.util.ArrayList;

public class LevelManager {
	
	public String difficulty;
	public int numPlayers;
	public boolean playing = true;
	public int levelNum = 0;
	private ArrayList<Level> levelList;
	private final int maxLevels = 1;
	private final String[] levelTypes = {"basic", "lava", "water", "ice", "final"};
	public boolean done = false;
	public boolean restart = true;
	public Level currentLevel;
	
	public LevelManager(String difficulty, int numPlayers) {
		this.difficulty = difficulty;
		this.numPlayers = numPlayers;
		runGame();
	}
	
	/*
	 * runGame() - Runs the game by initializing the levels and running them.
	 */
	private void runGame() {
		while (restart) {
			restart = false;
			levelList = new ArrayList<Level>();
			for (int i = 0; i < maxLevels; i++) {
				levelList.add(new Level(this, levelTypes[levelNum], difficulty));
			}
			for (int i = 0; i < maxLevels; i++) {	
				currentLevel = levelList.get(levelNum);
				levelList.get(levelNum).play();
			}
		}
	}
}
