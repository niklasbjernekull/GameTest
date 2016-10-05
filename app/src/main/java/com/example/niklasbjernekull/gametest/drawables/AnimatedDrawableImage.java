package com.example.niklasbjernekull.gametest.drawables;

import android.graphics.Bitmap;
import android.graphics.Canvas;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by Niklas.bjernekull on 2016-09-28.
 */

public abstract class AnimatedDrawableImage extends DrawableImage {
    //protected int numberOfFrames;
    protected ArrayList<DrawableImage> images;
    protected long previousTime, timeDiff = 500; //500ms

    protected int currentFrame;

    /*public AnimatedDrawableImage(int in_x, int in_y, int noFrames, ArrayList<Bitmap> in_images) {
        super(in_x, in_y);
        //numberOfFrames = noFrames;
        images = in_images;

        currentFrame = 0;

        previousTime = System.currentTimeMillis();
    }*/

    public AnimatedDrawableImage(int in_x, int in_y) {
        super(in_x, in_y);
        currentFrame = 0;
        images = new ArrayList<DrawableImage>();
    }

    public AnimatedDrawableImage() {
        this(0, 0);
    }

    public void stepFrame() {
        if(images == null)
            return;
        stepFrame(images.size());
    }

    public void stepFrame(int maxFrames) {
        long currentTime = System.currentTimeMillis();

        if(currentTime - previousTime >= timeDiff) {
            previousTime = currentTime;
            currentFrame++;
            if (currentFrame >= maxFrames)
                currentFrame = 0;
        }
    }

    public void setTimeDiff(long diff) {
        timeDiff = diff;
    }

    @Override
    public void setPos(int in_x, int in_y) {
        Iterator<DrawableImage> iterator = images.iterator();
        while(iterator.hasNext()) {
            iterator.next().setPos(in_x, in_y);
        }
    }

    @Override
    public void draw(Canvas canvas) {
        images.get(currentFrame).draw(canvas);
    }
}
