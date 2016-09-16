package com.example.vahanl.customfonts.utilities;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.PointF;
import android.graphics.Typeface;
import android.view.Display;
import android.view.Gravity;

import com.alexvasilkov.gestures.Settings;
import com.alexvasilkov.gestures.views.GestureImageView;

/**
 * Created by vahanl on 9/16/16.
 */

public class ImageUtils {

    private GestureImageView mGestureImageView;
    private Activity mActivity;
    private Paint mPaint;
    private Canvas mCanvas;

    public ImageUtils(GestureImageView gestureImageView,
                      Activity activity) {
        mGestureImageView = gestureImageView;
        mActivity = activity;
        mPaint = new Paint();
        mCanvas = new Canvas();
    }

    public void setProperties() {
        mGestureImageView.getController().getSettings()
                .setMaxZoom(2f)
                .setPanEnabled(true)
                .setZoomEnabled(true)
                .setDoubleTapEnabled(true)
                .setRotationEnabled(true)
                .setRestrictRotation(false)
                .setOverscrollDistance(0f, 0f)
                .setOverzoomFactor(2f)
                .setFillViewport(false)
                .setFitMethod(Settings.Fit.INSIDE)
                .setGravity(Gravity.CENTER)
                .setRestrictBounds(false);
    }

    private PointF getScreenCenter() {
        Display display = mActivity.getWindowManager().getDefaultDisplay();
        final Point size = new Point();
        display.getSize(size);
        int width = size.x;
        int height = size.y;
        float centerX = width/2;
        float centerY=height/2;
        return new PointF(centerX, centerY);
    }

    public void drawtext(String text) {
        mCanvas.drawText(text,
                getScreenCenter().x,
                getScreenCenter().y,
                mPaint);
    }

    public void setColor(int color) {
        mPaint.setColor(color);
    }

    public void setBackColor(int color){
        mCanvas.drawColor(color);
    }

    public void setTextSize(int textSize) {
        mPaint.setTextSize(textSize);
    }

    public void setTextAlign(Paint.Align align) {
        mPaint.setTextAlign(align);
    }

    public void setBackgroundImage(Bitmap bitmap) {
        mCanvas.setBitmap(bitmap);
        mGestureImageView.setImageBitmap(bitmap);
    }

    public void setFont(Typeface typeface) {
        mPaint.setTypeface(typeface);
    }

    public void drawMyText(String text,
                           int width,
                           int height,
                           int color,
                           int textSize) {
        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);

        mGestureImageView.setImageBitmap(bitmap);

        Paint paint = new Paint();
        paint.setColor(color);
        paint.setTextSize(textSize);

        paint.setTextAlign(Paint.Align.CENTER);
        float text_x = getScreenCenter().x;
        float text_y = getScreenCenter().y;
        canvas.drawText(text, text_x, text_y, paint);
    }

}


