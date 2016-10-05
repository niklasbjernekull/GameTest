package com.example.niklasbjernekull.gametest.drawables;

import android.graphics.Bitmap;
import android.graphics.Canvas;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by Niklas.bjernekull on 2016-09-28.
 */

public abstract class DirectableAnimatedDrawableImage extends AnimatedDrawableImage {
    protected ArrayList<DrawableImage> imagesUp;
    protected ArrayList<DrawableImage> imagesDown;
    protected ArrayList<DrawableImage> imagesLeft;
    protected ArrayList<DrawableImage> imagesRight;

    /*public DiractableAnimatedDrawableImage(int in_x, int in_y, ArrayList<Bitmap> up, ArrayList<Bitmap> down, ArrayList<Bitmap> left, ArrayList<Bitmap> right) {
        super(in_x, in_y);
        imagesUp = up;
        imagesDown = down;
        imagesLeft = left;
        imagesRight = right;
    }*/

    public DirectableAnimatedDrawableImage(int in_x, int in_y) {
        super(in_x, in_y);
    }

    public DirectableAnimatedDrawableImage() {
        super();
    }

    public void draw(Canvas canvas, int xDirection, int yDirection) {
        DrawableImage toDraw;
        if(xDirection > 0) {
            toDraw = imagesRight.get(currentFrame);
        } else if(xDirection < 0) {
            toDraw = imagesLeft.get(currentFrame);
        } else if(yDirection > 0) {
            toDraw = imagesDown.get(currentFrame);
        } else {
            toDraw = imagesUp.get(currentFrame);
        }

        toDraw.draw(canvas);
    }

    @Override
    public void setPos(int in_x, int in_y) {
        Iterator<DrawableImage> iterator = imagesUp.iterator();
        while(iterator.hasNext()) {
            iterator.next().setPos(in_x, in_y);
        }

        iterator = imagesDown.iterator();
        while(iterator.hasNext()) {
            iterator.next().setPos(in_x, in_y);
        }

        iterator = imagesLeft.iterator();
        while(iterator.hasNext()) {
            iterator.next().setPos(in_x, in_y);
        }

        iterator = imagesRight.iterator();
        while(iterator.hasNext()) {
            iterator.next().setPos(in_x, in_y);
        }
    }

    /*@Override
    public void stepFrame() {
        long currentTime = System.currentTimeMillis();

        if(currentTime - previousTime >= timeDiff) {
            currentFrame++;
            previousTime = currentTime;
            if (currentFrame >= 2)
                currentFrame = 0;
        }
    }*/
}
