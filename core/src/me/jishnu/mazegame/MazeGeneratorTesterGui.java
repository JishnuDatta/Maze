package me.jishnu.mazegame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class MazeGeneratorTesterGui {
    private Texture colorBar = new Texture("Color Bar.png");
    private TextureRegion tile0 = new TextureRegion(colorBar, 0,0,1,1);
    private TextureRegion tile1 = new TextureRegion(colorBar, 1,0,1,1);
    private TextureRegion tile2 = new TextureRegion(colorBar, 2,0,1,1);
    private TextureRegion[] tiles = {tile0, tile1, tile2};
    private MazeGenerator mazeGenerator;
    private Batch batch;

    public MazeGeneratorTesterGui(MazeGenerator mazeGenerator, Batch batch){
        this.mazeGenerator = mazeGenerator;
        this.batch = batch;
    }

    public void render() {
        for(int f = 0; f < mazeGenerator.getFloors(); f++){
            for(int x = 0; x < mazeGenerator.getSizeX(); x++){
                for (int y = 0; y < mazeGenerator.getSizeY(); y++){
                    batch.draw(tiles[mazeGenerator.getPositionInMazeArray(f, x, y)], x * 10, y * 10, 10, 10);
                }
            }
        }
    }
}
