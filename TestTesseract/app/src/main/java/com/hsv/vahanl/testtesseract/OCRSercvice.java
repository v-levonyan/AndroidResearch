package com.hsv.vahanl.testtesseract;

import android.app.IntentService;
import android.app.Service;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;

import com.googlecode.tesseract.android.TessBaseAPI;

public class OCRSercvice extends IntentService {

    private OCREngine ocrEngine;

    public OCRSercvice() {
        super("OCRService");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        ocrEngine = new OCREngine(new DigitScanner(new TessBaseAPI()));
   }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        intent.setAction(MainActivity.OCR_ACTION_KEY);

        String photoPath = intent.getStringExtra(MainActivity.PHOTO_PATH);
        Point size = intent.getParcelableExtra(MainActivity.SIZE_EXTRA);
//        Bitmap bitmap = (Bitmap) intent.getParcelableExtra(MainActivity.IMAGE_EXTRA);
        Bitmap bitmap = PictureUtils.getScaledBitmap(photoPath,size);
        String dataPath = intent.getStringExtra(MainActivity.DATAPATH_EXTRA);
        String lang = "eng";
        ocrEngine.init(dataPath, lang);
        ocrEngine.setImage(bitmap);
        String OCRResult = ocrEngine.getText();
        LocalBroadcastManager.getInstance(getApplicationContext()).sendBroadcast(intent.putExtra("broadcastMessage", OCRResult));
    }
}
