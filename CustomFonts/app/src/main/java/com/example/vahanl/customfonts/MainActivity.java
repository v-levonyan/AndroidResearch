package com.example.vahanl.customfonts;

import android.graphics.Paint;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

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

        mGestureImageView = (GestureImageView) findViewById(R.id.gesture_image_view);
        mBubbleTextView = (TextView) findViewById(R.id.singleMessage);


        mImageUtils = new ImageUtils(mGestureImageView, this);
        mImageUtils.setProperties();


        mImageUtils.setScreenSizeBitmap();

        mImageUtils.loadBitmapFromView(mBubbleTextView);



        mEditText = (EditText) findViewById(R.id.edit_text);
        mTaptoEdit = (TextView) findViewById(R.id.tap_to_edit);

        setListeners();
    }


    @Override
    public void onFontSelected(Typeface font) {
//        mImageUtils.setFont(font);
//        mEditText.setTypeface(font);
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    public void onFrameSelected(int drawableId) {
//        Drawable drawable = getResources().getDrawable(drawableId);
//        Bitmap bitmap = ImageUtils.drawableToBitmap(drawable);
//        mImageUtils.drawShape(bitmap);

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

    private void setListeners() {
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

        mTaptoEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mGestureImageView.getController().getSettings().disableGestures();
                FragmentManager fm = getSupportFragmentManager();
                FragmentTransaction transaction = fm.beginTransaction();
                transaction.add(R.id.fragment_container, new ChooseFontDialog());
                transaction.commit();
                getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                        WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
            }
        });
    }
}
