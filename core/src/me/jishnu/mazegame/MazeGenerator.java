package me.jishnu.mazegame;

import com.badlogic.gdx.utils.Array;
import java.util.concurrent.ThreadLocalRandom;

public class MazeGenerator {
    private int floors, sizeX, sizeY, players;
    private int[][][] mazeArray;
    int zeroes, ones, twos = 0;
    float timer;
    Coordinates start0 = new Coordinates(0,1,1);
    Coordinates block0 = new Coordinates(1,1,1);
    Array<Coordinates> wallList;
    Array<Commands> commandsWallList;
    public enum Commands {XY, Z};


    public MazeGenerator(int floors, int sizeX, int sizeY, int players){
        //First indicates floor, second indicates,
        this.floors = floors;
        this.sizeX = sizeX;
        this.sizeY = sizeY;
        this.players = players;
        mazeArray = new int[floors][sizeX][sizeY];
        setUpMaze();
        //printMaze();
        timer = 0;
        wallList = new Array<Coordinates>();
        commandsWallList = new Array<Commands>();
        //mazeArray[start1.f][start1.x][start1.y] = 1;
        addToCoordinatesList(start0, wallList, commandsWallList);
        //addToCoordinatesList(start1, wallList, commandsWallList);
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
        if(players == 1){
            mazeArray[start0.f][start0.x][start0.y] = 1;
            mazeArray[block0.f][block0.x][block0.y] = 2;
        }
        else{
        }
        //The key
        mazeArray[floors/2][sizeX/2][sizeY/2] = 4;
    }

    public void update(float dt){
        if(timer > 0.1f){
            System.out.println(wallList);
            generateMazeStep();
            timer = 0;
        }
        timer += dt;
    }

    public void generateMazeStep(){
        if(wallList.size > 0) {
            Coordinates nextWall = wallList.removeIndex(ThreadLocalRandom.current().nextInt(0, wallList.size));
            Coordinates newCell;
            //Checks where the new cell is based on which wall got chosen.
            //Left
            if(mazeArray[nextWall.f][nextWall.x - 1][nextWall.y] != 2 && mazeArray[nextWall.f][nextWall.x - 1][nextWall.y] != 1){
              newCell = new Coordinates(nextWall.f, nextWall.x - 1, nextWall.y);
            }
            //Right
            else if(mazeArray[nextWall.f][nextWall.x + 1][nextWall.y] != 2 && mazeArray[nextWall.f][nextWall.x + 1][nextWall.y] != 1){
                newCell = new Coordinates(nextWall.f, nextWall.x + 1, nextWall.y);
            }
            //Backwards
            else if(mazeArray[nextWall.f][nextWall.x][nextWall.y - 1] != 2 &&  mazeArray[nextWall.f][nextWall.x][nextWall.y - 1] != 1){
                newCell = new Coordinates(nextWall.f, nextWall.x, nextWall.y-1);
            }
            //Forwards
            else if(mazeArray[nextWall.f][nextWall.x][nextWall.y + 1] != 2 && mazeArray[nextWall.f][nextWall.x][nextWall.y + 1] != 1){
                newCell = new Coordinates(nextWall.f, nextWall.x, nextWall.y+1);
            }
            //Upwards
            else if(nextWall.f != getFloors() - 1 &&  mazeArray[nextWall.f + 1][nextWall.x][nextWall.y] != 1 && mazeArray[nextWall.f+1][nextWall.x][nextWall.y] != 2){
                newCell = new Coordinates(nextWall.f+1, nextWall.x, nextWall.y);
            }
            //Downwards
            else if(nextWall.f != 0 && mazeArray[nextWall.f - 1][nextWall.x][nextWall.y] != 1 && mazeArray[nextWall.f-1][nextWall.x][nextWall.y] != 2){
                newCell = new Coordinates(nextWall.f-1, nextWall.x, nextWall.y);
            }
            else{
                newCell = new Coordinates(-1,-1,-1);
            }
            if(newCell.f != -1) {
                mazeArray[nextWall.f][nextWall.x][nextWall.y] = 1;
                mazeArray[newCell.f][newCell.x][newCell.y] = 1;
                addToCoordinatesList(newCell, wallList, commandsWallList);
            }
        }
    }

    private void addToCoordinatesList(Coordinates newCell, Array<Coordinates> wallList, Array<Commands> attachedCommand){
        //Left
        if(mazeArray[newCell.f][newCell.x - 1][newCell.y] != 2 && mazeArray[newCell.f][newCell.x - 1][newCell.y] != 1){
            wallList.add(new Coordinates(newCell.f, newCell.x - 1, newCell.y));
            attachedCommand.add(Commands.XY);
        }
        //Right
        if(mazeArray[newCell.f][newCell.x + 1][newCell.y] != 2 && mazeArray[newCell.f][newCell.x + 1][newCell.y] != 1){
            wallList.add(new Coordinates(newCell.f, newCell.x + 1, newCell.y));
            attachedCommand.add(Commands.XY);
        }
        //Backwards
        if(mazeArray[newCell.f][newCell.x][newCell.y - 1] != 2 &&  mazeArray[newCell.f][newCell.x][newCell.y - 1] != 1){
            wallList.add(new Coordinates(newCell.f, newCell.x, newCell.y - 1));
            attachedCommand.add(Commands.XY);
        }
        //Forwards
        if(mazeArray[newCell.f][newCell.x][newCell.y + 1] != 2 && mazeArray[newCell.f][newCell.x][newCell.y + 1] != 1){
            wallList.add(new Coordinates(newCell.f, newCell.x, newCell.y + 1));
            attachedCommand.add(Commands.XY);
        }
        //Upwards
        if(newCell.f != getFloors() - 1 &&  mazeArray[newCell.f + 1][newCell.x][newCell.y] != 1 && mazeArray[newCell.f+1][newCell.x][newCell.y] != 2){
            wallList.add(new Coordinates(newCell.f + 1, newCell.x, newCell.y));
            attachedCommand.add(Commands.Z);
        }
        //Downwards
        if(newCell.f != 0 && mazeArray[newCell.f - 1][newCell.x][newCell.y] != 1 && mazeArray[newCell.f-1][newCell.x][newCell.y] != 2){
            wallList.add(new Coordinates(newCell.f - 1, newCell.x, newCell.y ));
            attachedCommand.add(Commands.Z);
        }
    }

    //Bias indicates how many times more names into the hat xys put in than zs.
//    private int indexBiasCalculation(int bias){
//        int xys = 0;
//        int zs = 0;
//        int total = 0;
//        for(Commands command: commandsWallList){
//            if(command == Commands.XY){
//                xys++;
//            }
//            else if(command == Commands.Z){
//                zs++;
//            }
//        }
//        for(xy){
//
//        }
//        int placement = ThreadLocalRandom.current().nextInt(0, total - 1);
//    }


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
