package me.jishnu.mazegame.Tiles;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.World;

public class Wall extends InteractiveTileObject{
    public Wall(World world, Rectangle bounds) {
        super(world, bounds);
    }
}
