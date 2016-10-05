package com.example.niklasbjernekull.gametest.drawables;

import android.graphics.Bitmap;
import android.graphics.Canvas;

import java.util.ArrayList;

/**
 * Created by Niklas.bjernekull on 2016-09-30.
 */

public abstract class CollectionDrawableImages {
    protected ArrayList<DrawableImage> collection = new ArrayList<DrawableImage>();

    public void drawImage(int index, int x, int y, Canvas canvas) {
        DrawableImage di = collection.get(index);

        if(di == null)
            return;

        di.setPos(x,y);
        di.draw(canvas);
    }
}
