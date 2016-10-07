package com.example.niklasbjernekull.gametest.mainMenu;

import android.content.res.Resources;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.view.MotionEvent;

import com.example.niklasbjernekull.gametest.AbstractGameStateView;
import com.example.niklasbjernekull.gametest.GameStateView;
import com.example.niklasbjernekull.gametest.R;
import com.example.niklasbjernekull.gametest.drawables.DrawableImage;
import com.example.niklasbjernekull.gametest.gameData.GameData;

/**
 * Created by Niklas on 2016-10-07.
 */

public class MainMenu extends AbstractGameStateView implements GameStateView {
    private DrawableImage titleLogo;
    private DrawableImage startButton;

    public MainMenu(Resources res, GameData in_data, int in_width, int in_height) {
        super(in_data, in_width, in_height);
        titleLogo = new DrawableImage(BitmapFactory.decodeResource(res, R.drawable.skull));
        titleLogo.scale(width, width);
        titleLogo.setPos(0,height/10);
        startButton = new DrawableImage(BitmapFactory.decodeResource(res, R.drawable.start_button2));
        startButton.scale(width/2,width/8);
    }

    @Override
    public void update() {

    }

    @Override
    public void draw(Canvas canvas) {
        canvas.drawColor(Color.WHITE);

        titleLogo.draw(canvas);
        startButton.draw(canvas);
    }

    @Override
    public void input(MotionEvent motionEvent) {

    }

    @Override
    public void setFps(long in_fps) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }
}
