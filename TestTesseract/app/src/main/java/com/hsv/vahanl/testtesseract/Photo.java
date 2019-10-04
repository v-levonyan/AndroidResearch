package com.hsv.vahanl.testtesseract;

import android.graphics.Bitmap;

import java.io.File;

public class Photo {
    private Bitmap image;
    private File file;

    public Photo(Bitmap image, File file) {
        this.image = image;
        this.file = file;
    }

    public Photo(File file) {
        this.file = file;
    }

    public Bitmap getImage() {
        return image;
    }

    public File getFile() {
        return file;
    }

    public void setImage(Bitmap image) {
        this.image = image;
    }

    public void setFile(File file) {
        this.file = file;
    }
}
