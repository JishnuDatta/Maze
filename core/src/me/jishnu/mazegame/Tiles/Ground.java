package me.jishnu.mazegame.Tiles;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

import me.jishnu.mazegame.Constants;

public class Ground extends InteractiveTileObject{
        public Ground(World world, Rectangle bounds) {
            super(world, bounds);
            FixtureDef fdef = new FixtureDef();
            PolygonShape shape = new PolygonShape();
            shape.setAsBox((bounds.getWidth() / 2), (bounds.getHeight() / 2));
            fdef.shape = shape;
            fdef.isSensor = true;
            fdef.filter.categoryBits = Constants.GROUND_BIT;
            body.createFixture(fdef).setUserData(this);
        }
    }

