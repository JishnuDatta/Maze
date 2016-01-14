package me.jishnu.mazegame.Tools;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;

import java.util.HashMap;

import box2dLight.PointLight;
import me.jishnu.mazegame.InteractiveObjects.Key;
import me.jishnu.mazegame.Tiles.LadderDown;
import me.jishnu.mazegame.Tiles.LadderUp;
import me.jishnu.mazegame.Tiles.Wall;

public class MazeGeneratorTesterGui{
    private HashMap<me.jishnu.mazegame.Tools.Constants.tiles,TextureRegion> tiles;
    private MazeGenerator mazeGenerator;
    private me.jishnu.mazegame.Tools.Constants.tiles[][][] mazeArray;
    private int floors, sizeX, sizeY;
    private me.jishnu.mazegame.Screens.PlayScreen playScreen;
    private Array<PointLight> templeLights;

    public MazeGeneratorTesterGui( MazeGenerator mazeGenerator, me.jishnu.mazegame.Screens.PlayScreen playScreen){
        this.mazeGenerator = mazeGenerator;
        this.playScreen = playScreen;
        templeLights = new Array<PointLight>();
        mazeArray = mazeGenerator.getMazeArray();
        tiles = new HashMap<me.jishnu.mazegame.Tools.Constants.tiles, TextureRegion>();
        tiles.put(me.jishnu.mazegame.Tools.Constants.tiles.WALL, new TextureRegion(playScreen.getAtlas().findRegion("Tiles"), 0, 0, 16, 16));
        tiles.put(me.jishnu.mazegame.Tools.Constants.tiles.GROUND, new TextureRegion(playScreen.getAtlas().findRegion("Tiles"), 16, 0, 16, 16));
        tiles.put(me.jishnu.mazegame.Tools.Constants.tiles.TEMPLE, new TextureRegion(playScreen.getAtlas().findRegion("Tiles"), 32, 0, 16, 16));
        tiles.put(me.jishnu.mazegame.Tools.Constants.tiles.LADDER_UP_EAST, new TextureRegion(playScreen.getAtlas().findRegion("Tiles"), 48, 0, 16, 16));
        tiles.put(me.jishnu.mazegame.Tools.Constants.tiles.LADDER_DOWN_EAST, new TextureRegion(playScreen.getAtlas().findRegion("Tiles"), 64, 0, 16, 16));
        tiles.put(me.jishnu.mazegame.Tools.Constants.tiles.LADDER_UP_WEST, new TextureRegion(playScreen.getAtlas().findRegion("Tiles"), 48, 0, 16, 16));
        tiles.put(me.jishnu.mazegame.Tools.Constants.tiles.LADDER_DOWN_WEST, new TextureRegion(playScreen.getAtlas().findRegion("Tiles"), 64, 0, 16, 16));
        tiles.put(me.jishnu.mazegame.Tools.Constants.tiles.LADDER_UP_SOUTH, new TextureRegion(playScreen.getAtlas().findRegion("Tiles"), 48, 0, 16, 16));
        tiles.put(me.jishnu.mazegame.Tools.Constants.tiles.LADDER_DOWN_SOUTH, new TextureRegion(playScreen.getAtlas().findRegion("Tiles"), 64, 0, 16, 16));
        tiles.put(me.jishnu.mazegame.Tools.Constants.tiles.LADDER_UP_NORTH, new TextureRegion(playScreen.getAtlas().findRegion("Tiles"), 48, 0, 16, 16));
        tiles.put(me.jishnu.mazegame.Tools.Constants.tiles.LADDER_DOWN_NORTH, new TextureRegion(playScreen.getAtlas().findRegion("Tiles"), 64, 0, 16, 16));
    }

    public void render(float dt, SpriteBatch batch){
        for(int f = 0; f < mazeGenerator.getFloors(); f++){
            for(int x = 0; x < mazeGenerator.getSizeX(); x++){
                for (int y = 0; y < mazeGenerator.getSizeY(); y++){
                    batch.draw(tiles.get(mazeArray[f][x][y]), (f * mazeGenerator.getSizeX()* me.jishnu.mazegame.Tools.Constants.TILE_SIZE  + x * me.jishnu.mazegame.Tools.Constants.TILE_SIZE) * me.jishnu.mazegame.Tools.Constants.SCALING,  y * me.jishnu.mazegame.Tools.Constants.TILE_SIZE * me.jishnu.mazegame.Tools.Constants.SCALING, me.jishnu.mazegame.Tools.Constants.TILE_SIZE * me.jishnu.mazegame.Tools.Constants.SCALING, me.jishnu.mazegame.Tools.Constants.TILE_SIZE* me.jishnu.mazegame.Tools.Constants.SCALING);
                }
            }
        }
    }

    public void createBox2DStuff(World world){
        for(int f = 0; f < mazeGenerator.getFloors(); f++){
            for(int x = 0; x < mazeGenerator.getSizeX(); x++){
                for (int y = 0; y < mazeGenerator.getSizeY(); y++){
                    if (mazeArray[f][x][y] == me.jishnu.mazegame.Tools.Constants.tiles.WALL){
                        new Wall(world, new Rectangle(xConverted(new Coordinates(f,x,y)), yConverted(new Coordinates(f,x,y)), me.jishnu.mazegame.Tools.Constants.TILE_SIZE * me.jishnu.mazegame.Tools.Constants.SCALING, me.jishnu.mazegame.Tools.Constants.TILE_SIZE* me.jishnu.mazegame.Tools.Constants.SCALING ));
                    }
                    else if(mazeArray[f][x][y] == me.jishnu.mazegame.Tools.Constants.tiles.GROUND){
                        //new Ground(world, new Rectangle(xConverted(new Coordinates(f,x,y)), yConverted(new Coordinates(f,x,y)), Constants.TILE_SIZE * Constants.SCALING, Constants.TILE_SIZE* Constants.SCALING ));
                    }
                    //Ladder Ups
                    else if(mazeArray[f][x][y] == me.jishnu.mazegame.Tools.Constants.tiles.LADDER_UP_EAST || mazeArray[f][x][y] == me.jishnu.mazegame.Tools.Constants.tiles.LADDER_UP_WEST || mazeArray[f][x][y] == me.jishnu.mazegame.Tools.Constants.tiles.LADDER_UP_NORTH || mazeArray[f][x][y] == me.jishnu.mazegame.Tools.Constants.tiles.LADDER_UP_SOUTH ){
                        new LadderUp(world, new Rectangle(xConverted(new Coordinates(f,x,y)), yConverted(new Coordinates(f,x,y)), me.jishnu.mazegame.Tools.Constants.TILE_SIZE * me.jishnu.mazegame.Tools.Constants.SCALING, me.jishnu.mazegame.Tools.Constants.TILE_SIZE* me.jishnu.mazegame.Tools.Constants.SCALING), new Coordinates(f,x,y));
                    }
                    //Ladder Downs
                    else if(mazeArray[f][x][y] == me.jishnu.mazegame.Tools.Constants.tiles.LADDER_DOWN_EAST|| mazeArray[f][x][y] == me.jishnu.mazegame.Tools.Constants.tiles.LADDER_DOWN_WEST || mazeArray[f][x][y] == me.jishnu.mazegame.Tools.Constants.tiles.LADDER_DOWN_NORTH || mazeArray[f][x][y] == me.jishnu.mazegame.Tools.Constants.tiles.LADDER_DOWN_SOUTH ){
                        new LadderDown(world, new Rectangle(xConverted(new Coordinates(f,x,y)), yConverted(new Coordinates(f,x,y)), me.jishnu.mazegame.Tools.Constants.TILE_SIZE * me.jishnu.mazegame.Tools.Constants.SCALING, me.jishnu.mazegame.Tools.Constants.TILE_SIZE* me.jishnu.mazegame.Tools.Constants.SCALING), new Coordinates(f,x,y));
                    }
                    else if(mazeArray[f][x][y] == me.jishnu.mazegame.Tools.Constants.tiles.TEMPLE){
                        new Key(playScreen, new Coordinates(f,x,y));
                    }
                    if(f == mazeGenerator.getFloors() - 1 && mazeArray[f][x][y] == me.jishnu.mazegame.Tools.Constants.tiles.GROUND){
                        templeLights.add(new PointLight(playScreen.getRayHandler(), 20, new Color(0.8f,0.5f,0,1), (float) 1 * me.jishnu.mazegame.Tools.Constants.TILE_SIZE * me.jishnu.mazegame.Tools.Constants.SCALING, xConverted(new Coordinates(f, x, y)) + (me.jishnu.mazegame.Tools.Constants.TILE_SIZE / 2 * me.jishnu.mazegame.Tools.Constants.SCALING), yConverted(new Coordinates(f,x,y)) + (me.jishnu.mazegame.Tools.Constants.TILE_SIZE / 2 * me.jishnu.mazegame.Tools.Constants.SCALING)));
                    }
                }
            }
        }
    }

    public float xConverted(Coordinates c){
        return (c.f * mazeGenerator.getSizeX()* me.jishnu.mazegame.Tools.Constants.TILE_SIZE  + c.x * me.jishnu.mazegame.Tools.Constants.TILE_SIZE) * me.jishnu.mazegame.Tools.Constants.SCALING;
    }

    public float yConverted(Coordinates c){
        return (c.y * me.jishnu.mazegame.Tools.Constants.TILE_SIZE * me.jishnu.mazegame.Tools.Constants.SCALING);
    }

    public void turnOffLights(){
        for(int i = 0; i < templeLights.size;){
            templeLights.get(i).remove(true);
            templeLights.removeIndex(i);
        }
    }
}
