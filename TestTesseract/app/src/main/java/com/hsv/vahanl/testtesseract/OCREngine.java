package com.hsv.vahanl.testtesseract;

import android.graphics.Bitmap;

import com.googlecode.tesseract.android.TessBaseAPI;

public class OCREngine {
    private Scanable scanable;

    public OCREngine(Scanable scanable) {
        this.scanable = scanable;
    }

    public void init(String datapath, String lang) {
        scanable.init(datapath, lang);

    }
    public void setImage(Bitmap image) {
        scanable.setImage(image);
    }

    public String getText() {
        return scanable.getText();
    }
}

