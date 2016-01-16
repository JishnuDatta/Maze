package me.jishnu.mazegame.Tiles;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.sun.xml.internal.bind.v2.runtime.reflect.opt.Const;

import me.jishnu.mazegame.Tools.Constants;

public class Ground extends InteractiveTileObject{
    public Constants.teams teamLastStepped;
        public Ground(World world, Rectangle bounds) {
            super(world, bounds);
            FixtureDef fdef = new FixtureDef();
            PolygonShape shape = new PolygonShape();
            shape.setAsBox((bounds.getWidth() / 2), (bounds.getHeight() / 2));
            fdef.shape = shape;
            fdef.isSensor = true;
            fdef.filter.categoryBits = Constants.GROUND_BIT;
            fdef.filter.groupIndex = -1;
            fdef.filter.maskBits = -1;
            body.createFixture(fdef).setUserData(this);
        }

    public void steppedOn(Constants.teams team){
        teamLastStepped = team;
    }
    }

