package me.jishnu.mazegame.Tools;

public class Coordinates {
    public int f, x, y;

    public Coordinates(int f, int x, int y) {
        this.f = f;
        this.x = x;
        this.y = y;
    }
    public String toString(){
        return  "(F: " + f + " X: " + x + " Y: " + y + ")";
    }
}
