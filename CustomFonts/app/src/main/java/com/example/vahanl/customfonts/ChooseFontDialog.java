package com.example.vahanl.customfonts;

import android.content.Context;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.example.vahanl.customfonts.adapters.BackGroundSpinnerAdapter;
import com.example.vahanl.customfonts.adapters.TextAlignSpinnerAdapter;

import java.io.IOException;

/**
 * Created by vahanl on 9/5/16.
 */

public class ChooseFontDialog extends DialogFragment {

    private static final String TAG = "ChooseFontDialog";


    NoticeDialogListener listener;
    Typeface font;
    int bgDrawableId;
    int textAlignDrawableId;

    public interface NoticeDialogListener {

        public void onFontSelected(Typeface font);
        public void onFrameSelected(int drawableId);
        public void onAlignmentSelected(int drawableId);


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
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.dialog_font, container,false);
        setFontSpinner(view);
        setBackgroundSpinner(view);
        setTextAlignSpinner(view);
        return view;
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
                listener.onFontSelected(font);
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
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                bgDrawableId = (int) parent.getItemAtPosition(position);
                listener.onFrameSelected(bgDrawableId);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void setTextAlignSpinner(View v) {
        Spinner textAlignSpinner = (Spinner) v.findViewById(R.id.text_align_spinner);
        textAlignSpinner.setAdapter(new TextAlignSpinnerAdapter(getActivity()));
        textAlignSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                textAlignDrawableId = (int) parent.getItemAtPosition(position);
                listener.onAlignmentSelected(textAlignDrawableId);
           }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

}
