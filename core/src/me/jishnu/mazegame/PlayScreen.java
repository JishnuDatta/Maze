package me.jishnu.mazegame;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import box2dLight.RayHandler;

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
    private RayHandler rayHandler;
    private float playerXDirection, playerYDirection;
    private Array<Body> bodyDeleteList;
    private Array<Coordinates> bodyCreateList;
    private TextureAtlas atlas;

    public PlayScreen(Game game) {
        //Please enter non-small odd numbers please into parameters please.
        this.game = game;
        batch = new SpriteBatch();
        world = new World(new Vector2(0,0), true);
        rayHandler = new RayHandler(world);
        atlas = new TextureAtlas("Assets.atlas");

        gamecam = new OrthographicCamera(1280* MazeGame.SCALING,720* MazeGame.SCALING);
        gamePort = new FitViewport(MazeGame.WIDTH * MazeGame.SCALING, MazeGame.HEIGHT * MazeGame.SCALING, gamecam);
        gamecam.position.set(1280/2 * MazeGame.SCALING, 720/2 * MazeGame.SCALING, 0);
            //Make sure this is even!
        maze = new MazeGenerator(1);
        mazeGui = new MazeGeneratorTesterGui(maze, this);
        b2dr = new Box2DDebugRenderer();
        world.setContactListener(new WorldContactListener());
        //0 = red, 1 = yellow, 2 = green, 3 = blue
        player = new Player(this, new Coordinates(0,2,2), 0);
        mazeGui.createBox2DStuff(world);
        gamecam.zoom = 0.05f;
        //gamecam.zoom = 2f;
        bodyDeleteList = new Array<Body>();
        bodyCreateList = new Array<Coordinates>();
        rayHandler.setShadows(false);
    }//&& Math.pow((Math.pow(,2) + Math.pow(player.body.getLinearVelocity().y, 2)), 0.5) <= 30 * MazeGame.SCALING

    public void handleInput(float dt) {
        playerXDirection = (float) (20 * Math.cos(player.body.getAngle()));
        playerYDirection = (float) (20 * Math.sin(player.body.getAngle()));
        if (Gdx.input.isKeyPressed(Input.Keys.UP))
            player.body.applyLinearImpulse(new Vector2(playerXDirection, playerYDirection), player.body.getWorldCenter(), true);
        if (Gdx.input.isKeyPressed(Input.Keys.DOWN))
            player.body.applyLinearImpulse(new Vector2(-playerXDirection, -playerYDirection), player.body.getWorldCenter(), true);
            if (Gdx.input.isKeyPressed(Input.Keys.RIGHT))
                player.body.setTransform(player.body.getPosition(), player.body.getAngle() - (2 * (float) Math.PI) * dt);
            if (Gdx.input.isKeyPressed(Input.Keys.LEFT))
                player.body.setTransform(player.body.getPosition(), player.body.getAngle() + (2* (float)Math.PI)*dt);
        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE))
            player.torchButton();
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float dt) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        handleInput(dt);
        gamecam.position.x = player.body.getPosition().x;
        gamecam.position.y = player.body.getPosition().y;
            player.update(dt);
        gamecam.update();
        batch.setProjectionMatrix(gamecam.combined);
        world.step(1 / 60f, 6, 2);
        batch.begin();
        mazeGui.render(dt, batch);
        player.render(batch);
        batch.end();
        b2dr.render(world, gamecam.combined);
        rayHandler.setCombinedMatrix(gamecam);
        rayHandler.updateAndRender();
        for(Body body: bodyDeleteList){
            world.destroyBody(body);
        }
        bodyDeleteList.clear();
        for(Coordinates c: bodyCreateList){
            player.createBody(c);
        }
        bodyCreateList.clear();
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

    public RayHandler getRayHandler() {
        return rayHandler;
    }

    public void addToDeleteList(Body body){
        bodyDeleteList.add(body);
    }

    public void addToCreateList(Coordinates c){
        bodyCreateList.add(c);
    }

    public MazeGenerator getMaze() {
        return maze;
    }

    public TextureAtlas getAtlas() {
        return atlas;
    }

    public MazeGeneratorTesterGui getMazeGui() {
        return mazeGui;
    }
}
