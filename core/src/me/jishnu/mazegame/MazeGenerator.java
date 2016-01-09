package me.jishnu.mazegame;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;
import java.util.concurrent.ThreadLocalRandom;

public class MazeGenerator {
    private int floors, sizeX, sizeY, players;
    private int[][][] mazeArray;
    float timer;
    private Array<Coordinates> wallList;
    private int lastFloor;
    private Coordinates[] vertices;
    private Coordinates[] blocks;;
    public MazeGenerator(int floors, int sizeX, int sizeY, int players){
        //First indicates floor, second indicates,
        this.floors = floors;
        this.sizeX = sizeX;
        this.sizeY = sizeY;
        this.players = players;
        Coordinates[] vertices1 = {new Coordinates(0,1,1), new Coordinates(0,sizeX - 2,1), new Coordinates(0,1,sizeY - 2), new Coordinates(0,sizeX - 2,sizeY - 2), new Coordinates(floors - 1,1,1), new Coordinates(floors - 1,sizeX - 2,1), new Coordinates(floors - 1,1,sizeY - 2), new Coordinates(floors - 1,sizeX - 2,sizeY - 2)};
        Coordinates[] blocks1 = {new Coordinates(1,1,1), new Coordinates(1,sizeX - 2,1), new Coordinates(1,1,sizeY - 2), new Coordinates(1,sizeX - 2,sizeY - 2), new Coordinates(floors - 2,1,1), new Coordinates(floors - 2,sizeX - 2,1), new Coordinates(floors - 2,1,sizeY - 2), new Coordinates(floors - 2,sizeX - 2,sizeY - 2)};
        vertices = vertices1;
        blocks = blocks1;
        wallList = new Array<Coordinates>();
        mazeArray = new int[floors][sizeX][sizeY];
        setUpMaze();
        //printMaze();
        timer = 0;
    }

    public void setUpMaze(){
        for(int f = 0; f < floors; f++){
            for(int x = 0; x < sizeX; x++){
                for (int y = 0; y < sizeY; y++){
                    //untouchable parts
                    if(y == 0 || y == (sizeY - 1) || x == 0 || x == (sizeX - 1) || (x%2 != 1 && y%2 != 1) && (x%2 != 1 || y%2 != 1)) {
                        mazeArray[f][x][y] = 2;
                    }
                    //drillable parts
                    else{
                        mazeArray[f][x][y] = 7;
                    }
                }
            }
        }
        if(players == 1){
            for(Coordinates c: vertices){
                mazeArray[c.f][c.x][c.y] = 1;
                //addToCoordinatesList(c, 0);
            }
            //Adding stuff to prevent ladders being made at spawn points
            for(Coordinates c: blocks){
                mazeArray[c.f][c.x][c.y] = 2;
            }
        }
        else{
        }

        //The key
        mazeArray[floors/2][sizeX/2][sizeY/2] = 4;
        int entranceChosen = ThreadLocalRandom.current().nextInt(0,4);
        mazeArray[floors/2][sizeX/2 + 1][sizeY/2] = 2;
        mazeArray[floors/2][sizeX/2 - 1][sizeY/2] = 2;
        mazeArray[floors/2][sizeX/2][sizeY/2 + 1] = 2;
        mazeArray[floors/2][sizeX/2][sizeY/2 - 1] = 2;
        if(entranceChosen == 0){
            mazeArray[floors/2][sizeX/2 + 1][sizeY/2] = 1;
            mazeArray[floors/2][sizeX/2 + 2][sizeY/2] = 1;
            addToCoordinatesList(new Coordinates(floors/2,sizeX/2 + 2,sizeY/2), 0);
        }
        else if(entranceChosen == 1){
           mazeArray[floors/2][sizeX/2 - 1][sizeY/2] = 1;
            mazeArray[floors/2][sizeX/2 - 2][sizeY/2] = 1;
            addToCoordinatesList(new Coordinates(floors/2,sizeX/2 - 2,sizeY/2), 0);
        }
        else if(entranceChosen == 2){
            mazeArray[floors/2][sizeX/2][sizeY/2 + 1] = 1;
            mazeArray[floors/2][sizeX/2 + 2][sizeY/2 + 2] = 1;
            addToCoordinatesList(new Coordinates(floors/2,sizeX/2,sizeY/2 + 2), 0);
        }
        else{
            mazeArray[floors/2][sizeX/2][sizeY/2 - 1] = 1;
            mazeArray[floors/2][sizeX/2][sizeY/2 - 2] = 1;
            addToCoordinatesList(new Coordinates(floors/2,sizeX/2,sizeY/2 - 2), 0);
        }
    }

    public void update(float dt){
        if(timer > 0.01f){
            generateMazeStep();
            timer = 0;
        }
        timer += dt;
    }

    public void generateMazeStep(){
        if(wallList.size > 0) {
            int index = ThreadLocalRandom.current().nextInt(0, wallList.size);
            Coordinates nextWall = wallList.removeIndex(index);
            Coordinates newCell;
            //Checks where the new cell is based on which wall got chosen.
            //Left
            if (mazeArray[nextWall.f][nextWall.x - 1][nextWall.y] == 7) {
                newCell = new Coordinates(nextWall.f, nextWall.x - 1, nextWall.y);
            }
            //Right
            else if (mazeArray[nextWall.f][nextWall.x + 1][nextWall.y] == 7) {
                newCell = new Coordinates(nextWall.f, nextWall.x + 1, nextWall.y);

            }
            //Backwards
            else if (mazeArray[nextWall.f][nextWall.x][nextWall.y - 1] == 7) {
                newCell = new Coordinates(nextWall.f, nextWall.x, nextWall.y - 1);

            }
            //Forwards
            else if (mazeArray[nextWall.f][nextWall.x][nextWall.y + 1] == 7) {
                newCell = new Coordinates(nextWall.f, nextWall.x, nextWall.y + 1);
            } else {
                newCell = new Coordinates(-1, -1, -1);
            }
            if (newCell.f != -1) {
                mazeArray[nextWall.f][nextWall.x][nextWall.y] = 1;
                mazeArray[newCell.f][newCell.x][newCell.y] = 1;

                addToCoordinatesList(newCell, 0);
                lastFloor = nextWall.f;
            }
        }
            //Ladders creation
          //  else if(true){
            //createLadders(1, lastFloor);
     //   }
            //WallCleanup
           // else{

            //}
    }


    private void createLadders(int upDown, int f){
        Array<Coordinates> potentialLadders = new Array<Coordinates>();
        int upLadders=0;
        int downLadders=0;
        for(int x = 0; x < sizeX; x++){
            for(int y = 0; y < sizeY; y++){
                if(mazeArray[f][x][y] == 6){
                    upLadders++;
                }
                if(mazeArray[f][x][y] == 9){
                    downLadders++;
                }
            }
        }
        //Up ladders
        for(int x = 0; x < sizeX; x++){
            for(int y = 0; y < sizeY; y++){
                if(mazeArray[f][x][y] == 1 && zDirectionHelper1(new Coordinates(f, x, y)) && f != floors - 1 && (mazeArray[f + 1][x][y] == 7 || (mazeArray[f + 1][x][y] == 1 && zDirectionHelper1(new Coordinates(f+1,x,y))))){
                  //potentialLadders.add(new Coordinates(f, x, y));
                    mazeArray[f][x][y] = 6;

                }
            }
        }
//        if(potentialLadders.size > 0) {
//            int index = ThreadLocalRandom.current().nextInt(0, potentialLadders.size);
//            mazeArray[f][potentialLadders.get(index).x][potentialLadders.get(index).y] = 6;
//            mazeArray[f + 1][potentialLadders.get(index).x][potentialLadders.get(index).y] = 9;
//        }
    }


    //Returns true only 1 path around the current coordinate
    private boolean zDirectionHelper1(Coordinates c){
        int howMany = 0;
        if(mazeArray[c.f][c.x+1][c.y] == 1){
            howMany ++;
        }
        if(mazeArray[c.f][c.x-1][c.y] == 1){
            howMany ++;
        }
        if(mazeArray[c.f][c.x][c.y+1] == 1){
            howMany ++;
        }
        if(mazeArray[c.f][c.x][c.y-1] == 1){
            howMany ++;
        }
        if(howMany == 1){
            return true;
        }
            return false;
    }

    private void addToCoordinatesList(Coordinates newCell, int type){
        //0 = XY movement, 1 = UP, 2 = DOWN.
        int chosen = ThreadLocalRandom.current().nextInt(0,4);
        //Left
        if(mazeArray[newCell.f][newCell.x - 1][newCell.y] == 7 && (type == 0 || chosen == 0)){
            wallList.add(new Coordinates(newCell.f, newCell.x - 1, newCell.y));
        }
        //Right
        if(mazeArray[newCell.f][newCell.x + 1][newCell.y] == 7 && (type == 0 || chosen == 1)){
            wallList.add(new Coordinates(newCell.f, newCell.x + 1, newCell.y));
        }
        //Backwards
        if(mazeArray[newCell.f][newCell.x][newCell.y - 1] == 7 && (type == 0 || chosen == 2)){
            wallList.add(new Coordinates(newCell.f, newCell.x, newCell.y - 1));
        }
        //Forwards
        if(mazeArray[newCell.f][newCell.x][newCell.y + 1] == 7 && (type == 0 || chosen == 3)){
            wallList.add(new Coordinates(newCell.f, newCell.x, newCell.y + 1));
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
