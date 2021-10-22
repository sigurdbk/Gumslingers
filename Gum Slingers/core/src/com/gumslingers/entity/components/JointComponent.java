package com.gumslingers.entity.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.physics.box2d.Joint;

public class JointComponent implements Component {
    public static final int WELD = 1;
    public static final int ROPE = 2;
    public static final int OTHER = 0;

    public Entity jointedEntity;
    public int type;
    public Joint joint;
    public Joint joint2;
    public boolean isJointed;
}
