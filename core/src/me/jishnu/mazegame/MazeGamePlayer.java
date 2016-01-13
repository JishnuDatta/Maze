package me.jishnu.mazegame;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;

public class MazeGamePlayer extends Game{
    private Screen currentScreen;
    private PlayScreen playScreen;
    public MazeGamePlayer(PlayScreen playScreen){
        this.playScreen = playScreen;
        create();
    }
    @Override
    public void create() {
        currentScreen = playScreen;
        setScreen(currentScreen);
    }

    public void show() {
        getScreen().show();
    }

    public void hide() {
        getScreen().hide();
    }

    public void render(float delta) {
        getScreen().render(delta);
    }
}
