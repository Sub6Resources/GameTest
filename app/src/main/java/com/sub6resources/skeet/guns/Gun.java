package com.sub6resources.skeet.guns;

import android.graphics.Canvas;
import android.util.Log;

import com.sub6resources.skeet.model.Pigeon;
import com.sub6resources.skeet.model.Target;

/**
 * Created by Matthew on 7/9/2016.
 */
public class Gun {
    private int shots;
    public Gun() {
        //TODO new class
        this.shots = 0;
    }
    public boolean fire(Pigeon pigeon, Target target) {
        //TODO fire
        Log.d("Main", "Fire!!!");
        shots++;
        if(target.getX() >=  pigeon.getX() - (pigeon.getBitmap().getWidth()/2) && target.getX() <= pigeon.getX() + (pigeon.getBitmap().getWidth()/2)) {
            if(target.getY() >=  pigeon.getY() - (pigeon.getBitmap().getHeight()/2) && target.getY() <= pigeon.getY() + (pigeon.getBitmap().getHeight()/2)) {
                return pigeon.destroy();
            }
        }

        return false;
    }
    public int getShots() {
        return shots;
    }
}
