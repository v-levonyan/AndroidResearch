package com.example.vahanl.tappydefender;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.ArrayList;

/**
 * Created by vahanl on 8/30/16.
 */

public class TDView extends SurfaceView implements Runnable {

    volatile boolean playing;
    Thread gameThread = null;

    private PlayerShip player;

    public EnemyShip enemy1;
    public EnemyShip enemy2;
    public EnemyShip enemy3;

    public ArrayList<SpaceDust> dustList = new ArrayList<>();

    private Paint paint;
    private Canvas canvas;
    private SurfaceHolder ourHolder;

    private float distanceRemaining;
    private long timeTaken;
    private long timeStarted;
    private long fastestTime;

    private int screenX;
    private int screenY;

    public TDView(Context context, int x, int y) {
        super(context);
        ourHolder = getHolder();
        paint = new Paint();
        player = new PlayerShip(context, x, y);
        enemy1 = new EnemyShip(context, x, y);
        enemy2 = new EnemyShip(context, x, y);
        enemy3 = new EnemyShip(context, x, y);

        screenX = x;
        screenY = y;

        int numSpecs = 40;

        for (int i = 0; i < numSpecs; i++) {
            SpaceDust spec = new SpaceDust(x, y);
            dustList.add(spec);
        }
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
        if (Rect.intersects(player.getHitBox(), enemy1.getHitBox())) {
            enemy1.setX(-100);
        }
        if (Rect.intersects(player.getHitBox(), enemy2.getHitBox())) {
            enemy2.setX(-100);
        }
        if (Rect.intersects(player.getHitBox(), enemy3.getHitBox())) {
            enemy3.setX(-100);
        }

        player.update();
        enemy1.update(player.getSpeed());
        enemy2.update(player.getSpeed());
        enemy3.update(player.getSpeed());

        for (SpaceDust sd : dustList) {
            sd.update(player.getSpeed());
        }
    }

    private void control() {
        try {
            gameThread.sleep(17);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void draw() {
        if (ourHolder.getSurface().isValid()) {
            canvas = ourHolder.lockCanvas();

            paint.setColor(Color.argb(255, 255, 255, 255));

            for (SpaceDust sd : dustList) {
                canvas.drawPoint(sd.getX(), sd.getY(), paint);
            }

            canvas.drawColor(Color.argb(255, 0, 0, 0));

            canvas.drawBitmap(player.getBitmap(), player.getX(), player.getY(), paint);

            canvas.drawBitmap(enemy1.getBitmap(), enemy1.getX(), enemy1.getY(),paint);
            canvas.drawBitmap(enemy2.getBitmap(), enemy2.getX(), enemy2.getY(),paint);
            canvas.drawBitmap(enemy3.getBitmap(), enemy3.getX(), enemy3.getY(),paint);

            paint.setTextAlign(Paint.Align.LEFT);
            paint.setColor(Color.argb(255, 255, 255, 255));
            paint.setTextSize(25);
            canvas.drawText("Fastest: " + fastestTime + "s", 10, 20, paint);
            canvas.drawText("Time: " + timeTaken + "s",  screenX / 2, 20, paint);
            canvas.drawText("Distance:" +
                    distanceRemaining / 1000 +
                    " KM", screenX / 3, screenY - 20, paint);
            canvas.drawText("Shield:" +
                    player.getShieldStrength(), 10, screenY - 20, paint);

            canvas.drawText("Speed:" + player.getSpeed() * 60 +
                    " MPS", (screenX /3 ) * 2, screenY - 20, paint);

            ourHolder.unlockCanvasAndPost(canvas);
        }
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

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction() & MotionEvent.ACTION_MASK) {

            case MotionEvent.ACTION_UP:
                player.stopBoosting();
                break;

            case MotionEvent.ACTION_DOWN:
                player.setBoosting();
                break;
        }
        return true;
    }
}
