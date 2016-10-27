package com.example.niklasbjernekull.gametest.game.inGameMenus;


import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;

import com.example.niklasbjernekull.gametest.R;

/**
 * Created by Niklas on 2016-10-06.
 */

public class GameStartMenu extends MenuBase {

    public GameStartMenu(int width, int height, int space, Resources res) {
        super(width, height, space);
        setAbortButton(BitmapFactory.decodeResource(res, R.drawable.quit_button2),
                BitmapFactory.decodeResource(res, R.drawable.quit_button3));
        setOkButton(BitmapFactory.decodeResource(res, R.drawable.start_button2),
                BitmapFactory.decodeResource(res, R.drawable.start_button3));
    }

    public Bitmap drawMultilineTextToBitmap(Resources resources, int gResId, String gText) {

        // prepare canvas
        float scale = resources.getDisplayMetrics().density;
        Bitmap bitmap = BitmapFactory.decodeResource(resources, gResId);

        android.graphics.Bitmap.Config bitmapConfig = bitmap.getConfig();

        // set default bitmap config if none
        if(bitmapConfig == null) {
            bitmapConfig = android.graphics.Bitmap.Config.ARGB_8888;
        }
        // resource bitmaps are imutable,
        // so we need to convert it to mutable one
        bitmap = bitmap.copy(bitmapConfig, true);

        Canvas canvas = new Canvas(bitmap);

        // new antialiased Paint
        TextPaint paint=new TextPaint(Paint.ANTI_ALIAS_FLAG);
        // text color - #3D3D3D
        paint.setColor(Color.rgb(61, 61, 61));
        // text size in pixels
        paint.setTextSize((int) (14 * scale));
        // text shadow
        paint.setShadowLayer(1f, 0f, 1f, Color.WHITE);

        // set text width to canvas width minus 16dp padding
        int textWidth = canvas.getWidth() - (int) (16 * scale);

        // init StaticLayout for text
        StaticLayout textLayout = new StaticLayout(
                gText, paint, textWidth, Layout.Alignment.ALIGN_CENTER, 1.0f, 0.0f, false);

        // get height of multiline text
        int textHeight = textLayout.getHeight();

        // get position of text's top left corner
        float x = (bitmap.getWidth() - textWidth)/2;
        float y = (bitmap.getHeight() - textHeight)/2;

        // draw text to the Canvas center
        /*canvas.save();
        canvas.translate(x, y);
        textLayout.draw(canvas);
        canvas.restore();*/

        return bitmap;
    }
}

/* How to draw text
http://www.helloandroid.com/tutorials/how-draw-multiline-text-canvas-easily

https://www.skoumal.net/en/android-how-draw-text-bitmap/
https://www.skoumal.net/en/android-drawing-multiline-text-on-bitmap/
 */
