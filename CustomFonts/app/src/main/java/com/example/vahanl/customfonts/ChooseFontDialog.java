package com.example.vahanl.customfonts;

import android.annotation.TargetApi;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.vahanl.customfonts.adapters.BackGroundSpinnerAdapter;

import java.io.IOException;

/**
 * Created by vahanl on 9/5/16.
 */

public class ChooseFontDialog extends DialogFragment {

    private static final String TAG = "ChooseFontDialog";

    private TextView abcTextView;

    NoticeDialogListener listener;
    Typeface font;
    int drawableId;

    public interface NoticeDialogListener {
        public void onDialogPositiveClick(Typeface font, int drawablId);
        public void onDialogNegativeClick(DialogFragment dialog);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            listener = (NoticeDialogListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() +
            "must implement NoticeDialogListener");
        }
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View v = inflater.inflate(R.layout.dialog_font, null);

        abcTextView = (TextView) v.findViewById(R.id.abc_textView);
        setFontSpinner(v);
        setBackgroundSpinner(v);

        builder.setView(v)
                .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        listener.onDialogPositiveClick(font, drawableId);
                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        listener.onDialogNegativeClick(ChooseFontDialog.this);
                    }
                });


        return builder.create();
    }

    public TextView getAbcTextView() {
        return abcTextView;
    }

    private void setFontSpinner(View v) {
        Spinner fontSpinner = (Spinner) v.findViewById(R.id.fonts_spinner);

        String[] fonts = new String[0];
        try {
            fonts = getActivity().getAssets().list("fonts");
        } catch (IOException e) {
            e.printStackTrace();
        }
        ArrayAdapter<CharSequence> adapter =
                new ArrayAdapter<CharSequence>(getActivity(),
                        android.R.layout.simple_spinner_item,
                        fonts);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        fontSpinner.setAdapter(adapter);
        fontSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String fontName = (String) parent.getItemAtPosition(position);
                String assetPath = "fonts/" + fontName;
                font = Typeface.createFromAsset(getActivity().getAssets(), assetPath);
                abcTextView.setTypeface(font);
                Log.i(TAG, "fontname: " + fontName);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void setBackgroundSpinner(View v) {
        Spinner backgroundSpinner = (Spinner) v.findViewById(R.id.background_spinner);
        backgroundSpinner.setAdapter(new BackGroundSpinnerAdapter(getActivity()));
        backgroundSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @TargetApi(Build.VERSION_CODES.LOLLIPOP)
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                drawableId = (int) parent.getItemAtPosition(position);
                abcTextView.setBackgroundResource(drawableId);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

}
