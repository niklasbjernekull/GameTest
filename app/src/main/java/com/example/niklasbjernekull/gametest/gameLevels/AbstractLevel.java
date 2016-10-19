package com.example.niklasbjernekull.gametest.gameLevels;

/**
 * Created by Niklas.bjernekull on 2016-10-19.
 */

public class AbstractLevel{
    protected static String name;
    protected static String information;
    protected static int numberOfBoxesWidth;
    protected static int numberOfBoxesHeight;

    //background
    //theme
    //coins
    //max score
    //set max score

    public String getName() {
        return name;
    }

    public int getWidth() {
        return numberOfBoxesWidth;
    }

    public int getHeight() {
        return numberOfBoxesHeight;
    }
}
