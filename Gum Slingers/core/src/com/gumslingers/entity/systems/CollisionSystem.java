package com.gumslingers.entity.systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.gumslingers.Mappers;
import com.gumslingers.entity.components.CollisionComponent;
import com.gumslingers.entity.components.JointComponent;
import com.gumslingers.entity.components.PlayerComponent;
import com.gumslingers.entity.components.TypeComponent;

public class CollisionSystem extends IteratingSystem {
    
    public CollisionSystem() {
        super(Family.all(CollisionComponent.class).get());
        
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        CollisionComponent collision = Mappers.collision.get(entity);
        Entity collidedEntity = collision.collisionEntity;

        TypeComponent colliderType = entity.getComponent(TypeComponent.class);
        if (collidedEntity != null) {
            TypeComponent collidedType = collidedEntity.getComponent(TypeComponent.class);
            if (colliderType.type == TypeComponent.HOOK) {
                switch (collidedType.type) {
                    case TypeComponent.SCENERY:
                        System.out.println("Hook hit Scenery");
                        JointComponent joint = new JointComponent();
                        joint.jointedEntity = collidedEntity;
                        joint.type = JointComponent.WELD;
                        entity.add(joint);
                        break;
                }
            }
            else if (colliderType.type == TypeComponent.PLAYER) {
                switch (collidedType.type) {
                    case TypeComponent.SCENERY:
                        System.out.println("Player hit Scenery");
                        PlayerComponent player = entity.getComponent(PlayerComponent.class);
                        player.canJump = true;
                        break;
                }
            }
            else if (colliderType.type == TypeComponent.SCENERY) {
                switch (collidedType.type) {
                    case TypeComponent.HOOK:
                        System.out.println("Scenery hit Hook");
                        JointComponent joint = new JointComponent();
                        joint.jointedEntity = collidedEntity;
                        joint.type = JointComponent.WELD;
                        entity.add(joint);
                        break;

                    case TypeComponent.PLAYER:
                        System.out.println("Scenery hit Player");
                        PlayerComponent player = collidedEntity.getComponent(PlayerComponent.class);
                        player.canJump = true;
                        break;
                }
            }

            collision.collisionEntity = null;
        }
    }
    
}
