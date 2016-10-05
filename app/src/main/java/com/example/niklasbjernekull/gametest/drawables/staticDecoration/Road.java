package com.example.niklasbjernekull.gametest.drawables.staticDecoration;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;

import com.example.niklasbjernekull.gametest.R;
import com.example.niklasbjernekull.gametest.drawables.DrawableImage;

/**
 * Created by Niklas.bjernekull on 2016-10-03.
 */

public class Road {
    private DrawableImage path;
    private DrawableImage corner_left;
    private DrawableImage corner_right;
    int x, y;

    public Road(Resources res) {
        path = new DrawableImage(BitmapFactory.decodeResource(res, R.drawable.path));
        path.scale(100, 100);
        corner_left = new DrawableImage(BitmapFactory.decodeResource(res, R.drawable.path_alt_left));
        corner_left.scale(100,100);
        corner_right = new DrawableImage(BitmapFactory.decodeResource(res, R.drawable.path_alt_right));
        corner_right.scale(100,100);
    }

    public void setPos(int in_x, int in_y) {
        x = in_x;
        y = in_y;
    }

    public void draw(Canvas canvas, int type) {
        switch(type) {
            case 0:
                path.setPos(x, y);
                path.draw(canvas);
                break;
            case 1:
                corner_left.setPos(x, y);
                corner_left.draw(canvas);
                break;
            case 2:
                corner_right.setPos(x, y);
                corner_right.draw(canvas);
                break;
            default:
                path.setPos(x, y);
                path.draw(canvas);
                break;
        }
    }
}
