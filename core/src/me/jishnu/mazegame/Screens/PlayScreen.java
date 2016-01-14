package me.jishnu.mazegame.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.FitViewport;

import box2dLight.RayHandler;
import me.jishnu.mazegame.Tools.Constants;
import me.jishnu.mazegame.Tools.Coordinates;
import me.jishnu.mazegame.Tools.MazeGenerator;
import me.jishnu.mazegame.Tools.MazeGeneratorTesterGui;
import me.jishnu.mazegame.Tools.WorldContactListener;

public class PlayScreen implements Screen{
    private SpriteBatch batch;
    private MazeGenerator maze;
    private MazeGeneratorTesterGui mazeGui;
    private World world;
    private OrthographicCamera gamecam;
    private me.jishnu.mazegame.InteractiveObjects.Player player;
    private Array<me.jishnu.mazegame.InteractiveObjects.Player> otherPlayers;
    private Box2DDebugRenderer b2dr;
    private RayHandler rayHandler;
    private Array<Body> bodyDeleteList;
    private Array<Coordinates> bodyCreateList;
    private TextureAtlas atlas;

    public PlayScreen(MazeGenerator maze, Coordinates c , Constants.teams team) {
        batch = new SpriteBatch();
        world = new World(new Vector2(0,0), true);
        rayHandler = new RayHandler(world);
        atlas = new TextureAtlas("Assets.atlas");

        gamecam = new OrthographicCamera(1280* Constants.SCALING,720* Constants.SCALING);
        new FitViewport(Constants.WIDTH * Constants.SCALING, Constants.HEIGHT * Constants.SCALING, gamecam);
        gamecam.zoom = 0.05f;
        //gamecam.zoom = 1f;

        this.maze = maze;
        mazeGui = new MazeGeneratorTesterGui(maze, this);
        mazeGui.createBox2DStuff(world);

        b2dr = new Box2DDebugRenderer();
        world.setContactListener(new WorldContactListener());

        this.player = new me.jishnu.mazegame.InteractiveObjects.Player(this, c, team);
        System.out.println("created player");
        otherPlayers = new Array<me.jishnu.mazegame.InteractiveObjects.Player>();
        bodyDeleteList = new Array<Body>();
        bodyCreateList = new Array<Coordinates>();
        rayHandler.setShadows(false);
        //rayHandler.setBlur(false);
    }

    public void handleInput(float dt) {
        player.handleInput(dt);
        for(me.jishnu.mazegame.InteractiveObjects.Player player: otherPlayers){
            player.handleInput(dt);
        }
    }

    public void addPlayer(){
        otherPlayers.add(new me.jishnu.mazegame.InteractiveObjects.Player(this, new Coordinates(0,2,2), Constants.teams.RED_TEAM));
    }

    @Override
    public void show() {
    }

    @Override
    public void render(float dt) {
        Gdx.gl.glClearColor(1, 1, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        handleInput(dt);
        player.update(dt);
        for(me.jishnu.mazegame.InteractiveObjects.Player player: otherPlayers){
            player.update(dt);
        }
        gamecam.position.x = player.body.getPosition().x;
        gamecam.position.y = player.body.getPosition().y;

        gamecam.update();
        batch.setProjectionMatrix(gamecam.combined);
        world.step(1 / 60f, 6, 2);
        batch.begin();
        mazeGui.render(dt, batch);
        player.render(batch);
        for(me.jishnu.mazegame.InteractiveObjects.Player player: otherPlayers){
            player.render(batch);
        }
        batch.end();
       // b2dr.render(world, gamecam.combined);
        rayHandler.setCombinedMatrix(gamecam);
        rayHandler.updateAndRender();
        for(Body body: bodyDeleteList){
            world.destroyBody(body);
        }
        bodyDeleteList.clear();
        for(Coordinates c: bodyCreateList){
            player.createBody(c);
            for(me.jishnu.mazegame.InteractiveObjects.Player player: otherPlayers){
                player.createBody(c);
            }
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
