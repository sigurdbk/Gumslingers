package com.gumslingers.entity.systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.joints.RopeJoint;
import com.gumslingers.Controller;
import com.gumslingers.LevelFactory;
import com.gumslingers.Mappers;
import com.gumslingers.entity.components.BodyComponent;
import com.gumslingers.entity.components.HookComponent;
import com.gumslingers.entity.components.JointComponent;
import com.gumslingers.entity.components.PlayerComponent;

import org.json.JSONException;
import org.json.JSONObject;

public class PlayerControlSystem extends IteratingSystem {
    private Controller controller;
    private LevelFactory levelFactory;

    public PlayerControlSystem(Controller controller, LevelFactory levelFactory) {
        super(Family.all(PlayerComponent.class).get());
        
        this.controller = controller;
        this.levelFactory = levelFactory;
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);

    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        BodyComponent body = Mappers.body.get(entity);
        PlayerComponent player = Mappers.player.get(entity);
        Entity hook = player.hook;

        if (playerMovement(body, player)) {
            JSONObject data = new JSONObject();
			try {
                data.put("x", body.body.getPosition().x);
                data.put("y", body.body.getPosition().y);
                getEngine().getSystem(ServerSystem.class).updates.add(data);
			} catch (JSONException e) {
				Gdx.app.log("PlayerControlSystem", "Error creating data for player movement update");
			}
        }

        if (controller.shoot) {
            if (player.shotDelay <= 0) {
                Vector3 mousePos = new Vector3(controller.xPosBullet, controller.yPosBullet, 0);
                player.camera.unproject(mousePos);

                float speed = 3f;
                float playerX = body.body.getPosition().x;
                float playerY = body.body.getPosition().y;
                float xVel = mousePos.x - playerX;
                float yVel = mousePos.y - playerY;

                levelFactory.createBullet(playerX, playerY + 1.3f, xVel*speed, yVel*speed);
                player.shotDelay = 1f;
            }
        }

        if (controller.hook) {
            player.ropeTightened = false;
            player.hookHeld+= deltaTime;
            if (player.hookDelay <= 0 && player.hookHeld <= 0.3f) {
                if (hook != null) {
                    hook.getComponent(HookComponent.class).isDead = true;
                }
                Vector3 mousePos = new Vector3(controller.xPosHook, controller.yPosHook, 0);
                player.camera.unproject(mousePos);
            
                float playerX = body.body.getPosition().x;
                float playerY = body.body.getPosition().y;
                float xVel = mousePos.x - playerX;
                float yVel = mousePos.y - playerY;

                levelFactory.createHook(playerX, playerY + 1.3f, xVel, yVel, entity);
                player.hookDelay = 1f;
                
            }
            else {
                if (hook != null) {
                    if (hook.getComponent(HookComponent.class).roped) {
                        RopeJoint rope1 = (RopeJoint) hook.getComponent(JointComponent.class).joint;
                        RopeJoint rope2 = (RopeJoint) hook.getComponent(JointComponent.class).joint2;
                        rope1.setMaxLength(rope1.getMaxLength() - 5);
                        rope2.setMaxLength(rope2.getMaxLength() - 5);
                    }
                }
            }
        }
        else {
            player.hookHeld = 0;
            if (!player.ropeTightened) {
                if (hook != null) {
                    if (hook.getComponent(HookComponent.class).roped) {
                        float maxLength = body.body.getPosition().dst(hook.getComponent(BodyComponent.class).body.getPosition()) - 1.2f;
                        System.out.println("PlayerControlSystem: " + maxLength);
                        RopeJoint rope1 = (RopeJoint) hook.getComponent(JointComponent.class).joint;
                        RopeJoint rope2 = (RopeJoint) hook.getComponent(JointComponent.class).joint2;
                        rope1.setMaxLength(maxLength);
                        rope2.setMaxLength(maxLength);
                        player.ropeTightened = true;
                    }
                }
            }
        }
        player.hookDelay -= deltaTime;
        player.shotDelay -= deltaTime;
    }

    private boolean playerMovement(BodyComponent body, PlayerComponent player) {
        boolean hasMoved = false;
        if (controller.left) {
            body.body.setLinearVelocity(body.body.getLinearVelocity().x - 0.2f, body.body.getLinearVelocity().y);
            hasMoved = true;
        }
        if (controller.right) {
            body.body.setLinearVelocity(body.body.getLinearVelocity().x + 0.2f, body.body.getLinearVelocity().y);
            hasMoved = true;
        }
        if (controller.jump && player.canJump) {
            body.body.applyLinearImpulse(new Vector2(0, body.body.getMass()*10), body.body.getWorldCenter(), true);
            player.canJump = false;
            hasMoved = true;
        }

        return hasMoved;
    }
}
