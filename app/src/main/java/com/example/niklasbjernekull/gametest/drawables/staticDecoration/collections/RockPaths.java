package com.example.niklasbjernekull.gametest.drawables.staticDecoration.collections;

import android.content.res.Resources;
import android.graphics.BitmapFactory;

import com.example.niklasbjernekull.gametest.R;
import com.example.niklasbjernekull.gametest.drawables.CollectionDrawableImages;
import com.example.niklasbjernekull.gametest.drawables.DrawableImage;

/**
 * Created by Niklas.bjernekull on 2016-09-30.
 */

public class RockPaths extends CollectionDrawableImages {

    public RockPaths(Resources res) {
        DrawableImage diTemp = new DrawableImage(BitmapFactory.decodeResource(res, R.drawable.rock0));
        diTemp.scale(180,180);
        collection.add(diTemp);
        diTemp = new DrawableImage(BitmapFactory.decodeResource(res, R.drawable.rock1));
        diTemp.scale(180,180);
        collection.add(diTemp);
        diTemp = new DrawableImage(BitmapFactory.decodeResource(res, R.drawable.rock2));
        diTemp.scale(180,180);
        collection.add(diTemp);
        diTemp = new DrawableImage(BitmapFactory.decodeResource(res, R.drawable.rock3));
        diTemp.scale(180,180);
        collection.add(diTemp);
        diTemp = new DrawableImage(BitmapFactory.decodeResource(res, R.drawable.rock4));
        diTemp.scale(180,180);
        collection.add(diTemp);
        diTemp = new DrawableImage(BitmapFactory.decodeResource(res, R.drawable.rock5));
        diTemp.scale(180,180);
        collection.add(diTemp);
    }
}
