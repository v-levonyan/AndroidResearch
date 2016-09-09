package com.example.vahanl.tunningview;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "editext";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        EditText editText = (EditText) findViewById(R.id.editText2);
        LinearLayout.LayoutParams layoutParams =
                (LinearLayout.LayoutParams) editText.getLayoutParams();

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

//        TextView tv = new TextView(this);
//        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(80, 100);
//        tv.setLayoutParams(layoutParams);
//        tv.setText("testing 1 2 3");
//        tv.setTextColor(Color.BLACK);
//        tv.setBackgroundColor(Color.TRANSPARENT);




        Bitmap testB;

        testB = Bitmap.createBitmap(
                layoutParams.width,
                layoutParams.height,
                Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(testB);
//        tv.layout(0, 0, 80, 100);
        editText.draw(c);

        ImageView iv = (ImageView) findViewById(R.id.menuIcon);
        iv.setLayoutParams(layoutParams);
        iv.setBackgroundColor(Color.GRAY);
        iv.setImageBitmap(testB);
        iv.setMaxHeight(layoutParams.height);
        iv.setMaxWidth(layoutParams.width);

    }

}
