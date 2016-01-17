package me.jishnu.mazegame.Tools;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import box2dLight.PointLight;
import me.jishnu.mazegame.InteractiveObjects.Key;
import me.jishnu.mazegame.Screens.PlayScreen;
import me.jishnu.mazegame.Tiles.Ground;
import me.jishnu.mazegame.Tiles.InteractiveTileObject;
import me.jishnu.mazegame.Tiles.LadderDown;
import me.jishnu.mazegame.Tiles.LadderUp;
import me.jishnu.mazegame.Tiles.Wall;

public class MazeGeneratorTesterGui{

    private MazeGenerator mazeGenerator;
    private Constants.tiles[][][] mazeArray;
    private Array<InteractiveTileObject> tiles;
    private PlayScreen playScreen;
    private Array<PointLight> templeLights;

    public MazeGeneratorTesterGui(MazeGenerator mazeGenerator, PlayScreen playScreen){
        this.mazeGenerator = mazeGenerator;
        this.playScreen = playScreen;
        templeLights = new Array<PointLight>();
        mazeArray = mazeGenerator.getMazeArray();
        tiles = new Array<InteractiveTileObject>();
    }

    public void render(float dt, SpriteBatch batch){
        for(InteractiveTileObject tile: tiles){
                tile.render(batch);
        }
    }

    public void createBox2DStuff(){
        for(int f = 0; f < mazeGenerator.getFloors(); f++){
            for(int x = 0; x < mazeGenerator.getSizeX(); x++){
                for (int y = 0; y < mazeGenerator.getSizeY(); y++){
                    if (mazeArray[f][x][y] == Constants.tiles.WALL){
                        tiles.add(new Wall(playScreen, new Rectangle(xConverted(new Coordinates(f,x,y)), yConverted(new Coordinates(f,x,y)), Constants.TILE_SIZE * Constants.SCALING, Constants.TILE_SIZE* Constants.SCALING )));
                    }
                    else if(mazeArray[f][x][y] == Constants.tiles.GROUND){
                        for(int i = 0; i < 10; i++){
                            for(int j = 0; j < 10; j++){
                                tiles.add(new Ground(playScreen, new Rectangle(xConverted(new Coordinates(f, x, y)) + (i * Constants.TILE_SIZE / 10 * Constants.SCALING), yConverted(new Coordinates(f,x,y)) + (j * Constants.TILE_SIZE / 10 * Constants.SCALING), Constants.TILE_SIZE / 10 * Constants.SCALING, Constants.TILE_SIZE / 10 * Constants.SCALING )));
                            }
                        }
                    }
                    //Ladder Ups
                    else if(mazeArray[f][x][y] == Constants.tiles.LADDER_UP_EAST || mazeArray[f][x][y] == Constants.tiles.LADDER_UP_WEST || mazeArray[f][x][y] == Constants.tiles.LADDER_UP_NORTH || mazeArray[f][x][y] == Constants.tiles.LADDER_UP_SOUTH ){
                        tiles.add(new LadderUp(playScreen, new Rectangle(xConverted(new Coordinates(f,x,y)), yConverted(new Coordinates(f,x,y)), Constants.TILE_SIZE * Constants.SCALING, Constants.TILE_SIZE* Constants.SCALING), new Coordinates(f,x,y)));
                    }
                    //Ladder Downs
                    else if(mazeArray[f][x][y] == Constants.tiles.LADDER_DOWN_EAST|| mazeArray[f][x][y] == Constants.tiles.LADDER_DOWN_WEST || mazeArray[f][x][y] == Constants.tiles.LADDER_DOWN_NORTH || mazeArray[f][x][y] == Constants.tiles.LADDER_DOWN_SOUTH ){
                        tiles.add(new LadderDown(playScreen, new Rectangle(xConverted(new Coordinates(f,x,y)), yConverted(new Coordinates(f,x,y)), Constants.TILE_SIZE * Constants.SCALING, Constants.TILE_SIZE* Constants.SCALING), new Coordinates(f,x,y)));
                    }
                    else if(mazeArray[f][x][y] == Constants.tiles.TEMPLE){
                        new Key(playScreen, new Coordinates(f,x,y));
                    }
                    if(f == mazeGenerator.getFloors() - 1 && mazeArray[f][x][y] == Constants.tiles.GROUND){
                        templeLights.add(new PointLight(playScreen.getRayHandler(), 20, new Color(0.8f,0.5f,0,1), (float) 1 * Constants.TILE_SIZE * Constants.SCALING, xConverted(new Coordinates(f, x, y)) + (Constants.TILE_SIZE / 2 * Constants.SCALING), yConverted(new Coordinates(f,x,y)) + (Constants.TILE_SIZE / 2 * Constants.SCALING)));
                    }
                }
            }
        }
    }

    public float xConverted(Coordinates c){
        return (c.f * mazeGenerator.getSizeX()* Constants.TILE_SIZE  + c.x * Constants.TILE_SIZE) * Constants.SCALING;
    }

    public float yConverted(Coordinates c){
        return (c.y * Constants.TILE_SIZE * Constants.SCALING);
    }

    public void turnOffLights(){
        for(int i = 0; i < templeLights.size;){
            templeLights.get(i).remove(true);
            templeLights.removeIndex(i);
        }
    }
}
