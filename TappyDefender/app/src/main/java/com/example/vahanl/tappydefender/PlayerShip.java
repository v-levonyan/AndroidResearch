package com.example.vahanl.tappydefender;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;

/**
 * Created by vahanl on 8/30/16.
 */

public class PlayerShip {
    private Bitmap bitmap;
    int x, y;
    int speed;
    private boolean boosting;

    private final int GRAVITY = -12;

    private int maxY;
    private int minY;

    private final int MIN_SPEED = 1;
    private final int MAX_SPEED = 20;

    private Rect hitBox;

    public Rect getHitBox() {
        return hitBox;
    }

    public PlayerShip(Context context, int screenX, int screenY) {
        this.x = 50;
        this.y = 50;
        speed = 1;
        bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.ship_new);
        boosting = false;
        maxY = screenY - bitmap.getHeight();
        minY = 0;
        hitBox = new Rect(x, y, bitmap.getWidth(), bitmap.getHeight());
    }

    public void update() {
        if (boosting) {
            speed += 2;
        } else {
            speed -= 5;
        }

        if (speed > MAX_SPEED) {
            speed = MAX_SPEED;
        }

        if (speed < MIN_SPEED) {
            speed = MIN_SPEED;
        }

        y -= speed + GRAVITY;

        if (y < minY) {
            y = minY;
        }

        if(y > maxY) {
            y = maxY;
        }
        hitBox.left = x;
        hitBox.top = y;
        hitBox.right = x + bitmap.getWidth();
        hitBox.bottom = y + bitmap.getHeight();
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public int getSpeed() {
        return speed;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setBoosting() {
        boosting = true;
    }

    public void stopBoosting() {
        boosting = false;
    }

    public String getShieldStrength() {
        return "";
    }
}
