package com.egs.vahanl.pointofinterest;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by vahanl on 7/18/16.
 */
public class POIList {
    private static POIList sPOIList;
    private Context mContext;
    private SQLiteDatabase mDatabase;

    public static POIList getInstance(Context context) {
        if (sPOIList == null) {
            sPOIList = new POIList(context);
        }
        return sPOIList;
    }

    private POIList(Context context) {
        mContext = context.getApplicationContext();
        mDatabase = new POIBaseHelper(mContext).getWritableDatabase();
    }
}
