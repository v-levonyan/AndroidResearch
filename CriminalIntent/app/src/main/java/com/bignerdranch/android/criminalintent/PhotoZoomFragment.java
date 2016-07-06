package com.bignerdranch.android.criminalintent;

import android.app.AlertDialog;
import android.app.Dialog;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

public class PhotoZoomFragment extends DialogFragment {

    public static final String EXTRA_PHOTO = "com.bignerdranch.android.criminalintent.photo";

    private static final String ARG_PHOTO = "photo";

    public static PhotoZoomFragment newInstance(Bitmap bitmap) {
        Bundle args = new Bundle();
        args.putParcelable(ARG_PHOTO, bitmap);
        PhotoZoomFragment fragment = new PhotoZoomFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final Bitmap bitmap = getArguments().getParcelable(ARG_PHOTO);

        View v = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_photo, null);
        ImageView imageView = (ImageView) v.findViewById(R.id.zoomed_photo);
        imageView.setImageBitmap(bitmap);

        return new AlertDialog.Builder(getActivity()).setView(v).create();
    }
}
