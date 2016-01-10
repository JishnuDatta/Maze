package me.jishnu.mazegame;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class PlayScreen implements Screen{
    private Game game;
    private SpriteBatch batch;
    private MazeGenerator maze;
    private MazeGeneratorTesterGui mazeGui;
    private World world;
    private OrthographicCamera gamecam;
    private Viewport gamePort;
    private Player player;
    private Box2DDebugRenderer b2dr;

    public PlayScreen(Game game) {
        //Please enter non-small odd numbers please into parameters please.
        this.game = game;
        batch = new SpriteBatch();
        world = new World(new Vector2(0,0), true);
        gamecam = new OrthographicCamera(1280,720);
        gamePort = new FitViewport(MazeGame.WIDTH / MazeGame.PPM, MazeGame.HEIGHT / MazeGame.PPM, gamecam);
        gamecam.position.set(1280/2, 720/2, 0);
            //Make sure this is even!
        maze = new MazeGenerator(5);
        mazeGui = new MazeGeneratorTesterGui(maze);
        b2dr = new Box2DDebugRenderer();
        player = new Player(this, new Rectangle(0,0,100,100));
        mazeGui.createBox2DStuff(world);
    }

    public void handleInput() {
        if (Gdx.input.isKeyPressed(Input.Keys.UP) && player.body.getLinearVelocity().y <= 2)
                player.body.applyLinearImpulse(new Vector2(0, 0.1f), player.body.getWorldCenter(), true);
        if (Gdx.input.isKeyPressed(Input.Keys.DOWN) && player.body.getLinearVelocity().y >= -2)
            player.body.applyLinearImpulse(new Vector2(0, -0.1f), player.body.getWorldCenter(), true);
            if (Gdx.input.isKeyPressed(Input.Keys.RIGHT) && player.body.getLinearVelocity().x <= 2)
                player.body.applyLinearImpulse(new Vector2(0.1f, 0), player.body.getWorldCenter(), true);
            if (Gdx.input.isKeyPressed(Input.Keys.LEFT) && player.body.getLinearVelocity().x >= -2)
                player.body.applyLinearImpulse(new Vector2(-0.1f, 0), player.body.getWorldCenter(), true);
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float dt) {
        Gdx.gl.glClearColor(0.5f, 0.5f, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        handleInput();
        gamecam.update();
        batch.setProjectionMatrix(gamecam.combined);
        world.step(1 / 120f, 6, 2);
        batch.begin();
        mazeGui.render(dt, batch);
        batch.end();
        b2dr.render(world, gamecam.combined);
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
        world.dispose();
    }

    public World getWorld() {
        return world;
    }
}
