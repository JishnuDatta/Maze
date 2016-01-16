package me.jishnu.mazegame.Tools;

import com.badlogic.gdx.utils.Array;

public class Constants {
    public enum tiles {WALL, GROUND, TEMPLE, DRILLABLE_WALL, LADDER_UP_WEST, LADDER_UP_EAST, LADDER_UP_SOUTH, LADDER_UP_NORTH, LADDER_DOWN_WEST, LADDER_DOWN_EAST, LADDER_DOWN_SOUTH, LADDER_DOWN_NORTH}
    public enum teams{RED_TEAM, YELLOW_TEAM, GREEN_TEAM, BLUE_TEAM}

 public static final short MAZE_LEVELS = 5;

    //public Coordinates[] startingPositions = {new Coordinates[0][2][2], new Coordinates[0][][], new Coordinates[0][][],new Coordinates[0][][]};


public static final short NOTHING_BIT = 0;
    public static final short WALL_BIT = 1;
    public static final short PLAYER_BIT = 2;
    public static final short LADDER_UP_BIT = 4;
    public static final short LADDER_DOWN_BIT = 8;
    public static final short KEY_BIT = 16;
    public static final short GROUND_BIT = 32;

    public static final int TILE_SIZE = 10;

    public static final float SCALING = 0.5f;
    public static final int WIDTH = 1280;
    public static final int HEIGHT = 720;
}
