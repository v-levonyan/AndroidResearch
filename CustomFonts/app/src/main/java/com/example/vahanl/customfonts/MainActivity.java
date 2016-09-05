package com.example.vahanl.customfonts;

import android.graphics.Typeface;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity
        implements ChooseFontDialog.NoticeDialogListener{

    private EditText editText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editText = (EditText) findViewById(R.id.editText);

        editText.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {

                new ChooseFontDialog().show(getSupportFragmentManager(), "ChooseFontDialog");
                return true;
            }
        });
    }

    @Override
    public void onDialogPositiveClick(Typeface font) {
        editText.setTypeface(font);

    }

    @Override
    public void onDialogNegativeClick(DialogFragment dialog) {

    }
}
