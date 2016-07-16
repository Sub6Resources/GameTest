package com.sub6resources.skeet;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.app.Activity;
import android.view.Window;
import android.view.WindowManager;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();
    private MainGamePanel mainGamePanel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //changed from R.layout.ActivityMain or something like that
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        mainGamePanel = new MainGamePanel(this);
        setContentView(mainGamePanel);
        Log.d(TAG, "View added");
    }

    @Override
    protected void onPause() {
        Log.d(TAG, "Pausing...");
        mainGamePanel.getThread().setRunning(false);
        super.onPause();
    }
    @Override
    protected void onResume() {
        Log.d(TAG, "Resuming...");
        mainGamePanel.getThread().setRunning(true);
        super.onResume();
    }
    @Override
    protected void onDestroy() {
        Log.d(TAG, "Destroying...");
        super.onDestroy();
    }

    @Override
    protected void onStop() {
        Log.d(TAG, "Stopping...");
        super.onStop();
    }
}
