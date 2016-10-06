package com.example.niklasbjernekull.gametest.game.path;

import android.graphics.Point;

/**
 * Created by Niklas.bjernekull on 2016-09-28.
 *
 * Individual Path pieces.
 * With coordinates and type
 */
public class PathPiece {
    public int box_x;
    public int box_y;
    private int type;

    /**
     * Constructor that sets the variables
     * @param in_x
     * @param in_y
     * @param in_type
     */
    public PathPiece(int in_x, int in_y, int in_type) {
        box_x = in_x;
        box_y = in_y;
        type = in_type;
    }

    /**Get coordinates as Point*/
    public Point getBoxNumber() {
        return new Point(box_x, box_y);
    }

    /**Get type of path*/
    public int getType() {
        return type;
    }
}
