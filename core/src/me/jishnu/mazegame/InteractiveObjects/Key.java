package me.jishnu.mazegame.InteractiveObjects;

import com.badlogic.gdx.graphics.Color;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;

import box2dLight.PointLight;
import me.jishnu.mazegame.Constants;
import me.jishnu.mazegame.Coordinates;
import me.jishnu.mazegame.MazeGame;
import me.jishnu.mazegame.MazeGeneratorTesterGui;
import me.jishnu.mazegame.PlayScreen;
import me.jishnu.mazegame.Player;


public class Key extends Sprite{
    private PlayScreen playScreen;
    protected World world;
    public Body body;
    private PointLight torch;
    private Body oldBody;

    public Key(PlayScreen playScreen, Coordinates c){
        super(playScreen.getAtlas().findRegion("Player"), 0, 0, 16, 16);
        setSize(getWidth() * Constants.SCALING / 4, getHeight() * Constants.SCALING / 4);
        setOriginCenter();
        this.playScreen = playScreen;
        this.world = playScreen.getWorld();
        createBody(c);
    }
    public void createBody(Coordinates c){
        BodyDef bdef = new BodyDef();
        FixtureDef fdef = new FixtureDef();
        CircleShape shape = new CircleShape();

        bdef.type = BodyDef.BodyType.DynamicBody;

        bdef.position.set(((Constants.TILE_SIZE * c.f * playScreen.getMaze().getSizeX())+ Constants.TILE_SIZE / 2 + Constants.TILE_SIZE * c.x) * Constants.SCALING, ( Constants.TILE_SIZE / 2 + Constants.TILE_SIZE * c.y) * Constants.SCALING);
        body = world.createBody(bdef);

        body.setFixedRotation(false);
        shape.setRadius((Constants.TILE_SIZE / 10) * Constants.SCALING);
        fdef.shape = shape;
        fdef.density = 60;
        fdef.friction = 0;
        fdef.filter.categoryBits = Constants.KEY_BIT;
        body.createFixture(fdef).setUserData(this);

        torch = new PointLight(playScreen.getRayHandler(), 20, new Color(1,1,0.5f,1), (float) 1 * Constants.TILE_SIZE * Constants.SCALING, (c.f * playScreen.getMaze().getSizeX()* Constants.TILE_SIZE  + c.x * Constants.TILE_SIZE) * Constants.SCALING, (c.y * Constants.TILE_SIZE * Constants.SCALING));
        torch.attachToBody(body);
    }

    public void pickedUp(Player player){
        torch.remove();
        oldBody = body;
        playScreen.addToDeleteList(oldBody);
        body = null;
        player.pickedUpKey();
        playScreen.getMazeGui().turnOffLights();
    }

    //Dropped or shifted?
    public void dropped(Coordinates c){
        if (playScreen.getMaze().getMazeArray()[c.f][c.x + 1][c.y] == Constants.tiles.GROUND) {
            playScreen.addToCreateList(new Coordinates(c.f, c.x + 1, c.y));
        } else if (playScreen.getMaze().getMazeArray()[c.f][c.x - 1][c.y] == Constants.tiles.GROUND) {
            playScreen.addToCreateList(new Coordinates(c.f, c.x - 1, c.y));
        } else if (playScreen.getMaze().getMazeArray()[c.f][c.x][c.y + 1] == Constants.tiles.GROUND) {
            playScreen.addToCreateList(new Coordinates(c.f, c.x, c.y + 1));
        } else if (playScreen.getMaze().getMazeArray()[c.f][c.x][c.y - 1] == Constants.tiles.GROUND) {
            playScreen.addToCreateList(new Coordinates(c.f, c.x, c.y - 1));
        }

    }

    public void render(SpriteBatch batch){
        draw(batch);
    }
}
