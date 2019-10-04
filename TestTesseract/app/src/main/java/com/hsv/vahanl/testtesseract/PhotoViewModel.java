package com.hsv.vahanl.testtesseract;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;

import java.io.File;

import static android.support.v4.content.FileProvider.getUriForFile;

public class PhotoViewModel extends AndroidViewModel {

    private final Uri uri;
    private final File file;
    private MutableLiveData<Photo> photo;

    private Application application;


    public PhotoViewModel(@NonNull Application application) {
        super(application);
        this.application = application;
        file = PictureUtils.createImageFile(application);
        uri = getUriForFile(application, "com.hsv.vahanl.testtesseract.fileprovider", file);
    }

    public MutableLiveData<Photo> getPhoto() {
        if (photo == null) {
            photo = new MutableLiveData<>();
        }
        return photo;
    }

    public Uri getUri() {
        return uri;
    }

    public File getFile() {
        return file;
    }


}
