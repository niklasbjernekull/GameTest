package com.example.niklasbjernekull.gametest.game.inGameMenus;

import android.content.res.Resources;
import android.graphics.BitmapFactory;

import com.example.niklasbjernekull.gametest.R;

/**
 * Created by Niklas.bjernekull on 2016-10-20.
 */

public class GameOverMenu extends MenuBase {

    public GameOverMenu(int width, int height, int space, Resources res) {
        super(width, height, space);
        setAbortButton(BitmapFactory.decodeResource(res, R.drawable.quit_button2),
                BitmapFactory.decodeResource(res, R.drawable.quit_button3));
        setOkButton(BitmapFactory.decodeResource(res, R.drawable.start_button2),
                BitmapFactory.decodeResource(res, R.drawable.start_button3));
    }
}
