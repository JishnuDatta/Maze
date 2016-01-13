package me.jishnu.mazegame;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Array;

import org.json.JSONException;
import org.json.JSONObject;

import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;

public class MazeGame extends ApplicationAdapter{
	private Socket socket;
	private Array<MazeGenerator> currentGames = new Array<MazeGenerator>();
	private Array<MazeGamePlayer> allUsers = new Array<MazeGamePlayer>();

	public void create() {
		allUsers.add(new MazeGamePlayer(new PlayScreen(new MazeGenerator(5), new Coordinates(0, 2, 2), Constants.teams.RED_TEAM)));

		connectSocket();
		configSocketEvents();
	}

	@Override
	public void render() {
		for(MazeGamePlayer mazeGamePlayer: allUsers){
			mazeGamePlayer.render(Gdx.graphics.getDeltaTime());
		}
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
//				if((allUsers.size % 4) == 0){
//					System.out.println("creating new mae");
//					currentGames.add(new MazeGenerator(5));
//				}
				//Add team stuff here
				//allUsers.add(new PlayScreen(currentGames.get(currentGames.size - 1), new Coordinates(0,2,2), Constants.teams.RED_TEAM));
			}
		}).on("socketID", new Emitter.Listener() {
			@Override
			public void call(Object... args) {
				JSONObject data = (JSONObject) args[0];
				try {
					String id = data.getString("id");
					Gdx.app.log("SocketIO", "My ID: " + id);
				}
				catch(JSONException e){
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
