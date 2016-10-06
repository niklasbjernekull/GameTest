package com.example.niklasbjernekull.gametest.game.inGameMenus;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

/**
 * Created by Niklas on 2016-10-06.
 */

public abstract class MenuBase {
    private int x, y;
    private int size_x, size_y;
    private int corner = 100;
    Paint roundPaint;

    public MenuBase(int in_x, int in_y, int in_size_x, int in_size_y) {
        x = in_x;
        y = in_y;
        size_x = in_size_x;
        size_y = in_size_y;

        roundPaint = new Paint();
        roundPaint.setColor(Color.argb(220,  255, 255, 255));
    }

    public MenuBase(int width, int height, int space) {
        this(space, space, width - space*2, height - space*2);
        /*x = space;
        y = space;
        size_x = width - space*2;
        size_y = height - space*2;*/
    }

    public void setPos(int in_x, int in_y) {
        x = in_x;
        y = in_y;
    }

    public void setSize(int in_size_x, int in_size_y) {
        size_x = in_size_x;
        size_y = in_size_y;
    }

    public void draw(Canvas canvas) {
        //Paint transparent rectangle with rounded corners
        canvas.drawRoundRect(x, y, size_x, size_y, corner, corner, roundPaint);

    }

}
