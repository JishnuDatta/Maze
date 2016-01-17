package me.jishnu.mazegame;


import com.badlogic.gdx.Game;

import java.util.concurrent.ThreadLocalRandom;

import me.jishnu.mazegame.Screens.PlayScreen;
import me.jishnu.mazegame.Tools.Constants;
import me.jishnu.mazegame.Tools.MazeGenerator;

public class MazeGame extends Game{
	@Override
	public void render() {
		super.render();
	}

	public void create() {
		//setScreen(new WaitScreen());
		Constants.teams[] teams = {Constants.teams.RED_TEAM, Constants.teams.BLUE_TEAM, Constants.teams.GREEN_TEAM, Constants.teams.YELLOW_TEAM};
		int indexTeam = ThreadLocalRandom.current().nextInt(0, teams.length);
		MazeGenerator mazeGen = new MazeGenerator(Constants.MAZE_LEVELS);
		setScreen(new PlayScreen(mazeGen, mazeGen.getTeamBases().get(teams[indexTeam]), teams[indexTeam], this));
	}
}
