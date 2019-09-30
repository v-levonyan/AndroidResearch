package com.hsv.vahanl.testtesseract;

import android.graphics.Bitmap;

import com.googlecode.tesseract.android.TessBaseAPI;

public class DigitScanner implements Scanable {
    private TessBaseAPI mTess;

    public DigitScanner(TessBaseAPI mTess) {
        this.mTess = mTess;
    }

    @Override
    public void init(String datapath, String lang) {
        mTess.init(datapath, lang);
    }

    @Override
    public void setImage(Bitmap image) {
        mTess.setImage(image);
    }

    @Override
    public String getText() {
        return mTess.getUTF8Text();
    }
}
