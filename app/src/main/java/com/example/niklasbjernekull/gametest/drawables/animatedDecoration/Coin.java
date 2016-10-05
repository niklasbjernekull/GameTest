package com.example.niklasbjernekull.gametest.drawables.animatedDecoration;

import android.content.res.Resources;
import android.graphics.BitmapFactory;

import com.example.niklasbjernekull.gametest.R;
import com.example.niklasbjernekull.gametest.drawables.AnimatedDrawableImage;
import com.example.niklasbjernekull.gametest.drawables.DrawableImage;

/**
 * Created by Niklas.bjernekull on 2016-09-30.
 */

public class Coin extends AnimatedDrawableImage {

    public Coin(Resources res) {
        super();
        DrawableImage diTemp = new DrawableImage(BitmapFactory.decodeResource(res, R.drawable.coin0));
        diTemp.scale(100,100);
        images.add(diTemp);
        diTemp = new DrawableImage(BitmapFactory.decodeResource(res, R.drawable.coin1));
        diTemp.scale(100,100);
        images.add(diTemp);
        diTemp = new DrawableImage(BitmapFactory.decodeResource(res, R.drawable.coin2));
        diTemp.scale(100,100);
        images.add(diTemp);
        diTemp = new DrawableImage(BitmapFactory.decodeResource(res, R.drawable.coin3));
        diTemp.scale(100,100);
        images.add(diTemp);
        diTemp = new DrawableImage(BitmapFactory.decodeResource(res, R.drawable.coin4));
        diTemp.scale(100,100);
        images.add(diTemp);
        diTemp = new DrawableImage(BitmapFactory.decodeResource(res, R.drawable.coin5));
        diTemp.scale(100,100);
        images.add(diTemp);

        setTimeDiff(100); //200ms
    }
}
