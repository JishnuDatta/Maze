package me.jishnu.mazegame.Tiles;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

import me.jishnu.mazegame.MazeGame;

public abstract class InteractiveTileObject {
    protected World world;
    protected Rectangle bounds;
    protected Body body;

    public InteractiveTileObject(World world, Rectangle bounds) {
        this.world = world;
        this.bounds = bounds;
        BodyDef bdef = new BodyDef();
        FixtureDef fdef = new FixtureDef();
        PolygonShape shape = new PolygonShape();

        bdef.type = BodyDef.BodyType.StaticBody;
        bdef.position.set((bounds.getX() + bounds.getWidth() / 2) / MazeGame.PPM, (bounds.getY() + bounds.getHeight() / 2) / MazeGame.PPM);

        body = world.createBody(bdef);

        shape.setAsBox((bounds.getWidth() / 2) / MazeGame.PPM, (bounds.getHeight() / 2) / MazeGame.PPM);
        fdef.shape = shape;
       // fdef.filter.categoryBits = MazeGame.TILE_BIT;
        body.createFixture(fdef).setUserData(this);
    }
}