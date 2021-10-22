package com.gumslingers.entity.components;

import com.badlogic.ashley.core.Component;

public class TypeComponent implements Component {
    public static final int PLAYER = 1;
    public static final int BULLET = 2;
    public static final int HOOK = 3;
    public static final int SCENERY = 4;
    public static final int ENEMY = 5;
    public static final int OTHER = 0;
    
    public int type = OTHER;
}
