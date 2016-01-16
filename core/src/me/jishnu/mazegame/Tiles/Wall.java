package me.jishnu.mazegame.Tiles;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;


import me.jishnu.mazegame.Screens.PlayScreen;
import me.jishnu.mazegame.Tools.Constants;

public class Wall extends InteractiveTileObject{
    public Wall(PlayScreen playScreen, Rectangle bounds) {
        super(playScreen, bounds);
        FixtureDef fdef = new FixtureDef();
        PolygonShape shape = new PolygonShape();
        shape.setAsBox((bounds.getWidth() / 2), (bounds.getHeight() / 2));
        fdef.shape = shape;
        fdef.friction = 0;
        fdef.filter.categoryBits = Constants.WALL_BIT;
        body.createFixture(fdef).setUserData(this);
        setRegion(playScreen.getAtlas().findRegion("Tiles"), 0, 0, 16, 16);
    }
}
