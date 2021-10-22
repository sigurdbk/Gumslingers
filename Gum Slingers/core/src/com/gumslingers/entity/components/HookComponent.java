package com.gumslingers.entity.components;

import com.badlogic.ashley.core.Component;

public class HookComponent implements Component {
    public float xVel;
    public float yVel;
    public float halfLife = 1.3f;
    public boolean welded = false;
    public boolean roped = false;
    public boolean isDead = false;
}
