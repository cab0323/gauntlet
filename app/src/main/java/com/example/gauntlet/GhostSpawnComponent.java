package com.example.gauntlet;

import android.graphics.PointF;

import java.util.Random;

class GhostSpawnComponent implements SpawnComponent {
    @Override
    public void spawn(Transform playerLTransform, Transform t) {

        Random random = new Random();

        int spawnIndex = random.nextInt(Transform.getSpawnableLocations().size());

        t.setLocation((int)Transform.spawnableLocations.get(spawnIndex).x,
                (int)Transform.spawnableLocations.get(spawnIndex).y);


    }
}
