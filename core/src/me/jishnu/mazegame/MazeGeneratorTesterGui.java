package me.jishnu.mazegame;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;

import java.util.HashMap;

import box2dLight.PointLight;
import me.jishnu.mazegame.InteractiveObjects.Key;
import me.jishnu.mazegame.Tiles.Ground;
import me.jishnu.mazegame.Tiles.LadderDown;
import me.jishnu.mazegame.Tiles.LadderUp;
import me.jishnu.mazegame.Tiles.Wall;

public class MazeGeneratorTesterGui{
    private HashMap< Constants.tiles ,TextureRegion> tiles;
    private MazeGenerator mazeGenerator;
    private Constants.tiles[][][] mazeArray;
    private PlayScreen playScreen;
    private Array<PointLight> templeLights;

    public MazeGeneratorTesterGui( MazeGenerator mazeGenerator, PlayScreen playScreen){
        this.mazeGenerator = mazeGenerator;
        this.playScreen = playScreen;
        templeLights = new Array<PointLight>();
        mazeArray = mazeGenerator.getMazeArray();
        tiles = new HashMap<Constants.tiles, TextureRegion>();
        tiles.put(Constants.tiles.WALL, new TextureRegion(playScreen.getAtlas().findRegion("Tiles"), 0, 0, 16, 16));
        tiles.put(Constants.tiles.GROUND, new TextureRegion(playScreen.getAtlas().findRegion("Tiles"), 16, 0, 16, 16));
        tiles.put(Constants.tiles.TEMPLE, new TextureRegion(playScreen.getAtlas().findRegion("Tiles"), 32, 0, 16, 16));
        tiles.put(Constants.tiles.LADDER_UP_EAST, new TextureRegion(playScreen.getAtlas().findRegion("Tiles"), 48, 0, 16, 16));
        tiles.put(Constants.tiles.LADDER_DOWN_EAST, new TextureRegion(playScreen.getAtlas().findRegion("Tiles"), 64, 0, 16, 16));
        tiles.put(Constants.tiles.LADDER_UP_WEST, new TextureRegion(playScreen.getAtlas().findRegion("Tiles"), 48, 0, 16, 16));
        tiles.put(Constants.tiles.LADDER_DOWN_WEST, new TextureRegion(playScreen.getAtlas().findRegion("Tiles"), 64, 0, 16, 16));
        tiles.put(Constants.tiles.LADDER_UP_SOUTH, new TextureRegion(playScreen.getAtlas().findRegion("Tiles"), 48, 0, 16, 16));
        tiles.put(Constants.tiles.LADDER_DOWN_SOUTH, new TextureRegion(playScreen.getAtlas().findRegion("Tiles"), 64, 0, 16, 16));
        tiles.put(Constants.tiles.LADDER_UP_NORTH, new TextureRegion(playScreen.getAtlas().findRegion("Tiles"), 48, 0, 16, 16));
        tiles.put(Constants.tiles.LADDER_DOWN_NORTH, new TextureRegion(playScreen.getAtlas().findRegion("Tiles"), 64, 0, 16, 16));
    }

    public void render(float dt, SpriteBatch batch){
        for(int f = 0; f < mazeGenerator.getFloors(); f++){
            for(int x = 0; x < mazeGenerator.getSizeX(); x++){
                for (int y = 0; y < mazeGenerator.getSizeY(); y++){
                    batch.draw(tiles.get(mazeArray[f][x][y]), (f * mazeGenerator.getSizeX()* Constants.TILE_SIZE  + x * Constants.TILE_SIZE) * MazeGame.SCALING,  y * Constants.TILE_SIZE * MazeGame.SCALING, Constants.TILE_SIZE * MazeGame.SCALING, Constants.TILE_SIZE* MazeGame.SCALING);
                }
            }
        }
    }

    public void createBox2DStuff(World world){
        for(int f = 0; f < mazeGenerator.getFloors(); f++){
            for(int x = 0; x < mazeGenerator.getSizeX(); x++){
                for (int y = 0; y < mazeGenerator.getSizeY(); y++){
                    if (mazeArray[f][x][y] == Constants.tiles.WALL){
                        new Wall(world, new Rectangle(xConverted(new Coordinates(f,x,y)), yConverted(new Coordinates(f,x,y)), Constants.TILE_SIZE * MazeGame.SCALING, Constants.TILE_SIZE* MazeGame.SCALING ));
                    }
                    else if(mazeArray[f][x][y] == Constants.tiles.GROUND){
                        //new Ground(world, new Rectangle(xConverted(new Coordinates(f,x,y)), yConverted(new Coordinates(f,x,y)), Constants.TILE_SIZE * MazeGame.SCALING, Constants.TILE_SIZE* MazeGame.SCALING ));
                    }
                    //Ladder Ups
                    else if(mazeArray[f][x][y] == Constants.tiles.LADDER_UP_EAST || mazeArray[f][x][y] == Constants.tiles.LADDER_UP_WEST || mazeArray[f][x][y] == Constants.tiles.LADDER_UP_NORTH || mazeArray[f][x][y] == Constants.tiles.LADDER_UP_SOUTH ){
                        new LadderUp(world, new Rectangle(xConverted(new Coordinates(f,x,y)), yConverted(new Coordinates(f,x,y)), Constants.TILE_SIZE * MazeGame.SCALING, Constants.TILE_SIZE* MazeGame.SCALING), new Coordinates(f,x,y));
                    }
                    //Ladder Downs
                    else if(mazeArray[f][x][y] == Constants.tiles.LADDER_DOWN_EAST|| mazeArray[f][x][y] == Constants.tiles.LADDER_DOWN_WEST || mazeArray[f][x][y] == Constants.tiles.LADDER_DOWN_NORTH || mazeArray[f][x][y] == Constants.tiles.LADDER_DOWN_SOUTH ){
                        new LadderDown(world, new Rectangle(xConverted(new Coordinates(f,x,y)), yConverted(new Coordinates(f,x,y)), Constants.TILE_SIZE * MazeGame.SCALING, Constants.TILE_SIZE* MazeGame.SCALING), new Coordinates(f,x,y));
                    }
                    else if(mazeArray[f][x][y] == Constants.tiles.TEMPLE){
                        new Key(playScreen, new Coordinates(f,x,y));
                    }
                    if(f == mazeGenerator.getFloors() - 1 && mazeArray[f][x][y] == Constants.tiles.GROUND){
                        templeLights.add(new PointLight(playScreen.getRayHandler(), 20, new Color(0.8f,0.5f,0,1), (float) 1 * Constants.TILE_SIZE * MazeGame.SCALING, xConverted(new Coordinates(f, x, y)) + (Constants.TILE_SIZE / 2 * MazeGame.SCALING), yConverted(new Coordinates(f,x,y)) + (Constants.TILE_SIZE / 2 * MazeGame.SCALING)));
                    }
                }
            }
        }
    }

    public float xConverted(Coordinates c){
        return (c.f * mazeGenerator.getSizeX()* Constants.TILE_SIZE  + c.x * Constants.TILE_SIZE) * MazeGame.SCALING;
    }

    public float yConverted(Coordinates c){
        return (c.y * Constants.TILE_SIZE * MazeGame.SCALING);
    }

    public void turnOffLights(){
        for(int i = 0; i < templeLights.size;){
            templeLights.get(i).remove(true);
            templeLights.removeIndex(i);
        }
    }
}
