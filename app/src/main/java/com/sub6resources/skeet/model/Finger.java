package com.sub6resources.skeet.model;

import android.graphics.Bitmap;
import android.graphics.Canvas;

/**
 * Created by Matthew on 7/9/2016.
 */
public class Finger {

    private boolean touched;
    private int direction;
    private int magnitude;
    private Bitmap bitmap;
    private int x;
    private int y;

    public Finger(Bitmap bitmap, int x, int y) {
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
    public int getDirection() { return direction; }
    public void setDirection(int Direction) {this.direction = direction;}
    public int getMagnitude() { return magnitude;}
    public void setMagnitude(int magnitude) {this.magnitude = magnitude;}
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

}
