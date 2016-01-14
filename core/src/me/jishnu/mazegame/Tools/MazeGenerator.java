package me.jishnu.mazegame.Tools;

import com.badlogic.gdx.utils.Array;
import java.util.concurrent.ThreadLocalRandom;

public class MazeGenerator {
    private int floors;
    private Constants.tiles[][][] mazeArray;
    float timer;
    private Array<me.jishnu.mazegame.Tools.Coordinates> wallList;
    private int maxDimension;
    private int lastFloor;
    private boolean finished;

    public MazeGenerator(int floors) {
        this.floors = floors;
        maxDimension = 5 + 4 * (floors);
        wallList = new Array<me.jishnu.mazegame.Tools.Coordinates>();
        lastFloor = floors - 1;
        mazeArray = new Constants.tiles[floors][maxDimension][maxDimension];
        timer = 0;
        finished = false;
        setUpMaze();
       while(!finished){
            generateMazeStep();
        }
    }

    public Constants.tiles[][][] getMazeArray(){
        return mazeArray;
    }

    private void setUpMaze() {
        for (int f = 0; f < floors; f++) {
            for (int x = 0; x < maxDimension; x++) {
                for (int y = 0; y < maxDimension; y++) {
                    if(x%2 == 1 && y%2 == 1){
                        mazeArray[f][x][y] = Constants.tiles.WALL;
                    }
                    else if(( x < maxDimension - 2 *f - 1 && y < maxDimension - 2 * f - 1 && x > 2 * f && y > 2 * f)){
                        mazeArray[f][x][y] = Constants.tiles.DRILLABLE_WALL;
                    }
                    else{
                        mazeArray[f][x][y] = Constants.tiles.WALL;
                    }
                }
            }
        }

        //The key
        mazeArray[floors - 1][maxDimension/2][maxDimension/2] = Constants.tiles.TEMPLE;
        zDirectionHelper2(new me.jishnu.mazegame.Tools.Coordinates(floors - 1 ,maxDimension/2,maxDimension/2));
    }

    private void generateMazeStep(){
        if(wallList.size > 0) {
            int index = ThreadLocalRandom.current().nextInt(0, wallList.size);
            me.jishnu.mazegame.Tools.Coordinates nextWall = wallList.removeIndex(index);
            me.jishnu.mazegame.Tools.Coordinates newCell;
            //Checks where the new cell is based on which wall got chosen.
            //Left
            if (mazeArray[nextWall.f][nextWall.x - 1][nextWall.y] == Constants.tiles.DRILLABLE_WALL) {
                 newCell = new me.jishnu.mazegame.Tools.Coordinates(nextWall.f, nextWall.x - 1, nextWall.y);
            }
            //Right
            else if (mazeArray[nextWall.f][nextWall.x + 1][nextWall.y] == Constants.tiles.DRILLABLE_WALL) {
                newCell = new me.jishnu.mazegame.Tools.Coordinates(nextWall.f, nextWall.x + 1, nextWall.y);

            }
            //Backwards
            else if (mazeArray[nextWall.f][nextWall.x][nextWall.y - 1] == Constants.tiles.DRILLABLE_WALL) {
                newCell = new me.jishnu.mazegame.Tools.Coordinates(nextWall.f, nextWall.x, nextWall.y - 1);

            }
            //Forwards
            else if (mazeArray[nextWall.f][nextWall.x][nextWall.y + 1] == Constants.tiles.DRILLABLE_WALL) {
                newCell = new me.jishnu.mazegame.Tools.Coordinates(nextWall.f, nextWall.x, nextWall.y + 1);
            } else {
                newCell = new me.jishnu.mazegame.Tools.Coordinates(-1, -1, -1);
            }
            if (newCell.f != -1) {
                mazeArray[nextWall.f][nextWall.x][nextWall.y] = Constants.tiles.GROUND;
                mazeArray[newCell.f][newCell.x][newCell.y] = Constants.tiles.GROUND;

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
                        if(mazeArray[f][x][y] == Constants.tiles.DRILLABLE_WALL){
                            mazeArray[f][x][y] = Constants.tiles.WALL;
                        }
                    }
                }
            }
            finished = true;
        }
    }


    private void createLadders(int f){
        Array<me.jishnu.mazegame.Tools.Coordinates> potentialLadders = new Array<me.jishnu.mazegame.Tools.Coordinates>();
        for(int x = 0; x < maxDimension; x+=2){
            for(int y = 0; y < maxDimension; y+=2){
                if(mazeArray[f][x][y] == Constants.tiles.GROUND && zDirectionHelper1(new me.jishnu.mazegame.Tools.Coordinates(f, x, y))) {
                    potentialLadders.add(new me.jishnu.mazegame.Tools.Coordinates(f, x, y));
                }
            }
        }
        if(potentialLadders.size > 0) {
            int ladders = ThreadLocalRandom.current().nextInt(1, 2 + 2 * (floors - f));
            if(potentialLadders.size < ladders) {
                ladders = potentialLadders.size;
            }
            for(int i = 0; i < ladders; i++){
                int index = ThreadLocalRandom.current().nextInt(0, potentialLadders.size);
                if(zDirectionHelper2(new me.jishnu.mazegame.Tools.Coordinates(f - 1, potentialLadders.get(index).x, potentialLadders.get(index).y))){
                    if(mazeArray[f][potentialLadders.get(index).x + 1][potentialLadders.get(index).y] == Constants.tiles.GROUND){
                        mazeArray[f][potentialLadders.get(index).x][potentialLadders.get(index).y] = Constants.tiles.LADDER_DOWN_EAST;
                    }
                    else if(mazeArray[f][potentialLadders.get(index).x - 1][potentialLadders.get(index).y] == Constants.tiles.GROUND){
                        mazeArray[f][potentialLadders.get(index).x][potentialLadders.get(index).y] = Constants.tiles.LADDER_DOWN_WEST;
                    }
                    else if(mazeArray[f][potentialLadders.get(index).x][potentialLadders.get(index).y + 1] == Constants.tiles.GROUND){
                        mazeArray[f][potentialLadders.get(index).x][potentialLadders.get(index).y] = Constants.tiles.LADDER_DOWN_NORTH;
                    }
                    else{
                        mazeArray[f][potentialLadders.get(index).x][potentialLadders.get(index).y] = Constants.tiles.LADDER_DOWN_SOUTH;
                    }
                    if(mazeArray[f - 1][potentialLadders.get(index).x + 1][potentialLadders.get(index).y] == Constants.tiles.GROUND){
                        mazeArray[f - 1][potentialLadders.get(index).x][potentialLadders.get(index).y] = Constants.tiles.LADDER_UP_EAST;

                    }
                    else if(mazeArray[f - 1][potentialLadders.get(index).x - 1][potentialLadders.get(index).y] == Constants.tiles.GROUND){
                        mazeArray[f - 1][potentialLadders.get(index).x][potentialLadders.get(index).y] = Constants.tiles.LADDER_UP_WEST;
                    }
                    else if(mazeArray[f - 1][potentialLadders.get(index).x][potentialLadders.get(index).y + 1] == Constants.tiles.GROUND){
                        mazeArray[f - 1][potentialLadders.get(index).x][potentialLadders.get(index).y] = Constants.tiles.LADDER_UP_NORTH;
                    }
                    else{
                        mazeArray[f - 1][potentialLadders.get(index).x][potentialLadders.get(index).y] = Constants.tiles.LADDER_UP_SOUTH;
                    }
                    potentialLadders.removeIndex(index);
                }
                else{
                    potentialLadders.removeIndex(index);
                }
            }
        }
    }

    //Returns true only 1 path around the current coordinate
    private boolean zDirectionHelper1(me.jishnu.mazegame.Tools.Coordinates c){
        int howMany = 0;
        if(mazeArray[c.f][c.x+1][c.y] == Constants.tiles.GROUND){
            howMany ++;
        }
        if(mazeArray[c.f][c.x-1][c.y] == Constants.tiles.GROUND){
            howMany ++;
        }
        if(mazeArray[c.f][c.x][c.y+1] == Constants.tiles.GROUND){
            howMany ++;
        }
        if(mazeArray[c.f][c.x][c.y-1] == Constants.tiles.GROUND){
            howMany ++;
        }
            return (howMany == 1);
    }

    private boolean zDirectionHelper2(me.jishnu.mazegame.Tools.Coordinates c){
        Array<me.jishnu.mazegame.Tools.Coordinates> possibleExits = new Array<me.jishnu.mazegame.Tools.Coordinates>();
        Array<me.jishnu.mazegame.Tools.Coordinates> possibleNewCell = new Array<me.jishnu.mazegame.Tools.Coordinates>();
        if(mazeArray[c.f][c.x][c.y] != Constants.tiles.GROUND){
        if(mazeArray[c.f][c.x - 1][c.y] == Constants.tiles.DRILLABLE_WALL && mazeArray[c.f][c.x - 2][c.y] == Constants.tiles.DRILLABLE_WALL){
            possibleExits.add(new me.jishnu.mazegame.Tools.Coordinates(c.f, c.x - 1, c.y));
            possibleNewCell.add(new me.jishnu.mazegame.Tools.Coordinates(c.f, c.x - 2, c.y));
        }
        //Right
        if(mazeArray[c.f][c.x + 1][c.y] == Constants.tiles.DRILLABLE_WALL && mazeArray[c.f][c.x + 2][c.y] == Constants.tiles.DRILLABLE_WALL){
            possibleExits.add(new me.jishnu.mazegame.Tools.Coordinates(c.f, c.x + 1, c.y));
            possibleNewCell.add(new me.jishnu.mazegame.Tools.Coordinates(c.f, c.x + 2, c.y));
        }
        //Backwards
        if(mazeArray[c.f][c.x][c.y - 1] == Constants.tiles.DRILLABLE_WALL && mazeArray[c.f][c.x][c.y - 2] == Constants.tiles.DRILLABLE_WALL){
            possibleExits.add(new me.jishnu.mazegame.Tools.Coordinates(c.f, c.x, c.y - 1));
            possibleNewCell.add(new me.jishnu.mazegame.Tools.Coordinates(c.f, c.x, c.y - 2));
        }
        //Forwards
        if(mazeArray[c.f][c.x][c.y + 1] == Constants.tiles.DRILLABLE_WALL && mazeArray[c.f][c.x][c.y + 2] == Constants.tiles.DRILLABLE_WALL){
            possibleExits.add(new me.jishnu.mazegame.Tools.Coordinates(c.f, c.x, c.y + 1));
            possibleNewCell.add(new me.jishnu.mazegame.Tools.Coordinates(c.f, c.x, c.y + 2));
        }
        if(possibleExits.size > 0) {
            int index = ThreadLocalRandom.current().nextInt(0, possibleExits.size);
            me.jishnu.mazegame.Tools.Coordinates possibleExit = possibleExits.removeIndex(index);
            me.jishnu.mazegame.Tools.Coordinates possibleCell = possibleNewCell.removeIndex(index);
            mazeArray[possibleExit.f][possibleExit.x][possibleExit.y] = Constants.tiles.GROUND;
            mazeArray[possibleCell.f][possibleCell.x][possibleCell.y] = Constants.tiles.GROUND;
            addToCoordinatesList(possibleCell);
            for (me.jishnu.mazegame.Tools.Coordinates blocked : possibleExits) {
                mazeArray[blocked.f][blocked.x][blocked.y] = Constants.tiles.WALL;
            }
            return true;
        }}
        return false;
    }


    private void addToCoordinatesList(me.jishnu.mazegame.Tools.Coordinates newCell){
        //Left
        if(mazeArray[newCell.f][newCell.x - 1][newCell.y] == Constants.tiles.DRILLABLE_WALL){
            wallList.add(new me.jishnu.mazegame.Tools.Coordinates(newCell.f, newCell.x - 1, newCell.y));
        }
        //Right
        if(mazeArray[newCell.f][newCell.x + 1][newCell.y] == Constants.tiles.DRILLABLE_WALL){
            wallList.add(new me.jishnu.mazegame.Tools.Coordinates(newCell.f, newCell.x + 1, newCell.y));
        }
        //Backwards
        if(mazeArray[newCell.f][newCell.x][newCell.y - 1] == Constants.tiles.DRILLABLE_WALL){
            wallList.add(new me.jishnu.mazegame.Tools.Coordinates(newCell.f, newCell.x, newCell.y - 1));
        }
        //Forwards
        if(mazeArray[newCell.f][newCell.x][newCell.y + 1] == Constants.tiles.DRILLABLE_WALL){
            wallList.add(new me.jishnu.mazegame.Tools.Coordinates(newCell.f, newCell.x, newCell.y + 1));
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
}
