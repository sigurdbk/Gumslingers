package com.gumslingers.entity.components;

import com.badlogic.ashley.core.Component;

public class BulletComponent implements Component {
    public float xVel;
    public float yVel;
    public float halfLife = 2f;
    public boolean isDead = false;
}
