package com.egs.vahanl.pointofinterest;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.egs.vahanl.pointofinterest.POIDbSchema.POITable;

/**
 * Created by vahanl on 7/18/16.
 */
public class POIBaseHelper extends SQLiteOpenHelper {
    private static final int VERSION = 1;
    private static final String DATABASE_NAME = "POI.db";
    public POIBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + POITable.NAME + "(" +
        "_id integer primary key autoincrement, " +
        POITable.Cols.ID + ", " +
        POITable.Cols.TITLE + ", " +
        POITable.Cols.ADDRESS + ", " +
        POITable.Cols.TRANSPORT + ", " +
        POITable.Cols.EMAIL + ", " +
        POITable.Cols.GEOCOORDINATES + ", " +
        POITable.Cols.DESCRIPTION + ", " +
        POITable.Cols.PHONE + ")"
        );

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
