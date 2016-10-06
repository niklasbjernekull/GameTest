package com.example.niklasbjernekull.gametest;

import android.graphics.Canvas;
import android.view.MotionEvent;

/**
 * Created by Niklas.bjernekull on 2016-10-06.
 */

public interface GameStateView {

    public void update();

    public void draw(Canvas canvas);

    public void input(MotionEvent motionEvent);

    public void setFps(long in_fps);

    public void pause();

    public void resume();
}
