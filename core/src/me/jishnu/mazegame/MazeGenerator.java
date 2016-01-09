package me.jishnu.mazegame;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;
import java.util.concurrent.ThreadLocalRandom;

public class MazeGenerator {
    private int floors;
    private int[][][] mazeArray;
    float timer;
    private Array<Coordinates> wallList;
    private int maxDimension;
    private int lastFloor;
    private Coordinates[] vertices;
    private Coordinates[] blocks;

    public MazeGenerator(int floors) {
        //First indicates floor, second indicates,
        this.floors = floors;
        maxDimension = 5 + 2 * (floors);
        Coordinates[] vertices1 = {new Coordinates(0, 1, 1), new Coordinates(0, maxDimension - 2, 1), new Coordinates(0, 1, maxDimension - 2), new Coordinates(0, maxDimension - 2, maxDimension - 2)};
        vertices = vertices1;
        wallList = new Array<Coordinates>();
        lastFloor = floors - 1;
        mazeArray = new int[floors][maxDimension][maxDimension];
        setUpMaze();
        //printMaze();
        timer = 0;
    }

    public void setUpMaze() {
        for (int f = 0; f < floors; f++) {
            for (int x = 0; x < maxDimension; x++) {
                for (int y = 0; y < maxDimension; y++) {
                    if((x%2 != 1 && y%2 != 1) && (x%2 != 1 || y%2 != 1) ){
                        mazeArray[f][x][y] = 0;
                    }
                    else if(x < maxDimension - f - 1 && y < maxDimension - f - 1 && x > f && y > f){
                        mazeArray[f][x][y] = 7;
                    }
                    else{
                        mazeArray[f][x][y] = 0;
                    }
                }
            }
        }

        //The key
        mazeArray[floors - 1][maxDimension/2][maxDimension/2] = 4;
        int entranceChosen = ThreadLocalRandom.current().nextInt(0,4);
        mazeArray[floors-1][maxDimension/2 + 1][maxDimension/2] = 2;
        mazeArray[floors-1][maxDimension/2 - 1][maxDimension/2] = 2;
        mazeArray[floors-1][maxDimension/2][maxDimension/2 + 1] = 2;
        mazeArray[floors-1][maxDimension/2][maxDimension/2 - 1] = 2;
        if(entranceChosen == 0){
            mazeArray[floors-1][maxDimension/2 + 1][maxDimension/2] = 1;
            mazeArray[floors-1][maxDimension/2 + 2][maxDimension/2] = 1;
            addToCoordinatesList(new Coordinates(floors-1,maxDimension/2 + 2,maxDimension/2));
        }
        else if(entranceChosen == 1){
           mazeArray[floors-1][maxDimension/2 - 1][maxDimension/2] = 1;
            mazeArray[floors-1][maxDimension/2 - 2][maxDimension/2] = 1;
            addToCoordinatesList(new Coordinates(floors-1,maxDimension/2 - 2,maxDimension/2));
        }
        else if(entranceChosen == 2){
            mazeArray[floors-1][maxDimension/2][maxDimension/2 + 1] = 1;
            mazeArray[floors-1][maxDimension/2][maxDimension/2 + 2] = 1;
            addToCoordinatesList(new Coordinates(floors-1,maxDimension/2,maxDimension/2 + 2));
        }
        else{
            mazeArray[floors-1][maxDimension/2][maxDimension/2 - 1] = 1;
            mazeArray[floors-1][maxDimension/2][maxDimension/2 - 2] = 1;
            addToCoordinatesList(new Coordinates(floors-1,maxDimension/2,maxDimension/2 - 2));
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

                addToCoordinatesList(newCell);
                lastFloor = nextWall.f;
            }
        }
        //Ladders creation
        else if(lastFloor != 0){
            createLadders(lastFloor);
        }
        else{
            for(int f = 0; f < floors; f++){
                for(int x = 0; x < maxDimension; x++) {
                    for(int y = 0; y < maxDimension; y++) {
                        if(mazeArray[f][x][y] == 7){
                           // mazeArray[f][x][y] = 0;
                        }
                    }
                }
            }
        }
    }


    private void createLadders(int f){
        Array<Coordinates> potentialLadders = new Array<Coordinates>();
        //Down ladders
        for(int x = 1; x < maxDimension; x+=2){
            for(int y = 1; y < maxDimension; y+=2){
                if(mazeArray[f][x][y] == 1 && zDirectionHelper1(new Coordinates(f,x,y))){
                  potentialLadders.add(new Coordinates(f, x, y));
                }
            }
        }
        if(potentialLadders.size > 0) {
            //Starts with 2, goes up by 1 every floor it goes down
            int ladders = ThreadLocalRandom.current().nextInt(1, 2 + floors - f);
            for(int i = 0; i < ladders; i++){
                int index = ThreadLocalRandom.current().nextInt(0, potentialLadders.size);
                mazeArray[f][potentialLadders.get(index).x][potentialLadders.get(index).y] = 6;
                mazeArray[f - 1][potentialLadders.get(index).x][potentialLadders.get(index).y] = 9;
                zDirectionHelper2(new Coordinates(f - 1, potentialLadders.get(index).x, potentialLadders.get(index).y));
            }
        }
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

    private void zDirectionHelper2(Coordinates c){
        Array<Coordinates> possibleExits = new Array<Coordinates>();
        if(mazeArray[c.f][c.x - 2][c.y] == 7){
            possibleExits.add(new Coordinates(c.f, c.x - 1, c.y));
        }
        //Right
        if(mazeArray[c.f][c.x + 2][c.y] == 7 ){
            possibleExits.add(new Coordinates(c.f, c.x + 1, c.y));
        }
        //Backwards
        if(mazeArray[c.f][c.x][c.y - 2] == 7 ){
            possibleExits.add(new Coordinates(c.f, c.x, c.y - 1));
        }
        //Forwards
        if(mazeArray[c.f][c.x][c.y + 2] == 7 ){
            possibleExits.add(new Coordinates(c.f, c.x, c.y + 1));
        }
        int index = ThreadLocalRandom.current().nextInt(0,possibleExits.size);
        wallList.add(possibleExits.removeIndex(index));
        for(Coordinates blocked: possibleExits){
            mazeArray[blocked.f][blocked.x][blocked.y] = 0;
        }
    }

    private void addToCoordinatesList(Coordinates newCell){
        //Left
        if(mazeArray[newCell.f][newCell.x - 1][newCell.y] == 7){
            wallList.add(new Coordinates(newCell.f, newCell.x - 1, newCell.y));
        }
        //Right
        if(mazeArray[newCell.f][newCell.x + 1][newCell.y] == 7){
            wallList.add(new Coordinates(newCell.f, newCell.x + 1, newCell.y));
        }
        //Backwards
        if(mazeArray[newCell.f][newCell.x][newCell.y - 1] == 7){
            wallList.add(new Coordinates(newCell.f, newCell.x, newCell.y - 1));
        }
        //Forwards
        if(mazeArray[newCell.f][newCell.x][newCell.y + 1] == 7){
            wallList.add(new Coordinates(newCell.f, newCell.x, newCell.y + 1));
        }
    }

    public void printMaze(){
        for(int f = 0; f < floors; f++){
            System.out.println("Floor: " + f);
            System.out.println();
            for (int y = maxDimension -1; y >= 0 ; y--){
                for(int x = 0; x < maxDimension; x++){
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
        return maxDimension;
    }

    public int getSizeY() {
        return maxDimension;
    }

    public int getPositionInMazeArray(int f, int x, int y) {
        return mazeArray[f][x][y];
    }
}
