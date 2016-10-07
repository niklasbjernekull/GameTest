package com.example.niklasbjernekull.gametest;

import android.content.res.Resources;

import com.example.niklasbjernekull.gametest.gameData.GameData;

/**
 * Created by Niklas on 2016-10-07.
 */

public abstract class AbstractGameStateView {
    protected GameData gameData;
    protected int width;
    protected int height;

    public AbstractGameStateView (GameData in_data, int in_width, int in_height) {
        gameData = in_data;
        width = in_width;
        height =in_height;
    }
}
