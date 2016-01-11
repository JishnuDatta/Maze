package me.jishnu.mazegame;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import box2dLight.ConeLight;

public class Player{
    private PlayScreen playScreen;
    protected World world;
    public Body body;
    private ConeLight torch;
    private Body oldBody;

    public Player(PlayScreen playScreen, Coordinates c) {
        this.playScreen = playScreen;
        this.world = playScreen.getWorld();
        createBody(c);

    }

    public void createBody(Coordinates c){
        BodyDef bdef = new BodyDef();
        FixtureDef fdef = new FixtureDef();
        CircleShape shape = new CircleShape();

        bdef.type = BodyDef.BodyType.DynamicBody;

        bdef.position.set(((Constants.TILE_SIZE * c.f * playScreen.getMaze().getSizeX())+ Constants.TILE_SIZE / 2 + Constants.TILE_SIZE * c.x) * MazeGame.SCALING, ( Constants.TILE_SIZE / 2 + Constants.TILE_SIZE * c.y) * MazeGame.SCALING);
        body = world.createBody(bdef);

        body.setFixedRotation(false);
        shape.setRadius((Constants.TILE_SIZE / 10) * MazeGame.SCALING);
        fdef.shape = shape;
        fdef.density = 60;
        fdef.friction = 0;
        fdef.filter.categoryBits = Constants.PLAYER_BIT;
        body.createFixture(fdef).setUserData(this);

        torch = new ConeLight(playScreen.getRayHandler(), 20, new Color(0,1,1,1), 40 * MazeGame.SCALING, body.getPosition().x, body.getPosition().y, 0.001f,20);
        torch.attachToBody(body);
                if (playScreen.getMaze().getMazeArray()[c.f][c.x - 1][c.y] == 1) {
                body.setTransform(body.getPosition(), 150);
            } else if (playScreen.getMaze().getMazeArray()[c.f][c.x][c.y + 1] == 1) {
                body.setTransform(body.getPosition(), 60);
            } else if (playScreen.getMaze().getMazeArray()[c.f][c.x][c.y - 1] == 1) {
                body.setTransform(body.getPosition(), 240);
        }
    }

    public void teleport(Coordinates c){
        torch.remove();
        oldBody = body;
        playScreen.addToDeleteList(oldBody);
        body = null;
            if (playScreen.getMaze().getMazeArray()[c.f][c.x + 1][c.y] == 1) {
                playScreen.addToCreateList(new Coordinates(c.f, c.x + 1, c.y));
            } else if (playScreen.getMaze().getMazeArray()[c.f][c.x - 1][c.y] == 1) {
                playScreen.addToCreateList(new Coordinates(c.f, c.x - 1, c.y));
            } else if (playScreen.getMaze().getMazeArray()[c.f][c.x][c.y + 1] == 1) {
                playScreen.addToCreateList(new Coordinates(c.f, c.x, c.y + 1));
            } else if (playScreen.getMaze().getMazeArray()[c.f][c.x][c.y - 1] == 1) {
                playScreen.addToCreateList(new Coordinates(c.f, c.x, c.y - 1));
        }
    }
}
