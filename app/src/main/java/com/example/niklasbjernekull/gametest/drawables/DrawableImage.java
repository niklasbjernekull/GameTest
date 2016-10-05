package com.example.niklasbjernekull.gametest.drawables;

import android.graphics.Bitmap;
import android.graphics.Canvas;

/**
 * Created by Niklas.bjernekull on 2016-09-28.
 */

public class DrawableImage {
    private Bitmap image, org_image;
    public int x, y;


    public DrawableImage(Bitmap in_image, int in_x, int in_y) {
        image = in_image;
        x = in_x;
        y = in_y;
    }

    public DrawableImage(Bitmap in_image) {
        image = in_image;
        x = 0;
        y = 0;
    }

    public DrawableImage(int in_x, int in_y) {
        x = in_x;
        y = in_y;
    }

    public void draw(Canvas canvas) {
        canvas.drawBitmap(image, x, y, null);
    }

    public void setX(int in_x) {
        x = in_x;
    }
    public void setY(int in_y) {
        y = in_y;
    }

    public void setPos(int in_x, int in_y) {
        x = in_x;
        y = in_y;
    }

    public void scale(int in_scale_x, int in_scale_y) {
        if(org_image == null)
            org_image = image;

        image = Bitmap.createScaledBitmap(org_image, in_scale_x, in_scale_y, false);
    }
}
