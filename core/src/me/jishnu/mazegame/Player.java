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
import me.jishnu.mazegame.MazeGame;
import sun.nio.cs.ext.MacThai;

public class Player{
    protected World world;
    protected TiledMapTile tile;
    protected Rectangle bounds;
    public Body body;
    private ConeLight torch;

    public Player(PlayScreen playScreen, Rectangle spawn) {
        this.world = playScreen.getWorld();
        this.bounds = spawn;
        BodyDef bdef = new BodyDef();
        FixtureDef fdef = new FixtureDef();
        CircleShape shape = new CircleShape();

        bdef.type = BodyDef.BodyType.DynamicBody;

        bdef.position.set(25 * MazeGame.SCALING, 25 * MazeGame.SCALING);
        body = world.createBody(bdef);

        body.setFixedRotation(false);
        shape.setRadius(1 * MazeGame.SCALING);
        fdef.shape = shape;
        fdef.density = 60;
        // fdef.filter.categoryBits = MazeGame.TILE_BIT;
        body.createFixture(fdef).setUserData(this);

        torch = new ConeLight(playScreen.getRayHandler(), 20, new Color(0,1,1,1), 20 * MazeGame.SCALING, body.getPosition().x, body.getPosition().y, 0.001f,20);
        torch.attachToBody(body);

    }
}
