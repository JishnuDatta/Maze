package me.jishnu.mazegame;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class MazeGame extends ApplicationAdapter {
	SpriteBatch batch;
	Texture img;
	private MazeGenerator maze;
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		maze = new MazeGenerator(1, 10, 10, batch);
		img = new Texture("badlogic.jpg");
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(1, 1, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
		maze.render();
		batch.end();
	}
}
