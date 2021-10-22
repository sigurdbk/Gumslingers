package com.gumslingers.entity.systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.joints.RopeJoint;
import com.badlogic.gdx.physics.box2d.joints.RopeJointDef;
import com.badlogic.gdx.physics.box2d.joints.WeldJointDef;
import com.gumslingers.Mappers;
import com.gumslingers.entity.components.BodyComponent;
import com.gumslingers.entity.components.HookComponent;
import com.gumslingers.entity.components.JointComponent;
import com.gumslingers.entity.components.TypeComponent;

public class JointSystem extends IteratingSystem {
    private World world;

    public JointSystem(World world) {
        super(Family.all(JointComponent.class).get());

        this.world = world;
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        JointComponent joint = Mappers.joint.get(entity);
        Entity jointedEntity = joint.jointedEntity;

        if (joint.jointedEntity != null) {
            if (!joint.isJointed) {
                Body bodyA = entity.getComponent(BodyComponent.class).body;
                Body bodyB = jointedEntity.getComponent(BodyComponent.class).body;
                switch (joint.type) {
                    case JointComponent.WELD:
                        weld(bodyA, bodyB);
                        weld(bodyA, bodyB);
                        joint.isJointed = true;
                        if (entity.getComponent(TypeComponent.class).type == TypeComponent.HOOK) {
                            entity.getComponent(HookComponent.class).welded = true;
                        }
                        else if (jointedEntity.getComponent(TypeComponent.class).type == TypeComponent.HOOK) {
                            jointedEntity.getComponent(HookComponent.class).welded = true;
                        }
                        break;
                    case JointComponent.ROPE:
                        if (entity.getComponent(TypeComponent.class).type == TypeComponent.HOOK) {
                            if (entity.getComponent(HookComponent.class).welded) {
                                joint.joint = rope(bodyA, bodyB);
                                joint.joint2 = rope(bodyA, bodyB);
                                joint.isJointed = true;
                                entity.getComponent(HookComponent.class).roped = true;
                            }
                        }
                        else if (jointedEntity.getComponent(TypeComponent.class).type == TypeComponent.HOOK) {
                            if (jointedEntity.getComponent(HookComponent.class).welded) {
                                joint.joint = rope(bodyA, bodyB);
                                joint.joint2 = rope(bodyA, bodyB);
                                joint.isJointed = true;
                                jointedEntity.getComponent(HookComponent.class).roped = true;
                            }
                        }
                        break;
                }
            }
        }
    }

    private void weld(Body bodyA, Body bodyB) {
        Vector2 worldCoordsAnchorPoint = bodyA.getWorldPoint( new Vector2(0.2f, 0));

        WeldJointDef def = new WeldJointDef();
        def.bodyA = bodyA;
        def.bodyB = bodyB;
        def.localAnchorA.set(def.bodyA.getLocalPoint(worldCoordsAnchorPoint));
        def.localAnchorB.set(def.bodyB.getLocalPoint(worldCoordsAnchorPoint));

        world.createJoint(def);
    }

    private RopeJoint rope(Body bodyA, Body bodyB) {
        float maxLength = bodyA.getPosition().dst(bodyB.getPosition()) - 2f;
        System.out.println("JointSystem: " + maxLength);
        RopeJointDef def = new RopeJointDef();
        def.bodyA = bodyA;
        def.bodyB = bodyB;
        def.localAnchorA.set(0f, -0.2f);
        def.localAnchorB.set(0f, 1f);
        def.maxLength = maxLength;

        return (RopeJoint) world.createJoint(def);
    }
    
}
