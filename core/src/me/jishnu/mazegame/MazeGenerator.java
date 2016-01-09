package me.jishnu.mazegame;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;
import java.util.concurrent.ThreadLocalRandom;

public class MazeGenerator {
    private int floors, sizeX, sizeY, players;
    private int[][][] mazeArray;
    float timer;
    private Array<Coordinates> wallList;
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

        for(Coordinates c: vertices){
            addToCoordinatesList(c, 0);
        }
    }

    public void setUpMaze(){
        for(int f = 0; f < floors; f++){
            for(int x = 0; x < sizeX; x++){
                for (int y = 0; y < sizeY; y++){
                    //untouchable parts
                    if(y == 0 || y == (sizeY - 1) || x == 0 || x == (sizeX - 1) || (x%2 != 1 && y%2 != 1) || (x%2 != 1 || y%2 != 1)&& f%2!= 0) {
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
                System.out.println("yo");
                mazeArray[c.f][c.x][c.y] = 1;
            }
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
            //addToCoordinatesList(new Coordinates(floors/2,sizeX/2 + 1,sizeY/2), 0);
        }
        else if(entranceChosen == 1){
           mazeArray[floors/2][sizeX/2 - 1][sizeY/2] = 1;
          //  addToCoordinatesList(new Coordinates(floors/2,sizeX/2 - 1,sizeY/2), 0);
        }
        else if(entranceChosen == 2){
            mazeArray[floors/2][sizeX/2][sizeY/2 + 1] = 1;
          //  addToCoordinatesList(new Coordinates(floors/2,sizeX/2,sizeY/2 + 1), 0);
        }
        else{
            mazeArray[floors/2][sizeX/2][sizeY/2 - 1] = 1;
          //  addToCoordinatesList(new Coordinates(floors/2,sizeX/2,sizeY/2 - 1), 0);
        }
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
            //Random generator bias's with a ^4 power relationship, objects at the end of the wall list.
            double r = Math.random();
            r = Math.pow(r, 0.05);
            r *= (wallList.size - 1);
            int index = (int) Math.round(r);
            Coordinates nextWall = wallList.removeIndex(index);
            Coordinates newCell;
            //Checks where the new cell is based on which wall got chosen.
            //Left
            boolean wentUp = false;
            boolean wentDown = false;
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

            }
            //Upwards
            else if (nextWall.f != getFloors() - 1 && mazeArray[nextWall.f + 1][nextWall.x][nextWall.y] == 7 && (zDirectionHelper1(new Coordinates(nextWall.f - 1, nextWall.x, nextWall.y)))) {
                newCell = new Coordinates(nextWall.f + 1, nextWall.x, nextWall.y);
                wentUp = true;
            }
            //Downwards
            else if (nextWall.f != 0 && mazeArray[nextWall.f - 1][nextWall.x][nextWall.y] == 7 && (zDirectionHelper1(new Coordinates(nextWall.f + 1, nextWall.x, nextWall.y)))) {
                newCell = new Coordinates(nextWall.f - 1, nextWall.x, nextWall.y);
                wentDown = true;

            } else {
                newCell = new Coordinates(-1, -1, -1);
            }
            if (newCell.f != -1) {
                if (wentDown) {
                    mazeArray[nextWall.f + 1][nextWall.x][nextWall.y] = 5;
                    mazeArray[nextWall.f][nextWall.x][nextWall.y] = 1;
                    mazeArray[newCell.f][newCell.x][newCell.y] = 6;
                    addToCoordinatesList(newCell, 3);

                } else if (wentUp) {
                    mazeArray[nextWall.f - 1][nextWall.x][nextWall.y] = 6;
                    mazeArray[nextWall.f][nextWall.x][nextWall.y] = 1;
                    mazeArray[newCell.f][newCell.x][newCell.y] = 5;
                    addToCoordinatesList(newCell, 2);

                } else {
                    mazeArray[nextWall.f][nextWall.x][nextWall.y] = 1;
                    mazeArray[newCell.f][newCell.x][newCell.y] = 1;
                    addToCoordinatesList(newCell, 0);
                }
            } else {
                mazeArray[nextWall.f][nextWall.x][nextWall.y] = 2;
            }
        }
    }

    //Only let ladders be created at dead ends
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

    private boolean zDirectionHelper2(){
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
        //Upwards
        if(newCell.f != getFloors() - 1 &&  mazeArray[newCell.f + 1][newCell.x][newCell.y] == 7 && type != 2 ){
            //ladderList.add(new Coordinates(newCell.f + 1, newCell.x, newCell.y));
            wallList.insert(0, new Coordinates(newCell.f + 1, newCell.x, newCell.y));
        }
        //Downwards
        if(newCell.f != 0 && mazeArray[newCell.f - 1][newCell.x][newCell.y] == 7 & type != 3){
            //ladderList.add(new Coordinates(newCell.f - 1, newCell.x, newCell.y ));
            wallList.insert(0, new Coordinates(newCell.f - 1, newCell.x, newCell.y));
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
