package me.jishnu.mazegame.Tiles;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import me.jishnu.mazegame.Screens.PlayScreen;
import me.jishnu.mazegame.Tools.Constants;

public class Ground extends InteractiveTileObject{
    public Constants.teams teamLastStepped;
        public Ground(PlayScreen playScreen, Rectangle bounds) {
            super(playScreen, bounds);
            FixtureDef fdef = new FixtureDef();
            PolygonShape shape = new PolygonShape();
            shape.setAsBox((bounds.getWidth() / 2), (bounds.getHeight() / 2));
            fdef.shape = shape;
            fdef.isSensor = true;
            fdef.filter.categoryBits = Constants.GROUND_BIT;
            fdef.filter.groupIndex = -1;
            fdef.filter.maskBits = -1;
            body.createFixture(fdef).setUserData(this);
            setRegion(playScreen.getAtlas().findRegion("Tiles"), 16, 0, 16, 16);
        }

    public void steppedOn(Constants.teams team) {
        if(team != teamLastStepped) {
            if(team == Constants.teams.BLUE_TEAM){
                setColor(Color.CYAN);
            }
            else if(team == Constants.teams.RED_TEAM){
                setColor(Color.RED);
            }
            else if(team == Constants.teams.GREEN_TEAM){
                setColor(Color.GREEN);
            }
            else if(team == Constants.teams.YELLOW_TEAM){
                setColor(Color.YELLOW);
            }
        }
    }

    }

