package com.gumslingers;

import com.badlogic.ashley.core.ComponentMapper;
import com.gumslingers.entity.components.BodyComponent;
import com.gumslingers.entity.components.BulletComponent;
import com.gumslingers.entity.components.CollisionComponent;
import com.gumslingers.entity.components.HookComponent;
import com.gumslingers.entity.components.JointComponent;
import com.gumslingers.entity.components.PlayerComponent;
import com.gumslingers.entity.components.TransformComponent;
import com.gumslingers.entity.components.TypeComponent;

public class Mappers {
    public static final ComponentMapper<BodyComponent> body = ComponentMapper.getFor(BodyComponent.class);
    public static final ComponentMapper<BulletComponent> bullet = ComponentMapper.getFor(BulletComponent.class);
    public static final ComponentMapper<CollisionComponent> collision = ComponentMapper.getFor(CollisionComponent.class);
    public static final ComponentMapper<HookComponent> hook = ComponentMapper.getFor(HookComponent.class);
    public static final ComponentMapper<JointComponent> joint = ComponentMapper.getFor(JointComponent.class);
    public static final ComponentMapper<PlayerComponent> player = ComponentMapper.getFor(PlayerComponent.class);
    public static final ComponentMapper<TransformComponent> transform = ComponentMapper.getFor(TransformComponent.class);
    public static final ComponentMapper<TypeComponent> type = ComponentMapper.getFor(TypeComponent.class);
}
