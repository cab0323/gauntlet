package com.example.gauntlet;

import android.content.Context;
import android.content.SharedPreferences;

final class GameState {
    private static volatile boolean mThreadRunning = false;
    private static volatile boolean mPaused = true;
    private static volatile boolean mGameOver = true;
    private static volatile boolean mDrawing = false;

    //if player has key, key will show on HUD
    private static volatile boolean mHasKey = false;

    // This object will have access to the deSpawnReSpawn method
    // in GameEngine- once it is initialized
    private GameStarter gameStarter;


    private int mScore;
    private int mHighScore;
    private int mHealth;

    // This is how we will make all the high scores persist
    private SharedPreferences.Editor mEditor;

    GameState(GameStarter gs, Context context){
        // This initializes the gameStarter reference
        gameStarter = gs;
        // Get the current high score
        SharedPreferences prefs;
        prefs = context.getSharedPreferences("HiScore",
                Context.MODE_PRIVATE);

        // Initialize the mEditor ready
        mEditor = prefs.edit();

        // Load high score from a entry in the file
        // labeled "hiscore"
        // if not available highscore set to zero 0
        mHighScore = prefs.getInt("hi_score", 0);
    }

    private void endGame(){
        mGameOver = true;
        mPaused = true;
        if(mScore > mHighScore){
            mHighScore = mScore;
            // Save high score
            mEditor.putInt("hi_score", mHighScore);
            mEditor.commit();
        }
    }

    void startNewGame(){
        mScore = 0;
        mHealth = 3;
        // Don't want to be drawing objects
        // while deSpawnReSpawn is
        // clearing them and spawning them again
        stopDrawing();
        gameStarter.deSpawnReSpawn();
        resume();

        // Now we can draw again
        startDrawing();
    }


    void loseLife(SoundEngine se){
//         mHealth--;
//        se.playPlayerExplode();
        if(mHealth == 0){
            pause();
            endGame();
        }
    }

    int getHealth(){
        return mHealth;
    }

    void increaseScore(){
        mScore++;
    }

    int getScore(){
        return mScore;
    }

    int getHighScore(){
        return mHighScore;
    }

    void pause(){
        mPaused = true;
    }

    void resume(){
        mGameOver = false;
        mPaused = false;
    }

    void stopEverything(){
        mPaused = true;
        mGameOver = true;
        mThreadRunning = false;
    }

    boolean getThreadRunning(){
        return mThreadRunning;
    }

    void startThread(){
        mThreadRunning = true;
    }

    private void stopDrawing(){
        mDrawing = false;
    }

    private void startDrawing(){
        mDrawing = true;
    }

    boolean getDrawing() {
        return mDrawing;
    }

    boolean getPaused(){
        return mPaused;
    }

    boolean getGameOver(){
        return mGameOver;
    }

    //when player has key set to true
    void setKeyStatus(boolean keyStatus){
        mHasKey = keyStatus;
    }

    boolean getKey(){
        return mHasKey;
    }
    //=======

}