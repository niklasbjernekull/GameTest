package com.example.niklasbjernekull.gametest;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by Niklas.bjernekull on 2016-09-28.
 */
public class PathPieceHandler {

    private ArrayList<PathPiece> pathPieces = new ArrayList<PathPiece>();

    public synchronized boolean addPathPiece(int x, int y, int type) {
        if(isBoxOccupied(x, y))
            return false;

        pathPieces.add(new PathPiece(x, y, type));
        return true;
    }

    public synchronized int getType(int x, int y) {
        PathPiece temp;
        Iterator<PathPiece> pathIterator = pathPieces.iterator();
        while(pathIterator.hasNext()) {
            temp = pathIterator.next();
            if(temp == null) {
                System.out.println("!!!--- PathPiece is null ---!!!");
                return -1;
            }
            if(temp.box_x == x && temp.box_y == y) {
                return temp.getType();
            }
        }
        return -1;
    }

    public boolean isBoxOccupied(int x, int y) {
        PathPiece temp;
        Iterator<PathPiece> pathIterator = pathPieces.iterator();
        while(pathIterator.hasNext()) {
            temp = pathIterator.next();
            if(temp.box_x == x && temp.box_y == y) {
                return true;
            }
        }
        return false;
    }
}
