package com.example.niklasbjernekull.gametest;

import android.graphics.Point;

/**
 * Created by Niklas.bjernekull on 2016-09-28.
 */
public class PathPiece {
    public int box_x;
    public int box_y;
    private int type;

    public PathPiece(int in_x, int in_y, int in_type) {
        box_x = in_x;
        box_y = in_y;
        type = in_type;
    }

    public Point getBoxNumber() {
        return new Point(box_x, box_y);
    }

    public int getType() {
        return type;
    }
}
