package com.hsv.vahanl.testtesseract;

import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.googlecode.tesseract.android.TessBaseAPI;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    static final int REQUEST_TAKE_PHOTO = 1;
    private static final String PHOTO_EXTRA = "photo";
    private String mCurrentPhotoPath;


    private Bitmap mImage;
    private TessBaseAPI mTess;
    private String mDatapath = "";
    private Button mOCRButton;
    private Button mTakePhotoButton;

    private ImageView mImageView;

    private File mPhotoFile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mPhotoFile = PictureUtils.createImageFile(this);
        final Intent captureImage = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        Uri uri = Uri.fromFile(mPhotoFile);
        captureImage.putExtra(MediaStore.EXTRA_OUTPUT, uri);

        mTakePhotoButton = (Button) findViewById(R.id.take_photo_btn);
        mTakePhotoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(captureImage, REQUEST_TAKE_PHOTO);
            }
        });


        mDatapath = getFilesDir() + "/tesseract/";

        checkFile(new File(mDatapath + "tessdata/"));

        String lang = "eng";
        mTess = new TessBaseAPI();
        mTess.init(mDatapath, lang);


        mImageView = (ImageView) findViewById(R.id.imageView);
        mOCRButton = (Button) findViewById(R.id.button_ocr);
        mOCRButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mTess.setImage(mImage);
                String OCRResult = mTess.getUTF8Text();
                TextView OCRTextview = (TextView) findViewById(R.id.ocr_text_view);
                OCRTextview.setText(OCRResult);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case REQUEST_TAKE_PHOTO: {
                if (resultCode == RESULT_OK) {
                    updatePhotoView();
                }
                break;
            }
        }
    }

    private void updatePhotoView() {
        if (mPhotoFile == null || !mPhotoFile.exists()) {
            mImageView.setImageDrawable(null);
        } else {
            mImage = PictureUtils.getScaledBitmap(mPhotoFile.getPath(), this);
            try {
                PictureUtils.normalizeBitmap(mImage, mPhotoFile.getAbsolutePath());
            } catch (IOException e) {
                e.printStackTrace();
            }
            mImageView.setImageBitmap(mImage);
        }
    }

    private void copyFile() {
        try {
            //location we want the file to be at
            String filepath = mDatapath + "/tessdata/eng.traineddata";

            //get access to AssetManager
            AssetManager assetManager = getAssets();

            //open byte streams for reading/writing
            InputStream instream = assetManager.open("tessdata/eng.traineddata");
            OutputStream outstream = new FileOutputStream(filepath);

            //copy the file to the location specified by filepath
            byte[] buffer = new byte[1024];
            int read;
            while ((read = instream.read(buffer)) != -1) {
                outstream.write(buffer, 0, read);
            }
            outstream.flush();
            outstream.close();
            instream.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void checkFile(File dir) {
        //directory does not exist, but we can successfully create it
        if (!dir.exists()&& dir.mkdirs()){
            copyFile();
        }
        //The directory exists, but there is no data file in it
        if(dir.exists()) {
            String datafilepath = mDatapath + "/tessdata/eng.traineddata";
            File datafile = new File(datafilepath);
            if (!datafile.exists()) {
                copyFile();
            }
        }
    }




}
