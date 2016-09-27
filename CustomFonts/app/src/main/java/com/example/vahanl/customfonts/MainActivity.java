package com.example.vahanl.customfonts;

import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import com.alexvasilkov.gestures.Settings;
import com.alexvasilkov.gestures.views.GestureImageView;
import com.example.vahanl.customfonts.utilities.ImageUtils;

public class MainActivity extends AppCompatActivity
        implements ChooseFontDialog.NoticeDialogListener {

    private EditText mEditText;
    private TextView mTaptoEdit;
    private ImageUtils mImageUtils;
    private GestureImageView mGestureImageView;
    private TextView mBubbleTextView;

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        mBubbleTextView = (TextView) findViewById(R.id.singleMessage);
        mEditText = (EditText) findViewById(R.id.edit_text);
        mTaptoEdit = (TextView) findViewById(R.id.tap_to_edit);
        mGestureImageView = (GestureImageView) findViewById(R.id.gesture_image_view);

        mImageUtils = new ImageUtils(mGestureImageView, this);
        mImageUtils.setProperties();
        mImageUtils.setScreenSizeBitmap();

        setListeners();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_dialog, menu);
        return true;
    }


    @Override
    public void onFontSelected(Typeface font) {
        mBubbleTextView.setTypeface(font);
        mImageUtils.loadBitmapFromView(mBubbleTextView);
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    public void onFrameSelected(int drawableId) {
        mBubbleTextView.setBackgroundResource(drawableId);
        mImageUtils.loadBitmapFromView(mBubbleTextView);
    }

    @Override
    public void onAlignmentSelected(int drawableId) {
        switch (drawableId) {
            case R.drawable.left_align:
                mBubbleTextView.setGravity(Gravity.LEFT);
                break;
            case R.drawable.center_align:
                mBubbleTextView.setGravity(Gravity.CENTER);
                break;
            case R.drawable.right_align:
                mBubbleTextView.setGravity(Gravity.RIGHT);
                break;
            default:
                mBubbleTextView.setGravity(Gravity.NO_GRAVITY);
        }
        mImageUtils.loadBitmapFromView(mBubbleTextView);

    }

    @Override
    public void onColorSelected(int color) {
        mBubbleTextView.setTextColor(color);
        mImageUtils.loadBitmapFromView(mBubbleTextView);
    }

    @Override
    public void onDialogPositiveClick() {
    }

    @Override
    public void onDialogNegativeClick(DialogFragment dialog) {

    }


    private void setListeners() {
        mEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    String text = v.getText().toString();
                    mBubbleTextView.setText(text);
                    mImageUtils.loadBitmapFromView(mBubbleTextView);
                }
                return false;
            }
        });

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

}
