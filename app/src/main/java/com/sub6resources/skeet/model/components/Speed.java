package com.sub6resources.skeet.model.components;

/**
 * Created by Matthew on 7/6/2016.
 */
public class Speed {
    public static final int DIRECTION_RIGHT = 1;
    public static final int DIRECTION_LEFT  = -1;
    public static final int DIRECTION_UP    = -1;
    public static final int DIRECTION_DOWN  = 1;
    public static final int DIRECTION_BACK = 1;
    public static final int DIRECTION_FORWARD = -1;

    private float xv = 1;
    private float yv = 1;
    private float zv = 1;

    private int xDirection = DIRECTION_RIGHT;
    private int yDirection = DIRECTION_DOWN;
    private int zDirection = DIRECTION_BACK;

    public Speed() {
        this.xv = 1;
        this.yv = 1;
        this.zv = 1;
    }
    public Speed(float xv, float yv, float zv) {
        this.xv = xv;
        this.yv = yv;
        this.zv = zv;
    }
    public float getXv() {
        return xv;
    }
    public void setXv(float xv) {
        this.xv = xv;
    }
    public float getYv() {
        return yv;
    }
    public void setYv(float yv) {
        this.yv = yv;
    }
    public float getZv() {
        return zv;
    }
    public void setZv(float zv) {
        this.zv = zv;
    }
    public int getxDirection() {
        return xDirection;
    }
    public void setxDirection(int xDirection) {
        this.xDirection = xDirection;
    }
    public int getyDirection() {
        return yDirection;
    }
    public void setyDirection(int yDirection) {
        this.yDirection = yDirection;
    }
    public int getzDirection() {
        return zDirection;
    }
    public void setzDirection(int zDirection) {
        this.zDirection = zDirection;
    }
    //changes direction on the x axis
    public void toggleXDirection() {
        xDirection = xDirection * -1;
    }
    public void toggleYDirection() {
        yDirection = yDirection * -1;
    }
    public void toggleZDirection() {
        zDirection = zDirection * -1;
    }
}
