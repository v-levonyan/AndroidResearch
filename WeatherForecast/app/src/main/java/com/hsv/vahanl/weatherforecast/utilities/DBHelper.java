package com.hsv.vahanl.weatherforecast.utilities;

import com.hsv.vahanl.weatherforecast.data.CityCurrentWeatherInfo;
import com.hsv.vahanl.weatherforecast.data.CityForecast;

import io.realm.Realm;
import io.realm.RealmModel;
import io.realm.RealmObject;

/**
 * Created by vahanl on 8/23/16.
 */
public class DBHelper {

    private static Realm sRealm = Realm.getDefaultInstance();

    public static CityForecast getForecastById(long cityId) {
        CityForecast result = Realm.getDefaultInstance()
                .where(CityForecast.class)
                .equalTo("city.id", cityId)
                .findFirst();
        return result;
    }


    public static CityCurrentWeatherInfo getCurrentWeatherById(long cityId) {
        CityCurrentWeatherInfo result = Realm.getDefaultInstance()
                .where(CityCurrentWeatherInfo.class)
                .equalTo("id", cityId)
                .findFirst();
        return result;
    }

    public static void copyOrUpdate(RealmObject realmObject) {
        sRealm.beginTransaction();
        sRealm.copyToRealmOrUpdate(realmObject);
        sRealm.commitTransaction();
    }

    public static void closeRealm() {
        sRealm.close();
    }
}
