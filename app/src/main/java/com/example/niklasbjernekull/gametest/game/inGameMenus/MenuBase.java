package com.example.niklasbjernekull.gametest.game.inGameMenus;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;

import com.example.niklasbjernekull.gametest.constants.InGameMenuConstants;
import com.example.niklasbjernekull.gametest.drawables.DrawableImage;

/**
 * Created by Niklas on 2016-10-06.
 *
 * Abstract class for in game menus.
 * Contains common methods that are used by child classes.
 */

public abstract class MenuBase {
    private int x, y;
    private int size_x, size_y;
    private int corner = 100;
    private Paint roundPaint;
    private DrawableImage abortButton;
    private DrawableImage okButton;
    private DrawableImage abortButtonPressed;
    private DrawableImage okButtonPressed;
    private int buttonSpaceDivider = 30;
    private int buttonRatio = 4;
    private int buttonSpaceSide;
    private int buttonSpaceBottom;
    private int buttonWidth;
    private int buttonHeight;
    private boolean abortButtonActive = false;
    private boolean okButtonActive = false;

    /**
     * Constructor that takes coordinates for upper left corner and the width and height of the
     * rectangle as indata.
     * @param in_x - x coordinates for rectangle
     * @param in_y - y coordinates for rectangle
     * @param in_size_x - width of rectangle
     * @param in_size_y - height of rectangle
     */
    public MenuBase(int in_x, int in_y, int in_size_x, int in_size_y) {
        x = in_x;
        y = in_y;
        size_x = in_size_x;
        size_y = in_size_y;

        roundPaint = new Paint();
        roundPaint.setColor(Color.argb(220,  255, 255, 255));

        setButtonData();
    }

    /**
     * Constructor that takes indata for a centered rectangle (even spacing on all sides)
     * x = space;
     * y = space;
     * size_x = width - space*2;
     * size_y = height - space*2;
     * @param width - width of rectangle
     * @param height - height of rectangle
     * @param space - space on all sides of rectangle
     */
    public MenuBase(int width, int height, int space) {
        this(space, space, width - space*2, height - space*2);
    }

    private void setButtonData() {
        buttonSpaceSide = size_x/buttonSpaceDivider; //width / 30 = space size
        buttonSpaceBottom = buttonSpaceSide*3;
        buttonWidth = (size_x - 3*buttonSpaceSide)/2; //buttonWidth = width - spaces / 2 (2 buttons)
        buttonHeight = buttonWidth/buttonRatio; // height vs width ratio 1:4
    }

    /**
     * Set button to the lower left
     * @param in_bmp
     */
    protected void setAbortButton(Bitmap in_bmp, Bitmap in_bmp_pressed) {
        int buttonX = x+buttonSpaceSide;
        int buttonY = y+size_y-buttonSpaceBottom-buttonHeight;

        abortButton = new DrawableImage(in_bmp, buttonX, buttonY);
        abortButton.scale(buttonWidth, buttonHeight);

        abortButtonPressed = new DrawableImage(in_bmp_pressed, buttonX, buttonY);
        abortButtonPressed.scale(buttonWidth, buttonHeight);
    }

    /**
     * Set button to the lower right
     * @param in_bmp
     */
    protected void setOkButton(Bitmap in_bmp, Bitmap in_bmp_pressed) {
        int buttonX = x+2*buttonSpaceSide+buttonWidth;
        int buttonY = y+size_y-buttonSpaceBottom-buttonHeight;

        okButton = new DrawableImage(in_bmp, buttonX, buttonY);
        okButton.scale(buttonWidth, buttonHeight);

        okButtonPressed = new DrawableImage(in_bmp_pressed, buttonX, buttonY);
        okButtonPressed.scale(buttonWidth, buttonHeight);
    }

    /**
     * Change start position of rectangle (upper left corner)
     * @param in_x
     * @param in_y
     */
    public void setPos(int in_x, int in_y) {
        x = in_x;
        y = in_y;
    }

    /**
     * Change size of rectangle
     * @param in_size_x
     * @param in_size_y
     */
    public void setSize(int in_size_x, int in_size_y) {
        size_x = in_size_x;
        size_y = in_size_y;
    }

    /**
     * Paint transparent rectangle with rounded corners
     * @param canvas
     */
    public void draw(Canvas canvas) {
        canvas.drawRoundRect(x, y, size_x+x, size_y+y, corner, corner, roundPaint);

        if(abortButtonActive) {
            abortButtonPressed.draw(canvas);
        } else {
            abortButton.draw(canvas);
        }

        if(okButtonActive) {
            okButtonPressed.draw(canvas);
        } else {
            okButton.draw(canvas);
        }

    }

    /**
     * If touch coordinates is within buttons, activate them
     * @param in_x
     * @param in_y
     */
    public void onTouchPress(int in_x, int in_y) {
        if(in_x >= abortButton.x && in_x <= abortButton.x+buttonWidth &&
                in_y >= abortButton.y && in_y <= abortButton.y+buttonHeight) {
            abortButtonActive = true;
        } else if(in_x >= okButton.x && in_x <= okButton.x+buttonWidth &&
                in_y >= okButton.y && in_y <= okButton.y+buttonHeight) {
            okButtonActive = true;
        }
    }

    /**
     * If touch action is released on the same button it pressed return info
     * @param in_x
     * @param in_y
     * @return
     */
    public int onTouchRelease(int in_x, int in_y) {
        Log.i("MenuBase", "onTouchRelease");
        if(in_x >= abortButton.x && in_x <= abortButton.x+buttonWidth &&
                in_y >= abortButton.y && in_y <= abortButton.y+buttonHeight &&
                abortButtonActive) {
            abortButtonActive = false;
            Log.i("Menu Base", "Abort Released!");
            return InGameMenuConstants.ABORT_BUTTON_PRESSED;
        } else if(in_x >= okButton.x && in_x <= okButton.x+buttonWidth &&
                in_y >= okButton.y && in_y <= okButton.y+buttonHeight &&
                okButtonActive) {
            okButtonActive = false;
            Log.i("Menu Base", "Ok Released!");
            return InGameMenuConstants.OK_BUTTON_PRESSED;
        } else {
            abortButtonActive = false;
            okButtonActive = false;
        }
        return InGameMenuConstants.NO_BUTTON_PRESSED;
    }

}
