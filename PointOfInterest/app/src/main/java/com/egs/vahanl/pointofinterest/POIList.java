package com.egs.vahanl.pointofinterest;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.CursorWrapper;
import android.database.sqlite.SQLiteDatabase;

import com.egs.vahanl.pointofinterest.database.POICursorWrapper;
import com.egs.vahanl.pointofinterest.database.POIDbSchema.POITable;
import com.egs.vahanl.pointofinterest.database.POIBaseHelper;

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


    public List<POI> getPOIs() {
        List<POI> pois = new ArrayList<>();

        POICursorWrapper cursor = queryPois(null, null);

        try {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                pois.add(cursor.getPoi());
                cursor.moveToNext();
            }
        } finally {
            cursor.close();
        }
        return pois;
    }

    public POI getPoi(int id) {
        POICursorWrapper cursor = queryPois(POITable.Cols.ID + " =?",
                new String[] {Integer.toString(id)});
        try {
            if (cursor.getCount() == 0) {
                return null;
            }
            cursor.moveToFirst();
            return cursor.getPoi();
        } finally {
            cursor.close();
        }
    }

    public void addPOI(POI poi) {
        ContentValues values = getContentValues(poi);
        mDatabase.insert(POITable.NAME, null, values);
    }

    public void updatePOI(POI poi) {
        String idString = Integer.toString(poi.getId());
        ContentValues values = getContentValues(poi);
        mDatabase.update(POITable.NAME, values,
                POITable.Cols.ID + " =?",
                new String[]{idString});
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

    private POICursorWrapper queryPois(String whereClause, String[] whereArgs) {
        Cursor cursor = mDatabase.query(
                POITable.NAME,
                null,
                whereClause,
                whereArgs,
                null,
                null,
                null
        );
        return new POICursorWrapper(cursor);
    }
}
