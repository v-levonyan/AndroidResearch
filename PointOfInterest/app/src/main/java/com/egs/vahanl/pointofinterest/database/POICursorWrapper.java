package com.egs.vahanl.pointofinterest.database;

import android.database.Cursor;
import android.database.CursorWrapper;

import com.egs.vahanl.pointofinterest.POI;
import com.egs.vahanl.pointofinterest.database.POIDbSchema.POITable;

/**
 * Created by vahanl on 7/21/16.
 */
public class POICursorWrapper extends CursorWrapper {

    public POICursorWrapper(Cursor cursor) {
        super(cursor);
    }

    public POI getPoi() {
        int id = getInt(getColumnIndex(POITable.Cols.ID));
        String title = getString(getColumnIndex(POITable.Cols.TITLE));
        String address = getString(getColumnIndex(POITable.Cols.ADDRESS));
        String transport = getString(getColumnIndex(POITable.Cols.TRANSPORT));
        String email = getString(getColumnIndex(POITable.Cols.EMAIL));
        String geocoordinates = getString(getColumnIndex(POITable.Cols.GEOCOORDINATES));
        String description = getString(getColumnIndex(POITable.Cols.DESCRIPTION));
        String phone = getString(getColumnIndex(POITable.Cols.PHONE));

        POI poi = new POI(
                id, title,
                address, transport,
                email, geocoordinates,
                description, phone);

        return poi;
    }
}
