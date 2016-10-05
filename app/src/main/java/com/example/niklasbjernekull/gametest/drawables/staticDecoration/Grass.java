package com.example.niklasbjernekull.gametest.drawables.staticDecoration;

import android.content.res.Resources;
import android.graphics.BitmapFactory;

import com.example.niklasbjernekull.gametest.R;
import com.example.niklasbjernekull.gametest.drawables.DrawableImage;

/**
 * Created by Niklas.bjernekull on 2016-09-29.
 */

public class Grass extends DrawableImage {
    public Grass(Resources res, int in_x, int in_y) {
        super(BitmapFactory.decodeResource(res, R.drawable.grass), in_x, in_y);
    }
}
