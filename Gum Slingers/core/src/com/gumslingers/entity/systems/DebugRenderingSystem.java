package com.gumslingers.entity.systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;

public class DebugRenderingSystem extends IteratingSystem {
    private Box2DDebugRenderer debugRenderer;
    private OrthographicCamera camera;
    private World world;

    public DebugRenderingSystem( OrthographicCamera camera, World world) {
        super(Family.all().get());

        this.camera = camera;
        this.world = world;
        this.debugRenderer = new Box2DDebugRenderer();
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);

        debugRenderer.render(world, camera.combined);
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
    
    }
}
