package com.example.vahanl.tunningview;

import android.content.ClipData;
import android.content.ClipDescription;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.PointF;
import android.graphics.Rect;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Base64;
import android.util.Log;
import android.view.Display;
import android.view.DragEvent;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alexvasilkov.gestures.GestureController;
import com.alexvasilkov.gestures.Settings;
import com.alexvasilkov.gestures.views.GestureImageView;

import java.io.UnsupportedEncodingException;

import static android.R.attr.bitmap;
import static android.R.attr.width;


public class MainActivity extends AppCompatActivity {

    private static final String TAG = "editext";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final GestureImageView gestureView = (GestureImageView) findViewById(R.id.imageView);


        EditText editText = (EditText) findViewById(R.id.editText2);

        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                Log.i(TAG, "beforeTextChanged: " +
                        s.toString() +
                        " start: " + start +
                        " after: " + after +
                        " count: " + count);
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Log.i(TAG, "onTextChanged: " +
                        s.toString() +
                        " start: " + start +
                        " before: " + before +
                        " count: " + count);
            }


            @Override
            public void afterTextChanged(Editable s) {
                Log.i(TAG, "afterTextChanged: " + s.toString());

            }
        });


        gestureView.setBackgroundColor(Color.BLUE);
        gestureView.
                setImageBitmap(null);

        int width = getWindowManager().getDefaultDisplay().getWidth();
        int height = getWindowManager().getDefaultDisplay().getHeight();

        gestureView.getController().getSettings()
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


        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);

        gestureView.setImageBitmap(bitmap);

        Paint paint = new Paint();
        paint.setColor(Color.RED);
        paint.setTextSize(64);
        paint.setTextAlign(Paint.Align.CENTER);
        float text_x = getScreenCenter().x;
        float text_y = getScreenCenter().y;
        canvas.drawText("Dont worry be happy", text_x, text_y, paint);


    }

    private PointF getScreenCenter() {
        Display display = getWindowManager().getDefaultDisplay();
        final Point size = new Point();
        display.getSize(size);
        int width = size.x;
        int height = size.y;
        float centerX = width/2;
        float centerY=height/2;
        return new PointF(centerX, centerY);
    }





}
