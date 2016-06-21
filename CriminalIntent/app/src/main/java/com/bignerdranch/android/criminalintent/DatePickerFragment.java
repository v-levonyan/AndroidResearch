package com.bignerdranch.android.criminalintent;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;

/**
 * Created by vahan on 6/21/16.
 */
public class DatePickerFragment extends DialogFragment {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        View v = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_date, null);
        return new AlertDialog.Builder(getActivity())
                .setView(v)
                .setTitle(R.string.data_picker_title).setPositiveButton(android.R.string.ok, null)
                .create();
    }
}
