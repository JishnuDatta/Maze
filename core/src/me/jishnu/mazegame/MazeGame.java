package me.jishnu.mazegame;


import com.badlogic.gdx.Game;

import me.jishnu.mazegame.Screens.PlayScreen;
import me.jishnu.mazegame.Screens.WaitScreen;
import me.jishnu.mazegame.Tools.Constants;
import me.jishnu.mazegame.Tools.Coordinates;
import me.jishnu.mazegame.Tools.MazeGenerator;

public class MazeGame extends Game{
	private PlayScreen playScreen;
	private Constants.teams team;
	private Coordinates coordinates;
	private MazeGenerator generator;

	@Override
	public void render() {
		super.render();
	}

	public void create() {
		//setScreen(new WaitScreen());
		setScreen(new PlayScreen(new MazeGenerator(5), new Coordinates(0,2,2), Constants.teams.BLUE_TEAM));
	}
}
