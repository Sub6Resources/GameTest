package com.sub6resources.gametest;

/**
 * Created by Matthew on 7/6/2016.
 */
public class MainThread extends Thread {
    //flag to hold game state
    private boolean running;
    public void setRunning(boolean running) {
        this.running = running;
    }

    @Override
    public void run() {
        while(running) {
            //update game state
            //render state to the screen
        }
    }
}
