package com.sub6resources.skeet.model;

/**
 * Created by Matthew on 7/6/2016.
 */

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.Log;

import com.sub6resources.skeet.MainGamePanel;
import com.sub6resources.skeet.model.components.Speed;

public class Pigeon {
    private Bitmap bitmap;
    private float x;
    private float y;
    private float z;
    private boolean touched;
    private Speed speed;

    public Pigeon(Bitmap bitmap, int x, int y, int z, float vx, float vy, float vz) {
        this.bitmap = bitmap;
        this.x = x;
        this.y = y;
        this.z = z;
        speed = new Speed(vx,vy,vz);
    }

    public Bitmap getBitmap() {
        return bitmap;
    }
    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }
    public float getX() {
        return x;
    }
    public void setX(int x) {
        this.x = x;
    }
    public float getY() {
        return y;
    }
    public void setY(int y) {
        this.y = y;
    }
    public float getZ() {
        return z;
    }
    public void setZ(int z) {
        this.z = z;
    }
    public boolean isTouched() {
        return touched;
    }
    public void setTouched(boolean touched) {
        this.touched = touched;
    }
    public void draw(Canvas canvas) {
        canvas.drawBitmap(bitmap, null, new RectF(x - (bitmap.getWidth() / 2), y - (bitmap.getHeight() / 2), (x + bitmap.getWidth()) - z, (y + bitmap.getHeight()) - z), null);
        float drewx = x - (bitmap.getWidth() / 2);
        float drewy = y - (bitmap.getHeight() / 2);
        float drewz = bitmap.getWidth() - z;
        //Log.d("Main","Drew pigeon at "+drewx+","+drewy+","+drewz);
    }
    public void handleActionDown(int eventX, int eventY) {
        if(eventX >= (x - bitmap.getWidth() / 2) && (eventX <= (x + bitmap.getWidth()/2))) {
            if(eventY >= (y - bitmap.getHeight()/2) && (y <= (y +bitmap.getWidth()/2))) {
                //skeet touched
                setTouched(true);
            } else {
                setTouched(false);
            }
        } else {
            setTouched(false);
        }
    }
    public void update() {
        if(!touched) {
            x += (speed.getXv() * speed.getxDirection());
            y += (speed.getYv() * speed.getyDirection());
            z += (speed.getZv() * speed.getzDirection());
        }

    }
    public boolean destroy() {
        return true;
    }
    public Speed getSpeed() {
        return speed;
    }
}

