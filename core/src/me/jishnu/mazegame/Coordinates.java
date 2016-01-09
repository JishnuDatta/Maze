package me.jishnu.mazegame;

public class Coordinates {
    int f, x, y;

    public Coordinates(int f, int x, int y) {
        this.f = f;
        this.x = x;
        this.y = y;
    }
    public String toString(){
        return  "(F: " + f + " X: " + x + " Y: " + y + ")";
    }

    public boolean equals(Coordinates comparison){
        if(f == comparison.f && x == comparison.x && y == comparison.y){
            return true;
        }
        else{
            return false;
        }
    }
}
