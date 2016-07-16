package com.sub6resources.skeet.model;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.util.Log;

import com.sub6resources.skeet.MainGamePanel;
import com.sub6resources.skeet.model.components.Speed;

/**
 * Created by Matthew on 7/9/2016.
 */
public class Target {
    private int x;
    private int y;
    private Bitmap bitmap;
    private Speed speed;
    private MainGamePanel mainGamePanel;
    private int direction;

    public final int UP = 2;
    public final int DOWN = 1;
    public final int RIGHT = 0;
    public final int LEFT = -1;

    public Target(Bitmap bitmap, int x, int y) {
        this.x = x;
        this.y = y;
        this.bitmap = bitmap;
        speed = new Speed(0,0,0);
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
    public void draw(Canvas canvas) {
        canvas.drawBitmap(bitmap, x - (bitmap.getWidth() / 2), y - (bitmap.getHeight() / 2), null);
    }
    public void update(int x1, int x2, int y1, int y2, boolean collisionx, boolean collisiony) {
        updateVelocity(x1,x2,y1,y2,collisionx,collisiony);
            x += (speed.getXv() * speed.getxDirection());
            y += (speed.getYv() * speed.getyDirection());


    }
    public Speed getSpeed() {
        return speed;
    }
    public void setSpeed(float xv, float yv) {
        speed = new Speed(xv, yv, 0f);

    }
    public void updateVelocity(int x1, int x2, int y1, int y2,boolean x,boolean y) {

        int dx = x1-x2;
        int dy = y1-y2;

        if(dx < 0) {
            if(Math.abs(dx) > Math.abs(dy)) {
                setDirection("RIGHT");
            }
        }
        else {
            if(Math.abs(dx) > Math.abs(dy)) {
                setDirection("LEFT");
            }
        }
        if(dy < 0) {
            if(Math.abs(dy) > Math.abs(dx)) {
                setDirection("DOWN");
            }
        }
        else {
            if(Math.abs(dy) > Math.abs(dx)) {
                setDirection("UP");
            }
        }
        int pyth = dx^2 + dy^2;
        double velocity = Math.sqrt(pyth);
        //Log.d("Main", "Distance from center of trackpad: "+velocity);
            dx = dx * -1;
            dy = dy * -1;
        if(x) {
            setSpeed(0,dy/10);
        }
        if(y) {
            setSpeed(dx/10,0);
        }
        if(!x && !y) {
            setSpeed(dx / 10, dy / 10);
        }

        //Log.d("Main","New Target Speed: "+dx+","+dy);
    }
    public int getDirection() {
        return direction;
    }
    public void setDirection(int direction) {
        this.direction = direction;
    }
    public void setDirection(String direction) {
        if(direction == "UP") {
            this.direction = this.UP;
        }
        if(direction == "DOWN") {
            this.direction = this.DOWN;
        }
        if(direction == "LEFT") {
            this.direction = this.LEFT;
        }
        if(direction == "RIGHT") {
            this.direction = this.RIGHT;
        }
    }
}
