package com.sub6resources.skeet.model;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;

import com.sub6resources.skeet.R;
import com.sub6resources.skeet.guns.Gun;

/**
 * Created by Matthew on 7/9/2016.
 */
public class FireButton {
    private boolean touched;
    private int x;
    private int y;
    private Bitmap bitmap;
    private Gun gun = new Gun();

    public FireButton(Bitmap bitmap, int x, int y) {
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
    public void update() {
        if(isTouched()) {
            //TODO add touched graphics

        }
        else {
            //TODO clear touched graphics

        }
    }
    public void draw(Canvas canvas) {
        canvas.drawBitmap(bitmap, x - (bitmap.getWidth() / 2), y - (bitmap.getHeight() / 2), null);
    }
    public boolean handleActionDown(int eventX, int eventY, Pigeon pigeon, Target target) {
        if(eventX >= (x - bitmap.getWidth() / 2) && (eventX <= (x + bitmap.getWidth()/2))) {
            if(eventY >= (y - bitmap.getHeight()/2) && (y <= (y +bitmap.getWidth()/2))) {
                //firebutton touched
                setTouched(true);
                return gun.fire(pigeon,target);
            } else {
                setTouched(false);
            }
        } else {
            setTouched(false);
        }
        return false;
    }
    public Gun getGun() {
        return gun;
    }
}
