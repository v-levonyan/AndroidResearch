package com.hsv.vahanl.testtesseract;

import android.graphics.Bitmap;

public interface Scanable {
    void init(String datapath, String lang);
    void setImage(Bitmap image);
    String getText();
}
