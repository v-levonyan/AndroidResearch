package com.egs.vahanl.pointofinterest;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by vahanl on 7/18/16.
 */
public class POIList {
    private static POIList sPOIList;
    private Context mContext;
    private SQLiteDatabase mDatabase;
    private List<POI> mPOIs;

    public static POIList getInstance(Context context) {
        if (sPOIList == null) {
            sPOIList = new POIList(context);
        }
        return sPOIList;
    }

    private POIList(Context context) {
        mContext = context.getApplicationContext();
        mDatabase = new POIBaseHelper(mContext).getWritableDatabase();
        mPOIs = new ArrayList<>();
    }


    public List<POI> getPOIs() {
        return mPOIs;
    }

    public void setPOIs(List<POI> POIs) {
        mPOIs = POIs;
    }
}
