package me.jishnu.mazegame.Tiles;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;

import me.jishnu.mazegame.Screens.PlayScreen;
import me.jishnu.mazegame.Tools.Constants;

public class Base extends InteractiveTileObject{
    public Base(PlayScreen playScreen, Rectangle bounds, Constants.tiles team) {
        super(playScreen, bounds);
        FixtureDef fdef = new FixtureDef();
        PolygonShape shape = new PolygonShape();
        shape.setAsBox((bounds.getWidth() / 2), (bounds.getHeight() / 2));
        fdef.shape = shape;
        fdef.isSensor = true;
        fdef.filter.categoryBits = Constants.BASE_BIT;
        fdef.filter.groupIndex = -1;
        fdef.filter.maskBits = -1;
        body.createFixture(fdef).setUserData(team);
        if(team == Constants.tiles.RED_BASE){
            setRegion(playScreen.getAtlas().findRegion("Tiles"), 80, 0, 16, 16);
        }
        else if(team == Constants.tiles.YELLOW_BASE){
            setRegion(playScreen.getAtlas().findRegion("Tiles"), 96, 0, 16, 16);
        }
        else if(team == Constants.tiles.GREEN_BASE){
            setRegion(playScreen.getAtlas().findRegion("Tiles"), 112, 0, 16, 16);
        }
        else if (team == Constants.tiles.BLUE_BASE){
            setRegion(playScreen.getAtlas().findRegion("Tiles"), 128, 0, 16, 16);
        }
    }
}
