package com.example.niklasbjernekull.gametest.splashScreen;

import android.content.res.Resources;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.util.Log;
import android.view.MotionEvent;

import com.example.niklasbjernekull.gametest.GameStateView;
import com.example.niklasbjernekull.gametest.R;
import com.example.niklasbjernekull.gametest.constants.GameStates;
import com.example.niklasbjernekull.gametest.drawables.DrawableImage;
import com.example.niklasbjernekull.gametest.gameData.GameData;

/**
 * Created by Niklas.bjernekull on 2016-10-06.
 */

public class SplashScreen implements GameStateView {
    private long startTime;
    private long waitTimeMs = 3000;
    private DrawableImage logo;
    private GameData gameData;
    private float alpha = 0;
    private long fps = 0;

    public SplashScreen(Resources res, GameData in_data) {
        startTime = System.currentTimeMillis();

        logo = new DrawableImage(BitmapFactory.decodeResource(res, R.drawable.rendan));
        logo.scale(500, 500);

        gameData = in_data;
    }

    @Override
    public void update() {
        if(alpha < 255 && fps > 0) {
            alpha += 100/fps; // 1/fps = +1 (total) each second
        } else if (alpha > 255) {
            alpha = 255;
        }
    }

    @Override
    public void draw(Canvas canvas) {
        int width = canvas.getWidth();
        int height = canvas.getHeight();

        canvas.drawColor(Color.WHITE);

        logo.setPos(width/2-250, height/2-250);
        logo.draw(canvas, (int) alpha);
    }

    @Override
    public void input(MotionEvent motionEvent) {
        switch (motionEvent.getAction() & MotionEvent.ACTION_MASK) {

            // Player has touched the screen
            case MotionEvent.ACTION_DOWN:
                long currentTime = System.currentTimeMillis();
                if(currentTime - startTime > waitTimeMs) {
                    gameData.setCurrentState(GameStates.GAME);
                }
                break;
        }
    }

    @Override
    public void setFps(long in_fps) {
        fps = in_fps;
    }

    @Override
    public void pause() {
        // Nothing to pause
    }

    @Override
    public void resume() {
        // Nothing to resume
    }
}
