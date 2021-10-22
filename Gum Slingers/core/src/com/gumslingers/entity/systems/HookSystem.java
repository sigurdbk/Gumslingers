package com.gumslingers.entity.systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.gumslingers.Mappers;
import com.gumslingers.entity.components.BodyComponent;
import com.gumslingers.entity.components.HookComponent;

public class HookSystem extends IteratingSystem {

    public HookSystem() {
        super(Family.all(HookComponent.class).get());
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        BodyComponent body = Mappers.body.get(entity);
        HookComponent hook = Mappers.hook.get(entity);

        body.body.setLinearVelocity(hook.xVel, hook.yVel);
        hook.halfLife -= deltaTime;
        if (hook.isDead || (hook.halfLife <= 0 && !hook.welded)) {
            body.isDead = true;
        }

    }
    
}
