package com.egs.vahanl.pointofinterest;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.IntegerRes;

import com.egs.vahanl.pointofinterest.POIDbSchema.POITable;
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

    public void addPOI(POI poi) {
        ContentValues values = getContentValues(poi);
        mDatabase.insert(POITable.NAME, null, values);
    }

    public POI getPoiById(int id) {
        POI foundPoi = new POI();
        for (POI poi: mPOIs) {
            if (poi.getId() == id) {
                foundPoi = poi;
            }
        }
        return foundPoi;
    }

    public void updatePOI(POI poi) {
        String idString = Integer.toString(poi.getId());
        ContentValues values = getContentValues(poi);
        mDatabase.update(POITable.NAME, values,
                POITable.Cols.ID + " =?",
                new String[] {idString});
    }

    private static ContentValues getContentValues(POI poi) {
        ContentValues values = new ContentValues();
        values.put(POITable.Cols.ID, poi.getId());
        values.put(POITable.Cols.TITLE, poi.getTitle());
        values.put(POITable.Cols.ADDRESS, poi.getAddress());
        values.put(POITable.Cols.TRANSPORT, poi.getTransport());
        values.put(POITable.Cols.EMAIL, poi.getEmail());
        values.put(POITable.Cols.GEOCOORDINATES, poi.getGeocoordinates());
        values.put(POITable.Cols.DESCRIPTION, poi.getDescription());
        values.put(POITable.Cols.PHONE, poi.getPhone());

        return values;
    }
}
