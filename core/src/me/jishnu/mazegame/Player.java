package me.jishnu.mazegame;

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

import me.jishnu.mazegame.MazeGame;

public class Player{
    protected World world;
    protected TiledMapTile tile;
    protected Rectangle bounds;
    public Body body;

    public Player(PlayScreen playScreen, Rectangle spawn) {
        this.world = playScreen.getWorld();
        this.bounds = spawn;
        BodyDef bdef = new BodyDef();
        FixtureDef fdef = new FixtureDef();
        CircleShape shape = new CircleShape();

        bdef.type = BodyDef.BodyType.DynamicBody;
        //bdef.position.set(20,20);

        bdef.position.set(20, 20);
        System.out.println(bdef.position.x + "   " + bdef.position.y);
        body = world.createBody(bdef);

        shape.setRadius(3);
        fdef.shape = shape;
        // fdef.filter.categoryBits = MazeGame.TILE_BIT;
        body.createFixture(fdef).setUserData(this);
    }
    public void update(float dt) {
    }
}
