package com.hsv.vahanl.testtesseract;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.MutableLiveData;
import android.content.res.AssetManager;
import android.support.annotation.NonNull;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class OcrTextViewModel extends AndroidViewModel {

    private final String dataPath;
    private MutableLiveData<OcrText> ocrText;

    private Application application;

    public OcrTextViewModel(@NonNull Application application) {
        super(application);
        this.application = application;
        dataPath = application.getFilesDir() + "/tesseract/";
        checkFile();
    }

    public MutableLiveData<OcrText> getOcrText() {
        if (ocrText == null) {
            ocrText = new MutableLiveData<>();
        }
        return ocrText;
    }

    private void copyFile() {
        try {
            //location we want the file to be at
            String filepath = dataPath + "/tessdata/eng.traineddata";

            //get access to AssetManager
            AssetManager assetManager = application.getAssets();

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

    private void checkFile() {
        File dir = new File(dataPath + "tessdata/");
        //directory does not exist, but we can successfully create it
        if (!dir.exists()&& dir.mkdirs()){
            copyFile();
        }
        //The directory exists, but there is no data file in it
        if(dir.exists()) {
            String datafilepath = dataPath + "/tessdata/eng.traineddata";
            File datafile = new File(datafilepath);
            if (!datafile.exists()) {
                copyFile();
            }
        }
    }

    public String getDataPath() {
        return dataPath;
    }
}
