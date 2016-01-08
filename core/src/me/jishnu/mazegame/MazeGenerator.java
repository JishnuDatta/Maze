package me.jishnu.mazegame;

import com.badlogic.gdx.graphics.g2d.Batch;

import java.lang.reflect.Array;

public class MazeGenerator {
    private int floors, sizeX, sizeY;
    private int[][][] mazeArray;
    MazeGeneratorTesterGui mgtg;
    int zeroes, ones, twos = 0;

    public MazeGenerator(int floors, int sizeX, int sizeY, Batch batch){
        //First indicates floor, second indicates,
        this.floors = floors;
        this.sizeX = sizeX;
        this.sizeY = sizeY;
        mazeArray = new int[floors][sizeX][sizeY];
        generateMaze();
        mgtg = new MazeGeneratorTesterGui(this, batch);
        printMaze();
    }

    public void generateMaze(){
        for(int f = 0; f < floors; f++){
            for(int x = 0; x < sizeX; x++){
                for (int y = 0; y < sizeY; y++){
                    int randomInt = (int) Math.abs(Math.random() * 3f);
                    mazeArray[f][x][y] = randomInt;
                    if(randomInt == 0){
                        zeroes++;
                    }
                    else if(randomInt == 1){
                        ones ++;
                    }
                    else if(randomInt == 2){
                        twos++;
                    }
                }
            }
        }
    }

    public void printMaze(){
        for(int f = 0; f < floors; f++){
            System.out.println("Floor: " + f);
            System.out.println();
            for(int x = 0; x < sizeX; x++){
                for (int y = 0; y < sizeY; y++){
                    System.out.print(mazeArray[f][x][y]);
                }
                System.out.println();
            }
            System.out.println();
        }
        System.out.println("Zeroes : " + zeroes + " Ones: " + ones + " Twos: " + twos);
    }

    public int getFloors() {
        return floors;
    }

    public int getSizeX() {
        return sizeX;
    }

    public int getSizeY() {
        return sizeY;
    }

    public int getPositionInMazeArray(int f, int x, int y) {
        return mazeArray[f][x][y];
    }

    public void render(){
        mgtg.render();
    }
}
