package com.gumslingers;

import com.badlogic.ashley.core.Engine;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.physics.box2d.Box2D;
import com.gumslingers.entity.systems.HookSystem;
import com.gumslingers.entity.systems.BulletSystem;
import com.gumslingers.entity.systems.CollisionSystem;
import com.gumslingers.entity.systems.DebugRenderingSystem;
import com.gumslingers.entity.systems.JointSystem;
import com.gumslingers.entity.systems.PhysicsSystem;
import com.gumslingers.entity.systems.PlayerControlSystem;
import com.gumslingers.entity.systems.ServerSystem;

public class GumSlingers extends ApplicationAdapter {
	private Controller controller;
	private Engine engine;
	private LevelFactory levelFactory;
	private OrthographicCamera camera;

	public static final float FPS = 1/60f;
	
	
	@Override
	public void create () {
		Box2D.init();
		
		camera = new OrthographicCamera(16*8,9*8);
		controller = new Controller();
		engine = new Engine();
		levelFactory = new LevelFactory(engine);

		engine.addSystem(new BulletSystem());
		engine.addSystem(new CollisionSystem());
		engine.addSystem(new DebugRenderingSystem(camera, levelFactory.world));
		engine.addSystem(new HookSystem());
		engine.addSystem(new JointSystem(levelFactory.world));
		engine.addSystem(new PhysicsSystem(levelFactory.world));
		engine.addSystem(new PlayerControlSystem(controller, levelFactory));
		engine.addSystem(new ServerSystem(levelFactory, camera));


		Gdx.input.setInputProcessor(controller);

		levelFactory.createFloor();
		levelFactory.createBar(0, 10);
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(0f, 0f, 0f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		engine.update(FPS);
	}
	
	@Override
	public void dispose () {
		
	}

	

	
}
