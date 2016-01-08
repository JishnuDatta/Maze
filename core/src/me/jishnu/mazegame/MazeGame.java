package me.jishnu.mazegame;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class MazeGame extends Game {
	private Screen currentScreen;
	
	@Override
	public void create () {
		currentScreen = new PlayScreen(this);
		setScreen(currentScreen);
	}

	@Override
	public void render () {
		super.render();
	}
}