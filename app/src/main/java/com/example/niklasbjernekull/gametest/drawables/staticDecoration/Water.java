package com.example.niklasbjernekull.gametest.drawables.staticDecoration;

import android.content.res.Resources;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;

import com.example.niklasbjernekull.gametest.R;
import com.example.niklasbjernekull.gametest.drawables.DrawableImage;

/**
 * Created by Niklas.bjernekull on 2016-10-03.
 */

public class Water {
    private DrawableImage water0;
    private DrawableImage water1;
    int x, y;

    public Water(Resources res) {
        water0 = new DrawableImage(BitmapFactory.decodeResource(res, R.drawable.water0));
        water1 = new DrawableImage(BitmapFactory.decodeResource(res, R.drawable.water1));
    }

    public void scale(int scale_x, int scale_y) {
        water0.scale(scale_x, scale_y);
        water1.scale(scale_x, scale_y);
    }

    public void setPos(int in_x, int in_y) {
        x = in_x;
        y = in_y;
    }

    public void draw(Canvas canvas, int type) {
        switch(type) {
            case 0:
                water0.setPos(x, y);
                water0.draw(canvas);
                break;
            case 1:
                water1.setPos(x, y);
                water1.draw(canvas);
                break;
            default:
                water0.setPos(x, y);
                water0.draw(canvas);
                break;
        }
    }
}
