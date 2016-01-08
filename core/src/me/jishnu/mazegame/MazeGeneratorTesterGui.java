package me.jishnu.mazegame;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

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

    private static final int TILE_SIZE = 6;
    private static final int TILE_GAP = 2;
    private static final int FLOOR_GAP = 10;
    public MazeGeneratorTesterGui( MazeGenerator mazeGenerator){
        this.mazeGenerator = mazeGenerator;
    }


    public void render(float dt, SpriteBatch batch){
        for(int f = 0; f < mazeGenerator.getFloors(); f++){
            for(int x = 0; x < mazeGenerator.getSizeX(); x++){
                for (int y = 0; y < mazeGenerator.getSizeY(); y++){
                    batch.draw(tiles[mazeGenerator.getPositionInMazeArray(f, x, y)], (f * (FLOOR_GAP + mazeGenerator.getSizeX()* (TILE_SIZE + TILE_GAP))) + x * (TILE_SIZE + TILE_GAP), y * (TILE_SIZE + TILE_GAP), TILE_SIZE, TILE_SIZE);
                }
            }
        }
    }
}
