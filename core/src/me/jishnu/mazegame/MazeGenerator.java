package me.jishnu.mazegame;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;

import java.util.concurrent.ThreadLocalRandom;


public class MazeGenerator {
    private int floors, sizeX, sizeY;
    private int[][][] mazeArray;
    int zeroes, ones, twos = 0;
    float timer;
    Coordinates start0, start1, start2, start3, start4, start5, start6, start7;
    Array<Coordinates> wallList;
    public enum Commands {XY, Z};


    public MazeGenerator(int floors, int sizeX, int sizeY){
        //First indicates floor, second indicates,
        this.floors = floors;
        this.sizeX = sizeX;
        this.sizeY = sizeY;
        mazeArray = new int[floors][sizeX][sizeY];
        setUpMaze();
        printMaze();
        timer = 0;
        start0 = new Coordinates(0,1,1);
        start1 = new Coordinates(0,sizeX - 2,1);
//        start2 = new Coordinates(0,1,1);
//        start3 = new Coordinates(0,1,1);
//        start4 = new Coordinates(0,1,1);
//        start5 = new Coordinates(0,1,1);
//        start6 = new Coordinates(0,1,1);
//        start7 = new Coordinates(0,1,1);
        wallList = new Array<Coordinates>();
        mazeArray[start0.f][start0.x][start0.y] = 1;
        mazeArray[start1.f][start1.x][start1.y] = 1;
        addToCoordinatesList(start0, wallList);
        addToCoordinatesList(start1, wallList);


    }

    public void setUpMaze(){
        for(int f = 0; f < floors; f++){
            for(int x = 0; x < sizeX; x++){
                for (int y = 0; y < sizeY; y++){
                    //Edges
                    if(y == 0 || y == (sizeY - 1) || x == 0 || x == (sizeX - 1)) {
                        mazeArray[f][x][y] = 2;
                    }
                    //
                    else if(x%2 != 1 && y%2 != 1) {
                        mazeArray[f][x][y] = 2;
                    }
                    else if((x%2 != 1 || y%2 != 1)&& f%2!= 0) {
                        mazeArray[f][x][y] = 2;
                    }
                    //Blue in between
                    else if(x%2 != 1 || y%2 != 1) {
                        mazeArray[f][x][y] = 7;
                    }

                    else{
                        mazeArray[f][x][y] = 0;
                    }
                }
            }
        }
        mazeArray[floors/2][sizeX/2][sizeY/2] = 4;
    }

    public void update(float dt){
        if(timer > 0.0001f){
            generateMazeStep();
            timer = 0;
        }
        timer += dt;
    }

    public void generateMazeStep(){
        if(wallList.size > 0) {
            Coordinates nextWall = wallList.removeIndex(ThreadLocalRandom.current().nextInt(0, wallList.size));

            Array<Coordinates> nextCellList = new Array<Coordinates>();





            addToCoordinatesList(nextWall, nextCellList);
            if(nextCellList.size > 0) {
                mazeArray[nextWall.f][nextWall.x][nextWall.y] = 1;
                Coordinates newCell = nextCellList.get(ThreadLocalRandom.current().nextInt(0, nextCellList.size));
                mazeArray[newCell.f][newCell.x][newCell.y] = 1;
                addToCoordinatesList(newCell, wallList);
            }
        }
    }

    private void addToCoordinatesList(Coordinates coordinates, Array<Coordinates> coordinatesList, Array<Commands> attachedCommand){
        //Left
        if(mazeArray[coordinates.f][coordinates.x - 1][coordinates.y] != 2 && mazeArray[coordinates.f][coordinates.x - 1][coordinates.y] != 1){
            coordinatesList.add(new Coordinates(coordinates.f, coordinates.x - 1, coordinates.y));
            attachedCommand.add(Commands.XY);
        }
        //Right
        if(mazeArray[coordinates.f][coordinates.x + 1][coordinates.y] != 2 && mazeArray[coordinates.f][coordinates.x + 1][coordinates.y] != 1){
            coordinatesList.add(new Coordinates(coordinates.f, coordinates.x + 1, coordinates.y));
            attachedCommand.add(Commands.XY);
        }
        //Backwards
        if(mazeArray[coordinates.f][coordinates.x][coordinates.y - 1] != 2 &&  mazeArray[coordinates.f][coordinates.x][coordinates.y - 1] != 1){
            coordinatesList.add(new Coordinates(coordinates.f, coordinates.x, coordinates.y - 1));
            attachedCommand.add(Commands.XY);
        }
        //Forwards
        if(mazeArray[coordinates.f][coordinates.x][coordinates.y + 1] != 2 && mazeArray[coordinates.f][coordinates.x][coordinates.y + 1] != 1){
            coordinatesList.add(new Coordinates(coordinates.f, coordinates.x, coordinates.y + 1));
            attachedCommand.add(Commands.XY);
        }
        //Upwards
        if(coordinates.f != getFloors() - 1 &&  mazeArray[coordinates.f + 1][coordinates.x][coordinates.y] != 1 && mazeArray[coordinates.f+1][coordinates.x][coordinates.y] != 2){
            coordinatesList.add(new Coordinates(coordinates.f + 1, coordinates.x, coordinates.y));
            attachedCommand.add(Commands.Z);
        }
        //Downwards
        if(coordinates.f != 0 && mazeArray[coordinates.f - 1][coordinates.x][coordinates.y] != 1 && mazeArray[coordinates.f-1][coordinates.x][coordinates.y] != 2){
            coordinatesList.add(new Coordinates(coordinates.f - 1, coordinates.x, coordinates.y ));
            attachedCommand.add(Commands.Z);
        }
    }


    public void printMaze(){
        for(int f = 0; f < floors; f++){
            System.out.println("Floor: " + f);
            System.out.println();
            for (int y = sizeY -1; y >= 0 ; y--){
                for(int x = 0; x < sizeX; x++){
                    System.out.print(mazeArray[f][x][y]);
                }
                System.out.println();
            }
            System.out.println();
        }
        //System.out.println("Zeroes : " + zeroes + " Ones: " + ones + " Twos: " + twos);
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



}
