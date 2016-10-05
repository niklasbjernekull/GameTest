package com.example.niklasbjernekull.gametest.drawables.animatedDecoration.directable;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.example.niklasbjernekull.gametest.R;
import com.example.niklasbjernekull.gametest.drawables.DirectableAnimatedDrawableImage;
import com.example.niklasbjernekull.gametest.drawables.DrawableImage;

import java.util.ArrayList;

/**
 * Created by Niklas.bjernekull on 2016-09-29.
 */

public class Ember extends DirectableAnimatedDrawableImage {
    public Ember(Resources res) {
        super();

        imagesUp = new ArrayList<DrawableImage>();
        DrawableImage diTemp = new DrawableImage(BitmapFactory.decodeResource(res, R.drawable.ash_up1));
        //diTemp.scale(100,100);
        imagesUp.add(diTemp);
        diTemp = new DrawableImage(BitmapFactory.decodeResource(res, R.drawable.ash_up2));
        imagesUp.add(diTemp);

        imagesDown = new ArrayList<DrawableImage>();
        diTemp = new DrawableImage(BitmapFactory.decodeResource(res, R.drawable.ash_down1));
        imagesDown.add(diTemp);
        diTemp = new DrawableImage(BitmapFactory.decodeResource(res, R.drawable.ash_down2));
        imagesDown.add(diTemp);

        imagesLeft = new ArrayList<DrawableImage>();
        diTemp = new DrawableImage(BitmapFactory.decodeResource(res, R.drawable.ash_left1));
        imagesLeft.add(diTemp);
        diTemp = new DrawableImage(BitmapFactory.decodeResource(res, R.drawable.ash_left2));
        imagesLeft.add(diTemp);

        imagesRight = new ArrayList<DrawableImage>();
        diTemp = new DrawableImage(BitmapFactory.decodeResource(res, R.drawable.ash_right1));
        imagesRight.add(diTemp);
        diTemp = new DrawableImage(BitmapFactory.decodeResource(res, R.drawable.ash_right2));
        imagesRight.add(diTemp);


        //imagesUp.add(BitmapFactory.decodeResource(res, R.drawable.ash_up1));
        //imagesUp.add(BitmapFactory.decodeResource(res, R.drawable.ash_up2));
        /*imagesDown = new ArrayList<Bitmap>();
        imagesDown.add(BitmapFactory.decodeResource(res, R.drawable.ash_down1));
        imagesDown.add(BitmapFactory.decodeResource(res, R.drawable.ash_down2));
        imagesLeft = new ArrayList<Bitmap>();
        imagesLeft.add(BitmapFactory.decodeResource(res, R.drawable.ash_left1));
        imagesLeft.add(BitmapFactory.decodeResource(res, R.drawable.ash_left2));
        imagesRight = new ArrayList<Bitmap>();
        imagesRight.add(BitmapFactory.decodeResource(res, R.drawable.ash_right1));
        imagesRight.add(BitmapFactory.decodeResource(res, R.drawable.ash_right2));*/
    }

    @Override
    public void stepFrame() {
        stepFrame(imagesUp.size());
    }
}
