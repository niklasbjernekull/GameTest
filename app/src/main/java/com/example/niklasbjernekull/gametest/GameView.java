package com.example.niklasbjernekull.gametest;

// GameView class will go here

// Here is our implementation of GameView
// It is an inner class.
// Note how the final closing curly brace }
// is inside SimpleGameEngine

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.example.niklasbjernekull.gametest.constants.GameStates;
import com.example.niklasbjernekull.gametest.drawables.animatedDecoration.Coin;
import com.example.niklasbjernekull.gametest.drawables.animatedDecoration.directable.Ember;
import com.example.niklasbjernekull.gametest.drawables.staticDecoration.Flower;
import com.example.niklasbjernekull.gametest.drawables.staticDecoration.Grass;
import com.example.niklasbjernekull.gametest.drawables.staticDecoration.Road;
import com.example.niklasbjernekull.gametest.drawables.staticDecoration.Rock;
import com.example.niklasbjernekull.gametest.drawables.staticDecoration.Skull;
import com.example.niklasbjernekull.gametest.drawables.staticDecoration.Star;
import com.example.niklasbjernekull.gametest.drawables.staticDecoration.Tree;
import com.example.niklasbjernekull.gametest.drawables.staticDecoration.Water;
import com.example.niklasbjernekull.gametest.drawables.staticDecoration.collections.RockPaths;
import com.example.niklasbjernekull.gametest.game.Game;
import com.example.niklasbjernekull.gametest.game.path.PathPieceHandler;
import com.example.niklasbjernekull.gametest.gameData.GameData;
import com.example.niklasbjernekull.gametest.splashScreen.SplashScreen;

import static com.example.niklasbjernekull.gametest.constants.GameStates.GAME_SPLASH_SCREEN;

// Notice we implement runnable so we have
// A thread and can override the run method.
class GameView extends SurfaceView implements Runnable {

    // This is our thread
    Thread gameThread = null;

    // This is new. We need a SurfaceHolder
    // When we use Paint and Canvas in a thread
    // We will see it in action in the draw method soon.
    private SurfaceHolder ourHolder;

    // A boolean which we will set and unset
    // when the game is running- or not.
    volatile boolean playing;

    // A Canvas and a Paint object
    private Canvas canvas;

    // This variable tracks the game frame rate
    long fps;

    // This is used to help calculate the fps
    private long timeThisFrame;

    private boolean firstTime = true;

    private GameStateView gameState;
    private SplashScreen splashScreen;
    private Game game;

    private GameData gameData;


    // When the we initialize (call new()) on gameView
    // This special constructor method runs
    public GameView(Context context) {
        // The next line of code asks the
        // SurfaceView class to set up our object.
        // How kind.
        super(context);

        // Initialize ourHolder and paint objects
        ourHolder = getHolder();

        gameData = new GameData();

        splashScreen = new SplashScreen(this.getResources(), gameData);
        game = new Game(this.getResources());

        gameState = splashScreen;
    }

    @Override
    public void run() {
        while (playing) {

            // Capture the current time in milliseconds in startFrameTime
            long startFrameTime = System.currentTimeMillis();

            // Draw the frame
            draw();

            // Update the frame
            update();

            // Calculate the fps this frame
            // We can then use the result to
            // time animations and more.
            timeThisFrame = System.currentTimeMillis() - startFrameTime;
            if (timeThisFrame > 0) {
                fps = 1000 / timeThisFrame;
                game.setFps(fps);
            }

        }

    }

    // Everything that needs to be updated goes in here
    // In later projects we will have dozens (arrays) of objects.
    // We will also do other things like collision detection.
    public void update() {
        if(gameData.hasStateChanged())
            changeGameState();

        gameState.update();
    }

    private void changeGameState() {
        switch (gameData.getState()) {

            case GameStates.GAME_SPLASH_SCREEN:
                gameState = splashScreen;
                break;

            case GameStates.GAME:
                gameState = game;
                break;

        }
    }


    // Draw the newly updated scene
    public void draw() {

        // Make sure our drawing surface is valid or we crash
        if (ourHolder.getSurface().isValid()) {

            // Lock the canvas ready to draw
            canvas = ourHolder.lockCanvas();

            gameState.draw(canvas);

            // Draw everything to the screen
            ourHolder.unlockCanvasAndPost(canvas);
        }

    }


    // If SimpleGameEngine Activity is paused/stopped
    // shutdown our thread.
    public void pause() {
        playing = false;
        game.pause();
        try {
            gameThread.join();
        } catch (InterruptedException e) {
            Log.e("Error:", "joining thread");
        }

    }

    // If SimpleGameEngine Activity is started then
    // start our thread.
    public void resume() {
        playing = true;
        game.resume();
        gameThread = new Thread(this);
        gameThread.start();
    }

    // The SurfaceView class implements onTouchListener
    // So we can override this method and detect screen touches.
    @Override
    public boolean onTouchEvent(MotionEvent motionEvent) {

        gameState.input(motionEvent);
        return true;
    }
}
