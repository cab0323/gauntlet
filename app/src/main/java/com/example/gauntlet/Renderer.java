package com.example.gauntlet;

import android.content.ContentProvider;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import com.example.gauntlet.PlayerAnimationComponent;

import java.util.ArrayList;

class Renderer {
    private Canvas mCanvas;
    private SurfaceHolder mSurfaceHolder;
    private Paint mPaint;
    private Context c;

    Renderer(SurfaceView sh, Context context){
        mSurfaceHolder = sh.getHolder();
        mPaint = new Paint();
        c = context;
    }

    void draw(ArrayList<GameObject> objects, GameState gs,
              HUD hud) {

        if (mSurfaceHolder.getSurface().isValid()) {
            mCanvas = mSurfaceHolder.lockCanvas();
            mCanvas.drawColor(Color.argb(255, 0, 0, 0));

            if (gs.getDrawing()) {
                // Draw all the game objects here
                for (GameObject object : objects) {
                    if(object.checkActive()) {
                        object.draw(mCanvas, mPaint);
                    }
                }

            }

            if(gs.getGameOver()) {
                // Draw just a background graphic here
                objects.get(Level.BACKGROUND_INDEX)
                        .draw(mCanvas, mPaint);

            }

            // Now we draw the HUD on top of everything else
            hud.draw(mCanvas, mPaint, gs, c);




            mSurfaceHolder.unlockCanvasAndPost(mCanvas);
        }
    }

}
