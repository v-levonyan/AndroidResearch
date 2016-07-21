package com.egs.vahanl.pointofinterest.database;

/**
 * Created by vahanl on 7/18/16.
 */
public class POIDbSchema {
    public static final class POITable {
        public static final String NAME = "POIS";

        public static final class Cols {
            public static final String ID = "id";
            public static final String TITLE = "title";
            public static final String ADDRESS = "address";
            public static final String TRANSPORT = "transport";
            public static final String EMAIL = "email";
            public static final String GEOCOORDINATES = "geocoordinates";
            public static final String DESCRIPTION = "description";
            public static final String PHONE = "phone";
        }
    }
}
