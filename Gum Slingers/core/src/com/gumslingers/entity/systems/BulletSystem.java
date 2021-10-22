package com.gumslingers.entity.systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.gumslingers.Mappers;
import com.gumslingers.entity.components.BodyComponent;
import com.gumslingers.entity.components.BulletComponent;

public class BulletSystem extends IteratingSystem {

    public BulletSystem() {
        super(Family.all(BulletComponent.class).get());
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        BodyComponent body = Mappers.body.get(entity);
        BulletComponent bullet = Mappers.bullet.get(entity);

        body.body.setLinearVelocity(bullet.xVel, bullet.yVel);
        bullet.halfLife -= deltaTime;

        if (bullet.isDead || bullet.halfLife <= 0) {
            body.isDead = true;
        }
    }
    
}
