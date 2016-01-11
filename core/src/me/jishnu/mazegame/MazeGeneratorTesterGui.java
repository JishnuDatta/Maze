package me.jishnu.mazegame;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.World;

import box2dLight.PointLight;
import me.jishnu.mazegame.Tiles.LadderDown;
import me.jishnu.mazegame.Tiles.LadderUp;
import me.jishnu.mazegame.Tiles.Wall;
import sun.security.ssl.SSLContextImpl;

public class MazeGeneratorTesterGui{
    private Texture colorBar = new Texture("Color Bar.png");
    private TextureRegion tile0 = new TextureRegion(colorBar, 0,0,1,1);
    private TextureRegion tile1 = new TextureRegion(colorBar, 1,0,1,1);
    private TextureRegion tile2 = new TextureRegion(colorBar, 2,0,1,1);
    private TextureRegion tile3 = new TextureRegion(colorBar, 3,0,1,1);
    private TextureRegion tile4 = new TextureRegion(colorBar, 4,0,1,1);
    private TextureRegion tile5 = new TextureRegion(colorBar, 5,0,1,1);
    private TextureRegion tile6 = new TextureRegion(colorBar, 6,0,1,1);
    private TextureRegion tile7 = new TextureRegion(colorBar, 7,0,1,1);
    private TextureRegion tile8 = new TextureRegion(colorBar, 8,0,1,1);
    private TextureRegion tile9 = new TextureRegion(colorBar, 9,0,1,1);

    private TextureRegion[] tiles = {tile0, tile1, tile2, tile3, tile4, tile5, tile6, tile7, tile8, tile9};
    private MazeGenerator mazeGenerator;
    private int[][][] mazeArray;
    private PlayScreen playScreen;

    public MazeGeneratorTesterGui( MazeGenerator mazeGenerator, PlayScreen playScreen){
        this.mazeGenerator = mazeGenerator;
        this.playScreen = playScreen;
        mazeArray = mazeGenerator.getMazeArray();
    }

    public void render(float dt, SpriteBatch batch){
        for(int f = 0; f < mazeGenerator.getFloors(); f++){
            for(int x = 0; x < mazeGenerator.getSizeX(); x++){
                for (int y = 0; y < mazeGenerator.getSizeY(); y++){
                    batch.draw(tiles[mazeArray[f][x][y]], (f * mazeGenerator.getSizeX()* Constants.TILE_SIZE  + x * Constants.TILE_SIZE) * MazeGame.SCALING,  y * Constants.TILE_SIZE * MazeGame.SCALING, Constants.TILE_SIZE * MazeGame.SCALING, Constants.TILE_SIZE* MazeGame.SCALING);
                }
            }
        }
    }

    public void createBox2DStuff(World world){
        for(int f = 0; f < mazeGenerator.getFloors(); f++){
            for(int x = 0; x < mazeGenerator.getSizeX(); x++){
                for (int y = 0; y < mazeGenerator.getSizeY(); y++){
                    if (mazeArray[f][x][y] == 0){
                        new Wall(world, new Rectangle(xConverted(new Coordinates(f,x,y)), yConverted(new Coordinates(f,x,y)), Constants.TILE_SIZE * MazeGame.SCALING, Constants.TILE_SIZE* MazeGame.SCALING ));
                    }
                    //Ladder Ups
                    else if(mazeArray[f][x][y] == 9){
                        new LadderUp(world, new Rectangle(xConverted(new Coordinates(f,x,y)), yConverted(new Coordinates(f,x,y)), Constants.TILE_SIZE * MazeGame.SCALING, Constants.TILE_SIZE* MazeGame.SCALING), new Coordinates(f,x,y));
                    }
                    //Ladder Downs
                    else if(mazeArray[f][x][y] == 6){
                        new LadderDown(world, new Rectangle(xConverted(new Coordinates(f,x,y)), yConverted(new Coordinates(f,x,y)), Constants.TILE_SIZE * MazeGame.SCALING, Constants.TILE_SIZE* MazeGame.SCALING), new Coordinates(f,x,y));
                    }
                    if(f == mazeGenerator.getFloors() - 1 && mazeArray[f][x][y] == 1){
                        new PointLight(playScreen.getRayHandler(), 20, new Color(0.8f,0.5f,0,1), (float) 1 * Constants.TILE_SIZE * MazeGame.SCALING, xConverted(new Coordinates(f, x, y)) + (Constants.TILE_SIZE / 2 * MazeGame.SCALING), yConverted(new Coordinates(f,x,y)) + (Constants.TILE_SIZE / 2 * MazeGame.SCALING));
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
}
