package me.jishnu.mazegame;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.World;

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

    private static final int TILE_SIZE = 10;
    private static final int TILE_GAP = 0;
    private static final int FLOOR_GAP = 0;
    public MazeGeneratorTesterGui( MazeGenerator mazeGenerator){
        this.mazeGenerator = mazeGenerator;
        mazeArray = mazeGenerator.getMazeArray();
    }

    public void render(float dt, SpriteBatch batch){
        for(int f = 0; f < mazeGenerator.getFloors(); f++){
            for(int x = 0; x < mazeGenerator.getSizeX(); x++){
                for (int y = 0; y < mazeGenerator.getSizeY(); y++){
                    batch.draw(tiles[mazeArray[f][x][y]], (f * mazeGenerator.getSizeX()* TILE_SIZE  + x * TILE_SIZE) * MazeGame.SCALING,  y * TILE_SIZE * MazeGame.SCALING, TILE_SIZE * MazeGame.SCALING, TILE_SIZE* MazeGame.SCALING);
                }
            }
        }
    }

    public void createBox2DStuff(World world){
        for(int f = 0; f < mazeGenerator.getFloors(); f++){
            for(int x = 0; x < mazeGenerator.getSizeX(); x++){
                for (int y = 0; y < mazeGenerator.getSizeY(); y++){
                    if(mazeArray[f][x][y] == 0){
                        new Wall(world, new Rectangle((f * mazeGenerator.getSizeX()* TILE_SIZE  + x * TILE_SIZE) * MazeGame.SCALING,  y * TILE_SIZE * MazeGame.SCALING, TILE_SIZE * MazeGame.SCALING, TILE_SIZE* MazeGame.SCALING));
                    }
                }
            }
        }
    }
}
