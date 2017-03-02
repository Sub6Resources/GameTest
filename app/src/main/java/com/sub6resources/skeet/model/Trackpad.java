package com.sub6resources.skeet.model;

import android.graphics.Bitmap;
import android.graphics.Canvas;

import com.sub6resources.skeet.MainGamePanel;

/**
 * Created by Matthew on 7/9/2016.
 */
public class Trackpad {
    private boolean touched;
    private int direction;
    private int magnitude;
    private Bitmap bitmap;
    private int x;
    private int y;

    public Trackpad(Bitmap bitmap, int x, int y, MainGamePanel mainGamePanel) {
        this.bitmap = bitmap;
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }
    public void setX(int x) {
        this.x = x;
    }
    public int getY() {
        return y;
    }
    public void setY(int y) {
        this.y = y;
    }
    public Bitmap getBitmap() {
        return bitmap;
    }
    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }
    public boolean isTouched() {
        return touched;
    }
    public void setTouched(boolean touched) {
        this.touched = touched;
    }
    public void draw(Canvas canvas) {
        canvas.drawBitmap(bitmap, x - (bitmap.getWidth() / 2), y - (bitmap.getHeight() / 2), null);
    }
    public void handleActionDown(int eventX, int eventY) {
        if(eventX >= (x - bitmap.getWidth() / 2) && (eventX <= (x + bitmap.getWidth()/2))) {
            if(eventY >= (y - bitmap.getHeight()/2) && (y <= (y +bitmap.getWidth()/2))) {
                //trackpad touched
                setTouched(true);
            } else {
                setTouched(false);
            }
        } else {
            setTouched(false);
        }
    }
}
