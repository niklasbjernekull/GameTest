package com.example.niklasbjernekull.gametest.gameData;

import com.example.niklasbjernekull.gametest.constants.GameStates;

/**
 * Created by Niklas.bjernekull on 2016-10-06.
 */

public class GameData {
    private boolean stateChanged = false;
    private int currentState = GameStates.GAME_NOT_INITIATED;

    public boolean hasStateChanged() {
        return stateChanged;
    }

    public int getState() {
        if(stateChanged)
            stateChanged = false;

        return currentState;
    }

    public void setCurrentState(int in_state) {
        currentState = in_state;
        stateChanged = true;
    }
}
