package com.sub6resources.gametest;

/**
 * Created by Matthew on 7/6/2016.
 */
import android.app.Activity;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.sub6resources.gametest.model.Droid;
import com.sub6resources.gametest.model.components.Speed;

public class MainGamePanel extends SurfaceView implements
        SurfaceHolder.Callback {

    private static final String TAG = MainGamePanel.class.getSimpleName();

    private MainThread thread;
    private Droid droid;
    public MainGamePanel(Context context) {
        super(context);
        // adding the callback (this) to the surface holder to intercept events
        getHolder().addCallback(this);

        //create droid and load bitmap
        droid = new Droid(BitmapFactory.decodeResource(getResources(),R.drawable.droid_1),50,50);
        thread = new MainThread(getHolder(), this);
        // make the GamePanel focusable so it can handle events
        setFocusable(true);
        setWillNotDraw(false);
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        thread.setRunning(true);
        thread.start();
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        Log.d(TAG, "Surface is being destroyed");
        boolean retry = true;
        while(retry) {
            try {
                thread.join();
                retry = false;
            } catch (InterruptedException e) {
                //try again shutting down the thread
            }
        }
        Log.d(TAG, "Thread was shut down cleanly");
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            droid.handleActionDown((int)event.getX(), (int)event.getY());
            if(event.getY() > getHeight() - 50) {
                thread.setRunning(false);
                ((Activity)getContext()).finish();
            } else {
                Log.d(TAG, "Coords: x=" + event.getX() + ",y=" + event.getY());
            }
        }
        if(event.getAction() == MotionEvent.ACTION_MOVE) {
            if(droid.isTouched()) {
                droid.setX((int)event.getX());
                droid.setY((int)event.getY());
                //TODO invalidate();
                //Log.d(TAG, "Drawing robot at "+event.getX()+","+event.getY());
                Log.d(TAG, "Move event");
            }
        }
        if (event.getAction() == MotionEvent.ACTION_UP) {
            //touch was released
            if(droid.isTouched()) {
                droid.setTouched(false);
            }
            Log.d(TAG, "Finger lifted");
        }
        return true;
    }

/*    @Override
    protected void onDraw(Canvas canvas) {
        //Log.d(TAG, "Drawing canvas color...");
        canvas.drawColor(Color.BLACK);
        //Log.d(TAG, "Drawing droid...");
        //Log.d(TAG, ""+canvas);
        droid.draw(canvas);
    }*/

    public void render(Canvas canvas) {
        canvas.drawColor(Color.BLACK);
        droid.draw(canvas);
        displayFps(canvas, avgFps);
    }

    public void update() {
        // check collision with right wall if heading right
        if (droid.getSpeed().getxDirection() == Speed.DIRECTION_RIGHT
                && droid.getX() + droid.getBitmap().getWidth() / 2 >= getWidth()) {
            droid.getSpeed().toggleXDirection();
        }
        // check collision with left wall if heading left
        if (droid.getSpeed().getxDirection() == Speed.DIRECTION_LEFT
                && droid.getX() - droid.getBitmap().getWidth() / 2 <= 0) {
            droid.getSpeed().toggleXDirection();
        }
        // check collision with bottom wall if heading down
        if (droid.getSpeed().getyDirection() == Speed.DIRECTION_DOWN
                && droid.getY() + droid.getBitmap().getHeight() / 2 >= getHeight()) {
            droid.getSpeed().toggleYDirection();
        }
        // check collision with top wall if heading up
        if (droid.getSpeed().getyDirection() == Speed.DIRECTION_UP
                && droid.getY() - droid.getBitmap().getHeight() / 2 <= 0) {
            droid.getSpeed().toggleYDirection();
        }
        // Update the lone droid
        droid.update();
    }
    private String avgFps;
    public void setAvgFps(String avgFps) {
        this.avgFps = avgFps;
    }
    private void displayFps(Canvas canvas, String fps) {
        if(canvas != null && fps != null) {
            Paint paint = new Paint();
            paint.setARGB(255,255,266,255);
            canvas.drawText(fps, this.getWidth() - 50, 20, paint);
        }
    }
}
