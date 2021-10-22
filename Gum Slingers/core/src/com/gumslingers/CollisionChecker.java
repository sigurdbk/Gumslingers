package com.gumslingers;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.gumslingers.entity.components.CollisionComponent;

public class CollisionChecker implements ContactListener {

    @Override
    public void beginContact(Contact contact) {
        Fixture fa = contact.getFixtureA();
        Fixture fb = contact.getFixtureB();

        if (fa.getBody().getUserData() instanceof Entity) {
            Entity entity = (Entity) fa.getBody().getUserData();
            updateCollision(entity, fb);
        }
        else if (fb.getBody().getUserData() instanceof Entity) {
            Entity entity = (Entity) fb.getBody().getUserData();
            updateCollision(entity, fa);
        }
    }

    @Override
    public void endContact(Contact contact) {
      
    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {
      
    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {
        
    }

    private void updateCollision(Entity entity, Fixture fb) {
        if (fb.getBody().getUserData() instanceof Entity) {
            Entity collided = (Entity) fb.getBody().getUserData();

            CollisionComponent colA = entity.getComponent(CollisionComponent.class);
            CollisionComponent colB = collided.getComponent(CollisionComponent.class);

            if (colA != null) {
                colA.collisionEntity = collided;
            }
            else if (colB != null) {
                colB.collisionEntity = entity;
            }
        }
    }
    
}
