package me.jishnu.mazegame.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.badlogic.gdx.tools.texturepacker.TexturePacker;

import me.jishnu.mazegame.MazeGame;

public class DesktopLauncher {
	public static void main (String[] arg) {
		TexturePacker.process("../assets/Individual Images", "../assets", "Assets");
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		new LwjglApplication(new MazeGame(), config);
		config.width = 1280;
		config.height = 720;
	}
}
