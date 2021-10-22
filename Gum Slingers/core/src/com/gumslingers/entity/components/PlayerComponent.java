package com.gumslingers.entity.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.graphics.OrthographicCamera;

public class PlayerComponent implements Component {
    public float shotDelay = 0f;
    public float hookDelay = 0f;
    public float hookHeld = 0f;
    public boolean canJump = true;
    public boolean ropeTightened;
    public boolean hasMoved;
    public OrthographicCamera camera;
    public Entity hook;
}
