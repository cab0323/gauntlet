package com.example.gauntlet;

import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.service.controls.templates.ControlTemplate;
import android.util.Log;
import android.util.Pair;


//test
import android.graphics.Bitmap;
import android.content.Context;
import android.content.res.Resources;

//
import java.util.ArrayList;

class HUD {
    private int mTextFormatting;
    private int mScreenHeight;
    private int mScreenWidth;

    private Pair<ArrayList<Rect>, Joystick> controls;
    static int SHOOT = 0;
    static int PAUSE = 1;

    HUD(Point size){
        mScreenHeight = size.y;
        mScreenWidth = size.x;
        mTextFormatting = size.x / 50;
        prepareControls();
    }

    private void prepareControls(){
        int buttonWidth = mScreenWidth / 14;
        int buttonHeight = mScreenHeight / 12;
        int buttonPadding = mScreenWidth / 90;
        ArrayList<Rect> buttons = new ArrayList<Rect>();

        Rect shoot = new Rect(mScreenWidth -
                buttonPadding - buttonWidth,
                mScreenHeight - (buttonHeight * 2) -
                        (buttonPadding * 2),
                mScreenWidth - buttonPadding,
                mScreenHeight - buttonHeight -
                        (buttonPadding *2));

        Rect pause = new Rect(
                mScreenWidth - buttonPadding -
                        buttonWidth,
                buttonPadding,
                mScreenWidth - buttonPadding,
                buttonPadding + buttonHeight);

        controls = new Pair<ArrayList<Rect>, Joystick>(new ArrayList<Rect>(),
                new Joystick((float)(mScreenWidth / 8), (float)(mScreenHeight / 1.25), mScreenWidth / 15));
        controls.first.add(SHOOT, shoot);
        controls.first.add(PAUSE, pause);

    }

    void draw(Canvas c, Paint p, GameState gs, Context context){

        // Draw the black rectangle on where HUD will be
        p.setColor(Color.argb(255,0,0,0));
        c.drawRect(5, 0,mScreenWidth / 4, mTextFormatting * 2, p);

        //draw HUD
        p.setColor(Color.argb(255,255,255,255));
        p.setTextSize(mTextFormatting);
        c.drawText("Health: " + gs.getHealth(),
                mTextFormatting,mTextFormatting,p);

        c.drawText("Score: " + gs.getScore(),
                mTextFormatting * 6,mTextFormatting,p);


        if(gs.getKey()) {
            //if player has a key show it on HUD
            int keyID = context.getResources().getIdentifier("hud_key", "drawable", context.getPackageName());
            Bitmap bitKey = BitmapFactory.decodeResource(context.getResources(), keyID);
            bitKey = Bitmap.createScaledBitmap(bitKey, mTextFormatting * 2, mTextFormatting * 2, false);
            c.drawBitmap(bitKey, (mScreenWidth / 4) - 80, 0, p);
        }

        if(gs.getGameOver()){
            p.setTextSize(mTextFormatting * 5);

            c.drawText("PRESS PLAY",
                    mScreenWidth /4, mScreenHeight /2 ,p);
        }

        if(gs.getPaused() && !gs.getGameOver()){
            p.setTextSize(mTextFormatting * 5);
            c.drawText("PAUSED",
                    mScreenWidth /3, mScreenHeight /2 ,p);
        }

        drawControls(c, p);
    }

    private void drawControls(Canvas c, Paint p){
        p.setColor(Color.argb(100,255,255,255));

        for(Rect r : controls.first){
            c.drawRect(r.left, r.top, r.right, r.bottom, p);
        }

       controls.second.drawJoystick(c, p);

        // Set the colors back
        p.setColor(Color.argb(255,255,255,255));
    }

    Pair<ArrayList<Rect>, Joystick> getControls(){
        return controls;
    }


}
