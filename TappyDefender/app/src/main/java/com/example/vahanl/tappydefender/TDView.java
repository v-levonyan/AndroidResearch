package com.example.vahanl.tappydefender;

import android.content.Context;
import android.view.SurfaceView;

/**
 * Created by vahanl on 8/30/16.
 */

public class TDView extends SurfaceView implements Runnable {

    volatile boolean playing;
    Thread gameThread = null;

    private PlayerShip player;

    public TDView(Context context) {
        super(context);
    }

    @Override
    public void run() {
        while (playing) {
            update();
            draw();
            control();
        }

    }

    private void update() {
    }

    private void control() {
    }

    private void draw() {

    }

    public void pause() {
        playing = false;
        try {
            gameThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void resume() {
        playing = true;
        gameThread = new Thread(this);
        gameThread.start();
    }
}
