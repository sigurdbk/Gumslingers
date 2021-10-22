package com.gumslingers.entity.systems;

import java.util.ArrayList;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.gumslingers.Mappers;
import com.gumslingers.entity.components.BodyComponent;
import com.gumslingers.entity.components.JointComponent;

public class PhysicsSystem extends IteratingSystem {
    private ArrayList<Entity> bodyQueue;
    private World world;

    public PhysicsSystem(World world) {
        super(Family.all(BodyComponent.class).get());

        this.world = world;
        this.bodyQueue = new ArrayList<>();
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);

        world.step(deltaTime, 6, 2);

        for (Entity entity : bodyQueue) {
            BodyComponent body = Mappers.body.get(entity);
            JointComponent joint = Mappers.joint.get(entity);
        
            if (joint != null) {
                if (joint.type == JointComponent.ROPE) {
                    Body bodyA = entity.getComponent(BodyComponent.class).body;
                    Body bodyB = joint.jointedEntity.getComponent(BodyComponent.class).body;
                    Vector2 target = bodyA.getPosition().sub(bodyB.getPosition());
                    float angle = MathUtils.atan2(-target.x, target.y);
                    bodyB.setTransform(bodyB.getPosition(), angle);
                }
            }
            if (body.isDead) {
                world.destroyBody(body.body);
                getEngine().removeEntity(entity);
            }
        }
        bodyQueue.clear();
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        bodyQueue.add(entity);
    }
    
}
