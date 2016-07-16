package com.sub6resources.skeet;

/**
 * Created by Matthew on 7/6/2016.
 */
import android.app.Activity;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.v4.view.MotionEventCompat;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.sub6resources.skeet.model.Finger;
import com.sub6resources.skeet.model.FireButton;
import com.sub6resources.skeet.model.Pigeon;
import com.sub6resources.skeet.model.Target;
import com.sub6resources.skeet.model.Trackpad;
import com.sub6resources.skeet.model.components.Speed;

public class MainGamePanel extends SurfaceView implements
        SurfaceHolder.Callback {

    private static final String TAG = MainGamePanel.class.getSimpleName();

    private MainThread thread;
    private Pigeon pigeon;
    private FireButton firebutton;
    private Trackpad trackpad;
    private Finger finger;
    private Target target;
    private int score = 0;
    public MainGamePanel(Context context) {
        super(context);
        // adding the callback (this) to the surface holder to intercept events
        getHolder().addCallback(this);

        //create droid and load bitmap
        pigeon = new Pigeon(BitmapFactory.decodeResource(getResources(),R.drawable.pigeon),50,50,0,1f,1f,0.1f);
        firebutton = new FireButton(BitmapFactory.decodeResource(getResources(),R.drawable.firebutton),950,450);
        trackpad = new Trackpad(BitmapFactory.decodeResource(getResources(),R.drawable.trackpad),75,450,this);
        target = new Target(BitmapFactory.decodeResource(getResources(),R.drawable.target),450,225);
        finger = null;
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
            if (event.getActionMasked() == MotionEvent.ACTION_DOWN) {
                if (pigeon != null) {
                    pigeon.handleActionDown((int) event.getX(), (int) event.getY());
                }
                boolean hit = firebutton.handleActionDown((int) event.getX(), (int) event.getY(), pigeon, target);
                if(hit) {
                    pigeon = null;
                    score++;
                }
                trackpad.handleActionDown((int) event.getX(), (int) event.getY());
                if (firebutton.isTouched()) {
                    firebutton.setBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.firebuttonpressed));
                }
                if (trackpad.isTouched()) {
                    //TODO add finger
                    finger = new Finger(BitmapFactory.decodeResource(getResources(), R.drawable.finger), (int) event.getX(), (int) event.getY());
                }

                //TODO find a place to put close button
                //if(event.getY() > getHeight() - 50) {
                //thread.setRunning(false);
                //    ((Activity)getContext()).finish();
                //} else {
                Log.d(TAG, "Coords: x=" + event.getX() + ",y=" + event.getY());
                //}
            }
        if(event.getActionMasked() == MotionEvent.ACTION_POINTER_DOWN) {
            int index = MotionEventCompat.getActionIndex(event);
            int xPos = (int) MotionEventCompat.getX(event, index);
            int yPos = (int) MotionEventCompat.getY(event, index);

            boolean hit = firebutton.handleActionDown(xPos,yPos,pigeon,target);
            if(hit) {
                pigeon = null;
                score++;
            }
            if (firebutton.isTouched()) {
                firebutton.setBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.firebuttonpressed));
            }
            Log.d("Main", "Secondary Event");
        }
            if (event.getActionMasked() == MotionEvent.ACTION_MOVE) {
                //if(pigeon.isTouched()) {
                //    pigeon.setX((int)event.getX());
                //    pigeon.setY((int)event.getY());
                //if ever not updating, use: invalidate();
                Log.d(TAG, "Move event: " + (int) event.getX() + "," + (int) event.getY());
                //}
                if (trackpad.isTouched()) {
                    if (finger != null) {
                        finger.setX((int) event.getX());
                        finger.setY((int) event.getY());
                    }
                }
                trackpad.handleActionDown((int) event.getX(), (int) event.getY());
            }
            if(event.getActionMasked() == MotionEvent.ACTION_POINTER_UP) {
                if (firebutton.isTouched()) {
                    firebutton.setTouched(false);
                }
            if(!firebutton.isTouched()) {
                firebutton.setBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.firebutton));
            }
        }
            if (event.getActionMasked() == MotionEvent.ACTION_UP) {
                //touch was released
                if(pigeon != null) {
                    if (pigeon.isTouched()) {
                        pigeon.setTouched(false);
                    }
                }
                if (firebutton.isTouched()) {
                    firebutton.setTouched(false);
                }
                if (trackpad.isTouched()) {
                    trackpad.setTouched(false);
                    finger = null;
                }
                if (finger != null) {
                    finger = null;
                }
                if (!firebutton.isTouched()) {
                    firebutton.setBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.firebutton));
                }
                Log.d(TAG, "Finger lifted");
            }
            return true;
    }

    public void render(Canvas canvas) {
        canvas.drawColor(Color.BLACK);
        if(pigeon != null) {
            pigeon.draw(canvas);
        }
        firebutton.draw(canvas);
        trackpad.draw(canvas);
        if(finger != null) {
            finger.draw(canvas);
        }
        target.draw(canvas);
        displayFps(canvas, avgFps);
        displayScore(canvas, "Score: "+score);
        displayShots(canvas, "Shots: "+firebutton.getGun().getShots());
    }

    public void update() {
        boolean collisionx = false;
        boolean collisiony = false;
        // check collision with right wall if heading right
        if ((target.getDirection() == target.RIGHT || target.getDirection() == target.UP || target.getDirection() == target.DOWN)
                && target.getX() >= getWidth()) {
            float yv = target.getSpeed().getYv();
            target.setSpeed(0,yv);
            collisionx = true;
            Log.d("Main Collision", "Right wall collision");
        }
        // check collision with left wall if heading left
        if ((target.getDirection() == target.LEFT || target.getDirection() == target.UP || target.getDirection() == target.DOWN)
                && target.getX() <= 0) {
            float yv = target.getSpeed().getYv();
            target.setSpeed(0,yv);
            collisionx = true;
            Log.d("Main Collision", "Left wall collision");
        }
        // check collision with bottom wall if heading down
        if ((target.getDirection() == target.DOWN || target.getDirection() == target.RIGHT || target.getDirection() == target.LEFT)
                && target.getY() >= getHeight()) {
            float xv = target.getSpeed().getXv();
            target.setSpeed(xv,0);
            collisiony = true;
            Log.d("Main Collision", "Bottom wall collision");
        }
        // check collision with top wall if heading up
        if ((target.getDirection() == target.UP || target.getDirection() == target.RIGHT || target.getDirection() == target.LEFT)
                && target.getY() <= 0) {
            float xv = target.getSpeed().getXv();
            target.setSpeed(xv,0);
            collisiony = true;
            Log.d("Main Collision", "Top wall collision");
        }
        // Update the lone droid
        if(pigeon != null) {
            pigeon.update();
            if(pigeon != null) {
                if ((pigeon.getBitmap().getWidth() - pigeon.getZ()) < 0) {
                    pigeon = null;
                }
            }
        }
        else {
            pigeon = new Pigeon(BitmapFactory.decodeResource(getResources(),R.drawable.pigeon),50,50,0,1f,1f,0.1f);
        }
        firebutton.update();
        if(finger != null) {
            target.update(trackpad.getX(), finger.getX(), trackpad.getY(), finger.getY(),collisionx,collisiony);
        }
        else {
            target.update(trackpad.getX(), trackpad.getX(), trackpad.getY(), trackpad.getY(),collisionx,collisiony);
        }
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
    private void displayScore(Canvas canvas, String score) {
        if(canvas != null && score != null) {
            Paint paint = new Paint();
            paint.setARGB(255,255,0,0);
            paint.setTextSize(40f);
            canvas.drawText(score,this.getWidth() - 250, 40, paint);
        }
    }
    private void displayShots(Canvas canvas, String shots) {
        if(canvas != null && shots != null) {
            Paint paint = new Paint();
            paint.setARGB(255,255,0,0);
            paint.setTextSize(40f);
            canvas.drawText(shots,this.getWidth() - 450, 40, paint);
        }
    }
    public MainThread getThread() {
        return thread;
    }
    public Finger getFinger() {
        return finger;
    }
    public Trackpad getTrackpad() {
        return trackpad;
    }
    public Pigeon getPigeon() {
        return pigeon;
    }
    public void setPigeon(Pigeon pigeon) {
        this.pigeon = pigeon;
    }
}
