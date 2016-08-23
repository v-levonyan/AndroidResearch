package com.hsv.vahanl.weatherforecast.utilities;

import com.hsv.vahanl.weatherforecast.data.CityForecast;

import io.realm.Realm;
import io.realm.RealmModel;
import io.realm.RealmObject;

/**
 * Created by vahanl on 8/23/16.
 */
public class DBHelper {

    public static CityForecast getForecastById(long cityId) {
        CityForecast result = Realm.getDefaultInstance()
                .where(CityForecast.class)
                .equalTo("city.id", cityId)
                .findFirst();
        return result;
    }
}
