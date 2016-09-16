package com.example.vahanl.customfonts;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import com.alexvasilkov.gestures.views.GestureImageView;
import com.example.vahanl.customfonts.utilities.ImageUtils;

public class MainActivity extends AppCompatActivity
        implements ChooseFontDialog.NoticeDialogListener{

    private EditText mEditText;
    private TextView mTaptoEdit;
    private ImageUtils mImageUtils;
    private GestureImageView mGestureImageView;

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mGestureImageView = (GestureImageView) findViewById(R.id.gesture_image_view);

        mImageUtils = new ImageUtils(mGestureImageView, this);
        mImageUtils.setProperties();
        int width = getWindowManager().getDefaultDisplay().getWidth();
        int height = getWindowManager().getDefaultDisplay().getHeight();
        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        mImageUtils.setBackgroundImage(bitmap);
        mImageUtils.setBackColor(Color.BLUE);
        mImageUtils.setTextSize(64);

        mEditText = (EditText) findViewById(R.id.edit_text);
        mEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    String text = v.getText().toString();
                    mImageUtils.drawtext(text);
                }
                return false;
            }
        });
        mTaptoEdit = (TextView) findViewById(R.id.tap_to_edit);
        mTaptoEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fm = getSupportFragmentManager();
                FragmentTransaction transaction = fm.beginTransaction();
                transaction.add(R.id.fragment_container, new ChooseFontDialog());
                transaction.commit();
            }
        });
    }

    @Override
    public void onFontSelected(Typeface font) {
        mImageUtils.setFont(font);
//        mEditText.setTypeface(font);
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    public void onFrameSelected(int drawableId) {
        Bitmap bm = BitmapFactory.decodeResource(getResources(), drawableId);
        mImageUtils.setBackgroundImage(bm);
//        mEditText.setBackgroundResource(drawableId);
    }

    @Override
    public void onAlignmentSelected(int drawableId) {
        switch (drawableId) {
            case R.drawable.left_align:
//                mEditText.setGravity(Gravity.START);
                mImageUtils.setTextAlign(Paint.Align.LEFT);
                break;
            case R.drawable.center_align:
                mImageUtils.setTextAlign(Paint.Align.CENTER);
//                mEditText.setGravity(Gravity.CENTER);
                break;
            case R.drawable.right_align:
//                mEditText.setGravity(Gravity.END);
                mImageUtils.setTextAlign(Paint.Align.RIGHT);
                break;
        }

    }

    @Override
    public void onDialogPositiveClick(Typeface font, int drawableId) {


    }

    @Override
    public void onDialogNegativeClick(DialogFragment dialog) {

    }
}
