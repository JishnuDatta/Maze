package me.jishnu.mazegame;

public class Coordinates {
    int f, x, y;

    public Coordinates(int f, int x, int y) {
        this.f = f;
        this.x = x;
        this.y = y;
    }
    public void printOut(){
        System.out.println("Floor: " + f + " X: " + x + " Y: " + y);
    }
}
