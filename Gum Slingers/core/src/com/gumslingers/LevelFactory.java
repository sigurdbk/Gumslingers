package com.gumslingers;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.gumslingers.entity.components.BodyComponent;
import com.gumslingers.entity.components.BulletComponent;
import com.gumslingers.entity.components.HookComponent;
import com.gumslingers.entity.components.CollisionComponent;
import com.gumslingers.entity.components.JointComponent;
import com.gumslingers.entity.components.PlayerComponent;
import com.gumslingers.entity.components.TypeComponent;

public class LevelFactory {
    private BodyFactory bodyFactory;
    private Engine engine;
    protected World world;

    /**
     * Constructor for LevelFactory
     * @param engine - the engine to add new enteties to
     */
    public LevelFactory(Engine engine) {
        this.engine = engine;
        this.world = new World(new Vector2(0,-10), true);
        this.bodyFactory = BodyFactory.getInstance(world);

        world.setContactListener(new CollisionChecker());
    }

    /**
     * Creates a new player
     * @param camera - the camera to center on this player
     */
    public Entity createPlayer(OrthographicCamera camera) {
		Entity entity = new Entity();

		BodyComponent body = new BodyComponent();
        body.body = bodyFactory.createPlayerBody();
        body.body.setUserData(entity);

        CollisionComponent collision = new CollisionComponent();

		PlayerComponent player = new PlayerComponent();
        player.camera = camera;

        TypeComponent type = new TypeComponent();
        type.type = TypeComponent.PLAYER;

        entity.add(body);
        entity.add(collision);
        entity.add(player);
        entity.add(type);

        engine.addEntity(entity);
        
        return entity;
    }

    public Entity createEnemy(float posX, float posY) {
        Entity entity = new Entity();

        BodyComponent body = new BodyComponent();
        body.body = bodyFactory.createEnemyBody(posX, posY);
        body.body.setUserData(entity);

        CollisionComponent collision = new CollisionComponent();

        TypeComponent type = new TypeComponent();
        type.type = TypeComponent.ENEMY;

        entity.add(body);
        entity.add(collision);
        entity.add(type);

        engine.addEntity(entity);

        return entity;
    }
    
    /**
     * Creates a new bar at a given position
     * @param x - the x value of the position
     * @param y - the y value of the position
     */
    public void createBar(float x, float y) {
		Entity entity = new Entity();

		BodyComponent body = new BodyComponent();
        body.body = bodyFactory.createBarBody(50, 1, x, y);
        body.body.setUserData(entity);

        CollisionComponent collision = new CollisionComponent();

        TypeComponent type = new TypeComponent();
        type.type = TypeComponent.SCENERY;

        entity.add(body);
        entity.add(collision);
        entity.add(type);

		engine.addEntity(entity);
    }

    /**
     * Creates a new floor
     */
    public void createFloor() {
        Entity entity = new Entity();

        BodyComponent body = new BodyComponent();
        body.body = bodyFactory.createBarBody(1000, 1, 0, -35);
        body.body.setUserData(entity);

        CollisionComponent collision = new CollisionComponent();

        TypeComponent type = new TypeComponent();
        type.type = TypeComponent.SCENERY;

        entity.add(body);
        entity.add(collision);
        entity.add(type);

        engine.addEntity(entity);
    }

    /**
     * Creates a new bullet at a given position with a given velocity
     * @param xPos - the x value of the position
     * @param yPos - the y value of the position
     * @param xVel - the x value of the velocity
     * @param yVel - the y value of the velocity
     */
    public void createBullet(float xPos, float yPos, float xVel, float yVel) {
        Entity entity = new Entity();

        BodyComponent body = new BodyComponent();
        body.body = bodyFactory.createBulletBody(xPos, yPos);

        BulletComponent bullet = new BulletComponent();
        bullet.xVel = xVel;
        bullet.yVel = yVel;

        CollisionComponent collision = new CollisionComponent();

        TypeComponent type = new TypeComponent();
        type.type = TypeComponent.BULLET;

        entity.add(body);
        entity.add(bullet);
        entity.add(collision);
        entity.add(type);

        engine.addEntity(entity);

    }
    
    /**
     * Creates a new hook at a given position with a given velocity. It then creates two ropes tied to a given player
     * @param xPos - the x value of the position
     * @param yPos - the y value of the position
     * @param xVel - the x value of the velocity
     * @param yVel - the y vlaue of the velocity
     * @param player - the pleyer to tie the hook with
     */
    public void createHook(float xPos, float yPos, float xVel, float yVel, Entity player) {
        Entity entity = new Entity();

        BodyComponent body = new BodyComponent();
        body.body = bodyFactory.createHookBody(xPos, yPos);
        body.body.setUserData(entity);

        CollisionComponent collision = new CollisionComponent();

        HookComponent hook = new HookComponent();
        hook.xVel = xVel;
        hook.yVel = yVel;

        JointComponent joint = new JointComponent();
        joint.jointedEntity = player;
        joint.type = JointComponent.ROPE;

        TypeComponent type = new TypeComponent();
        type.type = TypeComponent.HOOK;

        player.getComponent(PlayerComponent.class).hook = entity;

        entity.add(body);
        entity.add(hook);
        entity.add(collision);
        entity.add(joint);
        entity.add(type);

        engine.addEntity(entity);
    }
}
