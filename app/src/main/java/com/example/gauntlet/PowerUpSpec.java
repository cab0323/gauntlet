package com.example.gauntlet;

import android.graphics.PointF;

public class PowerUpSpec extends ObjectSpec {
    private static final String tag = "PowerUp";
    private static final String bitmapName = "power_up";
    private static final float speed = 0f;
    private static final PointF relativeScale = new PointF(18f, 18f);

    private static final String[] components = new String[] {
            "NPCGraphicsComponent",
            "StdGraphicsComponent",
            "PowerUpSpawnComponent",
            "SimpleMovementComponent"
    };

    PowerUpSpec(){
        super(tag, bitmapName, speed, relativeScale, components);
    }
}
