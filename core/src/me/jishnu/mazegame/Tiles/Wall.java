package me.jishnu.mazegame.Tiles;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

import me.jishnu.mazegame.Tools.Constants;

public class Wall extends InteractiveTileObject{
    public Wall(World world, Rectangle bounds) {
        super(world, bounds);
        FixtureDef fdef = new FixtureDef();
        PolygonShape shape = new PolygonShape();
        shape.setAsBox((bounds.getWidth() / 2), (bounds.getHeight() / 2));
        fdef.shape = shape;
        fdef.friction = 0;
        fdef.filter.categoryBits = Constants.WALL_BIT;
        //fdef.filter.groupIndex = 4;
       // fdef.filter.maskBits = 4;
        body.createFixture(fdef).setUserData(this);
    }
}
