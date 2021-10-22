package com.gumslingers;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;

public class BodyFactory {
    private World world;
    private static BodyFactory thisInstance;

    /**
     * Returns the current instance of BodyFactory to assure only one instance can exist at a time.
     * If no instance currently exist, it creates one and return that.
     * @param world - the world to create bodies in
     * @return the current instance of BodyFactory
     */
    public static BodyFactory getInstance(World world) {
        if (thisInstance == null) {
            thisInstance = new BodyFactory(world);
        }
        return thisInstance;
    }

    /**
     * Private constructor for BodyFactory
     * @param world - the world to create bodies in
     */
    private BodyFactory(World world) {
        this.world = world;
    }

    /**
     * Creates a new player body and returns it
     * @return a new player body
     */
    public Body createPlayerBody() {
		BodyDef def = new BodyDef();
		def.type = BodyType.DynamicBody;
        def.position.set(0, 0);

		Body player = world.createBody(def);

		PolygonShape shape = new PolygonShape();
		shape.setAsBox(1, 1);

		FixtureDef fdef = new FixtureDef();
		fdef.shape = shape;
		fdef.density = 1f;

		player.createFixture(fdef);
		shape.dispose();

		return player;

    }

    public Body createEnemyBody(float posX, float posY) {
        BodyDef def = new BodyDef();
		def.type = BodyType.KinematicBody;
        def.position.set(posX, posY);

		Body enemy = world.createBody(def);

		PolygonShape shape = new PolygonShape();
		shape.setAsBox(1, 1);

		FixtureDef fdef = new FixtureDef();
		fdef.shape = shape;
		fdef.density = 1f;

		enemy.createFixture(fdef);
		shape.dispose();

		return enemy;
    }
    
    /**
     * Creates a new bar body with a given position, and returns it
     * @param posX - the x value of the position
     * @param posY - the y value of the position
     * @return a new bar body
     */
    public Body createBarBody(float length, float height, float posX, float posY) {
		BodyDef def = new BodyDef();
		def.type = BodyType.StaticBody;
		def.position.set(posX, posY);

		Body bar = world.createBody(def);

		PolygonShape shape = new PolygonShape();
		shape.setAsBox(length, height);

		FixtureDef fdef = new FixtureDef();
		fdef.shape = shape;
		fdef.density = 0f;
		fdef.friction = 2.0f;

		bar.createFixture(fdef);
		shape.dispose();

		return bar;
    }

    /**
     * Creates a new bullet body with a given position, and returns it
     * @param posX - the x value of the position
     * @param posY - the y value of the position
     * @return a new bullet body
     */
    public Body createBulletBody(float posX, float posY) {
        BodyDef def = new BodyDef();
        def.type = BodyType.DynamicBody;
        def.position.set(posX, posY);

        CircleShape shape = new CircleShape();
        shape.setRadius(0.2f);

        Body bullet = world.createBody(def);
        bullet.createFixture(shape, 0f);
        bullet.setBullet(true);

        shape.dispose();

        return bullet;
    }
    
    /**
     * Creates a new hook body with a given position, and returns it
     * @param posX - the x value of the position
     * @param posY - the y value of the position
     * @return a new hook body
     */
    public Body createHookBody(float posX, float posY) {
        BodyDef def = new BodyDef();
        def.type = BodyType.DynamicBody;
        def.position.set(posX, posY);

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(0.2f, 0.2f);

        Body hook = world.createBody(def);
        hook.createFixture(shape, 0f);
        hook.setBullet(true);

        shape.dispose();

        return hook;
    }
}
