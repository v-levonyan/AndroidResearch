package com.example.vahanl.customfonts.utilities;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.PointF;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.view.Display;
import android.view.Gravity;
import android.view.View;

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
                .setRestrictBounds(true);
    }

    private PointF getScreenCenter() {
        Display display = mActivity.getWindowManager().getDefaultDisplay();
        final Point size = new Point();
        display.getSize(size);
        int width = size.x;
        int height = size.y;
        float centerX = width / 2;
        float centerY = height / 2;
        return new PointF(centerX, centerY);
    }

    public void drawtext(String text) {
        mCanvas.drawText(text,
                getScreenCenter().x,
                getScreenCenter().y,
                mPaint);
    }

    public void drawShape(Bitmap bm) {
        mCanvas.drawBitmap(bm, getScreenCenter().x, getScreenCenter().y, mPaint);
    }

    public void setColor(int color) {
        mPaint.setColor(color);
    }

    public void setBackColor(int color) {
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

    public void clearCanvas() {
        mCanvas.drawColor(0, PorterDuff.Mode.CLEAR);
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

    public static Bitmap drawableToBitmap(Drawable drawable) {

        if (drawable instanceof BitmapDrawable) {
            return ((BitmapDrawable) drawable).getBitmap();
        }

        Bitmap bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(),
                drawable.getIntrinsicHeight(),
                Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);

        return bitmap;
    }


    public void loadBitmapFromView(View v) {
        if (v.getMeasuredHeight() <= 0) {
            v.measure(ActionBar.LayoutParams.WRAP_CONTENT, ActionBar.LayoutParams.WRAP_CONTENT);

        }
        Bitmap b = Bitmap.createBitmap(
                v.getMeasuredWidth(),
                v.getMeasuredHeight(),
                Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(b);
        v.layout(0, 0, v.getMeasuredWidth(), v.getMeasuredHeight());
        v.draw(c);
        mCanvas.drawColor(0, PorterDuff.Mode.CLEAR);
        drawShape(b);
    }

    public void setScreenSizeBitmap() {
        int width = DeviceDimensionsHelper.getDisplayWidth(mActivity);
        int height = DeviceDimensionsHelper.getDisplayHeight(mActivity);
        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        setBackgroundImage(bitmap);
    }

}


