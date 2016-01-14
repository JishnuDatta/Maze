package me.jishnu.mazegame.Tiles;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

import me.jishnu.mazegame.Tools.Constants;
import me.jishnu.mazegame.Tools.Coordinates;
import me.jishnu.mazegame.InteractiveObjects.Player;

public class LadderUp extends InteractiveTileObject{
    private Coordinates c;
    public LadderUp(World world, Rectangle bounds, Coordinates c) {
        super(world, bounds);
        FixtureDef fdef = new FixtureDef();
        PolygonShape shape = new PolygonShape();
        shape.setAsBox((bounds.getWidth() / 2), (bounds.getHeight() / 2));
        fdef.shape = shape;
        fdef.filter.categoryBits = Constants.LADDER_UP_BIT;
        fdef.isSensor = true;
        body.createFixture(fdef).setUserData(this);
        this.c = c;
    }
    public void teleportUp(Player player){
        player.teleport(new Coordinates(c.f + 1, c.x, c.y));
    }
}
