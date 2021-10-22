package com.gumslingers.entity.systems;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.gumslingers.GumSlingers;
import com.gumslingers.LevelFactory;
import com.gumslingers.entity.components.BodyComponent;
import com.gumslingers.entity.components.PlayerComponent;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;

public class ServerSystem extends IteratingSystem {
    private Socket socket;
    private float timer;
    private HashMap<String, Entity> otherPlayers;
    private LevelFactory levelFactory;
    private OrthographicCamera camera;
	
	public ArrayList<JSONObject> updates = new ArrayList<>();

    public ServerSystem(LevelFactory levelFactory, OrthographicCamera camera) {
        super(Family.all(PlayerComponent.class).get());

        this.levelFactory = levelFactory;
        this.camera = camera;

        timer = 0f;
        otherPlayers = new HashMap<>();
        connectSocket();
        configSocketEvents();
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);

        updateServer(deltaTime);
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
      
    }

    public void updateServer(float deltaTime) {
		timer += deltaTime;
		if (timer >= GumSlingers.FPS && !updates.isEmpty()) {
			for (JSONObject data : updates) {
				socket.emit("playerMoved", data);
			}
			updates.clear();
		}
	}

    private void connectSocket() {
		try {
			socket = IO.socket("http://localhost:8080");
			socket.connect();
		} catch (URISyntaxException e) {
			System.out.println(e);
		}
	}

    private void configSocketEvents() {
		socket.on(Socket.EVENT_CONNECT, new Emitter.Listener() {
			@Override
			public void call(Object... args) {
				Gdx.app.log("SocketIO", "Connected");
				levelFactory.createPlayer(camera);
			}
		}).on("socketID", new Emitter.Listener() {
			@Override
			public void call(Object... args) {
				JSONObject data = (JSONObject) args[0];
				try {
					String id = data.getString("id");
					Gdx.app.log("SocketIO", "My ID " + id);
				} catch (JSONException e) {
					Gdx.app.log("SocketIO", "Error getting ID");
				}
			}
		}).on("newPlayer", new Emitter.Listener() {
			@Override
			public void call(Object... args) {
				JSONObject data = (JSONObject) args[0];
				try {
					String id = data.getString("id");
					Gdx.app.log("SocketIO", "New Player Connected: " + id);
					otherPlayers.put(id, levelFactory.createEnemy(0,0));
				} catch (JSONException e) {
					Gdx.app.log("SocketIO", "Error getting new PlayerID");
				}
			}
		}).on("playerDisconnected", new Emitter.Listener() {
			@Override
			public void call(Object... args) {
				JSONObject data = (JSONObject) args[0];
				try {
					String id = data.getString("id");
					getEngine().removeEntity(otherPlayers.get(id));
				} catch (JSONException e) {
					Gdx.app.log("SocketIO", "Error removing Player");
				}
			}
		}).on("getPlayers", new Emitter.Listener() {
			@Override
			public void call(Object... args) {
				JSONArray objects = (JSONArray) args[0];
				try {
					for(int i = 0; i < objects.length(); i++) {
						float x = ((Double) objects.getJSONObject(i).getDouble("x")).floatValue();
						float y = ((Double) objects.getJSONObject(i).getDouble("y")).floatValue();
						otherPlayers.put(objects.getJSONObject(i).getString("id"), levelFactory.createEnemy(x, y));
					}
				} catch (JSONException e) {
					Gdx.app.log("SocketIO", "Error getting Players");
				}
			}
		}).on("playerMoved", new Emitter.Listener() {
			@Override
			public void call(Object... args) {
				JSONObject data = (JSONObject) args[0];
				try {
                    String id = data.getString("id");
                    Double x = data.getDouble("x");
					Double y = data.getDouble("y");
                    Vector2 position = new Vector2(x.floatValue(), y.floatValue());
                    Body otherPlayer = otherPlayers.get(id).getComponent(BodyComponent.class).body;
                    otherPlayer.setTransform(position, otherPlayer.getAngle());
				} catch (JSONException e) {
					Gdx.app.log("SocketIO", "Error moving player");
				}
			}
		});
    } 
}
