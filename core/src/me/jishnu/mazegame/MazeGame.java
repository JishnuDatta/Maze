package me.jishnu.mazegame;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Array;

import org.json.JSONException;
import org.json.JSONObject;

import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;
import me.jishnu.mazegame.Screens.PlayScreen;
import me.jishnu.mazegame.Screens.WaitScreen;
import me.jishnu.mazegame.Tools.Constants;
import me.jishnu.mazegame.Tools.Coordinates;
import me.jishnu.mazegame.Tools.MazeGenerator;

public class MazeGame extends Game{
	private Socket socket;
	private PlayScreen playScreen;
	private Constants.teams team;
	private Coordinates coordinates;
	private MazeGenerator generator;

	@Override
	public void render() {
		super.render();
		if(team != null && coordinates != null && generator != null){
			playScreen = new PlayScreen(generator,coordinates,team);
			setScreen(playScreen);
			team = null;
		}
	}

	public void create() {
		connectSocket();
		configSocketEvents();
		setScreen(new WaitScreen());
	}

	public void connectSocket() {
		try {
			socket = IO.socket("http://localhost:8080");
			socket.connect();
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	public void configSocketEvents(){
		socket.on(Socket.EVENT_CONNECT, new Emitter.Listener() {
			@Override
			public void call(Object... args) {
				Gdx.app.log("SocketIO", "Connected");
			}
		}).on("socketID", new Emitter.Listener() {
			@Override
			public void call(Object... args) {
				JSONObject data = (JSONObject) args[0];
				try {
					String id = data.getString("id");
					Gdx.app.log("SocketIO", "My ID: " + id);
					team = Constants.teams.BLUE_TEAM;
					coordinates = new Coordinates(0,2,2);
					System.out.println("yo");
					generator = new MazeGenerator(3);
				}
				catch(Exception e){
					Gdx.app.log("SocketIO", "Error getting ID");
				}
			}
		}).on("newPlayer", new Emitter.Listener() {
			@Override
			public void call(Object... args) {
				JSONObject data = (JSONObject) args[0];
				try {
					String id = data.getString("id");
					Gdx.app.log("SocketIO", "New Player Connect: " + id);
				} catch (JSONException e) {
					Gdx.app.log("SocketIO", "Error getting new Player ID");
				}
			}
		});
	}
}
