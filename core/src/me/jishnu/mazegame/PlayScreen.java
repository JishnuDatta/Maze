package me.jishnu.mazegame;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class PlayScreen  implements Screen{
    private Game game;
    private SpriteBatch batch;
    private MazeGenerator maze;
    private MazeGeneratorTesterGui mazeGui;

    public PlayScreen(Game game) {
        //Please enter non-small odd numbers please into parameters please.
        this.game = game;
        batch = new SpriteBatch();
        maze = new MazeGenerator(5, 25, 25);
        mazeGui = new MazeGeneratorTesterGui(maze);
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float dt) {
        maze.update(dt);
        Gdx.gl.glClearColor(0.5f, 0.5f, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        mazeGui.render(dt, batch);
        batch.end();
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}
