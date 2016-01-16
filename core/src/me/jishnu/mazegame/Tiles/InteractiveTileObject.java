package me.jishnu.mazegame.Tiles;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

import me.jishnu.mazegame.MazeGame;
import me.jishnu.mazegame.Screens.PlayScreen;

public abstract class InteractiveTileObject extends Sprite{
    protected PlayScreen playScreen;
    protected World world;
    protected Rectangle bounds;
    protected Body body;

    public InteractiveTileObject(PlayScreen playScreen, Rectangle bounds) {
        super(playScreen.getAtlas().findRegion("Tiles"),32,0,16,16);
        this.playScreen = playScreen;
        this.world = playScreen.getWorld();
        this.bounds = bounds;
        BodyDef bdef = new BodyDef();
        bdef.type = BodyDef.BodyType.StaticBody;
        bdef.position.set((bounds.getX() + bounds.getWidth() / 2), (bounds.getY() + bounds.getHeight() / 2));
        body = world.createBody(bdef);
        setBounds((bounds.getX()), (bounds.getY()), (bounds.getWidth()), (bounds.getHeight()));
    }

    public void render(SpriteBatch batch){
        draw(batch);
    }

}
