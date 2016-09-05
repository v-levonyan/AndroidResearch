package com.example.vahanl.customfonts;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import java.io.IOException;
import java.util.Arrays;

/**
 * Created by vahanl on 9/5/16.
 */

public class ChooseFontDialog extends DialogFragment {

    private static final String TAG = "ChooseFontDialog";

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View v = inflater.inflate(R.layout.dialog_font, null);

        Spinner spinner = (Spinner) v.findViewById(R.id.fonts_spinner);


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

        spinner.setAdapter(adapter);

        builder.setView(v)
                .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //handle
                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //handle
                    }
                });


        return builder.create();
    }
}
