package com.example.niklasbjernekull.gametest.game;

import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.util.Log;
import android.view.MotionEvent;

import com.example.niklasbjernekull.gametest.AbstractGameStateView;
import com.example.niklasbjernekull.gametest.GameStateView;
import com.example.niklasbjernekull.gametest.constants.GameStates;
import com.example.niklasbjernekull.gametest.constants.InGameMenuConstants;
import com.example.niklasbjernekull.gametest.constants.InGameStates;
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
import com.example.niklasbjernekull.gametest.game.inGameMenus.GameStartMenu;
import com.example.niklasbjernekull.gametest.game.path.PathPieceHandler;
import com.example.niklasbjernekull.gametest.gameData.GameData;

/**
 * Created by Niklas.bjernekull on 2016-10-06.
 */

public class Game extends AbstractGameStateView implements GameStateView {
    // This is our thread
    //Thread gameThread = null;

    // This is new. We need a SurfaceHolder
    // When we use Paint and Canvas in a thread
    // We will see it in action in the draw method soon.
    //SurfaceHolder ourHolder;

    // A boolean which we will set and unset
    // when the game is running- or not.
    volatile boolean playing;

    // A Canvas and a Paint object
    //Canvas canvas;
    //Paint paint;

    // This variable tracks the game frame rate
    long fps;

    // This is used to help calculate the fps
    //private long timeThisFrame;

    // Declare an object of type Bitmap
    //Bitmap bitmapBob;

    // Bob starts off not moving
    //boolean isMoving = true;

    // He can walk at 150 pixels per second
    //float walkSpeedPerSecond = 150;

    // He starts 10 pixels from the left
    private float emberXPosition = -1;
    private float emberYPosition = -1;
    private int moveXDirection = 0;
    private int moveYDirection = -1;

    private static int numberOfBoxesWidth = 5;
    private static int numberOfBoxesHeight = 8;
    private int boxSize;
    private int roadWidth;
    private int roadHeight;
    private int bottomBuffer;
    private int roadLength = -1;
    private int secondsToArrival = 20;

    private boolean isDead = false;
    private boolean isWin = false;

    private boolean isDragging = false;

    private int cornerX = 0;
    private int cornerY = 0;

    private Skull skull;

    private Tree tree;
    private Flower flower;
    private Rock rock;
    private Grass grass;
    private Star star;
    private Ember ember;
    private RockPaths rockPaths;
    private Coin coin;
    private Road road;
    private Water water;

    boolean isFixedWalking = true;

    private int nextType;

    private PathPieceHandler pathPieces;

    private GameStartMenu gameStartMenu;

    private Resources resources;

    private int gameState = InGameStates.GAME_START_MENU;

    /**
     * Constructor for Game class
     * @param res
     */
    public Game(Resources res, GameData in_data, int in_width, int in_height) {
        super(in_data, in_width, in_height);
        resources = res;

        initAtCreation();

        initResources(res);

        playing = true;
    }

    // Everything that needs to be updated goes in here
    // In later projects we will have dozens (arrays) of objects.
    // We will also do other things like collision detection.
    @Override
    public void update() {

        if(gameState != InGameStates.GAME_PLAYING)
            return;
        // If bob is moving (the player is touching the screen)
        // then move him to the right based on his target speed and the current fps.
        if((moveXDirection != 0 || moveYDirection != 0) && emberXPosition >= 0 && !isDead && !isWin) { // && emberXPosition >= 0
            emberXPosition += moveXDirection * (roadLength/secondsToArrival/fps);
            emberYPosition += moveYDirection * (roadLength/secondsToArrival/fps);
            //Log.d(Game.class.toString(), "Move speed: " + (roadLength/secondsToArrival/fps));

            ember.stepFrame();
            coin.stepFrame();

            /*Log.d(Game.class.toString(), "Updating!");
            Log.d(Game.class.toString(), "Height diff [" + emberYPosition + ", " + (height-bottomBuffer) + "].");
            Log.d(Game.class.toString(), "Width diff [" + emberXPosition + ", " + (roadWidth + (boxSize*numberOfBoxesWidth)/2) + "].");*/

            if (isFixedWalking) {
                //Log.d(Game.class.toString(), "Road!");
                moveOnFixedRoad();
            } else if (isWithinGrid(emberXPosition, emberYPosition)) {
                //Log.d(Game.class.toString(), "Path!");
                moveOnAddedPath();
            } else if(emberXPosition ==  roadWidth + (boxSize*numberOfBoxesWidth)/2 && emberYPosition >= height-bottomBuffer) {
                Log.d(Game.class.toString(), "Is win!");
                isWin = true;
            } else {
                Log.d(Game.class.toString(), "Is death!");
                isDead = true;
                skull.setPos((int)emberXPosition, (int)emberYPosition);
            }

        }

    }


    // Draw the newly updated scene
    @Override
    public void draw(Canvas canvas) {

        // Draw the background color
        canvas.drawColor(Color.argb(255,  140, 200, 22));

        // Draw water
        Paint backgroundPaint = new Paint();
        backgroundPaint.setColor(Color.argb(255,  0, 100, 255));
        canvas.drawRect(roadWidth, roadHeight, width, height, backgroundPaint);
        drawWater(canvas);

        //Draw finish land
        backgroundPaint.setColor(Color.argb(255,  140, 200, 22));
        canvas.drawRect(roadWidth + 2 * boxSize, height-bottomBuffer, width, height, backgroundPaint);

        drawGrid(canvas);

        drawPath(canvas);

        Paint circlePaint = new Paint();
        circlePaint.setColor(Color.RED);
        canvas.drawCircle(emberXPosition, emberYPosition, 20, circlePaint);

        flower.draw(canvas);

        coin.setPos(roadWidth+40, roadHeight+40);
        coin.draw(canvas);
        coin.setPos(width-140, roadHeight+40);
        coin.draw(canvas);

        if(isDead)
            skull.draw(canvas);
        else {
            ember.setPos((int) emberXPosition - 40, (int) emberYPosition - 100);
            ember.draw(canvas, moveXDirection, moveYDirection);

            if(isWin) {
                star.setPos((int)emberXPosition - 20, (int)emberYPosition-160);
                star.draw(canvas);
            }

        }

        tree.draw(canvas);
        rock.draw(canvas);
        grass.draw(canvas);
        tree.setPos(width - 300, height - 220);
        tree.draw(canvas);
        tree.setPos(750, 30);

        debugDraw(canvas);

        if(gameState == InGameStates.GAME_START_MENU)
            gameStartMenu.draw(canvas);

    }

    // The SurfaceView class implements onTouchListener
    // So we can override this method and detect screen touches.
    @Override
    public void input(MotionEvent motionEvent) {

        switch (motionEvent.getAction() & MotionEvent.ACTION_MASK) {

            // Player has touched the screen
            case MotionEvent.ACTION_DOWN:
                Log.d(Game.class.toString(), "Touched at [" + motionEvent.getX() + ", " + motionEvent.getY() + "]");

                if(gameState == InGameStates.GAME_PLAYING) {
                    if (!isDragging && isWithinCornerPiece(motionEvent.getX(), motionEvent.getY())) {
                        isDragging = true;
                    }
                }

                // Handling Start Menu clicks
                if(gameStartMenu != null && gameState == InGameStates.GAME_START_MENU)
                    gameStartMenu.onTouchPress((int)motionEvent.getX(), (int)motionEvent.getY());

                break;

            case MotionEvent.ACTION_MOVE:
                if(gameState == InGameStates.GAME_PLAYING) {
                    if (isDragging) {
                        cornerX = (int) motionEvent.getX() - boxSize / 2;
                        cornerY = (int) motionEvent.getY() - boxSize / 2;
                    }
                }
                break;

            // Player has removed finger from screen
            case MotionEvent.ACTION_UP:
                if(gameState == InGameStates.GAME_PLAYING) {
                    isDragging = false;
                    cornerX = width-boxSize;
                    cornerY = 0;
                    if (isWithinGrid(motionEvent.getX(), motionEvent.getY()) && !isDead && !isWin) {
                        Log.d(Game.class.toString(), "Dropped within grid");
                        Point p = getBox((int) motionEvent.getX(), (int) motionEvent.getY());
                        Log.d(Game.class.toString(), "Box gotten [" + p.x + ", " + p.y + "]");
                        boolean success = pathPieces.addPathPiece(p.x, p.y, nextType);
                        if (success) {
                            Log.d(Game.class.toString(), "Success, change path piece!");
                            nextType = getNextType();
                        }
                    }/* else if (isDead || isWin) {
                        reset();
                    }*/
                }

                // Handling Start Menu clicks
                if(gameStartMenu != null && gameState == InGameStates.GAME_START_MENU)
                    if(gameStartMenu.onTouchRelease((int)motionEvent.getX(), (int)motionEvent.getY()) == InGameMenuConstants.OK_BUTTON_PRESSED)
                        gameState = InGameStates.GAME_PLAYING;

                break;
        }
    }

    /**
     * Checks if point is within corner path piece
     * @param x
     * @param y
     * @return
     */
    private boolean isWithinCornerPiece(float x, float y) {
        if(x >= width-boxSize && x <= width && y >= 0 && y <= boxSize)
            return true;
        return false;
    }

    @Override
    public void setFps(long in_fps) {
        fps = in_fps;
    }

    @Override
    public void pause() {
        playing = false;
    }

    @Override
    public void resume() {
        playing = true;
    }

    /**
     * Load level resources that are needed
     * @param res
     */
    private void initResources(Resources res) {
        // Load Skull from his .png file
        skull = new Skull(res, 0, 0);
        skull.scale(50,50);

        tree = new Tree(res, 720, 50);
        tree.scale(120,200);

        flower = new Flower(res, 660, 20);
        flower.scale(100,100);

        rock = new Rock(res, 450, 125);
        rock.scale(120,120);

        grass = new Grass(res, 250, 110);
        grass.scale(150,150);

        star = new Star(res, 0, 0);
        star.scale(50, 50);

        ember = new Ember(res);

        rockPaths = new RockPaths(res);

        coin = new Coin(res);

        road = new Road(res);

        water = new Water(res);
        water.scale(boxSize, boxSize);
    }


    private void reset() {
        isDead = false;
        isWin = false;
        isFixedWalking = true;

        emberXPosition = roadWidth/2;
        emberYPosition = height;

        moveXDirection = 0;
        moveYDirection = -1;

        nextType = getNextType();

        pathPieces = new PathPieceHandler();
    }

    private void moveOnAddedPath() {
        Point boxNumber = getBox((int)emberXPosition, (int)emberYPosition);
        int type = pathPieces.getType(boxNumber.x, boxNumber.y);

        int boxXpos = (int) (emberXPosition - (roadWidth + boxNumber.x*boxSize));
        int boxYpos = (int) (emberYPosition - (roadHeight + boxNumber.y*boxSize));
        int halfBox = boxSize/2;

        switch(type) {
            case 0: /* | */
                if(moveYDirection == 0 || moveXDirection != 0) {
                    isDead = true;
                    skull.setPos((int)emberXPosition-25, (int)emberYPosition-25);
                }
                break;
            case 1: /* - */
                if(moveYDirection != 0 || moveXDirection == 0) {
                    isDead = true;
                    skull.setPos((int)emberXPosition-25, (int)emberYPosition-25);
                }
                break;
            case 2: /* |_ */
                if((boxXpos == halfBox && boxYpos <= halfBox) || (boxYpos == halfBox && boxXpos >= halfBox)) {
                    //it's all good
                } else if(boxXpos < halfBox && moveXDirection == -1) {
                    emberXPosition += halfBox - boxXpos;
                    moveXDirection = 0;
                    moveYDirection = -1;
                }else if(boxYpos > halfBox && moveYDirection == 1) {
                    emberYPosition -= boxYpos - halfBox;
                    moveYDirection = 0;
                    moveXDirection = 1;
                } else {
                    isDead = true;
                    skull.setPos((int)emberXPosition-25, (int)emberYPosition-25);
                }
                break;
            case 3: /*  |¯ */
                if((boxXpos == halfBox && boxYpos >= halfBox) || (boxYpos == halfBox && boxXpos >= halfBox)) {
                    //it's all good
                } else if(boxXpos < halfBox && moveXDirection == -1) {
                    emberXPosition += halfBox - boxXpos;
                    moveXDirection = 0;
                    moveYDirection = 1;
                } else if(boxYpos < halfBox && moveYDirection == -1) {
                    emberYPosition -= boxYpos - halfBox;
                    moveYDirection = 0;
                    moveXDirection = 1;
                } else {
                    isDead = true;
                    skull.setPos((int)emberXPosition-25, (int)emberYPosition-25);
                }
                break;
            case 4: /* ¯| */
                if((boxXpos == halfBox && boxYpos >= halfBox) || (boxYpos == halfBox && boxXpos <= halfBox)) {
                    //it's all good
                } else if(boxXpos > halfBox && moveXDirection == 1) {
                    emberXPosition += halfBox - boxXpos;
                    moveXDirection = 0;
                    moveYDirection = 1;
                } else if(boxYpos < halfBox && moveYDirection == -1) {
                    emberYPosition -= boxYpos - halfBox;
                    moveYDirection = 0;
                    moveXDirection = -1;
                } else {
                    isDead = true;
                    skull.setPos((int)emberXPosition-25, (int)emberYPosition-25);
                }
                break;
            case 5: /*  _| */
                if((boxXpos == halfBox && boxYpos <= halfBox) || (boxYpos == halfBox && boxXpos <= halfBox)) {
                    //it's all good
                } else if(boxXpos > halfBox && moveXDirection == 1) {
                    emberXPosition += halfBox - boxXpos;
                    moveXDirection = 0;
                    moveYDirection = -1;
                } else if(boxYpos > halfBox && moveYDirection == 1) {
                    emberYPosition -= boxYpos - halfBox;
                    moveYDirection = 0;
                    moveXDirection = -1;
                } else {
                    isDead = true;
                    skull.setPos((int)emberXPosition-25, (int)emberYPosition-25);
                }
                break;
            default:
                System.out.println("It's " + type + "!");
                isDead = true;
                skull.setPos((int)emberXPosition-25, (int)emberYPosition-25);
                break;
        }

    }

    private void moveOnFixedRoad() {
        if(moveYDirection == -1 && emberYPosition <= roadHeight/2) {
            Log.d(Game.class.toString(), "Moving up, [" + emberXPosition + ", " + emberYPosition + "]");
            moveXDirection = 1;
            moveYDirection = 0;
            emberYPosition = roadHeight/2;
        } else if(moveXDirection == 1 && emberXPosition >= roadWidth + ((numberOfBoxesWidth*boxSize)/2)) {
            Log.d(Game.class.toString(), "Moving right, [" + emberXPosition + ", " + emberYPosition + "]");
            moveXDirection = 0;
            moveYDirection = 1;
            emberXPosition = roadWidth + ((numberOfBoxesWidth*boxSize)/2);
        } else if(isWithinGrid(emberXPosition, emberYPosition)) {
            Log.d(Game.class.toString(), "Is within grid, [" + emberXPosition + ", " + emberYPosition + "]");
            isFixedWalking = false;
        }
    }



    private void debugDraw(Canvas canvas) {
        Paint debugPaint = new Paint();
        // Choose the brush color for drawing
        debugPaint.setColor(Color.argb(255,  249, 129, 0));

        // Make the text a bit bigger
        debugPaint.setTextSize(45);
        // Display the current fps on the screen
        canvas.drawText("FPS: " + fps, 20, 40, debugPaint);
        /*canvas.drawText("Width: " + width, 20, 80, debugPaint);
        canvas.drawText("Height: " + height, 20, 120, debugPaint);*/
    }

    private void initAtCreation() {
        calculateBoxAndRoadSize();

        emberXPosition = roadWidth/2;
        emberYPosition = height;

        moveYDirection = -1;

        nextType = getNextType();

        /*Calculate length of road, see drawRoad for more information*/
        roadLength = (height - roadHeight/2) + (roadWidth/2 + ((numberOfBoxesWidth*boxSize)/2)) + roadHeight/2;

        pathPieces = new PathPieceHandler();

        gameStartMenu = new GameStartMenu(width, height, boxSize/2, resources);

        cornerX = width - boxSize;
        cornerY = 0;
    }

    private void drawWater(Canvas canvas) {
        int type = 0;
        for(int x = roadWidth; x < width; x += boxSize) {
            for(int y = roadHeight; y < height; y += boxSize) { // - bottomBuffer
                water.setPos(x,y);
                water.draw(canvas, type);
                if(type == 0) { type = 1; }
                else { type = 0; }
            }
        }
    }

    private void drawGrid(Canvas canvas) {
        Paint linePaint = new Paint();
        linePaint.setColor(Color.argb(50,  255, 255, 255));
        linePaint.setStrokeWidth(10);

        for(int i=0; i < numberOfBoxesWidth; i++) {
            float tempX = roadWidth+(boxSize*i);
            canvas.drawLine(tempX,roadHeight, tempX, height-bottomBuffer, linePaint);
        }

        for(int i=0; i <= numberOfBoxesHeight; i++) {
            float tempY = roadHeight+(boxSize*i);
            canvas.drawLine(roadWidth,tempY, width, tempY, linePaint);
        }

        linePaint.setColor(Color.argb(50,  255, 255, 255));
        linePaint.setStrokeWidth(5);

        for(int i=0; i < numberOfBoxesWidth; i++) {
            float tempX = roadWidth+(boxSize*i);
            canvas.drawLine(tempX,roadHeight, tempX, height-bottomBuffer, linePaint);
        }

        for(int i=0; i <= numberOfBoxesHeight; i++) {
            float tempY = roadHeight+(boxSize*i);
            canvas.drawLine(roadWidth,tempY, width, tempY, linePaint);
        }
    }

    private void drawRoad(Canvas canvas) {
        Paint roadPaint = new Paint();
        roadPaint.setColor(Color.BLACK);
        roadPaint.setStrokeWidth(10);

        //down to up
        //canvas.drawLine(roadWidth/2, roadHeight/2, roadWidth/2, height, roadPaint);

        int xDirection = roadWidth/2-50;
        int yDirection = roadHeight/2+50;
        for(;yDirection < height; yDirection += 100) { //one might be half outside canvas
            road.setPos(xDirection, yDirection);
            road.draw(canvas, 0);
        }

        //right to left
        float stopRoad = roadWidth + ((numberOfBoxesWidth*boxSize)/2);
        //canvas.drawLine(roadWidth/2, roadHeight/2, stopRoad , roadHeight/2, roadPaint);

        xDirection = roadWidth/2+50;
        yDirection = roadHeight/2-50;
        for(;xDirection < stopRoad; xDirection += 100) {
            road.setPos(xDirection, yDirection);
            road.draw(canvas, 0);
        }

        //upper left to paths
        //canvas.drawLine(stopRoad, roadHeight/2, stopRoad , roadHeight, roadPaint);

        xDirection = (int)stopRoad-50;
        yDirection = roadHeight/2+40;  //cheat (should be +50
        for(;yDirection < roadHeight; yDirection += 100) {
            road.setPos(xDirection, yDirection);
            road.draw(canvas, 0);
        }

        //draw road corners
        xDirection = roadWidth/2-50;
        yDirection = roadHeight/2-50;
        road.setPos(xDirection, yDirection);
        road.draw(canvas, 1);
        xDirection = (int)stopRoad-50;
        yDirection = roadHeight/2-50;
        road.setPos(xDirection, yDirection);
        road.draw(canvas, 2);

        //finish line
        canvas.drawLine(stopRoad, height - bottomBuffer, stopRoad , height, roadPaint);

        xDirection = (int)stopRoad-50;
        yDirection = height - bottomBuffer;
        for(;yDirection < height; yDirection += 100) {
            road.setPos(xDirection, yDirection);
            road.draw(canvas, 0);
        }
    }

    private void drawCornerFrame(Canvas canvas) {
        Paint linePaint = new Paint();
        linePaint.setColor(Color.argb(150,  255, 255, 255));
        linePaint.setStrokeWidth(10);

        canvas.drawLine(cornerX, cornerY, cornerX+boxSize, cornerY, linePaint);
        canvas.drawLine(cornerX+boxSize, cornerY, cornerX+boxSize, cornerY+boxSize, linePaint);
        canvas.drawLine(cornerX+boxSize, cornerY+boxSize, cornerX, cornerY+boxSize, linePaint);
        canvas.drawLine(cornerX, cornerY+boxSize, cornerX, cornerY, linePaint);
    }


    private void drawPath(Canvas canvas) {
        //static road
        drawRoad(canvas);

        //corner frame and next path to place
        drawCornerFrame(canvas);
        rockPaths.drawImage(nextType, cornerX, cornerY, canvas); //drawRoadPiece(nextType, width-boxSize, 0);

        //placed path
        for(int i = 0; i < numberOfBoxesWidth; i++) {
            for(int j = 0; j < numberOfBoxesHeight; j++) {
                int type = pathPieces.getType(i, j);
                if(type > -1)
                    rockPaths.drawImage(type, getPositionFromBoxNumber(i,roadWidth), getPositionFromBoxNumber(j, roadHeight), canvas); //drawRoadPiece(type, getPositionFromBoxNumber(i,roadWidth), getPositionFromBoxNumber(j, roadHeight));
            }
        }

    }

    private int getPositionFromBoxNumber(int number, int filler) {
        return number * boxSize + filler;
    }

    /*private void drawRoadPiece(int type, int x, int y) {
        rockPaths.drawImage(type, x, y, canvas);
    }*/

    private void calculateBoxAndRoadSize() {
        int boxWidth = width/(numberOfBoxesWidth+1); //+1 for road
        int boxHeight = height/(numberOfBoxesHeight+1);

        if(boxWidth < boxHeight)
            boxSize = boxWidth - (boxWidth%10); //round down to nearest 10
        else
            boxSize = boxHeight - (boxHeight%10);

        bottomBuffer = boxSize/2;
        roadWidth = width - (numberOfBoxesWidth * boxSize);
        roadHeight = height - (numberOfBoxesHeight * boxSize) - bottomBuffer;
    }


    private int getNextType() {
        return (int)(Math.random() * 6);
    }

    private boolean isWithinGrid(float x, float y) {
        if(x >= roadWidth && x < boxSize*numberOfBoxesWidth + roadWidth &&
                y >= roadHeight && y < boxSize*numberOfBoxesHeight + roadHeight) {
            Log.d(Game.class.toString(), "In grid [" + x + ", " + y + "].");
            return true;
        }

        /*Point box = getBoxT((int)emberXPosition, (int)emberYPosition);
        if(box.x >= 0 && box.x < numberOfBoxesWidth && box.y >= 0 && box.y < numberOfBoxesHeight) {
            Log.i(Game.class.toString(), "In box [" + box.x + ", " + box.y + "].");
            return true;
        }*/
        //Log.i(Game.class.toString(), "Out of box [" + box.x + ", " + box.y + "].");
        return false;
    }

    private Point getBox(int in_x, int in_y) {
        //position of point in grid (unrelated to road)
        int relative_x = in_x - roadWidth;
        int relative_y = in_y - roadHeight;

        if(relative_x < 0 || relative_y < 0
                || relative_x >= boxSize*numberOfBoxesWidth
                || relative_y >= boxSize*numberOfBoxesHeight) {
            return new Point(-1, -1); }

        //box 0,0 is start box top left
        int box_x = relative_x/boxSize;
        int box_y = relative_y/boxSize;

        Log.d(Game.class.toString(), "Max grid size [" + boxSize*numberOfBoxesWidth + ", " + boxSize*numberOfBoxesHeight + "].");
        Log.d(Game.class.toString(), "Relative pos [" + relative_x + ", " + relative_y + "].");
        Log.d(Game.class.toString(), "Return box [" + box_x + ", " + box_y + "].");
        return new Point(box_x, box_y);
    }
}
