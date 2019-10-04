package com.hsv.vahanl.testtesseract;

import android.arch.lifecycle.ViewModelProviders;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Point;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    static final int REQUEST_TAKE_PHOTO = 1;
    public static final String OCR_ACTION_KEY = "ocrActionKey";
    public static final String PHOTO_PATH = "photoPath";


    private Button mOCRButton;
    private Button mTakePhotoButton;
    private ResultReceiver mResultReceiver;

    private ImageView mImageView;
    private PhotoViewModel photoViewModel;
    private OcrTextViewModel ocrTextViewModel;

    public static final String DATAPATH_EXTRA = "datapathExtra";
    public static final String SIZE_EXTRA = "sizeExtra";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        photoViewModel = ViewModelProviders.of(this).get(PhotoViewModel.class);
        photoViewModel.getPhoto().observe(this, photo -> {
            updatePhotoView(photo);
        });


        final Intent captureImage = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        captureImage.putExtra(MediaStore.EXTRA_OUTPUT, photoViewModel.getUri());
        captureImage.addFlags(
                Intent.FLAG_GRANT_READ_URI_PERMISSION);

        mTakePhotoButton = findViewById(R.id.take_photo_btn);
        mTakePhotoButton.setOnClickListener(view -> {
            if (captureImage.resolveActivity(getPackageManager()) != null) {
                startActivityForResult(captureImage, REQUEST_TAKE_PHOTO);
            }
        });

        ocrTextViewModel = ViewModelProviders.of(this).get(OcrTextViewModel.class);

        TextView OCRTextview = findViewById(R.id.ocr_text_view);

        ocrTextViewModel.getOcrText().observe(this, text -> {
            OCRTextview.setText(text.getText());
        });

        mImageView = findViewById(R.id.imageView);
        mOCRButton = findViewById(R.id.button_ocr);
        final Intent ocrIntent = new Intent(this, OCRSercvice.class);

        mOCRButton.setOnClickListener(view -> {
            Point size = new Point();
            getWindowManager().getDefaultDisplay().getSize(size);
            ocrIntent.putExtra(SIZE_EXTRA, size);
            ocrIntent.putExtra(PHOTO_PATH, photoViewModel.getFile().getPath());
            ocrIntent.putExtra(DATAPATH_EXTRA, ocrTextViewModel.getDataPath());
            startService(ocrIntent);
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case REQUEST_TAKE_PHOTO: {
                if (resultCode == RESULT_OK) {
                    Photo photo = new Photo(photoViewModel.getFile());
                    photoViewModel.getPhoto().setValue(photo);
                }
                break;
            }
        }
    }

    private void updatePhotoView(Photo photo) {
        if (photo.getFile() == null || !photo.getFile().exists()) {
            mImageView.setImageDrawable(null);
        } else {
            photo.setImage(PictureUtils.getScaledBitmap(photoViewModel.getFile().getPath(), this));
            try {
                PictureUtils.normalizeBitmap(photo.getImage(), photoViewModel.getPhoto().getValue().getFile().getAbsolutePath());
            } catch (IOException e) {
                e.printStackTrace();
            }
            mImageView.setImageBitmap(photo.getImage());
        }
    }


    private void setReceiver() {
        mResultReceiver = new ResultReceiver();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(OCR_ACTION_KEY);

        LocalBroadcastManager.getInstance(this).registerReceiver(mResultReceiver, intentFilter);
    }

    @Override
    protected void onStart() {
        setReceiver();
        super.onStart();
    }

    @Override
    protected void onStop() {
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mResultReceiver);
        super.onStop();
    }

    private class ResultReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            String OCRResult = intent.getStringExtra("broadcastMessage");
            OcrText ocrText = new OcrText();
            ocrText.setText(OCRResult);
            ocrTextViewModel.getOcrText().setValue(ocrText);
        }
    }

}
