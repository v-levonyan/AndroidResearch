package com.example.vahanl.tappydefender;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_screen);

        SharedPreferences prefs = getSharedPreferences("HighScores", this.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();

        final Button buttonPlay = (Button) findViewById(R.id.playButton);

        final TextView textFastestTime = (TextView) findViewById(R.id.textHighScore);

        buttonPlay.setOnClickListener(this);

        long fastestTime = prefs.getLong("fastestTime", 1000000);

        textFastestTime.setText("Fastest Time: " + fastestTime);

    }

    @Override
    public void onClick(View v) {
        Intent i = new Intent(this, GameActivity.class);
        startActivity(i);
        finish();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            finish();
            return true;
        }
        return false;
    }

}
