package com.sub6resources.skeet;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class Home extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
    }

    public void mainGame() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
    public void mainGame(View view) {
        mainGame();
    }
}
